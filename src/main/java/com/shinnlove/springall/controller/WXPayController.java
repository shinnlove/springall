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
import com.shinnlove.springall.service.wxpay.request.WXPayRequestService;
import com.shinnlove.springall.util.tools.ResponseUtil;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.order.QueryOrderClient;

/**
 * 微信支付请求受理控制器。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayController.java, v 0.1 2018-12-20 09:56 shinnlove.jinsheng Exp $$
 */
@RestController
@RequestMapping(value = "/wxpay")
public class WXPayController {

    /** 微信支付服务 */
    @Autowired
    private WXPayRequestService wxPayRequestService;

    /**
     * 订单查询请求。
     *
     * TODO：接口定义领域模型、根据请求入参校验字段完整性、组装成Map类型入参，再调用支付服务：params -> payParams
     * TODO：为微信支付请求服务再包一层业务语义的服务，组装其原子能力（如JSAPI），然后吃掉所有错误。
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/orderquery", method = RequestMethod.POST)
    public JSONObject orderQuery(@RequestParam("payParams") String params) throws Exception {

        // 模拟支付入参
        Map<String, String> payParams = new HashMap<>();
        // 商户配置
        WXPayMchConfig mchConfig = new WXPayMchConfig();
        // 订单查询
        QueryOrderClient client = new QueryOrderClient(mchConfig);

        Map<String, String> result = wxPayRequestService.doPayRequest(client, payParams,
            (resp) -> System.out.println(resp));

        return ResponseUtil.success(result);
    }

}