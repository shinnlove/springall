/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.callback;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.service.client.AbstractWXPayClient;

/**
 * 微信支付被动通知类。
 *
 * TODO：这个类和response client看看是否留一个，或者都不需要。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayCallbackClient.java, v 0.1 2018-12-18 下午4:13 shinnlove.jinsheng Exp $$
 */
public abstract class WXPayCallbackClient extends AbstractWXPayClient {

    /**
     * 构造函数。
     *
     * @param wxPayMchConfig
     */
    public WXPayCallbackClient(WXPayMchConfig wxPayMchConfig) {
        super(wxPayMchConfig);
    }

}