/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.shinnlove.springall.model.Student;

/**
 * Redis对字符串的操作在Jedis中对应的API。
 * 
 * 命令格式：
 * set key value [ex seconds] [px milliseconds] [nx|xx]
 * 
 * 字符串命令很简单，就是key和序列化的value存入。
 * ex是过期的秒数、px是精确过期的毫秒数。
 * nx代表不存在才能设置成功；xx则是必须存在才可以设置成功、用于更新。
 * 
 * @author shinnlove.jinsheng
 * @version $Id: JedisString.java, v 0.1 2019-05-01 10:50 shinnlove.jinsheng Exp $$
 */
public class JedisString {

    /** jedis单连redis客户端 */
    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    public static void main(String[] args) {
        stringSetAndGet();

        existKey();

        modelStringSetAndGet();
    }

    /**
     * 判断key是否存在。jedis允许以此判断若干个key。
     */
    public static void existKey() {
        String key = "name";
        boolean exist = jedis.exists(key);
        System.out.println(exist);
    }

    /**
     * 以字符串的方式读写K/V。注意value必须是字符串或者序列化后的对象。
     */
    public static void stringSetAndGet() {
        // 普通字符串的序列化
        String key = "name";
        String value = "shinnlove";

        jedis.set(key, value);
        String result = jedis.get(key);
        System.out.println("name=" + key + ", value=" + result);
    }

    /**
     * 领域模型序列化存入jedis。
     */
    public static void modelStringSetAndGet() {
        // 领域对象的序列化
        Student student = new Student(1L, "shinnlove", 20);

        String key = "student";
        String value = JSON.toJSONString(student);
        jedis.set(key, value);

        String result = jedis.get(key);
        System.out.println("从redis获取result=" + result);

        Student s = JSON.parseObject(result, Student.class);
        System.out.println("反序列化后名字name=" + s.getName());
    }

}