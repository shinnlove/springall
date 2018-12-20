/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.handler;

import java.util.Map;

import com.shinnlove.springall.service.wxpay.callback.WXPayBizCallback;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.base.WXPayRequestClient;

/**
 * 微信支付请求服务接口，对外透出SDK的支付能力。
 *
 * SDK将请求结果向上层透出，上层入参是Map、出参结果也是Map。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayService.java, v 0.1 2018-12-19 上午10:49 shinnlove.jinsheng Exp $$
 */
public interface WXPayService {

    /**
     * 做微信支付的对外流程：Step1~StepN...
     *
     * 思考入参是反类型还是做模型转换...
     *
     * @param client        微信支付不同类型的请求客户端
     * @param keyPairs      微信支付请求入参
     * @param callback      微信支付结果回调（SDK应该只做与微信支付通信和加解密的事情，不应该强感知外围的业务逻辑）
     * @throws Exception
     * @return
     */
    Map<String, String> doPayRequest(WXPayRequestClient client, Map<String, String> keyPairs,
                                     WXPayBizCallback callback) throws Exception;

}
