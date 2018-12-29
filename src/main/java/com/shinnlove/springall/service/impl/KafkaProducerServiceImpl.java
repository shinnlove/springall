/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.impl;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import com.shinnlove.springall.service.KafkaProducerService;
import com.shinnlove.springall.util.code.SystemResultCode;
import com.shinnlove.springall.util.exception.SystemException;

/**
 * kafka消息发送者服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: KafkaProducerServiceImpl.java, v 0.1 2018-12-29 16:55 shinnlove.jinsheng Exp $$
 */
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public long sendMsg(String key, String value) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate
            .send(new ProducerRecord<>(key, value));
        try {
            SendResult<String, String> result = future.get();
            RecordMetadata metadata = result.getRecordMetadata();
            return metadata.offset();
        } catch (InterruptedException e) {
            throw new SystemException(SystemResultCode.SYSTEM_ERROR, e);
        } catch (ExecutionException e) {
            throw new SystemException(SystemResultCode.SYSTEM_ERROR, e);
        }
    }

}