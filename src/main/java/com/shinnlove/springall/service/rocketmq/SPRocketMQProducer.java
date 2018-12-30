/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.rocketmq;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.shinnlove.springall.util.code.SystemResultCode;
import com.shinnlove.springall.util.exception.SystemException;

/**
 * RocketMQ消息生产者Spring包装类，RocketMQ消息投递入口，在spring启动时候创建{@link DefaultMQProducer}并持久化在容器中。
 *
 * 因为{@link DefaultMQProducer}的构造函数不健全，NameServer必须实例化后设置，所以必须包装一个spring可以配置的Producer!!!
 * 建议本类透传{@link DefaultMQProducer}的send方法给外边，定义相关接口。
 *
 * @author shinnlove.jinsheng
 * @version $Id: SPRocketMQProducer.java, v 0.1 2018-12-30 11:59 shinnlove.jinsheng Exp $$
 */
public class SPRocketMQProducer implements InitializingBean, DisposableBean {

    /** 消息分组 */
    private static final String CONSUME_GROUP    = "my-default-group";

    /** name-server地址 */
    private static final String NAME_SERVER_ADDR = "192.168.0.100:9876";

    /** 消息失败重发次数 */
    private int                 retryTimes       = 3;

    /** RocketMQ生产者 */
    private DefaultMQProducer   defaultMQProducer;

    /**
     * 使用`DefaultMQProducer`发送RocketMQ消息。
     *
     * @param topic                     消息主题
     * @param tag                       消息标记
     * @param value                     消息值（需要外部序列化）
     * @param delayLevel                消息是否延时发送，延时发送的level
     * @param callback                  回调钩子，从外部传入，这一层只做包装透传
     * @throws SystemException
     */
    public void sendMsg(String topic, String tag, String value, int delayLevel,
                        SendCallback callback) throws SystemException {
        Message message = new Message(topic, tag, value.getBytes());
        if (delayLevel > 0) {
            message.setDelayTimeLevel(delayLevel);
        }
        try {
            defaultMQProducer.send(message, callback);
        } catch (MQClientException e) {
            throw new SystemException(SystemResultCode.SYSTEM_ERROR, e);
        } catch (RemotingException e) {
            throw new SystemException(SystemResultCode.SYSTEM_ERROR, e);
        } catch (InterruptedException e) {
            throw new SystemException(SystemResultCode.SYSTEM_ERROR, e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // initialize配置`DefaultMQProducer`
        defaultMQProducer = new DefaultMQProducer(CONSUME_GROUP);
        defaultMQProducer.setNamesrvAddr(NAME_SERVER_ADDR);
        defaultMQProducer.setRetryTimesWhenSendFailed(retryTimes);
        defaultMQProducer.start();
    }

    @Override
    public void destroy() throws Exception {
        if (defaultMQProducer != null) {
            defaultMQProducer.shutdown();
        }
    }

}