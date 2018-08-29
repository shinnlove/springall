/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.rocketmq;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

/**
 * 消费者示例，代码配置型。
 *
 * @author shinnlove.jinsheng
 * @version $Id: QuickStart.java, v 0.1 2018-08-29 下午2:07 shinnlove.jinsheng Exp $$
 */
public class QuickStart {

    /** 消息分组 */
    private static final String CONSUME_GROUP    = "my-default-group";

    /** name-server地址 */
    private static final String NAME_SERVER_ADDR = "192.168.0.106:9876";

    public static void main(String[] args) throws MQClientException, RemotingException,
                                          InterruptedException, MQBrokerException {
        startProducer();

        startConsumer();
    }

    /**
     * 启动生产者
     *
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQBrokerException
     */
    public static void startProducer() throws MQClientException, RemotingException,
                                      InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer(CONSUME_GROUP);
        producer.setNamesrvAddr(NAME_SERVER_ADDR);
        // 启动生产者准备发送消息
        producer.start();
        for (int i = 0; i < 100; i++) {
            // 创建一条消息
            Message msg = new Message("TopicTest", "TagA", ("hello rocketmq " + i).getBytes());
            SendResult sendResult = producer.send(msg);
            System.out.println(sendResult);
        }
        // 关闭生产者
        producer.shutdown();
    }

    /**
     * 开启消费者。
     *
     * @throws MQClientException
     */
    public static void startConsumer() throws MQClientException {
        // 消费者和关联的生产者在同一个消息分组中
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUME_GROUP);
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);
        // offset从头开始
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 广播模式消费
        consumer.setMessageModel(MessageModel.BROADCASTING);
        // 订阅`TopicTest`下所有消息
        consumer.subscribe("TopicTest", "*");
        // 注册消费监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName() + "Receive new message: "
                                   + msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动Consumer
        consumer.start();
    }

}