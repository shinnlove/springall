/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.dao.mch.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinnlove.springall.dao.converter.WXPayMchConfigConverter;
import com.shinnlove.springall.dao.mapper.WXPayMchConfigDaoMapper;
import com.shinnlove.springall.dao.mch.MchWXPayConfigRepository;
import com.shinnlove.springall.dao.model.WXPayMchConfigDO;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;

/**
 * 商户微信支付仓储。
 *
 * @author shinnlove.jinsheng
 * @version $Id: MchWXPayConfigRepositoryImpl.java, v 0.1 2018-12-21 下午1:49 shinnlove.jinsheng Exp $$
 */
@Service
public class MchWXPayConfigRepositoryImpl implements MchWXPayConfigRepository {

    /** mybatis-mapper */
    @Autowired
    private WXPayMchConfigDaoMapper wxPayMchConfigDaoMapper;

    /**
     * @see MchWXPayConfigRepository#queryWXPayConfigByMchId(long)
     */
    @Override
    public WXPayMchConfig queryWXPayConfigByMchId(long merchantId) {
        WXPayMchConfigDO configDO = wxPayMchConfigDaoMapper.getConfigByMchId(merchantId);
        return WXPayMchConfigConverter.toVO(configDO);
    }

}