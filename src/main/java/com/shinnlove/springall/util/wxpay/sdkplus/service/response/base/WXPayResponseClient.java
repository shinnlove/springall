/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.response.base;

import java.util.Map;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.service.client.AbstractWXPayClient;

/**
 * 微信支付通知响应类客户端。
 *
 * TODO：思考paynotify+nativepay是否有必要抽象一层，这个类存在的必要性，目前先不用。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayResponseClient.java, v 0.1 2018-12-18 下午5:20 shinnlove.jinsheng Exp $$
 */
public abstract class WXPayResponseClient extends AbstractWXPayClient {

    /** 微信支付结果XML报文 */
    protected String              payResponse;

    /** 微信支付结果Map */
    protected Map<String, String> payResult;

    /** 平台响应微信支付结果 */
    protected Map<String, String> answerResponse;

    /**
     * 构造函数。
     *
     * @param wxPayMchConfig
     */
    public WXPayResponseClient(WXPayMchConfig wxPayMchConfig) {
        super(wxPayMchConfig);
    }

}