/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.string;

import java.util.List;
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
 * jedis中的设置方法set有key和value，第三个参数是nx或xx的字符串，第四个参数是超时时间类型ex或px，第五个参数是long类型的时间。
 * 当设置成功后set方法返回OK字符串、否则就返回null对象。
 * 对于jedis的mset方法成功返回OK，失败返回null；对于msetnx方法多个key/value设置成功返回1，失败返回0。
 * 
 * jedis可以设置字节数组类型的key。
 * 
 * @author shinnlove.jinsheng
 * @version $Id: JedisStringBasic.java, v 0.1 2019-05-01 10:50 shinnlove.jinsheng Exp $$
 */
public class JedisStringBasic {

    /** jedis单连redis客户端 */
    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    public static void main(String[] args) {
        stringSetAndGet();

        existKey();

        modelStringSetAndGet();

        setExpireKey();

        setnxAndSetxx();

        multipleGetValue();

        multipleSetValue();

        incrAndDecr();

        rangeLengthGetSet();
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

    /**
     * 一次get多个值，值的顺序按照key的顺序返回，不存在则为null。
     * 
     * 高并发的场景下，一次读取多个key使用mget命令，可以节省n-1次的网络请求。
     */
    public static void multipleGetValue() {
        String oneKey = "evelyn";
        String twoKey = "shinn";
        String threeKey = "tony";
        String fouthKey = "silksnow";

        // 传入不确定数量的key
        List<String> mgetList = jedis.mget(oneKey, twoKey, threeKey, fouthKey);
        for (int i = 0; i < mgetList.size(); i++) {
            System.out.println("mget命令得到的第" + i + "个value=" + mgetList.get(i));
        }
    }

    /**
     * 一次设置多个key/value值。
     */
    public static void multipleSetValue() {
        String oneKey = "one";
        String oneValue = JSON.toJSONString(new Student(6L, "tony", 27));

        String twoKey = "two";
        String twoValue = JSON.toJSONString(new Student(7L, "silksnow", 27));

        String threeKey = "three";
        String threeValue = JSON.toJSONString(new Student(8L, "evelyn", 27));

        // 第一次直接设置
        String multiResult = jedis.mset(oneKey, oneValue, twoKey, twoValue, threeKey, threeValue);
        System.out.println("使用String的mset批量设置多个key/value结果result=" + multiResult);

        // 第二次用nx设置
        long nxSetResult = jedis.msetnx(oneKey, oneValue, twoKey, twoValue, threeKey, threeValue);
        System.out.println("使用String的msetnx批量设置多个key/value结果result=" + nxSetResult);
    }

    /**
     * Redis对一个值自增与自减，可以在当前目标key上自增自减指定值。
     */
    public static void incrAndDecr() {
        String oneCount = "1-count";
        String twoCount = "2-count";

        long oneResult = jedis.incr(oneCount);
        System.out.println("自增后oneResult=" + oneResult);

        long twoResult = jedis.incrBy(oneCount, 520L);
        System.out.println("自增520后twoResult=" + twoResult);

        long threeResult = jedis.decr(twoCount);
        System.out.println("自减后threeResult=" + threeResult);

        long fourResult = jedis.decrBy(twoCount, 666L);
        System.out.println("从666自减后fourResult=" + fourResult);
    }

    /**
     * Redis其他命令。如：strlen、getSet、setRange、getRange。
     */
    public static void rangeLengthGetSet() {
        String key = "info";
        String value = "lovepest";

        String originResult = jedis.set(key, "originValue");
        System.out.println("首次结果值设置result=" + originResult);

        long length = jedis.strlen(key);
        System.out.println("字符串key的长度length=" + length);

        String getSetResult = jedis.getSet(key, value);
        System.out.println("第二次设置后获取之前的结果result=" + getSetResult);

        long setRangeResult = jedis.setrange(key, 4L, "b");
        System.out.println("字符串偏移量结果result=" + setRangeResult);

        // 注意：起始位置和结束位置都能取到
        String getRangeResult = jedis.getrange(key, 3L, 5L);
        System.out.println("获取偏移量result=" + getRangeResult);

        // 追加数据，append命令返回字符串追加后的长度
        String appendValue = "family";
        long appendResult = jedis.append(key, appendValue);
        System.out.println("append结果result=" + appendResult);

        String currentValue = jedis.get(key);
        System.out.println("当前值result=" + currentValue);
    }

}