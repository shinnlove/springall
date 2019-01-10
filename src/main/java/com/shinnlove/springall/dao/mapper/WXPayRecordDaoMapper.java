/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.dao.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.shinnlove.springall.dao.model.WXPayRecord;

/**
 * 微信支付记录Dao。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayRecordDaoMapper.java, v 0.1 2019-01-09 14:52 shinnlove.jinsheng Exp $$
 */
@MapperScan
public interface WXPayRecordDaoMapper {

    /**
     * 根据orderId查询微信支付记录。
     *
     * @param orderId
     * @return
     */
    WXPayRecord getWXPayRecordByOrderId(long orderId);

    /**
     * 根据orderId锁定一笔微信支付记录。InnoDB悲观锁。
     *
     * @param orderId
     * @return
     */
    WXPayRecord getWXPayRecordByOrderIdForUpdate(long orderId);

    /**
     * 新增一条微信支付记录。
     *
     * @param record
     * @return
     */
    long addWXPayRecord(WXPayRecord record);

    /**
     * 更新微信支付记录。
     *
     * 一般用于：
     * a) 统一支付得到预支付id
     * b) 支付得到响应回调
     *
     * @param record
     * @return
     */
    int updateWXPayRecord(WXPayRecord record);

}
