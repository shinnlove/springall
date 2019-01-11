/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.request.report;

import java.util.Map;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.base.WXPayRequestClient;

/**
 * 微信支付-支付接口时效上报。
 *
 * @author shinnlove.jinsheng
 * @version $Id: PayReportClient.java, v 0.1 2018-12-19 下午1:07 shinnlove.jinsheng Exp $$
 */
public class PayReportClient extends WXPayRequestClient {

    /**
     * 构造函数。
     *
     * @param wxPayMchConfig
     */
    public PayReportClient(WXPayMchConfig wxPayMchConfig) {
        super(wxPayMchConfig);
    }

    @Override
    public void checkParameters(Map<String, String> keyPairs) throws Exception {

    }

    @Override
    public void fillRequestDetailParams(Map<String, String> keyPairs, Map<String, String> payParams) {

    }

    @Override
    public boolean requestNeedCert() {
        // 接口上报不需要证书
        return false;
    }

    @Override
    public String requestURLSuffix() {
        return null;
    }

}