/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.paymode.strategy.impl;

import java.util.Map;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.enums.WXPaySignType;
import com.shinnlove.springall.util.wxpay.sdkplus.service.paymode.strategy.WXPayModeStrategy;
import com.shinnlove.springall.util.wxpay.sdkplus.util.WXPayUtil;

/**
 * 微信支付——普通商户模式支付策略。
 *
 * @author shinnlove.jinsheng
 * @version $Id: OrdinaryPayModeStrategy.java, v 0.1 2018-12-18 下午5:03 shinnlove.jinsheng Exp $$
 */
public class OrdinaryPayModeStrategy implements WXPayModeStrategy {

    @Override
    public void fillRequestMainBodyParams(WXPayMchConfig wxPayMchConfig,
                                          final Map<String, String> payParameters) throws Exception {
        payParameters.put(WXPayConstants.APPID, wxPayMchConfig.getAppId());
        payParameters.put(WXPayConstants.MCH_ID, wxPayMchConfig.getMchId());
        payParameters.put(WXPayConstants.NONCE_STR, WXPayUtil.generateUUID());
        // 验签方式
        if (WXPaySignType.MD5.equals(wxPayMchConfig.getSignType())) {
            // MD5
            payParameters.put(WXPayConstants.SIGN_TYPE, WXPayConstants.MD5);
        } else if (WXPaySignType.HMACSHA256.equals(wxPayMchConfig.getSignType())) {
            // HMACSHA256
            payParameters.put(WXPayConstants.SIGN_TYPE, WXPayConstants.HMACSHA256);
        }
        String sign = WXPayUtil.generateSignature(payParameters, wxPayMchConfig.getApiKey(),
            wxPayMchConfig.getSignType());
        payParameters.put(WXPayConstants.SIGN, sign);
    }

}