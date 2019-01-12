/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shinnlove.springall.service.wxpay.jsapipay.WXPayJSAPIService;
import com.shinnlove.springall.service.wxpay.micropay.WXPayMicroService;
import com.shinnlove.springall.util.consts.SystemPayConsts;
import com.shinnlove.springall.util.tools.ResponseUtil;

/**
 * 微信支付控制器（JSAPI、WAP等）。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayController.java, v 0.1 2018-12-21 20:32 shinnlove.jinsheng Exp $$
 */
@RestController
@RequestMapping(value = "/wxpay")
public class WXPayController {

    /** jsapi支付服务 */
    @Autowired
    private WXPayJSAPIService wxPayJSAPIService;

    /** 刷卡支付 */
    @Autowired
    private WXPayMicroService wxPayMicroService;

    /**
     * 微信jsapi支付。
     *
     * @param requestData   json格式数据，包含：订单id、商户id、支付参数、支付类型
     * @return
     */
    @RequestMapping(value = "/jsapi", method = RequestMethod.POST)
    public JSONObject wxJsapiPay(@RequestBody String requestData) {

        // 基础参数
        JSONObject data = JSON.parseObject(requestData);
        String orderId = (String) data.get("order_id");
        String merchantId = (String) data.get("merchant_id");
        String payParam = (String) data.get("pay_param");
        String payType = (String) data.get("pay_type");

        // 支付入参，一并塞入merchantId做notifyURL的拼接
        HashMap<String, String> params = JSON.parseObject(payParam, HashMap.class);
        params.put(SystemPayConsts.MERCHANT_ID, merchantId);

        long oId = Long.valueOf(orderId);
        long mId = Long.valueOf(merchantId);
        // 先根据payType判断，这里默认直接进入微信支付
        int type = Integer.valueOf(payType);

        // 做jsapi支付，理论上返回2个key，一个是平台payId，一个是prepayId
        Map<String, String> result = wxPayJSAPIService.jsapiPay(oId, mId, params);

        return ResponseUtil.success(result);
    }

    /**
     * 微信刷卡支付。
     *
     * TODO：请求出入处理与jsapi相似，是否与jsapi支付合并？只是在场景上区分不同。
     *
     * 特别注意：刷卡支付如果后续发起撤订请求，应该由订单系统先发起关单操作，而后投递kafka消息给支付系统去关单。
     * 支付系统尽量在事务型消息的保证下去尽力把应该撤销的刷卡支付撤销（如果成功而撤销应该让钱退回用户账户）。
     *
     * @param requestData
     * @return
     */
    @RequestMapping(value = "/micropay", method = RequestMethod.POST)
    public JSONObject wxMicroPay(@RequestBody String requestData) {

        // 基础参数
        JSONObject data = JSON.parseObject(requestData);
        String orderId = (String) data.get("order_id");
        String merchantId = (String) data.get("merchant_id");
        String payParam = (String) data.get("pay_param");
        String payType = (String) data.get("pay_type");

        // 支付入参，一并塞入merchantId做notifyURL的拼接
        // attach-订单描述、body-刷卡支付描述、spbill_create_ip-刷卡支付设备ip、total_fee-刷卡总费用
        HashMap<String, String> params = JSON.parseObject(payParam, HashMap.class);
        params.put(SystemPayConsts.MERCHANT_ID, merchantId);

        long oId = Long.valueOf(orderId);
        long mId = Long.valueOf(merchantId);
        // 先根据payType判断，这里默认直接进入微信支付
        int type = Integer.valueOf(payType);

        // hold单做刷卡，后续这个参数透给上游
        Map<String, String> result = wxPayMicroService.microPay(oId, mId, params, false);

        return ResponseUtil.success(result);
    }

}