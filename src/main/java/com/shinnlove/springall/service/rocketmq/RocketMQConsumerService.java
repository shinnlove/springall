/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.rocketmq;

import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;

/**
 * RocketMQ消费者服务接口，给Spring封装的RocketMQ一个自注入的钩子。
 *
 * @author shinnlove.jinsheng
 * @version $Id: RocketMQConsumerService.java, v 0.1 2018-12-30 18:11 shinnlove.jinsheng Exp $$
 */
public interface RocketMQConsumerService extends MessageListenerConcurrently {

}
