/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.cache.basic.guava;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * Guava缓存使用。
 *
 * @author shinnlove.jinsheng
 * @version $Id: GuavaCache.java, v 0.1 2018-07-25 下午4:55 shinnlove.jinsheng Exp $$
 */
public class GuavaCache {

    public static void main(String[] args) {

        Cache<String, String> myCache = CacheBuilder.newBuilder()
        // 并发级别，guava重写了ConcurrentHashMap、ConcurrencyLevel用来设置Segment数量，越大并发能力越强
            .concurrencyLevel(4)
            // 设置TTL（time to live），缓存数据在给定的时间内没有写（创建/覆盖）时，则被回收，定期会回收缓存数据
            .expireAfterWrite(10, TimeUnit.SECONDS)
            // 设置TTI（time to idle），在给定的时间内没有被读/写则被回收，非常热的数据将会一直不回收
            .expireAfterAccess(200, TimeUnit.SECONDS)
            // 最大数量10000条，超过按照LRU进行回收
            .maximumSize(10000)
            // 启动记录统计信息，如命中率等
            .recordStats().build();

        myCache.put("shinnlove", "金升");

    }

}