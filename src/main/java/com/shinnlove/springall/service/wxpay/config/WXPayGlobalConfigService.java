/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayGlobalConfig;

/**
 * 微信支付全局配置服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayGlobalConfigService.java, v 0.1 2019-01-11 11:16 shinnlove.jinsheng Exp $$
 */
@Service
public class WXPayGlobalConfigService implements InitializingBean {

    /** 微信支付全局配置 */
    private WXPayGlobalConfig globalConfig;

    /**
     * 对外透出当前微信支付全局配置
     * 
     * @return
     */
    public WXPayGlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}