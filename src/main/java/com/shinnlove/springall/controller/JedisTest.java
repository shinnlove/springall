/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

import redis.clients.jedis.Jedis;

/**
 * @author shinnlove.jinsheng
 * @version $Id: JedisTest.java, v 0.1 2018-11-23 下午9:23 shinnlove.jinsheng Exp $$
 */
public class JedisTest {

    public static void main(String[] args) {
        String key = "name";
        String value = "shinnlove";
        Jedis jedis = new Jedis("192.168.0.108", 6379);
        jedis.set(key, value);
        String result = jedis.get(key);
        System.out.println(result);
    }

}