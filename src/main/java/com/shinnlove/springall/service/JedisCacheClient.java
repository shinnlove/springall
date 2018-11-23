/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Jedis连接客户端。
 *
 * @author shinnlove.jinsheng
 * @version $Id: JedisCacheClient.java, v 0.1 2018-11-23 下午8:26 shinnlove.jinsheng Exp $$
 */
@Service
public class JedisCacheClient {

    /** 自动注入redis连接池 */
    @Autowired
    private JedisPool jedisPool;

    /**
     * setVExpire(设置key值，同时设置失效时间 秒
     */
    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close(jedis);
        }
    }

    /**
     * (存入redis数据)
     */
    public void expire(String key, String value, Integer times) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            jedis.expire(key, times);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close(jedis);
        }
    }

    /**
     * 删除redis数据
     */
    public void del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close(jedis);
        }
    }

    /**
     * 获取key的值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String s = jedis.get(key);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close(jedis);
        }
        return null;
    }

    /**
     * 释放连接
     * 
     * @param jedis
     */
    public void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
            if (jedis.isConnected()) {
                try {
                    jedis.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}