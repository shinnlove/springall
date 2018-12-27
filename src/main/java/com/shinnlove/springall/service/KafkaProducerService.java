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
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer producer = new KafkaProducer(props);

        String topic = "test";

        final AtomicInteger messageNo = new AtomicInteger(0);
        while (true) {
            final String messageStr = "Message_" + messageNo;

            ProducerRecord record = new ProducerRecord(topic, messageNo.incrementAndGet(),
                messageStr);

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