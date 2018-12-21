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
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinnlove.springall.service.wxpay.response.WXPayResponseService;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.service.response.pay.WXPayNotifyClient;

/**
 * 微信支付处理通知控制器。
 *
 * 这个controller是需要直接把XML数据响应给微信的，因此不能作为Restful接口。
 * 本系统作为微信支付、支付宝支付的整合支付，如果后续还要拆分网关出来，再做成Restful接口给网关系统。
 *
 * TODO：方案一：当收到微信支付回调的时候，做完平台勾兑后事务，应该投递kafka消息让订单系统消费勾兑订单支付状态。
 * TODO：方案二：订单系统也可以开一个接口给支付系统来回调勾兑，这个接口也可以给掉单模块定时轮询状态勾兑用。
 * 感觉方案一支持更高的并发峰值、方案二会更通用一些。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayNotifyController.java, v 0.1 2018-12-20 下午3:17 shinnlove.jinsheng Exp $$
 */
@Controller
public class WXPayNotifyController {

    /** 微信响应服务 */
    @Autowired
    private WXPayResponseService wxPayResponseService;

    /**
     * 处理微信支付通知。
     *
     * @param xmlStr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wxpay/notify", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public String doResponseNotify(@RequestBody String xmlStr) throws Exception {

        // 商户配置
        WXPayMchConfig mchConfig = new WXPayMchConfig();

        WXPayNotifyClient client = new WXPayNotifyClient(mchConfig);

        // 处理微信通知
        String result = wxPayResponseService.doCallbackResponse(client, xmlStr, (resp) -> {
            // 处理业务
            System.out.println(resp);
            // 平台业务应答结果
            return new HashMap<>();
        });

        // 响应给微信
        return result;
    }

}