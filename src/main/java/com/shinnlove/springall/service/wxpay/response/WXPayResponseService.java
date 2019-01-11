/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.response;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinnlove.springall.service.wxpay.callback.WXPayBizCallback;
import com.shinnlove.springall.service.wxpay.config.WXPayGlobalConfigService;
import com.shinnlove.springall.service.wxpay.handler.WXPayCallback;
import com.shinnlove.springall.util.wxpay.sdkplus.service.handler.WXPayRespMsgHandler;
import com.shinnlove.springall.util.wxpay.sdkplus.util.WXPayUtil;

/**
 * 微信支付回调通知应答服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayResponseService.java, v 0.1 2018-12-20 下午3:00 shinnlove.jinsheng Exp $$
 */
@Service
public class WXPayResponseService implements WXPayCallback {

    /** 微信支付全局配置服务 */
    @Autowired
    private WXPayGlobalConfigService wxPayGlobalConfigService;

    @Override
    public String doCallbackResponse(WXPayRespMsgHandler client, String responseXml,
                                     WXPayBizCallback callback) throws Exception {
        // 签名验签参数转换
        final Map<String, String> response = client.processResponseXml(responseXml);

        // 回调业务并响应给微信
        Map<String, String> bizResult = callback.doPayCallback(response);

        // 响应业务处理应答结果
        return WXPayUtil.mapToXml(bizResult);
    }

}