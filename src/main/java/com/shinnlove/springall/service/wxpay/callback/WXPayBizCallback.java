/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.callback;

import java.util.Map;

/**
 * 微信支付SDK业务回调钩子。请在此处定义需要做的回调。
 *
 * SDK与微信侧交互后，所有后续持久化、判定业务结果、成功失败回调的行为都应该由发起方来定义！
 * SDK应该只做与微信支付通信和加解密的事情，不应该强感知外围的业务逻辑。
 *
 * 特别注意：这个lambda表达式钩子将会被主动请求类、被动响应类接口作为回调的契约——
 *   a) 对于请求类接口，业务处理后的返回值可以为null或空Map，请求客户端不会做任何处理；
 *   b) 对于响应类接口，业务处理后的返回值直接return出来给不同客户端响应给微信。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayBizCallback.java, v 0.1 2018-12-19 下午3:25 shinnlove.jinsheng Exp $$
 */
@FunctionalInterface
public interface WXPayBizCallback {

    /**
     * 微信支付回调钩子。
     *
     * @param responseData
     * @return
     */
    Map<String, String> doPayCallback(final Map<String, String> responseData);

}
