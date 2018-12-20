/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.handler;

import com.shinnlove.springall.service.wxpay.callback.WXPayBizCallback;
import com.shinnlove.springall.util.wxpay.sdkplus.service.handler.WXPayRespMsgHandler;

/**
 * 微信支付回调处理通知接口，对外透出处理回调的能力。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayCallback.java, v 0.1 2018-12-20 下午3:02 shinnlove.jinsheng Exp $$
 */
public interface WXPayCallback {

    /**
     * 微信支付消息通知、NATIVE支付回调。
     *
     * @param client        微信响应客户端，NOTIFY或NATIVE
     * @param responseXml   微信发来的响应XML报文
     * @param callback      处理XML报文结果的业务回调钩子
     * @return
     * @throws Exception
     */
    String doCallbackResponse(WXPayRespMsgHandler client, String responseXml,
                              WXPayBizCallback callback) throws Exception;

}
