/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.lock.redis;

import java.util.concurrent.TimeUnit;

import org.redisson.ClusterServersConfig;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.RLock;

/**
 * redis分布式锁。
 *
 * @author shinnlove.jinsheng
 * @version $Id: RedissonLock.java, v 0.1 2018-08-15 上午12:07 shinnlove.jinsheng Exp $$
 */
public class RedissonLock {

    private static Config               config;

    private static ClusterServersConfig clusterServersConfig;

    private static String               address = "127.0.0.1:6379";

    static {
        // 导入配置
        config = new Config();
        // 开启集群模式：主、从100，超时时间1秒
        clusterServersConfig = config.useClusterServers();
        clusterServersConfig.addNodeAddress(address);
        clusterServersConfig.setMasterConnectionPoolSize(100);
        clusterServersConfig.setSlaveConnectionPoolSize(100);
        clusterServersConfig.setTimeout(1000);
    }

    public static void main(String[] args) {
        // Redisson客户端
        RedissonClient redisson = null;

        try {
            // 通过配置创建
            redisson = Redisson.create(config);

            // 传入锁的名字返回一把锁
            RLock lock = redisson.getLock("testLock");

            // 阻塞获取锁
            // 特别注意：给出的时间和单位是持有锁的时间，如果需要无限时的持有锁，时间设置为-1即可
            // 特别注意：租约时间很重要，申请锁后节点挂了可以保证超时锁自动释放
            lock.lock(20, TimeUnit.MILLISECONDS);
            // 释放锁
            lock.unlock();

            // 尝试获取锁（等待多少秒）
            boolean result = lock.tryLock(10, 20, TimeUnit.MILLISECONDS);
            // 如果获取成功则返回true
            if (result) {
                // 成功扣减redis的实时库存后，可以发送消息到消息队列，由消费者负责实际库存的减少
                lock.forceUnlock();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (null != redisson) {
                redisson.shutdown();
            }
        }

    }

}