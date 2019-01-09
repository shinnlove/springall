/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.jsapipay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinnlove.springall.dao.mch.MchWXPayConfigRepository;
import com.shinnlove.springall.dao.model.WXPayRecord;
import com.shinnlove.springall.dao.wxpay.WXPayRecordRepository;
import com.shinnlove.springall.service.wxpay.request.WXPayRequestService;
import com.shinnlove.springall.util.code.SystemResultCode;
import com.shinnlove.springall.util.exception.SystemException;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.order.UnifiedOrderClient;

/**
 * 平台处理微信内H5网页JS唤起支付。
 *
 * 这个类就是支持之前的：
 * TODO：为微信支付请求服务再包一层业务语义的服务，组装其原子能力（如JSAPI），然后吃掉所有错误。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayJSAPIService.java, v 0.1 2018-12-21 下午1:20 shinnlove.jinsheng Exp $$
 */
@Service
public class WXPayJSAPIService {

    /** 平台支付层的id常量 */
    private static final String      PAY_ID = "pay_id";

    /** 微信支付记录仓储 */
    @Autowired
    private WXPayRecordRepository    wxPayRecordRepository;

    /** 微信配置仓储 */
    @Autowired
    private MchWXPayConfigRepository mchWXPayConfigRepository;

    /** 微信支付服务SDK */
    @Autowired
    private WXPayRequestService      wxPayRequestService;

    /**
     * jsapi支付业务。
     *
     * jsapi调用统一支付下单返回prepayId，连同返回平台待支付id（支付有不同类型，如微信支付）可以透出给订单系统做支付小票凭证。
     * 然后商城系统直接唤起微信支付、或跳转到微信保护地址唤起微信支付。
     *
     * 商城系统调用订单系统下单后、拿着orderId向支付系统申请支付。
     * 给支付系统的入参是：orderId、merchantId、customerId、payType。
     * 其中orderId和merchantId告诉支付系统订单来自于哪个商户（支付宝支付merchantId是备付金账户id？）。
     * customerId在微信支付下等同于openId。
     * payType=1代表是支付宝支付、payType=2代表是微信支付（这里进入了微信支付流程，因此本函数入参不需要payType）。
     *
     * 支付请求进入到支付系统后，首先会根据orderId查询待支付记录payId（同一orderId只能有一条payId，待支付或者已支付）。
     * 若已支付则直接返回订单已支付、不可重新支付；若未支付则为待支付记录调用微信接口，将微信返回数据透出给调用方。
     *
     * 所以jsapi就是生成一条待支付记录（若无）、并且调用微信接口返回prepayId信息返回的两个步骤。
     *
     * @param orderId       订单id
     * @param merchantId    商户id
     * @param payParams     支付参数
     * @return
     */
    public Map<String, String> jsapiPay(final long orderId, final long merchantId,
                                        final Map<String, String> payParams) {
        // 查询或生成待支付记录
        WXPayRecord payRecord = wxPayRecordRepository.queryPayRecordByOrderId(orderId);
        if (payRecord != null && payRecord.getIsPaid() > 0) {
            throw new SystemException("当前订单已经完成微信支付，无需重复支付");
        }
        if (payRecord == null) {
            // 无记录
            payRecord = new WXPayRecord(orderId, merchantId);
            long insert = wxPayRecordRepository.insertRecord(payRecord);
            if (insert <= 0) {
                throw new SystemException("创建支付信息失败，请稍后再试");
            }
        }
        final WXPayRecord pay = payRecord;

        WXPayMchConfig mchConfig = mchWXPayConfigRepository.queryWXPayConfigByMchId(merchantId);

        UnifiedOrderClient client = new UnifiedOrderClient(mchConfig);

        // 支付结果
        Map<String, String> result = new HashMap<>();
        result.put(PAY_ID, String.valueOf(pay.getId()));

        // TODO：result_code不是SUCCESS时需要映射一把错误码原因：@see https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
        try {
            result = wxPayRequestService
                .doPayRequest(client, payParams,
                    (resp) -> {
                        // 根据微信应答处理业务
                    if (!resp.containsKey(WXPayConstants.RESULT_CODE)) {
                        throw new SystemException("微信支付业务返回码不存在");
                    }

                    if (!WXPayConstants.SUCCESS.equalsIgnoreCase(resp
                        .get(WXPayConstants.RESULT_CODE))) {
                        String failMsg = resp.get(WXPayConstants.RESULT_CODE);
                        String des = resp.get(WXPayConstants.ERR_CODE_DES);
                        throw new SystemException("微信支付业务返回码FAIL" + failMsg + des);
                    }

                    if (!resp.containsKey(WXPayConstants.PREPAY_ID)) {
                        throw new SystemException("微信支付业务需返回统一下单prepay_id，当前不存在");
                    }
                    if ("".equals(resp.get(WXPayConstants.PREPAY_ID))) {
                        throw new SystemException("微信支付业务需返回统一下单prepay_id为空");
                    }

                    String tradeType = resp.get(WXPayConstants.TRADE_TYPE);
                    if (!WXPayConstants.JSAPI.equalsIgnoreCase(tradeType)) {
                        throw new SystemException("微信支付返回支付类型错误，JSAPI支付交易类型返回必须是JSAPI");
                    }

                    String prepayId = resp.get(WXPayConstants.PREPAY_ID);
                    String nonceStr = resp.get(WXPayConstants.NONCE_STR);
                    String sign = resp.get(WXPayConstants.SIGN);
                    String notifyURL = resp.get(WXPayConstants.NOTIFY_URL);

                    pay.setPrepayId(prepayId);
                    pay.setNonceStr(nonceStr);
                    pay.setSign(sign);
                    pay.setNotifyURL(notifyURL);

                    int update = wxPayRecordRepository.updateWXPayRecord(pay);
                    if (update <= 0) {
                        throw new SystemException("保存微信支付结果出错");
                    }

                    System.out.println(resp);

                    return null;
                });
        } catch (SystemException e) {
            // 打印日志、做错误的抛出或者结果集转换
            throw e;
        } catch (Exception e) {
            // 打印日志、
            throw new SystemException(SystemResultCode.SYSTEM_ERROR, e);
        }

        return result;
    }

}