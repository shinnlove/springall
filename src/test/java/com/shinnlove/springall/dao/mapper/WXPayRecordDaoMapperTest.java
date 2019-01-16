/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.dao.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shinnlove.springall.dao.model.WXPayRecord;

import javax.annotation.Resource;

/**
 * 测试mybatis接入。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayRecordDaoMapperTest.java, v 0.1 2019-01-09 15:22 shinnlove.jinsheng Exp $$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/spring-druid.xml",
        "classpath:/META-INF/spring/spring-mybatis.xml"})
public class WXPayRecordDaoMapperTest {

    /** mybatis-mapper */
    @Resource
    private WXPayRecordDaoMapper wxPayRecordDaoMapper;

    @Test
    public void test_getWXPayRecord() {
        long orderId = 666666L;
        WXPayRecord record = wxPayRecordDaoMapper.getWXPayRecordByOrderId(orderId);
        System.out.println(record.getMerchantId());
        System.out.println(record);
    }

    @Test
    public void test_updateWXPayRecord() {
        WXPayRecord record = new WXPayRecord();
        record.setOrderId(666666L);

        record.setPrepayId("wx21432512395713498571234");
        record.setSign("T34THJKEJKQBE4590342890HGF");
        record.setNonceStr("n42243jfewg09uwe");

        int result = wxPayRecordDaoMapper.updateWXPayRecord(record);
        System.out.println(result);
    }

}