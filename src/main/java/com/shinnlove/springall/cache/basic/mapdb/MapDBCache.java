/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.cache.basic.mapdb;

import java.util.concurrent.TimeUnit;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

/**
 * MapDB缓存。
 *
 * MapDB基于`Kotlin`来实现。
 *
 * 使用`MapDB`的时候还是要密切注意JVM性能!
 *
 * @author shinnlove.jinsheng
 * @version $Id: MapDBCache.java, v 0.1 2018-07-25 下午5:51 shinnlove.jinsheng Exp $$
 */
public class MapDBCache {

    public static final String FILE_PATH = "/Users/zhaochensheng/Downloads/MapDBCache.bak";

    public static void main(String[] args) {
        heapCache();

        directCache();

        diskCache();

        cascadeCache();
    }

    /**
     * 堆缓存。
     *
     * 使用heapDB创建堆缓存，使用memoryDB创建1MB左右的堆缓存。
     *
     */
    public static void heapCache() {
        HTreeMap heapCache = DBMaker.heapDB()
        // 并发级别
            .concurrencyScale(16).make().hashMap("heapCache")
            // 缓存的容量，当超过时使用LRU
            .expireMaxSize(10000)
            // 设置TTL（time to live），缓存数据在给定时间内没有写（创建或覆盖）则回收
            .expireAfterCreate(10, TimeUnit.SECONDS)
            // TTL
            .expireAfterUpdate(10, TimeUnit.SECONDS)
            // TTI（time to idle），缓存数据在给定时间没没有读、写则被回收
            .expireAfterGet(10, TimeUnit.SECONDS).create();

        // 可以配置.expireExecutor(定时线程池).expireExecutorPeriod(3000)
    }

    /**
     * 堆外缓存。
     *
     * 堆外缓存JVM启动参数：-XX:MaxDirectMemorySize=10G
     */
    public static void directCache() {

        HTreeMap directCache = DBMaker.memoryDirectDB().concurrencyScale(16).make()
            .hashMap("directCache").expireStoreSize(64 * 1024 * 1024).expireMaxSize(10000)
            .expireAfterCreate(10, TimeUnit.SECONDS).expireAfterUpdate(10, TimeUnit.SECONDS)
            .expireAfterGet(10, TimeUnit.SECONDS).create();

    }

    /**
     * 磁盘缓存。
     */
    public static void diskCache() {
        // 在磁盘文件上创建仓储
        DB db = DBMaker.fileDB(FILE_PATH)
        // 启用mmap
            .fileMmapEnable()
            // 在支持的平台上启用mmap
            .fileMmapEnableIfSupported()
            // 让mmap文件更快
            .fileMmapPreclearDisable()
            // 一些bug处理
            .cleanerHackEnable()
            // 启用事务
            .transactionEnable()
            // 增加JVM钩子
            .closeOnJvmShutdown()
            // 并发级别16
            .concurrencyScale(16).make();

        // 创建缓存
        HTreeMap myCache = db.hashMap("diskCache").expireMaxSize(10000)
            .expireAfterCreate(10, TimeUnit.SECONDS).expireAfterUpdate(10, TimeUnit.SECONDS)
            .expireAfterGet(10, TimeUnit.SECONDS).createOrOpen();

        myCache.put("shinnlove", "金升");

        // 开启了事务，MapDb开启了WAL，操作完缓存后要调用`db.commit()`方法提交事务
        db.commit();
    }

    /**
     * MapDB多级缓存，当堆存储溢出的时候存储到磁盘存储。
     */
    public static void cascadeCache() {
        // 堆存储DB
        DB heapDB = DBMaker.heapDB()
        // 并发级别
            .concurrencyScale(16).make();

        // 磁盘存储
        DB diskDB = DBMaker.fileDB(FILE_PATH)
        // 启用mmap
            .fileMmapEnable()
            // 在支持的平台上启用mmap
            .fileMmapEnableIfSupported()
            // 让mmap文件更快
            .fileMmapPreclearDisable()
            // 一些bug处理
            .cleanerHackEnable()
            // 启用事务
            .transactionEnable()
            // 增加JVM钩子
            .closeOnJvmShutdown()
            // 并发级别16
            .concurrencyScale(16).make();

        // 磁盘存储
        HTreeMap diskCache = diskDB.hashMap("diskCache").expireStoreSize(8 * 1024 * 1024 * 1024)
            .expireMaxSize(10000).expireAfterCreate(10, TimeUnit.SECONDS)
            .expireAfterUpdate(10, TimeUnit.SECONDS).expireAfterGet(10, TimeUnit.SECONDS)
            .createOrOpen();

        // 堆存储
        HTreeMap heapCache = heapDB.hashMap("heapCache").expireMaxSize(100)
            .expireAfterCreate(10, TimeUnit.SECONDS).expireAfterUpdate(10, TimeUnit.SECONDS)
            .expireAfterGet(10, TimeUnit.SECONDS)
            // 当缓存溢出时存储到disk
            .expireOverflow(diskCache).createOrOpen();

        // 优先操作`heapCache`，溢出时会自动操作`diskCache`
    }

}