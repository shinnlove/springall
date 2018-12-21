/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.dao.mch.impl;

import org.springframework.stereotype.Service;

import com.shinnlove.springall.dao.mch.MchWXPayConfigRepository;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;

/**
 * 商户微信支付仓储。
 *
 * @author shinnlove.jinsheng
 * @version $Id: MchWXPayConfigRepositoryImpl.java, v 0.1 2018-12-21 下午1:49 shinnlove.jinsheng Exp $$
 */
@Service
public class MchWXPayConfigRepositoryImpl implements MchWXPayConfigRepository {

    @Override
    public WXPayMchConfig queryWXPayConfigByMchId(long merchantId) {
        WXPayMchConfig config = new WXPayMchConfig();
        return config;
    }

}