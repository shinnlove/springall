/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.list;

import java.util.List;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

/**
 * Redis对list的操作在Jedis中对应的API。
 * 
 * 存储结构：ziplist或linkedlist。
 * 
 * 特别注意：list做range的时候左右都是闭区间、且list元素下标从0开始。
 * 
 * lrem的时候count=0代表删除所有的、count不为0取绝对值次数从左到右删除。
 * 
 * @author shinnlove.jinsheng
 * @version $Id: JedisListBasic.java, v 0.1 2019-05-05 16:21 shinnlove.jinsheng Exp $$
 */
public class JedisListBasic {

    /** jedis单连redis客户端 */
    private static Jedis        jedis        = new Jedis("127.0.0.1", 6379);

    /** 新品key */
    private static final String NEW_ARRIVALS = "newproduct:2019";

    public static void main(String[] args) {
        pushInsertRange();

        indexLenPop();

        remAndTrim();
    }

    /**
     * 打印list列表中全量的数据。
     * 
     * @param key
     */
    private static void printFullList(String key) {
        // 使用list固定的0 -1可以获取从左到右列表所有元素
        List<String> list = jedis.lrange(key, 0, -1);

        int total = list.size();
        for (int i = 0; i < total; i++) {
            System.out.println("全量列表中第" + i + "个元素为:" + list.get(i));
        }
    }

    /**
     * 对redis的list进行左右入队、插入元素、获取范围内元素。
     * 
     * 特别注意：lpush、rpush和linsert这3个命令都是返回入队、插入后list中总元素数量。
     */
    public static void pushInsertRange() {

        // 注意元素从右边挨个压入，因此高跟鞋在最后
        long rCount = jedis.rpush(NEW_ARRIVALS, "连衣裙", "休闲裤", "高跟鞋");

        // 注意元素从左边挨个压入，因此衬衣在最前
        long lCount = jedis.lpush(NEW_ARRIVALS, "丝袜", "凉鞋", "衬衣");

        // 休闲裤本来是第5个元素，现在在休闲裤后加入了运动衫，运动衫是第6个元素
        long iResult = jedis.linsert(NEW_ARRIVALS, BinaryClient.LIST_POSITION.AFTER, "休闲裤", "运动衫");

        System.out.println("添加结果：rCount=" + rCount + ", lCount=" + lCount + ", iResult=" + iResult);

        printFullList(NEW_ARRIVALS);

        // list是一个左右闭区间，下标从0开始
        List<String> otherList = jedis.lrange(NEW_ARRIVALS, 2, 4);
        int num = otherList.size();
        for (int i = 0; i < num; i++) {
            System.out.println("start=2,end=4的列表中第" + i + "个元素为:" + otherList.get(i));
        }

    }

    /**
     * 对list数据结构的取下标、计算长度和左右出队操作。
     * 
     * 特别注意：blpop和brpop是阻塞版本、可以设置阻塞timeout的时间、而最先执行命令的客户端可以最先获得可弹出的元素。
     * 如果blpop和brpop同时观察多个key，则只要有一个key先满足可以弹出条件，客户端立刻先返回这个key弹出的元素。
     */
    public static void indexLenPop() {
        // 统计
        long listLen = jedis.llen(NEW_ARRIVALS);
        System.out.println("当前list中元素数量为" + listLen + "个。");

        // 取下标
        int index = 3;
        String element = jedis.lindex(NEW_ARRIVALS, index);
        System.out.println("第" + index + "个元素值是" + element);

        String lPopElement = jedis.lpop(NEW_ARRIVALS);
        String rPopElement = jedis.rpop(NEW_ARRIVALS);

        System.out.println("左边出队元素是：" + lPopElement + "，右边出队元素是：" + rPopElement);

        // 出队后所有元素下标重新变化
        printFullList(NEW_ARRIVALS);
    }

    /**
     * 删除列表中的元素或者对列表进行剪裁修剪。
     */
    public static void remAndTrim() {
        printFullList(NEW_ARRIVALS);

        // index是下标，第index+1个元素
        String setResult = jedis.lset(NEW_ARRIVALS, 2, "雪纺裙");
        System.out.println("设置list下标元素result=" + setResult);

        // 再次打印全量列表
        printFullList(NEW_ARRIVALS);

        // count值为0代表删除所有
        long remCount = jedis.lrem(NEW_ARRIVALS, 0, "雪纺裙");
        System.out.println("list删除命令lrem删除了" + remCount + "个元素");

        // 裁剪list列表，原来5个元素，现在只剩下2，3，4这4个元素了
        String result = jedis.ltrim(NEW_ARRIVALS, 1, 3);
        System.out.println("list的trim命令裁剪列表结果result=" + result);

        // 列表裁剪后
        printFullList(NEW_ARRIVALS);
    }

}