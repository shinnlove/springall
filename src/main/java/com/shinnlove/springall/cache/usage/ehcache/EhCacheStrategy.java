/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.cache.usage.ehcache;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.*;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.spi.loaderwriter.BulkCacheLoadingException;
import org.ehcache.spi.loaderwriter.BulkCacheWritingException;
import org.ehcache.spi.loaderwriter.CacheLoaderWriter;

import com.shinnlove.springall.cache.service.CategoryService;

/**
 * EhCache缓存应用策略。
 *
 * 特别注意：`EhCache`的3.1版本没有自己解决dog-pile问题。
 *
 * @author shinnlove.jinsheng
 * @version $Id: EhCacheStrategy.java, v 0.1 2018-07-25 下午10:51 shinnlove.jinsheng Exp $$
 */
public class EhCacheStrategy {

    /** spring下注入的服务@AutoWired */
    private static CategoryService categoryService;

    public static void main(String[] args) {
        syncReadWriteThrough();

        singleWriteBehind();

        batchWriteBehind();
    }

    /**
     * ReadThrough外围都是委托给缓存读取，不命中回源读，委托给响应的加载器来解决，注意`dog-pile`效应。
     *
     * `EhCache`委托给了`CacheLoaderWriter`来做`同步`的`read-through`和`write-through`。
     */
    public static void syncReadWriteThrough() {
        // 缓存管理器
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

        // 缓存配置生成器
        CacheConfigurationBuilder<String, String> cacheConfig = CacheConfigurationBuilder
            .newCacheConfigurationBuilder(String.class, String.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(100, MemoryUnit.MB))
            // 并发分发级别4
            .withDispatcherConcurrency(4)
            // TTL 10秒
            .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)))
            // 使用缓存加载写入器来实现没有命中的数据加载
            // load与loadAll是read-through
            // write与writeAll是write-through
            .withLoaderWriter(new CacheLoaderWriter<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    // get方法不命中、从DB中读取数据并返回
                    return null;
                }

                @Override
                public Map<String, String> loadAll(Iterable<? extends String> keys)
                                                                                   throws BulkCacheLoadingException,
                                                                                   Exception {
                    // getAll方法不命中、批量读取返回(支持一次性get多个key)
                    return null;
                }

                @Override
                public void write(String key, String value) throws Exception {
                    // put方法调用时，写数据到源
                }

                @Override
                public void writeAll(Iterable<? extends Map.Entry<? extends String, ? extends String>> entries)
                                                                                                               throws BulkCacheWritingException,
                                                                                                               Exception {
                    // putAll方法调用时，批量写数据到源
                    for (Object entry : entries) {
                        // batch write
                    }
                }

                @Override
                public void delete(String key) throws Exception {
                    // remove方法调用时，从源删除数据
                }

                @Override
                public void deleteAll(Iterable<? extends String> keys)
                                                                      throws BulkCacheWritingException,
                                                                      Exception {
                    // removeAll方法调用时，从源批量删除数据
                    for (String key : keys) {
                        // batch delete
                    }
                }
            });

        // 管理器使用配置生成`EhCache`
        Cache<String, String> myCache = cacheManager.createCache("myCache", cacheConfig);

        // 包装`myCache`给外围使用
        myCache.get("shinnlove");

        Set<String> keys = new HashSet<>();
        keys.add("shinn");
        keys.add("evelyn");
        myCache.getAll(keys);

    }

    /**
     * EhCache单个靠后写。
     *
     * 使用内部线程池去写，使用了队列，可以控制总吞吐量。
     * 注意要合理的根据实际场景设置线程池的拒绝策略。
     */
    public static void singleWriteBehind() {
        // 带执行线程池的缓存管理器
        CacheManager cacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            // 通过`PooledExecutionServiceConfigurationBuilder`来配置线程池
            .using(
                PooledExecutionServiceConfigurationBuilder
                    .newPooledExecutionServiceConfigurationBuilder().pool("writeBehindPool", 1, 5)
                    .build()).build(true);

        // 缓存配置构造器
        CacheConfigurationBuilder cacheConfig = CacheConfigurationBuilder
            .newCacheConfigurationBuilder(String.class, String.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(100, MemoryUnit.MB))
            // 分发级别4
            .withDispatcherConcurrency(4)
            // 过期策略
            .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)))
            // 配置SoR策略
            .withLoaderWriter(new CacheLoaderWriter<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    return null;
                }

                @Override
                public Map<String, String> loadAll(Iterable<? extends String> keys)
                                                                                   throws BulkCacheLoadingException,
                                                                                   Exception {
                    return null;
                }

                @Override
                public void write(String key, String value) throws Exception {
                    // write
                }

                @Override
                public void writeAll(Iterable<? extends Map.Entry<? extends String, ? extends String>> entries)
                                                                                                               throws BulkCacheWritingException,
                                                                                                               Exception {
                    // `newUnBatchedWriteBehindConfiguration`不会调用这个方法
                }

                @Override
                public void delete(String key) throws Exception {
                    // delete
                }

                @Override
                public void deleteAll(Iterable<? extends String> keys)
                                                                      throws BulkCacheWritingException,
                                                                      Exception {

                }
            }).add(
            // `WriteBehindConfigurationBuilder`配置延后写策略
            // 如果配置`newUnBatchedWriteBehindConfiguration`，则所有的操作都会变成`write`和`delete`，不需要覆写`writeAll`或`deleteAll`方法
                WriteBehindConfigurationBuilder.newUnBatchedWriteBehindConfiguration()
                // 异步回写需要先将操作放入等待队列中，定义线程池队列大小
                // 查看源代码，实际能写的并发数量是(concurrent level * queue size = 5 * 2 = 10)
                    .queueSize(5)
                    // 并发级别2（配置多少个并发线程和队列进行`writeBehind`）
                    .concurrencyLevel(2)
                    // 使用`writeBehindPool`线程池
                    .useThreadPool("writeBehindPool").build());

        // 生成异步写的缓存
        Cache<String, String> singleWriteBehind = cacheManager.createCache("singleWriteBehind",
            cacheConfig);

    }

    /**
     * EhCache批量靠后写。
     */
    public static void batchWriteBehind() {
        // 带执行线程池的缓存管理器
        CacheManager cacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            .using(
                PooledExecutionServiceConfigurationBuilder
                    .newPooledExecutionServiceConfigurationBuilder().pool("writeBehindPool", 1, 5)
                    .build()).build(true);

        // 缓存配置构造器
        CacheConfigurationBuilder cacheConfig = CacheConfigurationBuilder
            .newCacheConfigurationBuilder(String.class, String.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(100, MemoryUnit.MB))
            .withDispatcherConcurrency(4)
            .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)))
            // 开启批量回写，会调用`writeAll`和`deleteAll`
            .withLoaderWriter(new CacheLoaderWriter<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    return null;
                }

                @Override
                public Map<String, String> loadAll(Iterable<? extends String> keys)
                                                                                   throws BulkCacheLoadingException,
                                                                                   Exception {
                    // 批量
                    return null;
                }

                @Override
                public void write(String key, String value) throws Exception {
                    // write
                }

                @Override
                public void writeAll(Iterable<? extends Map.Entry<? extends String, ? extends String>> entries)
                                                                                                               throws BulkCacheWritingException,
                                                                                                               Exception {
                    // 批量
                }

                @Override
                public void delete(String key) throws Exception {
                    // delete
                }

                @Override
                public void deleteAll(Iterable<? extends String> keys)
                                                                      throws BulkCacheWritingException,
                                                                      Exception {
                    // 批量
                }
            }).add(WriteBehindConfigurationBuilder
            // 使用批次提交
            // 批处理大小2、最大延迟3秒——当写操作数据达到批处理大小时、会将这个批次的数据发送给`CacheLoaderWriter`进行处理
            // 所以批次提交最大的数量为：(concurrent level * queue size * batch size = 1 * 5 * 2 = 10)
                .newBatchedWriteBehindConfiguration(3, TimeUnit.SECONDS, 2)
                // 队列5
                .queueSize(5)
                // 并发级别1
                .concurrencyLevel(1)
                // 重要：是否需要合并写，即相同的key是否只写一次
                .enableCoalescing().useThreadPool("writeBehindPool").build());

        // 生成异步写的缓存
        Cache<String, String> batchWriteBehind = cacheManager.createCache("batchWriteBehind",
            cacheConfig);
    }

}