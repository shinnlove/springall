/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

import com.shinnlove.springall.service.KafkaConsumerService;
import com.shinnlove.springall.util.log.LoggerUtil;

/**
 * kafka消费者服务实现类，实现{@link MessageListener}类，并且xml中配置成回调类。
 *
 * @author shinnlove.jinsheng
 * @version $Id: KafkaConsumerServiceImpl.java, v 0.1 2018-12-29 16:32 shinnlove.jinsheng Exp $$
 */
public class KafkaConsumerServiceImpl implements KafkaConsumerService,
                                     MessageListener<String, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);

    @Override
    public void receiveMsg() {

    }

    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        LoggerUtil.info(LOGGER, "接收到一条kafka消息：data=", data);
        System.out.println("接收到一条kafka消息：data=： " + data);
    }

}