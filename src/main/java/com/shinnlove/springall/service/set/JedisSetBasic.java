/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.set;

import redis.clients.jedis.Jedis;

/**
 * Redis对set集合的操作在Jedis中对应的API。
 * <p>
 * 存储结构：intset(元素个数不大于512个)或hashtable。
 *
 * @author shinnlove.jinsheng
 * @version $Id: JedisSetBasic.java, v 0.1 2019-05-05 18:06 shinnlove.jinsheng Exp $$
 */
public class JedisSetBasic {

    /** jedis单连redis客户端 */
    private static Jedis        jedis          = new Jedis("127.0.0.1", 6379);

    /** 集合操作的key */
    private static final String DISTRIBUTE_KEY = "user:shanghai";

    public static void main(String[] args) {
        addAndRem();
    }

    private static void countSet(String key) {
        long total = jedis.scard(key);
        System.out.println("集合中总元素数量为total=" + total);
    }

    public static void addAndRem() {
        long count = jedis.sadd(DISTRIBUTE_KEY, "tony", "evelyn", "silksnow");
        System.out.println("集合添加元素成功个数count=" + count);

        countSet(DISTRIBUTE_KEY);

        long secondCount = jedis.sadd(DISTRIBUTE_KEY, "shinnlove", "silksnow", "adam");
        System.out.println("集合添加元素成功个数secondCount=" + secondCount);

        countSet(DISTRIBUTE_KEY);
    }

}