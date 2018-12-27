/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * kafka消费者服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: KafkaConsumerService.java, v 0.1 2018-12-27 20:51 shinnlove.jinsheng Exp $$
 */
public class KafkaConsumerService {

    public static void main(String[] args) {
        // 设置消费者属性
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // 创建consumer并订阅主题
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test1", "test2"));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    // 消费消息，持久化offset
                    System.out.printf("offset=%d, key=%s, value=%s\n", record.offset(),
                        record.key(), record.value());
                }
            }
        } finally {
            // 关闭消费者
            consumer.close();
        }

    }

}