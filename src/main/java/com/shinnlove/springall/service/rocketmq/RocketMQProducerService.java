/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.rocketmq;

/**
 * RocketMQ消息生产者服务接口。
 *
 * @author shinnlove.jinsheng
 * @version $Id: RocketMQProducerService.java, v 0.1 2018-12-30 11:58 shinnlove.jinsheng Exp $$
 */
public interface RocketMQProducerService {

    /**
     * 发送RocketMQ消息。
     *
     * @param topic 
     * @param tag
     * @param value
     */
    void sendMsg(String topic, String tag, String value);

}
