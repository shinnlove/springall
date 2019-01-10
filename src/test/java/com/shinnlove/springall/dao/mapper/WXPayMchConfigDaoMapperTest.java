/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.dao.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shinnlove.springall.dao.converter.WXPayMchConfigConverter;
import com.shinnlove.springall.dao.model.WXPayMchConfigDO;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;

import javax.annotation.Resource;

/**
 * 测试微信支付商户配置。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayMchConfigDaoMapperTest.java, v 0.1 2019-01-10 20:01 shinnlove.jinsheng Exp $$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/spring-datasource-tx.xml",
                                   "classpath:/META-INF/spring/spring-database-mybatis.xml" })
public class WXPayMchConfigDaoMapperTest {

    /** mybatis-mapper */
    @Resource
    private WXPayMchConfigDaoMapper wxPayMchConfigDaoMapper;

    @Test
    public void test_getWXPayRecord() {
        long merchantId = 2014250006L;
        WXPayMchConfigDO configDO = wxPayMchConfigDaoMapper.getConfigByMchId(merchantId);
        WXPayMchConfig configVO = WXPayMchConfigConverter.toVO(configDO);
        System.out.println(configVO);
        System.out.println(configVO.getApiKey());
    }

    @Test
    public void test_addMchConfig() {
        WXPayMchConfigDO configDO = buildConfig();
        System.out.println("插入前id=" + configDO.getId());
        long result = wxPayMchConfigDaoMapper.addMchConfig(configDO);
        System.out.println("插入影响的行数：" + result);
        System.out.println("插入后id=" + configDO.getId());
    }

    private WXPayMchConfigDO buildConfig() {
        long merchantId = 201812200006L;
        WXPayMchConfigDO configDO = new WXPayMchConfigDO();
        configDO.setMerchantId(merchantId);
        configDO.setMchId("1230612306");
        configDO.setSubMchId("");
        configDO.setAppId("abcd1234");
        configDO.setSubAppId("");
        configDO.setAppSecret("abcadfasdfewfasf");
        configDO.setApiKey("weactyesweactyesweactyesweactyes");
        configDO.setCertP12("/Users/shinnlove/certs/cert_p12.pem");
        configDO.setSslCertPath("/Users/shinnlove/certs/ssl_cert.pem");
        configDO.setSslKeyPath("/Users/shinnlove/certs/ssl_key.pem");
        configDO.setRootcaPem("/Users/shinnlove/certs/rootca.pem");
        configDO.setPayMode(0);
        configDO.setSignType(1);
        configDO.setAvailable(1);
        configDO.setRemark("单元测试增加的微信支付配置");
        return configDO;
    }

}