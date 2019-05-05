/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.set;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * Redis对set集合的操作在Jedis中对应的API。
 * 
 * 存储结构：intset(元素个数不大于512个)或hashtable。
 * 
 * 命令格式：sadd key [element ...]
 * 
 * 集合的操作并不多，特别适合做元素打标后的交并差操作。
 *
 * @author shinnlove.jinsheng
 * @version $Id: JedisSetBasic.java, v 0.1 2019-05-05 18:06 shinnlove.jinsheng Exp $$
 */
public class JedisSetBasic {

    /** jedis单连redis客户端 */
    private static Jedis        jedis          = new Jedis("127.0.0.1", 6379);

    /** 集合操作的key */
    private static final String DISTRIBUTE_KEY = "user:shanghai";

    /** 用户tony、集合做交并补的key1 */
    private static final String USER_TONY      = "user:tony:12345";

    /** 用户shinn、集合做交并补的key2 */
    private static final String USER_SHINN     = "user:shinn:65432";

    public static void main(String[] args) {
        addAndRem();

        isMemberRandAndPop();

        smembers();

        interUnionDiff();
    }

    /**
     * 计算集合中某个key下元素的数量。
     * 
     * @param key
     */
    private static void countSet(String key) {
        long total = jedis.scard(key);
        System.out.println("集合中总元素数量为total=" + total);
    }

    /**
     * 向集合中某个key添加或移除元素。
     * 
     * 添加或删除成功都会返回成功数量、如果为0代表失败。
     */
    public static void addAndRem() {
        long count = jedis.sadd(DISTRIBUTE_KEY, "tony", "evelyn", "silksnow");
        System.out.println("集合添加元素成功个数count=" + count);

        countSet(DISTRIBUTE_KEY);

        long secondCount = jedis.sadd(DISTRIBUTE_KEY, "shinnlove", "silksnow", "adam");
        System.out.println("集合添加元素成功个数secondCount=" + secondCount);

        countSet(DISTRIBUTE_KEY);
    }

    /**
     * 判断某元素是否属于集合、随机返回与弹出集合，都支持count数量。
     * 
     */
    public static void isMemberRandAndPop() {
        // 判断某个成员是不是集合元素
        String memberName = "shinnlove";
        boolean is = jedis.sismember(DISTRIBUTE_KEY, memberName);
        System.out.println("memberName=" + memberName + "is set's member is " + is);

        // 随机返回如果不带count、则默认是1
        List<String> randoms = jedis.srandmember(DISTRIBUTE_KEY, 3);
        for (String s : randoms) {
            System.out.println("集合中随机返回元素：" + s);
        }

        // 3.2版本spop也支持count参数，这里jedis版本高也支持
        String one = jedis.spop(DISTRIBUTE_KEY);
        System.out.println("元素" + one + "从集合中被弹出并删除。");
    }

    /**
     * 获取集合某个key的所有元素。
     * 
     * 特别注意：这个命令与hgetall和lrange一样，都存在大量key阻塞的风险，可以建议用scan命令去遍历。
     */
    public static void smembers() {
        Set<String> members = jedis.smembers(DISTRIBUTE_KEY);
        for (String s : members) {
            System.out.println("集合中有元素：" + s);
        }
    }

    /**
     * 不同集合之间的交并差。
     * 
     * 特别注意：在前面的集合总是占有主动、如做差的时候去掉的是inter交集部分。
     */
    public static void interUnionDiff() {
        // 两个不同标签的用户
        long tonyCount = jedis.sadd(USER_TONY, "旅游", "篮球", "小霸王学习机", "哄女友", "逛街");
        System.out.println("tony一共有" + tonyCount + "个爱好。");

        long shinnCount = jedis.sadd(USER_SHINN, "旅游", "跑步", "爱老婆", "逛街", "乒乓球");
        System.out.println("shinn一共有" + shinnCount + "个爱好。");

        // 交集
        Set<String> inters = jedis.sinter(USER_TONY, USER_SHINN);
        for (String s : inters) {
            System.out.println("两个人相同的兴趣爱好有：" + s);
        }

        // 并集
        Set<String> unions = jedis.sunion(USER_TONY, USER_SHINN);
        for (String s : unions) {
            System.out.println("两个人加起来的兴趣爱好有：" + s);
        }

        // 差集（A集合中扣除B集合有的）
        Set<String> diffs = jedis.sdiff(USER_TONY, USER_SHINN);
        for (String s : diffs) {
            System.out.println("tony diff shinn的兴趣爱好有：" + s);
        }
    }

}