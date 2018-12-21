/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.dao.mch;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;

/**
 * 商户微信支付配置仓储。
 *
 * @author shinnlove.jinsheng
 * @version $Id: MchWXPayConfigRepository.java, v 0.1 2018-12-21 下午1:48 shinnlove.jinsheng Exp $$
 */
public interface MchWXPayConfigRepository {

    /**
     * 通过商户id查询商户配置。
     * 
     * @param merchantId 
     * @return
     */
    WXPayMchConfig queryWXPayConfigByMchId(long merchantId);

}
