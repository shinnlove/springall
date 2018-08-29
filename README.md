# springall
spring技术选型集成，不同分支代表不同技术选型，集成了常用技术的spring分支，不再零散。

appcache分支是各类应用级缓存（非分布式缓存的例子。）

# cachedemo

文档持续完善中...

不同应用级缓存：Guava、EhCache、MapDB使用与对比

# 一、缓存特点

## 1、缓存命中率



## 2、缓存回收策略





# 二、缓存类型与应用

## 1、堆缓存

一般使用Java堆来存储较热点的需要缓存对象。技术选型：`Guava`、`EhCache`和`MapDB`。

优点：

- 没有序列化与反序列化

缺点：

- 存储容量受限于堆空间大小

- 会影响`GC`时间

## 2、堆外缓存

缓存数据存储在堆外。技术选型：`EhCache`和`MapDB`。

优点：

- 减少GC暂停时间
- 支持更大的缓存空间（不受堆影响、只受机器内存影响）

缺点：

- 读取数据需要序列化和反序列化

## 3、磁盘缓存

存储在磁盘上，JVM重启时数据还在。技术选型：`EhCache`和`MapDB`。

## 4、分布式缓存

多JVM实例情况下缓存问题：

### a) 单机容量问题

合理设置每个JVM中的缓存容量。

### b) 数据一致性问题

允许一定时间内的不一致，缓存过期时间可以保证定期更新数据。



### c) 缓存不命中回源

多实例后DB整体访问量变多，使用一致性哈希分片算法。

解决策略：

- 使用`ehcache-clustered`和`terracotta server`实现Java进程间分布式缓存
- 使用`redis`分布式缓存



## 5、缓存选型

缓存最佳实践的一些建议：

- 单机

存储`最热`的数据到`堆`缓存、`相对热`的数据到`堆外`缓存，`不热`的数据到`磁盘`缓存。

- 集群

存储`最热`的数据到`堆`缓存，`相对热`的数据到堆外缓存，`全量`数据到`分布式`缓存。

# 三、缓存技术选型

## 1、不同选型的特点

实现缓存的技术选型主要有：`Guava`、`EhCache`和`MapDB`。

### a) Guava

只提供堆缓存，小巧灵活，性能最好。**只使用堆缓存建议首选**。

### b) EhCache

提供`堆缓存`、`堆外缓存`、`磁盘缓存`和`分布式缓存`。目前`3.x`版本没有太多API，集群情况下建议使用`2.x`版本。

`2.x`版本不支持堆外缓存，但性能稳定、API多。

### c) MapDB

支持`堆缓存`、`堆外缓存`、`磁盘缓存`。**可以设置多级缓存**。

`Kotlin`编写的嵌入式Java数据库引擎和集合框架，提供了`Maps`、`Sets`、`Lists`、`Queue`和`Bitmaps`等的支持。`ACID`事务、增量备份。

## 2、Guava

`guava`堆缓存的使用方式如下：

```java
Cache<String, String> myCache = CacheBuilder.newBuilder()
    // 并发级别，guava重写了ConcurrentHashMap、ConcurrencyLevel用来设置Segment数量，越大并发能力越强
    .concurrencyLevel(4)
    // 设置TTL（time to live），缓存数据在给定的时间内没有写（创建/覆盖）时，则被回收，定期会回收缓存数据
    .expireAfterWrite(10, TimeUnit.SECONDS)
    // 设置TTI（time to idle），在给定的时间内没有被读/写则被回收，非常热的数据将会一直不回收
    .expireAfterAccess(200, TimeUnit.SECONDS)
    // 最大数量10000条，超过按照LRU进行回收
    .maximumSize(10000)
    // 启动记录统计信息，如命中率等
    .recordStats().build();

myCache.put("shinnlove", "金升");
```

## 3、EhCache

`EhCache`的使用方法：`缓存管理器`+`缓存各种配置`+JVM钩子`close()`。

### a) 快速使用

```java
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
```

### b) 缓存配置

使用`CacheConfigurationBuilder`来创建不同类型的缓存配置。

#### i) 创建缓存类型

- 创建堆缓存

分配1000个数据单元：

`ResourcePoolsBuilder.newResourcePoolsBuilder().heap(1000, EntryUnit.ENTRIES)`

- 创建堆外缓存

分配100MB的堆外内存：

`ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(100, MemoryUnit.MB)`

- 创建磁盘缓存

分配100MB的磁盘缓存：

`ResourcePoolsBuilder.newResourcePoolsBuilder().disk(100, MemoryUnit.MB, true)`

- 配合`terratocca`分布式缓存

```java
CacheManagerBuilder
            .newCacheManagerBuilder().with(
                ClusteringServiceConfigurationBuilder.cluster(URI.create(CACHE_SERVER))
                    .readOperationTimeout(500, TimeUnit.MILLISECONDS).autoCreate()
```

### c) JVM钩子

不论是那种缓存，`EhCache`都需要添加JVM钩子来保证在JVM退出时能正常释放资源或存储到磁盘上。

```java
Runtime.getRuntime().addShutdownHook(new Thread(() -> cacheManager.close()));
```

## 4、MapDB



# 参考文献

## 1、Guava

## 2、EhCache

[EhCache入门](https://www.cnblogs.com/always-online/p/4106160.html)；
[EhCache的spring集成](https://www.cnblogs.com/mxmbk/articles/5162813.html)；
[EhCache示例](https://blog.csdn.net/vbirdbest/article/details/72763048)。

## 3、MapDB

















