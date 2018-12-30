/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.rocketmq;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * RocketMQ消息消费者Spring包装类，RocketMQ消息消费入口，在spring启动时候创建{@link DefaultMQPushConsumer}并持久化在容器中。
 *
 * 因为{@link DefaultMQPushConsumer}的构造函数不健全，NameServer必须实例化后设置，所以必须包装一个spring可以配置的Producer!!!
 * 建议本类透传{@link DefaultMQPushConsumer}的send方法给外边，定义相关接口。
 *
 *  特别注意：必须在afterPropertiesSet后才能自注入消费者服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: SPRocketMQConsumer.java, v 0.1 2018-12-30 16:14 shinnlove.jinsheng Exp $$
 */
public class SPRocketMQConsumer implements InitializingBean, DisposableBean {

    /** 消息分组 */
    private static final String     CONSUME_GROUP    = "my-default-group";

    /** name-server地址 */
    private static final String     NAME_SERVER_ADDR = "127.0.0.1:9876";

    /** 消费消息主题 */
    private static final String     TOPIC            = "my-topic";

    /** RocketMQ消费者 */
    private DefaultMQPushConsumer   defaultMQPushConsumer;

    /** 消费者服务自注入（反向注入） */
    private RocketMQConsumerService rocketMQConsumerService;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 创建消费者
        defaultMQPushConsumer = new DefaultMQPushConsumer(CONSUME_GROUP);
        defaultMQPushConsumer.setNamesrvAddr(NAME_SERVER_ADDR);
        // offset从头开始
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 广播模式消费
        defaultMQPushConsumer.setMessageModel(MessageModel.BROADCASTING);
        // 订阅`Topic`下所有消息
        defaultMQPushConsumer.subscribe(TOPIC, "*");
        // 注册消费监听服务（自注入）
        defaultMQPushConsumer.registerMessageListener(rocketMQConsumerService);
        // 启动Consumer
        defaultMQPushConsumer.start();
    }

    @Override
    public void destroy() throws Exception {
        if (defaultMQPushConsumer != null) {
            defaultMQPushConsumer.shutdown();
        }
    }

    /**
     * Setter method for property rocketMQConsumerService.
     *
     * @param rocketMQConsumerService value to be assigned to property rocketMQConsumerService
     */
    public void setRocketMQConsumerService(RocketMQConsumerService rocketMQConsumerService) {
        this.rocketMQConsumerService = rocketMQConsumerService;
    }

}