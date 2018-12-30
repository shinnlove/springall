/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.rocketmq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.shinnlove.springall.service.rocketmq.RocketMQProducerService;
import com.shinnlove.springall.service.rocketmq.SPRocketMQProducer;
import com.shinnlove.springall.util.log.LoggerUtil;

/**
 * RocketMQ消息生产者服务实现类，本类可以嵌入业务逻辑，如成功回调写日志、记DB等操作。
 *
 * @author shinnlove.jinsheng
 * @version $Id: RocketMQProducerServiceImpl.java, v 0.1 2018-12-30 12:10 shinnlove.jinsheng Exp $$
 */
public class RocketMQProducerServiceImpl implements RocketMQProducerService {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMQProducerServiceImpl.class);

    /** RocketMQ Spring封装类 */
    private SPRocketMQProducer  spRocketMQProducer;

    /**
     * @see RocketMQProducerService#sendMsg(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void sendMsg(String topic, String tag, String value) {
        LoggerUtil.info(LOGGER, "准备投递RocketMQ消息，topic=", topic, ", tag=", tag, ", value=", value);

        spRocketMQProducer.sendMsg(topic, tag, value, 0, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                LoggerUtil.info(LOGGER, "投递RocketMq消息成功，sendResult=", sendResult);

                // 基本信息
                String msgId = sendResult.getMsgId();
                MessageQueue queue = sendResult.getMessageQueue();
                String brokerName = queue.getBrokerName();
                int queueId = queue.getQueueId();
                long offset = sendResult.getQueueOffset();

                // 投递状态
                String statusMsg = "投递后处理状态未知";
                SendStatus sendStatus = sendResult.getSendStatus();
                // 注意正确处理发送状态
                switch (sendStatus) {
                    case FLUSH_DISK_TIMEOUT:
                        statusMsg = "没有在规定时间内完成刷盘";
                        break;
                    case SEND_OK:
                        statusMsg = "发送与同步OK";
                        break;
                    case FLUSH_SLAVE_TIMEOUT:
                        statusMsg = "没有在设定时间内完成主从同步";
                        break;
                    case SLAVE_NOT_AVAILABLE:
                        statusMsg = "没有找到对应的slave的Broker";
                        break;
                    default:
                        break;
                }

                LoggerUtil.info(LOGGER, "RocketMQ消息记DB，msgId=", msgId, ", brokerName=", brokerName,
                    ", queueId=", queueId, ", offset=", offset, ", statusMsg=", statusMsg);
            }

            @Override
            public void onException(Throwable e) {
                LoggerUtil.error(LOGGER, e, "投递RocketMQ消息失败，ex=", e);
            }

        });
    }

    /**
     * Setter method for property spRocketMQProducer.
     *
     * @param spRocketMQProducer value to be assigned to property spRocketMQProducer
     */
    public void setSpRocketMQProducer(SPRocketMQProducer spRocketMQProducer) {
        this.spRocketMQProducer = spRocketMQProducer;
    }

}