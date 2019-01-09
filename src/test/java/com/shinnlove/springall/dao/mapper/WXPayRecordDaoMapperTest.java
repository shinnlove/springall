/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.dao.mapper;

import com.shinnlove.springall.dao.model.WXPayRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试mybatis接入。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayRecordDaoMapperTest.java, v 0.1 2019-01-09 15:22 shinnlove.jinsheng Exp $$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/spring-datasource.xml",
                                   "classpath:/META-INF/spring/spring-database-mybatis.xml" })
public class WXPayRecordDaoMapperTest {

    @Autowired
    private WXPayRecordDaoMapper wxPayRecordDaoMapper;

    @Test
    public void test_getWXPayRecord() {
        long orderId = 1L;
        WXPayRecord record = wxPayRecordDaoMapper.getWXPayRecordByOrderId(orderId);
        System.out.println(record.getMerchantId());
        System.out.println(record);
    }

}