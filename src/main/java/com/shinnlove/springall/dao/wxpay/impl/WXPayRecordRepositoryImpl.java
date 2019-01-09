/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.dao.wxpay.impl;

import com.shinnlove.springall.dao.mapper.WXPayRecordDaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shinnlove.springall.dao.converter.WXPayRecordConverter;
import com.shinnlove.springall.dao.model.WXPayRecord;
import com.shinnlove.springall.dao.wxpay.WXPayRecordRepository;
import com.shinnlove.springall.model.WxpayRecordDO;

/**
 * 微信支付记录操作仓储。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayRecordRepositoryImpl.java, v 0.1 2018-12-21 下午1:58 shinnlove.jinsheng Exp $$
 */
@Repository(value = "wxPayRecordRepository")
public class WXPayRecordRepositoryImpl implements WXPayRecordRepository {

    @Autowired
    private WXPayRecordDaoMapper wxPayRecordDaoMapper;

    @Override
    public long insertRecord(WXPayRecord record) {
        return wxPayRecordDaoMapper.addWXPayRecord(record);
    }

    @Override
    public WXPayRecord queryPayRecordByOrderId(long orderId) {
        return null;
    }

    @Override
    public WXPayRecord queryPayRecordById(long payId) {
        return null;
    }

    @Override
    public int updateWXPayRecord(WXPayRecord record) {
        return 0;
    }

}