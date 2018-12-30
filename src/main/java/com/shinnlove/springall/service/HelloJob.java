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
        switch (shardingContext.getShardingItem()) {
            case 0:
                System.out.println("你好0");
                break;
            case 1:
                System.out.println("你好1");
                break;
            case 2:
                System.out.println("你好2");
                break;
            default:
                System.out.println("不知道的item");
                break;
        }
        System.out.println("定时任务调用完毕");
    }

}