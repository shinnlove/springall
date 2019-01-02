/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.hadoop;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

/**
 * 一个MapReduce任务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: AccessProcessJob.java, v 0.1 2019-01-02 20:32 shinnlove.jinsheng Exp $$
 */
public class AccessProcessJob {

    /**
     * Map
     */
    public static class AccessProcessMap extends MapReduceBase
                                                              implements
                                                              Mapper<LongWritable, Text, Text, Text> {
        @Override
        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output,
                        Reporter reporter) throws IOException {
            // 处理输入的日志记录
            String[] input_fields = value.toString().split(" ");
            output.collect(new Text(input_fields[3]), new Text("1"));
        }
    }

    /**
     * Reduce
     */
    public static class AccessProcessReduce extends MapReduceBase implements
                                                                 Reducer<Text, Text, Text, Text> {
        @Override
        public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output,
                           Reporter reporter) throws IOException {
            long count = 0;
            while (values.hasNext()) {
                String valueTemp = values.next().toString();
                long temp = Long.parseLong(valueTemp);
                count += temp;
            }

            output.collect(key, new Text(count + ""));
        }
    }

    public static void main(String[] args) {
        // 输入输出路径
        String input = "hdfs://localhost:9000/shinn/temp/access.log";
        String output = "hdfs://localhost:9000/shinn/temp/output/";

        // Map-Reduce任务配置
        JobConf conf = new JobConf(AccessProcessJob.class);
        conf.setJobName("AccessProcessJob");

        try {
            conf.setOutputKeyClass(Text.class);
            conf.setOutputValueClass(Text.class);

            conf.setMapperClass(AccessProcessMap.class);
            conf.setReducerClass(AccessProcessReduce.class);

            conf.setInputFormat(TextInputFormat.class);
            conf.setOutputFormat(TextOutputFormat.class);

            FileInputFormat.setInputPaths(conf, new Path(input));
            FileOutputFormat.setOutputPath(conf, new Path(output));

            conf.setNumMapTasks(1);
            conf.setNumReduceTasks(1);

            JobClient.runJob(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}