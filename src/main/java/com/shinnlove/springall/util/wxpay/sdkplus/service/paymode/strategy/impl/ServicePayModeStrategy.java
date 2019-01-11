/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.paymode.strategy.impl;

import java.util.Map;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.service.paymode.strategy.WXPayModeStrategy;

/**
 * 微信支付——服务商模式支付策略。
 *
 * @author shinnlove.jinsheng
 * @version $Id: ServicePayModeStrategy.java, v 0.1 2018-12-18 下午5:03 shinnlove.jinsheng Exp $$
 */
public class ServicePayModeStrategy implements WXPayModeStrategy {

    @Override
    public void fillRequestMainBodyParams(WXPayMchConfig wxPayMchConfig,
                                          final Map<String, String> payParameters) throws Exception {
        payParameters.put(WXPayConstants.APPID, wxPayMchConfig.getAppId());
        // 子appid
        payParameters.put(WXPayConstants.SUB_APPID, wxPayMchConfig.getSubAppId());
        payParameters.put(WXPayConstants.MCH_ID, wxPayMchConfig.getMchId());
        // 子商户id
        payParameters.put(WXPayConstants.SUB_MCH_ID, wxPayMchConfig.getSubMchId());
    }

}