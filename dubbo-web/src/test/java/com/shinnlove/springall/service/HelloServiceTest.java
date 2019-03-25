/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shinnlove.springall.dubbo.service.consumer.HelloServiceConsumer;

/**
 * dubbo服务发布测试。
 *
 * @author shinnlove.jinsheng
 * @version $Id: HelloServiceTest.java, v 0.1 2019-03-25 14:49 shinnlove.jinsheng Exp $$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/META-INF/spring/dubbo-service.xml" })
public class HelloServiceTest {

    @Autowired
    private HelloServiceConsumer helloServiceConsumer;

    @Test
    public void test_rpcService() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        helloServiceConsumer.callRpcService();
    }

}