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
 * @author shinnlove.jinsheng
 * @version $Id: JedisListBasic.java, v 0.1 2019-05-05 16:21 shinnlove.jinsheng Exp $$
 */
public class JedisListBasic {

    /** jedis单连redis客户端 */
    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    public static void main(String[] args) {
        pushInsertRange();
    }

    /**
     * 对redis的list进行左右入队、插入元素、获取范围内元素。
     * 
     * 特别注意：lpush、rpush和linsert这3个命令都是返回入队、插入后list中总元素数量。
     */
    public static void pushInsertRange() {
        String key = "newproduct:2019";

        // 注意元素从右边挨个压入，因此高跟鞋在最后
        long rCount = jedis.rpush(key, "连衣裙", "休闲裤", "高跟鞋");

        // 注意元素从左边挨个压入，因此衬衣在最前
        long lCount = jedis.lpush(key, "丝袜", "凉鞋", "衬衣");

        // 休闲裤本来是第5个元素，现在在休闲裤后加入了运动衫，运动衫是第6个元素
        long iResult = jedis.linsert(key, BinaryClient.LIST_POSITION.AFTER, "休闲裤", "运动衫");

        System.out.println("添加结果：rCount=" + rCount + ", lCount=" + lCount + ", iResult=" + iResult);

        // 使用list固定的0 -1可以获取从左到右列表所有元素
        List<String> list = jedis.lrange(key, 0, -1);

        int total = list.size();
        for (int i = 0; i < total; i++) {
            System.out.println("列表中第" + i + "个元素为:" + list.get(i));
        }

    }

}