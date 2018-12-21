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
 * @author shinnlove.jinsheng
 * @version $Id: WXPayJSAPIService.java, v 0.1 2018-12-21 下午1:20 shinnlove.jinsheng Exp $$
 */
@Service
public class WXPayJSAPIService {

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
     * jsapi支付第一步骤：为一笔jsapi支付生成平台待支付单子。
     *
     * @param merchantId    商户id
     * @param orderId       待支付的订单id
     * @return
     */
    public String jsapiPrepare(String merchantId, String orderId) {

    }

    /**
     * jsapi支付第二步骤：为一笔jsapi调用微信支付统一下单，生成prepayId返回。
     *
     * jsapi调用统一支付下单返回prepayId，而后跳转到微信保护地址弹出微信支付。
     *
     * @param merchantId    商户id
     * @param wxPayId       微信支付id
     * @param payParams     支付参数
     * @return
     */
    public Map<String, String> jsapiPay(final String merchantId, final String wxPayId,
                                        final Map<String, String> payParams) {

        // 商户配置
        WXPayMchConfig mchConfig = new WXPayMchConfig();

        UnifiedOrderClient client = new UnifiedOrderClient(mchConfig);

        Map<String, String> result = new HashMap<>();

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
                        throw new SystemException("微信支付业务返回码FAIL" + failMsg);
                    }

                    if (!resp.containsKey(WXPayConstants.PREPAY_ID)) {
                        throw new SystemException("微信支付业务需返回统一下单prepay_id，当前不存在");
                    }
                    if ("".equals(resp.get(WXPayConstants.PREPAY_ID))) {
                        throw new SystemException("微信支付业务需返回统一下单prepay_id为空");
                    }

                    String prepayId = resp.get(WXPayConstants.PREPAY_ID);
                    String tradeType = resp.get(WXPayConstants.TRADE_TYPE);

                    System.out.println(resp);
                    return null;
                });
        } catch (SystemException e) {

        } catch (Exception e) {
            throw new SystemException(SystemResultCode.SYSTEM_ERROR, e);
        }

        return result;
    }

}