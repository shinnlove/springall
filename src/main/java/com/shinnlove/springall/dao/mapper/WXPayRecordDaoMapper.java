/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.dao.mapper;

import com.shinnlove.springall.dao.model.WXPayRecord;

/**
 * 微信支付记录Dao。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayRecordDaoMapper.java, v 0.1 2019-01-09 14:52 shinnlove.jinsheng Exp $$
 */
public interface WXPayRecordDaoMapper {

    /**
     *
     * @param orderId
     * @return
     */
    WXPayRecord getWXPayRecordByOrderId(long orderId);

    /**
     *
     * @param record
     * @return
     */
    long addWXPayRecord(WXPayRecord record);

}
