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
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * 日志分析Bolt雷电头。
 *
 * @author shinnlove.jinsheng
 * @version $Id: LogAnalysis.java, v 0.1 2019-01-01 20:20 shinnlove.jinsheng Exp $$
 */
public class LogAnalysis implements IRichBolt {

    private static final long serialVersionUID = -8784289465657302498L;
    private OutputCollector   collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String logLine = input.getString(0);
        String[] input_fields = logLine.toString().split(" ");
        // 假定日志按空格切分后，第四位是需要的数据，就调用collector发射出去
        collector.emit(new Values(input_fields[1]));
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("page"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

}