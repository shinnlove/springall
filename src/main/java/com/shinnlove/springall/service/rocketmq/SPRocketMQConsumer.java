/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.rocketmq;

import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * RocketMQ消息消费者Spring包装类，RocketMQ消息消费入口，在spring启动时候创建{@link DefaultMQPushConsumer}并持久化在容器中。
 *  *
 *  * 因为{@link DefaultMQPushConsumer}的构造函数不健全，NameServer必须实例化后设置，所以必须包装一个spring可以配置的Producer!!!
 *  * 建议本类透传{@link DefaultMQPushConsumer}的send方法给外边，定义相关接口。
 *
 * @author shinnlove.jinsheng
 * @version $Id: SPRocketMQConsumer.java, v 0.1 2018-12-30 16:14 shinnlove.jinsheng Exp $$
 */
public class SPRocketMQConsumer implements InitializingBean, DisposableBean {

    /** 消息分组 */
    private static final String   CONSUME_GROUP    = "my-default-group";

    /** name-server地址 */
    private static final String   NAME_SERVER_ADDR = "127.0.0.1:9876";

    /** 消费消息主题 */
    private static final String   TOPIC            = "my-topic";

    /** RocketMQ生产者 */
    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Override
    public void afterPropertiesSet() throws Exception {
        defaultMQPushConsumer = new DefaultMQPushConsumer(CONSUME_GROUP);
        defaultMQPushConsumer.setNamesrvAddr(NAME_SERVER_ADDR);
        // offset从头开始
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 广播模式消费
        defaultMQPushConsumer.setMessageModel(MessageModel.BROADCASTING);
        // 订阅`TopicTest`下所有消息
        defaultMQPushConsumer.subscribe(TOPIC, "*");
        // 注册消费监听
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName() + "Receive new message: "
                                   + msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动Consumer
        defaultMQPushConsumer.start();
    }

    @Override
    public void destroy() throws Exception {
        if (defaultMQPushConsumer != null) {
            defaultMQPushConsumer.shutdown();
        }
    }

}