/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.handler;

import java.util.Map;

/**
 * 微信支付请求响应接口，处理请求XML解码、验签和回调。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayRespMsgHandler.java, v 0.1 2018-12-18 下午4:06 shinnlove.jinsheng Exp $$
 */
public interface WXPayRespMsgHandler {

    /**
     * 微信回复消息签名验签是否通过。
     * 
     * @param responseData
     * @return
     */
    boolean isResponseSignatureValid(Map<String, String> responseData);

    /**
     * 处理微信服务器侧应答消息。
     *
     * @param xmlData 
     * @return
     * @throws Exception
     */
    Map<String, String> processResponseXml(String xmlData) throws Exception;

}
