/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

/**
 * Case3：生产者异步发送消息、有选择路由的发送消息例子。
 *
 * @author shinnlove.jinsheng
 * @version $Id: ProducerQuickStart.java, v 0.1 2018-08-29 下午4:03 shinnlove.jinsheng Exp $$
 */
public class ProducerQuickStart {

    /** 消息分组 */
    private static final String CONSUME_GROUP    = "my-default-group";

    /** name-server地址 */
    private static final String NAME_SERVER_ADDR = "192.168.0.106:9876";

    public static void main(String[] args) throws MQClientException, RemotingException,
                                          InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(CONSUME_GROUP);
        producer.setNamesrvAddr(NAME_SERVER_ADDR);
        // 设置发送者重试消息次数，为了保证投递成功可以多试几次
        producer.setRetryTimesWhenSendFailed(3);
        producer.start();
        // 发送消息
        for (int i = 0; i < 1000; i++) {
            Message oneMsg = new Message("TopicTest", "TagA", ("Hello RocketMQ " + i).getBytes());
            Message msgDelay = new Message("TopicTest", "TagA", ("Hello RocketMQ " + i).getBytes());
            msgDelay.setDelayTimeLevel(1);
            // 异步发送消息需要设置`SendCallback`响应钩子
            producer.send(oneMsg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                    SendStatus sendStatus = sendResult.getSendStatus();
                    System.out.println("发送消息状态：" + sendResult);
                    // 注意正确处理发送状态
                    switch (sendStatus) {
                        case FLUSH_DISK_TIMEOUT:
                            System.out.println("没有在规定时间内完成刷盘");
                            break;
                        case SEND_OK:
                            System.out.println("发送与同步OK");
                            break;
                        case FLUSH_SLAVE_TIMEOUT:
                            System.out.println("没有在设定时间内完成主从同步");
                            break;
                        case SLAVE_NOT_AVAILABLE:
                            System.out.println("没有找到对应的slave的Broker");
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onException(Throwable e) {
                    // 打日志
                    e.printStackTrace();
                }
            });

            // 有选择性的发送消息(orderId是业务主键)
            int orderId = i;
            producer.send(oneMsg, (mqs, msg, arg) -> {

                // MessageQueueSelector

                int id = Integer.parseInt(arg.toString());
                int idMainIndex = id / 100;
                int size = mqs.size();
                int index = idMainIndex % size;
                return mqs.get(index);

            }, orderId, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult.getSendStatus());
                }

                @Override
                public void onException(Throwable e) {
                    e.printStackTrace();
                }
            });

        } // end for
        producer.shutdown();
    }
}