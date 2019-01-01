/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.storm.bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

/**
 * 页面访问统计Bolt雷电头。
 *
 * @author shinnlove.jinsheng
 * @version $Id: PageViewCounter.java, v 0.1 2019-01-01 20:23 shinnlove.jinsheng Exp $$
 */
public class PageViewCounter implements IRichBolt {

    private static final long serialVersionUID = -7150539270960682673L;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

    }

    @Override
    public void execute(Tuple input) {
        // 对PV进行统计，持久化存储（这里先简单输出）
        // 可以使用redis对象进行分布式持久化计数统计
        System.out.println("我是雷电头PageViewCounter，输出信息，上个雷电头传入数据是：" + input.getValue(0));
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

}