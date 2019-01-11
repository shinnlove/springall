/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.controller.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.shinnlove.springall.util.anotations.AspectLog;
import com.shinnlove.springall.util.consts.SystemConsts;
import com.shinnlove.springall.util.enums.LogType;
import com.shinnlove.springall.util.log.LoggerUtil;

/**
 * AOP联盟日志切面，config @see dispatch-servlet.xml。
 *
 * @author shinnlove.jinsheng
 * @version $Id: AopLogInterceptor.java, v 0.1 2018-11-23 下午4:25 shinnlove.jinsheng Exp $$
 */
public class AopLogInterceptor implements MethodInterceptor {

    /** 控制器切面日志 */
    private static Logger logger = LoggerFactory.getLogger(AopLogInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 切面基础属性
        String packageName = invocation.getMethod().getDeclaringClass().getPackage().getName();
        String className = invocation.getMethod().getDeclaringClass().getSimpleName();
        Method method = invocation.getMethod();
        String methodName = method.getName();
        Object[] args = invocation.getArguments();

        // 放过不打标签的
        AspectLog logTag = method.getAnnotation(AspectLog.class);
        if (logTag == null) {
            return invocation.proceed();
        }
        // 注解信息
        String logType = logTag.logType();
        String keyword = logTag.keyword();

        // 基础日志信息
        StringBuilder sb = new StringBuilder();
        sb.append("SAL接口请求").append(packageName).append(".").append(className).append(".")
            .append(methodName).append(",入参[args=")
            .append(ToStringBuilder.reflectionToString(args)).append("]");

        // 切面调用
        String successFlag = "Y";
        Object result = null;
        long start = System.currentTimeMillis();
        try {
            result = invocation.proceed();

            // 准备抽血
            StringBuilder extract = new StringBuilder();
            if (result instanceof JSONObject) {
                JSONObject o = (JSONObject) result;

                // 基础标记位
                Object flag = o.get(SystemConsts.SUCCESS);
                Object code = o.get(SystemConsts.ERROR_CODE);
                Object msg = o.get(SystemConsts.ERROR_MSG);
                Object data = o.get(SystemConsts.DATA);
                extract.append("success:").append(flag).append(",errCode:").append(code)
                    .append(",errMsg:").append(msg).append(",data:");

                if (data instanceof List) {
                    List list = (List) data;
                    // 结果列表抽样
                    if (LogType.DETAIL.name().equalsIgnoreCase(logType)) {
                        // 全量
                        extract.append(data);
                    } else if (LogType.SIZE.name().equalsIgnoreCase(logType)) {
                        // 只打印数量
                        extract.append("size=").append(list.size());
                    } else if (LogType.KEYWORD.name().equalsIgnoreCase(logType)) {
                        // 关键字
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).toString().indexOf(keyword) > 0) {
                                extract.append(list.get(i).toString());
                            }
                        }
                    } else {
                        // 默认打印digest，最多10条
                        int len = list.size() < 10 ? list.size() : 10;
                        for (int i = 0; i < len; i++) {
                            extract.append(list.get(i).toString());
                        }
                    }
                } else {
                    // 否则打印全量
                    extract.append(data);
                }

            }

            sb.append(",处理结果[result=").append(extract.toString()).append("]");

            return result;
        } catch (Throwable ex) {
            successFlag = "N";
            sb.append(",处理结果发生错误");
            throw ex;
        } finally {
            long elapse = System.currentTimeMillis() - start;
            sb.append(",SAL接口请求处理结果result=").append(successFlag).append(",整个过程耗时ms=[")
                .append(elapse).append("]");
            LoggerUtil.info(logger, sb.toString());
        }
    }

}