/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.shinnlove.springall.service.wxpay.jsapipay.WXPayJSAPIService;
import com.shinnlove.springall.util.tools.ResponseUtil;

/**
 * 微信支付控制器（JSAPI、WAP等）。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayController.java, v 0.1 2018-12-21 20:32 shinnlove.jinsheng Exp $$
 */
@RestController
public class WXPayController {

    /** jsapi支付服务 */
    @Autowired
    private WXPayJSAPIService wxPayJSAPIService;

    /**
     * 微信jsapi支付。
     *
     * @param orderId       订单id
     * @param merchantId    商户id
     * @param payParam      支付参数
     * @param payType       支付类型
     */
    @RequestMapping(value = "/wxpay/jsapi", method = RequestMethod.POST)
    public JSONObject wxJsapiPay(String orderId, String merchantId,
                                 @RequestParam(name = "payParam") String payParam, String payType) {
        // 先根据payType判断，这里默认直接进入微信支付
        int type = Integer.valueOf(payType);

        // 再将json的payParam解码成Map，校验参数，这里直接mock
        Map<String, String> params = new HashMap<>();

        long oId = Long.valueOf(orderId);
        long mId = Long.valueOf(merchantId);

        // 做jsapi支付，理论上返回2个key，一个是平台payId，一个是prepayId
        Map<String, String> result = wxPayJSAPIService.jsapiPay(oId, mId, params);

        return ResponseUtil.success(result);
    }

}