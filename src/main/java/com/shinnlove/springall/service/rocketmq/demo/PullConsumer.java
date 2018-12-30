/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.rocketmq.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageQueue;

/**
 * Case2：自主拉模式消费者。
 *
 * @author shinnlove.jinsheng
 * @version $Id: PullConsumer.java, v 0.1 2018-08-29 下午3:10 shinnlove.jinsheng Exp $$
 */
public class PullConsumer {

    /** 消息分组 */
    private static final String                  CONSUME_GROUP    = "my-default-group";

    /** name-server地址 */
    private static final String                  NAME_SERVER_ADDR = "192.168.0.106:9876";

    /** 每个消息分片内的偏移量 */
    private static final Map<MessageQueue, Long> OFFSET_TABLE     = new HashMap<>();

    public static void main(String[] args) throws MQClientException {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(CONSUME_GROUP);
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);
        consumer.start();
        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicTest1");
        for (MessageQueue mq : mqs) {
            long offset = consumer.fetchConsumeOffset(mq, true);
            System.out.println("Consume from the queue:" + mq);
            while (true) {
                try {
                    PullResult pullResult = consumer.pullBlockIfNotFound(mq, null,
                        getMessageQueueOffset(mq), 100);
                    System.out.println(pullResult);
                    // 下一个要处理的位置就是下一个未开始的位置
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());

                    // 处理拉消息的结果
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            break;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        consumer.shutdown();
    }

    /**
     * 获取一个队列的偏移位，没有存储过位置的队列默认从0开始获取。
     *
     * @param mq
     * @return
     */
    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = OFFSET_TABLE.get(mq);
        if (offset != null) {
            return offset;
        }
        return 0;
    }

    /**
     * 持久化一个队列的偏移位。
     *
     * @param mq
     * @param offset
     */
    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        OFFSET_TABLE.put(mq, offset);
    }

}