/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.cache.basic.ehcache;

import java.io.File;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.clustered.client.config.builders.ClusteredResourcePoolBuilder;
import org.ehcache.clustered.client.config.builders.ClusteringServiceConfigurationBuilder;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.PooledExecutionServiceConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.impl.config.persistence.CacheManagerPersistenceConfiguration;

/**
 * EhCache缓存。
 *
 * `EhCache`使用方法：缓存管理器+缓存各种配置+JVM钩子`close()`。
 *
 * 缓存的配置：
 * 都是使用`CacheConfigurationBuilder`创建方法`newCacheConfigurationBuilder()`，传入key、value类型，
 * 资源使用`ResourcePoolsBuilder`的`newResourcePoolsBuilder()`的`heap()`或`offheap()`方法来创建堆内存或者堆外内存。
 * 然后是分发、过期、遍历、对象大小等属性的设置。
 *
 * 特别注意：要添加JVM钩子。
 *
 * @author shinnlove.jinsheng
 * @version $Id: EhCache.java, v 0.1 2018-07-25 下午5:21 shinnlove.jinsheng Exp $$
 */
public class EhCache {

    public static final String FILE_PATH    = "/Users/zhaochensheng/Downloads/EhCache.bak";

    public static final String CACHE_SERVER = "terracotta://192/168/147/50:9510";

    public static void main(String[] args) {
        heapCache();

        directCache();

        diskCache();

        distributeCache();
    }

    /**
     * EhCache堆缓存。
     */
    public static void heapCache() {
        // 缓存管理
        final CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

        // 缓存配置构造器属性
        CacheConfigurationBuilder<String, String> cacheConfig = CacheConfigurationBuilder
            .newCacheConfigurationBuilder(String.class, String.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(1000, EntryUnit.ENTRIES))
            .withDispatcherConcurrency(4)
            .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)));

        // 创建缓存
        Cache<String, String> heapCache = cacheManager.createCache("heapCache", cacheConfig);

        // `CacheManager`在JVM关闭时调用close()释放资源，这是显示注册关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> cacheManager.close()));
    }

    /**
     * 分配直接内存。
     */
    public static void directCache() {
        // 缓存管理器
        final CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();

        // 堆外缓存配置
        CacheConfigurationBuilder<String, String> cacheConfig = CacheConfigurationBuilder
            .newCacheConfigurationBuilder(String.class, String.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(100, MemoryUnit.MB))
            // 分发并发级4
            .withDispatcherConcurrency(4)
            // 生存时间10秒
            .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)))
            // 统计对象大小时对象图遍历深度
            .withSizeOfMaxObjectGraph(3)
            // 可缓存的最大对象大小
            .withSizeOfMaxObjectSize(1, MemoryUnit.KB);

        // Warning: EhCache不支持基于容量的缓存过期策略
        Cache<String, String> directCache = cacheManager.createCache("directCache", cacheConfig);

        // 关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> cacheManager.close()));
    }

    /**
     * 磁盘文件缓存。
     *
     * 特别注意：JVM关闭的时候要调用cacheManager.close()保证内存数据dump到磁盘。
     */
    public static void diskCache() {
        // 缓存管理器
        final CacheManager cacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            // 默认线程池
            .using(
                PooledExecutionServiceConfigurationBuilder
                    .newPooledExecutionServiceConfigurationBuilder().defaultPool("default", 1, 1)
                    .build())
            // 磁盘文件存储位置
            .with(new CacheManagerPersistenceConfiguration(new File(FILE_PATH))).build();

        // 缓存配置构造器属性
        CacheConfigurationBuilder<String, String> cacheConfig = CacheConfigurationBuilder
            .newCacheConfigurationBuilder(String.class, String.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                // 磁盘缓存
                    .disk(100, MemoryUnit.MB, true))
            // 使用线程池dump文件到磁盘
            .withDiskStoreThreadPool("default", 5)
            // TTL生存时间50秒
            .withExpiry(Expirations.timeToLiveExpiration(Duration.of(50, TimeUnit.SECONDS)))
            // 统计对象大小时对象图遍历深度
            .withSizeOfMaxObjectGraph(3)
            // 可缓存的最大对象大小
            .withSizeOfMaxObjectSize(1, MemoryUnit.KB);

        // 创建缓存
        Cache<String, String> diskCache = cacheManager.createCache("diskCache", cacheConfig);

        // 添加JVM关闭钩子，将内存数据dump到磁盘!
        Runtime.getRuntime().addShutdownHook(new Thread(() -> cacheManager.close()));
    }

    /**
     * 分布式缓存。
     *
     * `EhCache`一般配合`terracotta`集群使用，大并发的生产环境建议使用`redis`主从或集群模式。
     */
    public static void distributeCache() {
        // 分布式持久化缓存管理器(初始化分布式连接)
        CacheManagerBuilder<PersistentCacheManager> clusteredCacheManagerBuild = CacheManagerBuilder
            .newCacheManagerBuilder().with(
                ClusteringServiceConfigurationBuilder.cluster(URI.create(CACHE_SERVER))
                    .readOperationTimeout(500, TimeUnit.MILLISECONDS).autoCreate());

        final PersistentCacheManager persistentCacheManager = clusteredCacheManagerBuild
            .build(true);

        // 缓存配置构造器属性
        CacheConfigurationBuilder<String, String> cacheConfig = CacheConfigurationBuilder
            .newCacheConfigurationBuilder(
                String.class,
                String.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().with(
                    ClusteredResourcePoolBuilder.clusteredDedicated("cache", 32, MemoryUnit.MB)))
            // 缓存分发级别4
            .withDispatcherConcurrency(4)
            // TTL10秒
            .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)));

        Cache<String, String> distributeCache = persistentCacheManager.createCache(
            "distributeCache", cacheConfig);

        // 添加JVM关闭钩子，将内存数据dump到磁盘!
        Runtime.getRuntime().addShutdownHook(new Thread(() -> persistentCacheManager.close()));
    }

}