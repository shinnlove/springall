/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.ReentrantLock;

/**
 * limitation of query quota.
 * 
 * dependency: query service client
 * 
 * cache data structure: ConcurrentHashMap<String,CurrencyDaily>
 *     
 * interface: 
 *  query from, to => in cache return 
 *                  => not in cache => lock and query
 *                  
 *  evict => background thread pools examine timestamp, fixed time clear
 * 
 * @author Tony Zhao
 * @version $Id: Solutions.java, v 0.1 2021-02-03 2:02 PM Tony Zhao Exp $$
 */
public class CurrencyCache {

    public static class CurrencyServiceClient {

        /**
         * Example, implement by client service jar.
         * 
         * @param fromCurrency 
         * @param toCurrency
         * @return
         */
        public double doQueryCurrency(String fromCurrency, String toCurrency) {
            return 0d;
        }

    }

    // lombok getter/setter
    public static class CurrencyDaily {
        private String fromCurrency;
        private String toCurrency;
        private double value;
        private long   timestamp;

        public CurrencyDaily() {
        }

        public CurrencyDaily(String fromCurrency, String toCurrency, double value, long timestamp) {
            this.fromCurrency = fromCurrency;
            this.toCurrency = toCurrency;
            this.value = value;
            this.timestamp = timestamp;
        }

        /**
         * Getter method for property value.
         *
         * @return property value of value
         */
        public double getValue() {
            return value;
        }
    }

    private Map<String, CurrencyDaily> currencyMap = new ConcurrentHashMap<>();

    // @Autowired bean
    private CurrencyServiceClient      currencyServiceClient;

    //    private Jedis

    // client query lock
    private ReentrantLock              lock        = new ReentrantLock();

    private ExecutorService            bkgExamPool = new ScheduledThreadPoolExecutor(10,
        new ThreadFactory() {
                                                           @Override
                                                           public Thread newThread(Runnable r) {
                                                               // define thread name, group 
                                                               return null;
                                                           }
                                                       });

    // inject by config @property
    private int                        examineIntervalSeconds;

    /**
     * This is the main query service exposed to customer service.
     *
     * RMB -> USD not exist => need query => fetch 1 : 0.14 => put into map
     *
     * @param fromCurrency
     * @param toCurrency
     * @return
     */
    public double queryCurrencyTrans(String fromCurrency, String toCurrency) {
        String currencyKey = fromCurrency + "-" + toCurrency;
        if (currencyMap.containsKey(currencyKey)) {
            CurrencyDaily cd = currencyMap.get(currencyKey);
            // todo: check timestamp
            return cd.getValue();
        }
        // not in cache
        double exchangeValue = 0d;
        try {
            lock.lock();

            exchangeValue = currencyServiceClient.doQueryCurrency(fromCurrency, toCurrency);

        } catch (Exception e) {
            // when error occur
        } finally {
            lock.unlock();
        }

        currencyMap.put(currencyKey,
            new CurrencyDaily(fromCurrency, toCurrency, exchangeValue, System.currentTimeMillis()));
        return exchangeValue;
    }

    // @init
    public void examInterval() {
        bkgExamPool.submit(() -> {
            // examine timestamp
        });
    }

}