/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.rocketmq;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

/**
 * Case5：消息分组部分有序的消费，生产者和消费者协同策略。
 *
 * @author shinnlove.jinsheng
 * @version $Id: PartialOrderedStrategy.java, v 0.1 2018-08-29 下午7:21 shinnlove.jinsheng Exp $$
 */
public class PartialOrderedStrategy {

    /** 消息分组 */
    private static final String CONSUME_GROUP    = "my-default-group";

    /** name-server地址 */
    private static final String NAME_SERVER_ADDR = "192.168.0.106:9876";

    public static void main(String[] args) throws InterruptedException, RemotingException,
                                          MQClientException, MQBrokerException {
        // 根据业务id路由
        producerRouteByBiz();

        // 消费者同一队列有序消费消息
        consumerOneQueueOrderly();
    }

    /**
     * 生产者根据订单Id将消息发到不同的分片中。
     *
     * @throws MQClientException        生产者启动可能出现错误
     * @throws RemotingException        发送消息可能产生错误
     * @throws InterruptedException     发送消息可能产生错误
     * @throws MQBrokerException        发送消息可能产生错误
     */
    public static void producerRouteByBiz() throws MQClientException, RemotingException,
                                           InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer(CONSUME_GROUP);
        producer.setNamesrvAddr(NAME_SERVER_ADDR);
        // 启动生产者准备发送消息
        producer.start();

        for (int i = 0; i < 100; i++) {
            // 假设当前订单id是i
            int orderId = i;
            // 生成订单相关消息
            Message oneMsg = new Message("OrderTopic8", "TagB", "Key" + i,
                ("Hello RocketMQ " + orderId + " " + i).getBytes());
            // 发送消息，并送入业务参数，最终会路由到select函数的形参中
            SendResult sendResult = producer.send(oneMsg, (mqs, msg, arg) -> {
                // MessageQueueSelector

                // 打印当前消息队列
                System.out.println("current queue selector mq numbers: " + mqs.size());
                // 打印准备发送消息信息
                System.out.println("msg info:" + msg.toString());
                // 打印可选择的队列信息
                for (MessageQueue mq : mqs) {
                    System.out.println(mq.toString());
                }
                // 根据传入业务参数进行路由
                Integer id = (Integer) arg;
                int index = id % mqs.size();
                return mqs.get(index);

            }, orderId);

            System.out.println(sendResult);

            switch (sendResult.getSendStatus()) {
                case SEND_OK:
                    System.out.println("消息发送成功");
                    break;
                default:
                    System.out.println("消息发送失败，状态是：" + sendResult.getSendStatus().name());
                    break;
            }

        }
    }

    /**
     * 一个队列一个线程的消费者。
     *
     * @throws MQClientException
     */
    public static void consumerOneQueueOrderly() throws MQClientException {
        // 消费者和关联的生产者在同一个消息分组中
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUME_GROUP);
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);
        // offset从头开始
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 广播模式消费
        consumer.setMessageModel(MessageModel.BROADCASTING);
        // 订阅`TopicTest`下所有消息
        consumer.subscribe("TopicTest", "*");

        // 单个MessageQueue消息并发
        // 特别注意：`MessageListenerOrderly`这种注册模式对于每个Queue都使用一个线程去处理
        consumer.registerMessageListener(new MessageListenerOrderly() {

            AtomicLong consumeTimes = new AtomicLong(0);

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                       ConsumeOrderlyContext context) {
                long count = consumeTimes.incrementAndGet();
                System.out.println("Received new messages orderly: " + count
                                   + new String(msgs.get(0).getBody()));

                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
    }

}