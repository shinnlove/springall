/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.response.pay;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.service.client.AbstractWXPayClient;

/**
 * 微信支付结果通知客户端。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayNotifyClient.java, v 0.1 2018-12-18 下午5:28 shinnlove.jinsheng Exp $$
 */
public class WXPayNotifyClient extends AbstractWXPayClient {

    /**
     * 构造函数。
     *
     * @param wxPayMchConfig
     */
    public WXPayNotifyClient(WXPayMchConfig wxPayMchConfig) {
        super(wxPayMchConfig);
    }

}