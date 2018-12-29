/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service;

/**
 * kafka消息发送者服务接口。
 *
 * @author shinnlove.jinsheng
 * @version $Id: KafkaProducerService.java, v 0.1 2018-12-29 16:38 shinnlove.jinsheng Exp $$
 */
public interface KafkaProducerService {

    /**
     * 同步发送一条消息，value需要序列化，返回消息投递后在分片内的offset。
     *
     * @param key
     * @param value
     * @return
     */
    long sendMsg(String key, String value);

}
