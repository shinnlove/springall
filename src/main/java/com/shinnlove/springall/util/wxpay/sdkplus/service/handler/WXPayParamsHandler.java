/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.handler;

import java.util.Map;

/**
 * 微信支付——请求参数处理接口。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayParamsHandler.java, v 0.1 2018-12-18 下午11:46 shinnlove.jinsheng Exp $$
 */
public interface WXPayParamsHandler {

    /**
     * Step1：校验微信支付必填参数。
     *
     * 必须校验的字段可以考虑做成支付模型然后注解反射校验。
     *
     * @param keyPairs
     * @throws Exception
     */
    void checkParameters(Map<String, String> keyPairs) throws Exception;

    /**
     * Step2：填写微信支付请求信息。
     *
     * 这部分信息分为两个Part：
     * 1、账户主体信息（区分普通商户模式和服务商户模式）；
     * 2、各类不同的微信支付接口请求必须的字段。
     * 
     * @param keyPairs  
     * @param payParams
     * @throws Exception
     */
    void fillRequestParams(Map<String, String> keyPairs, final Map<String, String> payParams)
                                                                                             throws Exception;

}
