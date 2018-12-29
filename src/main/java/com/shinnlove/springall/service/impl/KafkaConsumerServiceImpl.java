/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import com.shinnlove.springall.service.KafkaConsumerService;

/**
 * kafka消费者服务实现类，实现{@link MessageListener}类，并且xml中配置成回调类。
 *
 * @author shinnlove.jinsheng
 * @version $Id: KafkaConsumerServiceImpl.java, v 0.1 2018-12-29 16:32 shinnlove.jinsheng Exp $$
 */
public class KafkaConsumerServiceImpl implements KafkaConsumerService,
                                     MessageListener<String, String> {

    @Override
    public void receiveMsg() {

    }

    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        System.out.println("消息： " + data);
    }

}