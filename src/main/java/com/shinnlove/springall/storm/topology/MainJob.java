/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.storm.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

import com.shinnlove.springall.storm.bolt.LogAnalysis;
import com.shinnlove.springall.storm.bolt.PageViewCounter;
import com.shinnlove.springall.storm.spout.LogReader;

/**
 * Storm的任务提交与Topology拓扑结构配置。
 *
 * @author shinnlove.jinsheng
 * @version $Id: MainJob.java, v 0.1 2019-01-01 20:25 shinnlove.jinsheng Exp $$
 */
public class MainJob {

    private static final String SPOUT_NAME           = "log-reader";
    private static final String BOLT_ANALYSIS_NAME   = "log-analysis";
    private static final String BOLT_PAGE_COUNT_NAME = "pageview-counter";
    private static final String TOPOLOGY_NAME        = "log-process-topology";

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        // 配置水龙头和雷电头
        builder.setSpout(SPOUT_NAME, new LogReader());
        builder.setBolt(BOLT_ANALYSIS_NAME, new LogAnalysis()).shuffleGrouping(SPOUT_NAME);
        // 雷电头可以有并行度
        builder.setBolt(BOLT_PAGE_COUNT_NAME, new PageViewCounter(), 2).shuffleGrouping(
            BOLT_ANALYSIS_NAME);

        // 配置
        Config conf = new Config();
        conf.setDebug(false);
        // Q：这个参数是什么意思?
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

        // 提交本地集群模式运行
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(TOPOLOGY_NAME, conf, builder.createTopology());
    }

}