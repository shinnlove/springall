/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * Hello任务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: HelloJob.java, v 0.1 2018-12-30 22:28 shinnlove.jinsheng Exp $$
 */
public class HelloJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("你好");
    }

}