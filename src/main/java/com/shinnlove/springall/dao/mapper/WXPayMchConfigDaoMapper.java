/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.shinnlove.springall.dao.model.WXPayMchConfigDO;

/**
 * 商户微信支付配置Dao。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayMchConfigDaoMapper.java, v 0.1 2019-01-10 17:55 shinnlove.jinsheng Exp $$
 */
@MapperScan
public interface WXPayMchConfigDaoMapper {

    /**
     * 通过merchantId获取商户微信支付配置。
     * 
     * @param merchantId
     * @return
     */
    WXPayMchConfigDO getConfigByMchId(@Param("merchantId") long merchantId);

    /**
     * 新增一条微信支付商户配置。
     *
     * @param configDO
     * @return
     */
    long addMchConfig(WXPayMchConfigDO configDO);

}
