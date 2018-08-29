/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.cache.usage.guava;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.shinnlove.springall.cache.domain.model.Category;
import com.shinnlove.springall.cache.service.CategoryService;

/**
 * Guava缓存应用策略。
 *
 * @author shinnlove.jinsheng
 * @version $Id: GuavaCacheStrategy.java, v 0.1 2018-07-25 下午10:27 shinnlove.jinsheng Exp $$
 */
public class GuavaCacheStrategy {

    /** spring下注入的服务@AutoWired */
    private static CategoryService categoryService;

    public static void main(String[] args) throws Exception {
        readThrough();
    }

    /**
     * ReadThrough外围都是委托给缓存读取，不命中回源读，委托给响应的加载器来解决，注意`dog-pile`效应。
     *
     * `Guava`委托给了`CacheLoader`做`read-through`，没有实现`write-through`。
     */
    public static void readThrough() throws ExecutionException {

        // Guava缓存，键值对
        LoadingCache<Integer, Category> guavaCache = CacheBuilder.newBuilder()
        // 软引用
            .softValues().maximumSize(5000)
            // TTL，2分钟
            .expireAfterWrite(2, TimeUnit.MINUTES)
            // 生成缓存
            // 命中直接返回；没有命中则委托给`CacheLoader`回源到SoR查询源数据（使用`CacheLoader`可以很好的解决缓存的`dog-pile`问题，`guava`自己内部有加锁）
            // guava还支持get传入`Callable`来加载数据，没有命中的时候调用`callable#call()`加载
            .build(new CacheLoader<Integer, Category>() {
                @Override
                public Category load(final Integer sortId) throws Exception {
                    // 特别注意，`CacheLoader`中返回值不能为null，但是可以包装成Null对象
                    return categoryService.getCategoryBySortId(sortId);
                }
            });

        // 缓存`guavaCache`包装后，外围可直接调用
        int id = 1000;
        Category category = guavaCache.get(id);

    }

}