/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shinnlove.springall.service.wxpay.response.WXPayResponseService;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.service.response.scan.WXNativePayClient;

/**
 * 微信支付原生扫码控制器。
 *
 * 这个controller是需要直接把XML数据响应给微信的，因此不能作为Restful接口。
 * 本系统作为微信支付、支付宝支付的整合支付，如果后续还要拆分网关出来，再做成Restful接口给网关系统。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayNativeController.java, v 0.1 2018-12-21 下午1:11 shinnlove.jinsheng Exp $$
 */
@Controller
public class WXPayNativeController {

    /** 微信响应服务 */
    @Autowired
    private WXPayResponseService wxPayResponseService;

    /**
     * 处理原生扫码支付请求。
     *
     * @param xmlStr
     * @return
     */
    @RequestMapping(value = "/wxpay/notify", method = { RequestMethod.GET, RequestMethod.POST })
    public String nativeScan(@RequestBody String xmlStr) throws Exception {

        // 商户配置
        WXPayMchConfig mchConfig = new WXPayMchConfig();

        WXNativePayClient client = new WXNativePayClient(mchConfig);

        String result = wxPayResponseService.doCallbackResponse(client, xmlStr, (resp) -> {
            // 处理业务
            System.out.println(resp);
            // 平台业务应答结果
            return new HashMap<>();
        });

        return result;
    }

}