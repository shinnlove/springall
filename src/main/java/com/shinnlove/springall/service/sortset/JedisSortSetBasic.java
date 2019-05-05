/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.sortset;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * Redis对sortset集合的操作在Jedis中对应的API。
 *
 * 存储结构：ziplist(元素个数不大于128个或每个元素值小于64KB)或skiplist。
 *
 * 命令格式：zadd key [score element ...]，但在jedis里不能一次加多个score和element组合。
 * 
 * @author shinnlove.jinsheng
 * @version $Id: JedisSortSetBasic.java, v 0.1 2019-05-05 22:13 shinnlove.jinsheng Exp $$
 */
public class JedisSortSetBasic {

    /** jedis单连redis客户端 */
    private static Jedis        jedis       = new Jedis("127.0.0.1", 6379);

    /** 上海地区key */
    private static final String SHANGHAI    = "shanghai";

    /** 北京地区key */
    private static final String BEIJING     = "beijing";

    /** 上海与北京的交集 */
    private static final String SH_INTER_BJ = "sh-inter-bj";

    /** 上海与北京的并集 */
    private static final String SH_UNION_BJ = "sh-union-bj";

    /** 做排序集合的交并补用户-tony */
    private static final String TONY        = "tony";

    /** 做排序集合的交并补用户-shinn */
    private static final String SHINN       = "shinn";

    /** 做排序集合的交并补用户-evelyn */
    private static final String EVELYN      = "evelyn";

    /** 做排序集合的交并补用户-silksnow */
    private static final String SILKSNOW    = "silksnow";

    public static void main(String[] args) {
        interAndUnion();
    }

    public static void interAndUnion() {
        // 上海2个用户
        long result1 = jedis.zadd(SHANGHAI, 36, SHINN);
        long result2 = jedis.zadd(SHANGHAI, 120, EVELYN);

        // tony在上海读了76秒
        long result3 = jedis.zadd(SHANGHAI, 76, TONY);

        // 北京2个用户
        long result4 = jedis.zadd(BEIJING, 52, TONY);
        long result5 = jedis.zadd(BEIJING, 98, SILKSNOW);

        // evelyn在北京读了88秒
        long result6 = jedis.zadd(BEIJING, 88, EVELYN);

        System.out.println("有序集合添加成功元素个数result1=" + result1);

        // 做交集(可以带权重或用聚合函数)
        long interResult = jedis.zinterstore(SH_INTER_BJ, SHANGHAI, BEIJING);
        System.out.println("两个有序集合做交集后result=" + interResult);

        // 交集结果正序输出
        Set<Tuple> interRange = jedis.zrangeByScoreWithScores(SH_INTER_BJ, 0, 500);
        for (Tuple t : interRange) {
            System.out.println("有序集合做交集后，正序结果member=" + t.getElement() + ", score=" + t.getScore());
        }

        // 做并集(可以带权重或用聚合函数)
        long unionResult = jedis.zunionstore(SH_UNION_BJ, SHANGHAI, BEIJING);
        System.out.println("两个有序集合做并集后result=" + unionResult);

        // 并集结果反序输出
        // 特别注意：如果使用revRange，min如果还是0、max是500，是不会输出任何东西的，要配合正反序列从高到低给出!!!
        Set<Tuple> unionRevRange = jedis.zrevrangeByScoreWithScores(SH_UNION_BJ, 500, 0);
        for (Tuple t : unionRevRange) {
            System.out.println("有序集合做并集后，反序结果member=" + t.getElement() + ", score=" + t.getScore());
        }

    }

}