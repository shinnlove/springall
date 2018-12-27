/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.shinnlove.springall.util.exception.SystemException;

/**
 * kafka生产者服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: KafkaProducerService.java, v 0.1 2018-12-27 17:29 shinnlove.jinsheng Exp $$
 */
public class KafkaProducerService {

    public static void main(String[] args) {

        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");

        // kafka生产者属性
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("client.id", "DemoProducer");

        // Warning：消息的key是什么类型，这里序列化器就是什么类型的
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        // 消息体是string类型的，就可以使用string类型的序列化器
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 所以如果是自己的消息类型，需要实现java.io.Serializer接口，可以让消息序列化成字节码流，并实现kafka的`org.apache.kafka.common.serialization.Serializer`接口!!

        // 创建生产者
        KafkaProducer producer = new KafkaProducer(props);

        String topic = "test";

        final AtomicInteger messageNo = new AtomicInteger(0);
        while (true) {
            // 消息内容
            final String messageStr = "Message_" + messageNo;

            // 创建一条消息
            ProducerRecord record = new ProducerRecord(topic, messageNo.incrementAndGet(),
                messageStr);

            // 创建一条其他类型的消息
            ProducerRecord<Integer, SystemException> newRecord = new ProducerRecord<Integer, SystemException>(
                topic, messageNo.get(), new SystemException("error"));

            final long startTime = System.currentTimeMillis();

            if (isAsync) {
                // 异步回调
                producer.send(
                    record,
                    (metadata, exception) -> {
                        long elapseTime = System.currentTimeMillis() - startTime;
                        if (metadata != null) {
                            System.out.println("message(" + messageNo.get() + ", " + messageStr
                                               + ") sent to partition(" + metadata.partition()
                                               + "), offset(" + metadata.offset() + ") in "
                                               + elapseTime + " ms");
                        } else {
                            exception.printStackTrace();
                        }
                    });
            } else {
                // 同步等待
                Future<RecordMetadata> future = producer.send(record);
                try {
                    RecordMetadata metadata = future.get();

                    long elapseTime = System.currentTimeMillis() - startTime;
                    System.out.println("message(" + messageNo.get() + ", " + messageStr
                                       + ") sent to partition(" + metadata.partition()
                                       + "), offset(" + metadata.offset() + ") in " + elapseTime
                                       + " ms");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

        } // end while

    }

}