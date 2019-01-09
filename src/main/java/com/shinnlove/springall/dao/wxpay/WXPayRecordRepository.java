/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.dao.wxpay;

import com.shinnlove.springall.dao.model.WXPayRecord;

/**
 * 微信支付记录操作仓储。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayRecordRepository.java, v 0.1 2018-12-21 下午1:47 shinnlove.jinsheng Exp $$
 */
public interface WXPayRecordRepository {

    /**
     * 插入一条微信支付记录，返回插入后的id。
     *
     * @param record
     * @return
     */
    long insertRecord(WXPayRecord record);

    /**
     * 根据订单id查询是否存在支付记录。
     *
     * @param orderId
     * @return
     */
    WXPayRecord queryPayRecordByOrderId(long orderId);

    /**
     * 根据orderId锁定一笔微信支付记录。InnoDB悲观锁。
     *
     * @param orderId
     * @return
     */
    WXPayRecord queryPayRecordByOrderIdForUpdate(long orderId);

    /**
     * 根据id查询平台支付记录。
     *
     * @param payId
     * @return
     */
    WXPayRecord queryPayRecordById(long payId);

    /**
     * 更新微信支付记录。
     *
     * @param record
     * @return
     */
    int updateWXPayRecord(WXPayRecord record);

}
