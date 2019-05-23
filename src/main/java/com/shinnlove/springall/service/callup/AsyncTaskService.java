/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.callup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.shinnlove.springall.service.tasks.AsyncTaskWithResult;
import com.shinnlove.springall.service.threads.ThreadPoolUtil;

/**
 * @author shinnlove.jinsheng
 * @version $Id: AsyncTaskService.java, v 0.1 2019-05-23 13:06 shinnlove.jinsheng Exp $$
 */
public class AsyncTaskService {

    /** spring中构造注入哈 */
    private static ExecutorService bizExecutor = ThreadPoolUtil.createPool(10, 50, 100, "美国队长");

    public static void main(String[] args) throws Exception {

        System.out.println("美国队长");

        Future<String> f = bizExecutor.submit(new AsyncTaskWithResult<String>() {
            @Override
            public String doAsyncTask() {
                return "惊奇队长";
            }
        });

        System.out.println(f.get());

        System.out.println("奇异博士");

    }

}