/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayGlobalConfig;

/**
 * 微信支付全局配置服务，这个服务应该具备两种能力：
 *
 * 1、初始化系统的时候从配置表中读取配置信息并持久化在内存中；
 * 2、当修改微信支付全局配置时，第一时间刷新配置信息；这种修改可以消费kafka消息、或者由外部回调自身。
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
        // 先写死，后面读数据库
        globalConfig = new WXPayGlobalConfig(30000, 3000, 2, 10, 2, true);
    }

}