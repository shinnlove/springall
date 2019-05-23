/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.threads;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 命名线程工厂，这个类存在的目的就是为了持有编号。
 *
 * @author shinnlove.jinsheng
 * @version $Id: NamedThreadFactory.java, v 0.1 2019-03-30 21:54 shinnlove.jinsheng Exp $$
 */
public class NamedThreadFactory implements ThreadFactory {

    /** 静态的，无论何时New这个类，线程池编号是按序的，从1开始 */
    static final AtomicInteger poolNumber   = new AtomicInteger(1);

    /** 每条线程从1开始 */
    final AtomicInteger        threadNumber = new AtomicInteger(1);
    final ThreadGroup          group;
    final String               namePrefix;
    final boolean              isDaemon;

    public NamedThreadFactory() {
        this("diy-default-pool");
    }

    public NamedThreadFactory(String name) {
        this(name, false);
    }

    public NamedThreadFactory(String preffix, boolean daemon) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = preffix + "-" + poolNumber.getAndIncrement() + "-thread-";
        isDaemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        // 创建新线程
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        t.setDaemon(isDaemon);
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }

}