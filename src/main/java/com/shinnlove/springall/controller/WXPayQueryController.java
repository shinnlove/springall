/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.shinnlove.springall.service.wxpay.order.WXPayQueryOrderService;
import com.shinnlove.springall.util.tools.ResponseUtil;

/**
 * 微信支付查询控制器。
 *
 * 这个接口是给订单系统的掉单模块（掉单系统）查询支付状态用的。
 *
 * 订单控制器应该包含两个服务：
 * 1、订单支付表查询服务；
 * 2、微信支付查询订单服务（或支付宝支付订单查询服务）。
 *
 * 后续全部开发完可以重新调整下结构。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayController.java, v 0.1 2018-12-20 09:56 shinnlove.jinsheng Exp $$
 */
@RestController
@RequestMapping(value = "/wxpayquery")
public class WXPayQueryController {

    /** 微信支付订单查询服务 */
    @Autowired
    private WXPayQueryOrderService wxPayQueryOrderService;

    /**
     * 订单查询请求。
     *
     * TODO：接口定义领域模型、根据请求入参校验字段完整性、组装成Map类型入参，再调用支付服务：params -> payParams
     *
     * @param orderId 
     * @return
     */
    @RequestMapping(value = "/orderquery", method = RequestMethod.POST)
    public JSONObject orderQuery(String orderId) {

        Map<String, String> result = wxPayQueryOrderService.queryWXPayOrder(Long.valueOf(orderId));

        return ResponseUtil.success(result);
    }

}