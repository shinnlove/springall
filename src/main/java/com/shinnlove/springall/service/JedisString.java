/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service;

import java.util.concurrent.TimeUnit;

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
 * a) 原生超时命令
 * setex key seconds value
 * b) 全局锁nx
 * setnx k v  
 * c) 指定更新xx
 * set k v xx
 * 
 * jedis中的设置方法有key和value，第三个参数是nx或xx的字符串，第四个参数是超时时间类型ex或px，第五个参数是long类型的时间。
 * 当设置成功后set方法返回OK字符串、否则就返回null对象。
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

        setExpireKey();

        setnxAndSetxx();
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

        String setResult = jedis.set(key, value);
        System.out.println("设置结果setResult=" + setResult);

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
        System.out.println("序列化测试：从redis获取result=" + result);

        Student s = JSON.parseObject(result, Student.class);
        System.out.println("序列化测试：反序列化后名字name=" + s.getName());
    }

    /**
     * redis超时key的设置。注意ex是秒数，px是毫秒数。
     * 
     * 运行输出：
     * 超时测试等待前：从redis获取exResult={"age":22,"id":2,"name":"tony"}
     * 超时测试等待前：从redis获取pxResult={"age":23,"id":3,"name":"shinn"}
     * 超时测试等待后：从redis获取exResult=null
     * 超时测试等待后：从redis获取pxResult={"age":23,"id":3,"name":"shinn"}
     * 
     */
    public static void setExpireKey() {
        Student exStudent = new Student(2L, "tony", 22);
        Student pxStudent = new Student(3L, "shinn", 23);

        String exKey = "student-ex";
        String exValue = JSON.toJSONString(exStudent);

        String pxKey = "student-px";
        String pxValue = JSON.toJSONString(pxStudent);

        // ex：超时秒数
        jedis.setex(exKey, 5, exValue);

        // px：注意milliseconds是long类型的
        jedis.psetex(pxKey, 5432L, pxValue);

        String exResult = jedis.get(exKey);
        System.out.println("超时测试等待前：从redis获取exResult=" + exResult);

        String pxResult = jedis.get(pxKey);
        System.out.println("超时测试等待前：从redis获取pxResult=" + pxResult);

        // 精确沉睡毫秒
        try {
            TimeUnit.MILLISECONDS.sleep(5100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 注意：超时后再用redis的 get student-ex 得到 nil的结果，代码就直接null了
        String exCurrent = jedis.get(exKey);
        System.out.println("超时测试等待后：从redis获取exResult=" + exCurrent);

        // px描述还没超时，所以能获取到
        String pxCurrent = jedis.get(pxKey);
        System.out.println("超时测试等待后：从redis获取pxResult=" + pxCurrent);
    }

    /**
     * Redis的setnx和set k/v xx。
     */
    public static void setnxAndSetxx() {

        Student silksnow = new Student(4L, "silksnow", 26);
        Student evelyn = new Student(5L, "evelyn", 24);

        String silksnowKey = "silksnow";
        String silksnowValue = JSON.toJSONString(silksnow);

        String evelynKey = "evelyn";
        String evelynValue = JSON.toJSONString(evelyn);

        // 第一次可以设置
        long first = jedis.setnx(silksnowKey, silksnowValue);
        printResult(first);

        // 第二次无法设置
        long second = jedis.setnx(silksnowKey, silksnowValue);
        printResult(second);

        // 直接设置xx失败返回null
        String third = jedis.set(evelynKey, evelynValue, "xx");
        System.out.println("set key value xx的结果third=" + third);

        // 故意设置evely的key为silksnow
        String fourth = jedis.set(evelynKey, silksnowValue);
        System.out.println("普通set的结果fourth=" + fourth);

        // 再做更新，成功返回"OK"的字符串
        String fifth = jedis.set(evelynKey, evelynValue, "xx");
        System.out.println("set key value xx的结果fifth=" + fifth);
    }

    private static void printResult(Long result) {
        if (result == 1) {
            System.out.println("setnx设置成功");
        } else {
            System.out.println("setnx设置失败");
        }
    }

}