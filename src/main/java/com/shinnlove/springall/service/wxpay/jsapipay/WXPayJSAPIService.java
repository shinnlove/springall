/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.jsapipay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.shinnlove.springall.dao.mch.MchWXPayConfigRepository;
import com.shinnlove.springall.dao.model.WXPayRecord;
import com.shinnlove.springall.dao.wxpay.WXPayRecordRepository;
import com.shinnlove.springall.service.wxpay.request.WXPayRequestService;
import com.shinnlove.springall.service.wxpay.util.WXPayAssert;
import com.shinnlove.springall.util.code.SystemResultCode;
import com.shinnlove.springall.util.exception.SystemException;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.order.UnifiedOrderClient;

/**
 * 平台处理微信内H5网页JS唤起支付。
 *
 * 这个类就是支持之前的：WeActJSAPIPay。
 * 
 * TODO：为微信支付请求服务再包一层业务语义的服务，组装其原子能力（如JSAPI），然后吃掉所有错误。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayJSAPIService.java, v 0.1 2018-12-21 下午1:20 shinnlove.jinsheng Exp $$
 */
@Service
public class WXPayJSAPIService {

    /** 平台支付层的id常量 */
    private static final String      PAY_ID = "pay_id";

    /** tx-template */
    @Autowired
    private TransactionTemplate      transactionTemplate;

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
        // ready?
        WXPayMchConfig mchConfig = mchWXPayConfigRepository.queryWXPayConfigByMchId(merchantId);
        WXPayAssert.confAvailable(mchConfig);

        // pessimistic with innodb line-lock
        return transactionTemplate.execute(status -> {

            // which one?
            WXPayRecord payRecord = wxPayRecordRepository.getWXPayRecordByOrderIdForUpdate(orderId);
            WXPayAssert.notPaid(payRecord);

            // 首付
            if (payRecord == null) {
                payRecord = buildWXPayRecord(orderId, merchantId, payParams);
                wxPayRecordRepository.insertRecord(payRecord);
            }

            // 平台记录与支付结果
            final WXPayRecord pay = payRecord;
            Map<String, String> result = new HashMap<>();

            UnifiedOrderClient client = new UnifiedOrderClient(mchConfig);

            try {
                result = wxPayRequestService.doPayRequest(client, payParams, (resp) -> {

                    WXPayAssert.checkPayResp(resp);

                    pay.setPrepayId(resp.get(WXPayConstants.PREPAY_ID));
                    pay.setNonceStr(resp.get(WXPayConstants.NONCE_STR));
                    pay.setSign(resp.get(WXPayConstants.SIGN));
                    pay.setNotifyURL(resp.get(WXPayConstants.NOTIFY_URL));

                    wxPayRecordRepository.updateWXPayRecord(pay);

                    return null;
                });
            } catch (SystemException e) {
                // 自己人
                throw e;
            } catch (Exception e) {
                // 超时、dns解析、签名验签、稀奇古怪错误等统统穿上马甲扔给切面
                throw new SystemException(SystemResultCode.WXPAY_UNIFIED_ORDER_ERROR, e);
            }

            // 盖章
            result.put(PAY_ID, String.valueOf(pay.getId()));

            return result;
        });
    }

    /**
     * 新创建一条微信支付记录。
     *
     * @param orderId    
     * @param merchantId
     * @param payParams
     * @return
     */
    private WXPayRecord buildWXPayRecord(long orderId, long merchantId,
                                         Map<String, String> payParams) {
        WXPayRecord record = new WXPayRecord(orderId, merchantId);
        // 做个塞字段示例
        record.setOpenId(payParams.get(WXPayConstants.OPENID));
        return record;
    }

}