/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.anotations;

import java.lang.annotation.*;

import com.shinnlove.springall.controller.interceptor.AopLogInterceptor;

/**
 * 切面日志注解，使用方式：{@link AopLogInterceptor}。
 *
 * @author shinnlove.jinsheng
 * @version $Id: AspectLog.java, v 0.1 2018-11-23 下午2:53 shinnlove.jinsheng Exp $$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AspectLog {

    /**
     * 打日志的类型。
     *
     * @return
     */
    String logType() default "digest";

    /**
     * 当日志类型为keyword时候搜索的字符串，注意结果bean一定要覆盖toString，否则概不负责打印。
     *
     * @return
     */
    String keyword() default "";

}