/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.rocketmq.impl;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.shinnlove.springall.service.rocketmq.RocketMQConsumerService;

import java.util.List;

/**
 * RocketMQ消费者服务实现类。
 *
 * @author shinnlove.jinsheng
 * @version $Id: RocketMQConsumerServiceImpl.java, v 0.1 2018-12-30 18:51 shinnlove.jinsheng Exp $$
 */
public class RocketMQConsumerServiceImpl implements RocketMQConsumerService, MessageListenerConcurrently {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        return null;
    }

}