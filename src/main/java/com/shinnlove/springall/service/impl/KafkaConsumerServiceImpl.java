/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.shinnlove.springall.core.model.Student;
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
        // kafka消息基本信息
        String topic = data.topic();
        int partition = data.partition();
        long offset = data.offset();
        long timestamp = data.timestamp();
        String key = data.key();
        String value = data.value();

        LoggerUtil.info(LOGGER, "接收到一条kafka消息：topic=", topic, ", partition=", partition,
            ", offset=", offset, ", timestamp=", timestamp, ", key=", key, ", value=", value,
            ", data=", data);

        System.out.println("接收到一条kafka消息：value=" + value + ", 原始data=" + data);

        try {
            Student student = JSON.parseObject(value, Student.class);
            System.out.println("学生姓名是：" + student.getName());
        } catch (Exception e) {
            System.out.println("不是一条spring发来的student消息！");
        }
        System.out.println("处理消息结束");
    }

}