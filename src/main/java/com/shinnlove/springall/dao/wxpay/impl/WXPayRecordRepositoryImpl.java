/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.dao.wxpay.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shinnlove.springall.dao.mapper.WXPayRecordDaoMapper;
import com.shinnlove.springall.dao.model.WXPayRecord;
import com.shinnlove.springall.dao.wxpay.WXPayRecordRepository;
import com.shinnlove.springall.util.exception.SystemException;

/**
 * 微信支付记录操作仓储。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayRecordRepositoryImpl.java, v 0.1 2018-12-21 下午1:58 shinnlove.jinsheng Exp $$
 */
@Repository(value = "wxPayRecordRepository")
public class WXPayRecordRepositoryImpl implements WXPayRecordRepository {

    /** mybatis-mapper */
    @Autowired
    private WXPayRecordDaoMapper wxPayRecordDaoMapper;

    /**
     * @see WXPayRecordRepository#insertRecord(com.shinnlove.springall.dao.model.WXPayRecord)
     */
    @Override
    public long insertRecord(WXPayRecord record) {
        long result = wxPayRecordDaoMapper.addWXPayRecord(record);
        if (result <= 0) {
            throw new SystemException("创建支付信息失败，请稍后再试");
        }
        return result;
    }

    /**
     * @see WXPayRecordRepository#queryPayRecordByOrderId(long)
     */
    @Override
    public WXPayRecord queryPayRecordByOrderId(long orderId) {
        return wxPayRecordDaoMapper.getWXPayRecordByOrderId(orderId);
    }

    /**
     * @see WXPayRecordRepository#getWXPayRecordByOrderIdForUpdate(long) 
     */
    @Override
    public WXPayRecord getWXPayRecordByOrderIdForUpdate(long orderId) {
        return wxPayRecordDaoMapper.getWXPayRecordByOrderIdForUpdate(orderId);
    }

    @Override
    public WXPayRecord queryPayRecordById(long payId) {
        return null;
    }

    /**
     * @see WXPayRecordRepository#updateWXPayRecord(WXPayRecord) 
     */
    @Override
    public int updateWXPayRecord(WXPayRecord record) {
        int result = wxPayRecordDaoMapper.updateWXPayRecord(record);
        if (result <= 0) {
            throw new SystemException("保存微信支付结果出错");
        }
        return result;
    }

}