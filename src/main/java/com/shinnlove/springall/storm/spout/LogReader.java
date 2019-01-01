/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.storm.spout;

import java.util.Map;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import javax.jms.*;

/**
 * 日志读取Spout水龙头。
 *
 * @author shinnlove.jinsheng
 * @version $Id: LogReader.java, v 0.1 2019-01-01 20:10 shinnlove.jinsheng Exp $$
 */
public class LogReader implements IRichSpout {

    private static final long    serialVersionUID = -1587728571639511243L;

    private TopologyContext      context;
    private SpoutOutputCollector collector;
    private ConnectionFactory    connectionFactory;
    private Connection           connection;
    private Session              session;
    private Destination          destination;
    private MessageConsumer      consumer;

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.context = context;
        this.collector = collector;
        this.connectionFactory = new ActiveMQConnectionFactory(
            ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD,
            "tcp://127.0.0.1:61616");
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE, session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("LogQueue");
            consumer = session.createConsumer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void nextTuple() {
        try {
            TextMessage message = (TextMessage) consumer.receive(100000);
            this.collector.emit(new Values(message.getText()));
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ack(Object msgId) {

    }

    @Override
    public void fail(Object msgId) {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("logline"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

}