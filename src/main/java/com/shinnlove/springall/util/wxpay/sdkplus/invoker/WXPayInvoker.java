/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.invoker;

import java.util.Map;

/**
 * 微信支付SDK业务回调钩子。
 *
 * SDK与微信侧交互后，所有后续持久化、判定业务结果、成功失败回调的行为都应该由发起方来定义！
 * SDK应该只做与微信支付通信和加解密的事情，不应该强感知外围的业务逻辑。
 *
 * 请在此处定义需要做的回调。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayInvoker.java, v 0.1 2018-12-19 下午3:25 shinnlove.jinsheng Exp $$
 */
@FunctionalInterface
public interface WXPayInvoker {

    /**
     * 微信支付回调钩子。
     *
     * @param responseData
     * @return
     */
    void doPayCallback(final Map<String, String> responseData);

}
