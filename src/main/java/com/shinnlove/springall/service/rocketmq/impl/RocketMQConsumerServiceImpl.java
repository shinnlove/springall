/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.rocketmq.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.shinnlove.springall.core.model.Student;
import com.shinnlove.springall.service.rocketmq.RocketMQConsumerService;
import com.shinnlove.springall.util.log.LoggerUtil;

/**
 * RocketMQ消费者服务实现类。
 *
 * 本消费者服务可以持有一个线程池，去处理到来的消息。
 *
 * @author shinnlove.jinsheng
 * @version $Id: RocketMQConsumerServiceImpl.java, v 0.1 2018-12-30 18:51 shinnlove.jinsheng Exp $$
 */
public class RocketMQConsumerServiceImpl implements RocketMQConsumerService {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMQConsumerServiceImpl.class);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                    ConsumeConcurrentlyContext context) {
        System.out.println(Thread.currentThread().getName() + "Receive new message: " + msgs);

        MessageQueue messageQueue = context.getMessageQueue();
        String topic = messageQueue.getTopic();
        String brokerName = messageQueue.getBrokerName();

        for (MessageExt msgExt : msgs) {
            // 扔给线程池处理
            try {
                String msgId = msgExt.getMsgId();
                int queueId = msgExt.getQueueId();
                long offset = msgExt.getQueueOffset();
                byte[] body = msgExt.getBody();
                String info = new String(body);
                Student student = JSON.parseObject(info, Student.class);
                LoggerUtil.info(LOGGER, "解析RocketMQ消息，student=", student);
                System.out.println("学生姓名是：" + student.getName());
            } catch (Exception e) {
                LoggerUtil.error(LOGGER, e, "解析RocketMQ消息失败，ex=", e);
                System.out.println("解析RocketMQ消息失败");
            }
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}