/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.limitrate.guava;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;

/**
 * Guava限流的使用。
 *
 * 具体代码待完善，但是这里三种用法足以显示用途。
 *
 * @author shinnlove.jinsheng
 * @version $Id: GuavaRateLimiter.java, v 0.1 2018-08-14 下午11:44 shinnlove.jinsheng Exp $$
 */
public class GuavaRateLimiter {

    private static RateLimiter rateLimiter = RateLimiter.create(5);

    public static void main(String[] args) {
        // type1:阻塞式获取令牌
        rateLimiter.acquire();
        // type2:尝试获取令牌、获取不到立刻返回false
        rateLimiter.tryAcquire();
        // type3:尝试获取令牌、等待10秒，等待不到返回false
        rateLimiter.tryAcquire(10, TimeUnit.MILLISECONDS);
    }

}