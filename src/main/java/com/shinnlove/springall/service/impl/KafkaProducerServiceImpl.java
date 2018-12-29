/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.impl;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import com.shinnlove.springall.service.KafkaProducerService;
import com.shinnlove.springall.util.code.SystemResultCode;
import com.shinnlove.springall.util.exception.SystemException;
import com.shinnlove.springall.util.log.LoggerUtil;

/**
 * kafka消息发送者服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: KafkaProducerServiceImpl.java, v 0.1 2018-12-29 16:55 shinnlove.jinsheng Exp $$
 */
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private static final Logger           LOGGER = LoggerFactory
                                                     .getLogger(KafkaProducerServiceImpl.class);

    /** 往test中发送消息 */
    private static final String           TOPIC  = "test";

    /** !!!这里特别注意，如果bean是xml中实例化的，因为生成时序问题，建议不要使用@Autowired，避免出现null */
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public long sendMsg(String key, String value) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate
            .send(new ProducerRecord<>(TOPIC, key, value));
        try {
            SendResult<String, String> result = future.get();
            RecordMetadata metadata = result.getRecordMetadata();
            return metadata.offset();
        } catch (InterruptedException e) {
            LoggerUtil.error(LOGGER, e, e);
            throw new SystemException(SystemResultCode.SYSTEM_ERROR, e);
        } catch (ExecutionException e) {
            LoggerUtil.error(LOGGER, e, e);
            throw new SystemException(SystemResultCode.SYSTEM_ERROR, e);
        }
    }

    /**
     * Setter method for property kafkaTemplate.
     *
     * @param kafkaTemplate value to be assigned to property kafkaTemplate
     */
    public void setKafkaTemplate(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

}