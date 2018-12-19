/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.paymode.context;

import java.util.Map;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.enums.WXPayMode;
import com.shinnlove.springall.util.wxpay.sdkplus.service.paymode.strategy.WXPayModeStrategy;
import com.shinnlove.springall.util.wxpay.sdkplus.service.paymode.strategy.impl.OrdinaryPayModeStrategy;
import com.shinnlove.springall.util.wxpay.sdkplus.service.paymode.strategy.impl.ServicePayModeStrategy;

/**
 * 微信支付——支付模式上下文，对外屏蔽不同模式的差异。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayModeContext.java, v 0.1 2018-12-18 下午4:52 shinnlove.jinsheng Exp $$
 */
public class WXPayModeContext {

    /** 微信支付具体策略：0为普通商户模式、1为服务商模式 */
    private WXPayModeStrategy wxPayModeStrategy;

    /**
     * 构造函数：根据不同支付模式创建支付策略。
     *
     * @param wxPayMode
     */
    public WXPayModeContext(WXPayMode wxPayMode) {
        int payMode = wxPayMode.getCode();
        if (payMode == 1) {
            // 服务商模式
            wxPayModeStrategy = new ServicePayModeStrategy();
        } else {
            // 普通商户模式
            wxPayModeStrategy = new OrdinaryPayModeStrategy();
        }
    }

    /**
     * 上下文对外透出填充支付主体的能力：
     *  根据微信支付模式策略填入支付请求商户主体信息。
     *
     * @param wxPayMchConfig 
     * @param payParameters
     * @throws Exception
     */
    public void fillRequestMainBodyParams(WXPayMchConfig wxPayMchConfig,
                                          final Map<String, String> payParameters) throws Exception {
        wxPayModeStrategy.fillRequestMainBodyParams(wxPayMchConfig, payParameters);
    }

}