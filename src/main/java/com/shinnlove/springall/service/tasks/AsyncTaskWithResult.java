/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.tasks;

import java.util.concurrent.Callable;

/**
 * 异步有返回结果的多线程能力。
 *
 * @author shinnlove.jinsheng
 * @version $Id: AsyncTaskWithResult.java, v 0.1 2017-07-31 下午3:44 shinnlove.jinsheng Exp $$
 */
public abstract class AsyncTaskWithResult<T> implements Callable<T> {

    /**
     * @see Callable#call()
     */
    @Override
    public T call() throws Exception {
        return doAsyncTask();
    }

    /**
     * 提供一种异步有返回结果的多线程能力。
     *
     * @return
     */
    public abstract T doAsyncTask();

}