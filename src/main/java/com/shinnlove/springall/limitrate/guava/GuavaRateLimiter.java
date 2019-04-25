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
 * 特别注意：RateLimit这个句柄对象是用来做限流时、获取令牌的源头。
 * 获取令牌有三种方式：无条件阻塞、不阻塞等待、阻塞一定时间的等待。
 *
 * 这个句柄对象可以被改变，也就是当接收到消息onCall的时候，可以将限流对象重新构造，通常被定义为volatile的类型。
 *
 * 特别注意：RateLimit如果一次性`acquire`多个令牌，当突发流量吸走所有令牌，产生速率跟不上的时候，还会让线程等待到足量令牌才获得。
 *
 * @author shinnlove.jinsheng
 * @version $Id: GuavaRateLimiter.java, v 0.1 2018-08-14 下午11:44 shinnlove.jinsheng Exp $$
 */
public class GuavaRateLimiter {

    /** RateLimit句柄、初始化启动的时候是一个默认值，如200 */
    private static volatile RateLimiter rateLimiter = RateLimiter.create(200);

    /**
     * 用这个函数来代替模拟一个spring bean中实现了消息中间件的回调接口函数。
     * 
     * 消息传入消息体、主题之类的不再重复拆解，默认主调函数进入的时候已经获取到要限流的速率了。
     *
     * 特别注意：当调整新的RateLimit句柄的时候，给一个适当的缓冲时间，让系统流量逐步放大，但是不能超过压测最大值。
     *
     * @param currentRate
     */
    public void onCall(String currentRate) {
        // 原来流量200、现在过度到400，在5秒内逐步扩大流量
        RateLimiter newStrategy = RateLimiter.create(400, 5, TimeUnit.SECONDS);

        synchronized (this) {
            // sync modify
            rateLimiter = newStrategy;
        }
    }

    /**
     * 传入限流句柄，对应用层做阻塞式限流。
     *
     * @param rateLimiter
     */
    public void limitAndBlock(RateLimiter rateLimiter) {

        for (int i = 0; i < 10; i++) {
            // 阻塞式获取令牌返回的是等待时间
            double waitTime = rateLimiter.acquire();
            System.out.println(waitTime);
        }

    }

    /**
     * 传入限流句柄，对应用层做无等待的限流。
     *
     * @param rateLimiter
     */
    public void limitWithoutWait(RateLimiter rateLimiter) {

        // 尝试获取令牌、获取不到立刻返回false
        boolean result = rateLimiter.tryAcquire();
        if (result) {
            System.out.println("成功获取到token");
        } else {
            System.out.println("没有获取到token");
        }

    }

    /**
     * 传入限流句柄，对应用层做限时等待的限流。
     *
     * @param rateLimiter
     */
    public void limitTimedWait(RateLimiter rateLimiter) {

        // 尝试获取令牌、等待10毫秒，等待不到返回false
        boolean result = rateLimiter.tryAcquire(10, TimeUnit.MILLISECONDS);

        if (result) {
            System.out.println("成功获取到token");
        } else {
            System.out.println("没有获取到token");
        }

    }

    /**
     * 做一个小样例代表限流的方式。
     *
     * TODO：在本分支下做一个service服务bean，内置RateLimit做限流。用多线程来压测一下，展示限流效果，让代码更臻完善。调节流量用kafka投递消息消费。
     *
     * @param args
     */
    public static void main(String[] args) {
        GuavaRateLimiter grl = new GuavaRateLimiter();

        // type1:阻塞式获取令牌
        grl.limitAndBlock(rateLimiter);

        // type2:尝试获取令牌、获取不到立刻返回false
        grl.limitWithoutWait(rateLimiter);

        // type3:尝试获取令牌、等待10秒，等待不到返回false
        grl.limitTimedWait(rateLimiter);

        // 消息通知限流速率double（初始构造的时候200，承担2倍流量）
        grl.onCall("400");

    }

}