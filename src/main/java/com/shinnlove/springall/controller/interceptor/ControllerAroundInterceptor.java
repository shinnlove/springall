/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.controller.interceptor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shinnlove.springall.util.log.LoggerUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制器环绕拦截器，可以做一些前置校验。
 *
 * @author shinnlove.jinsheng
 * @version $Id: ControllerAroundInterceptor.java, v 0.1 2018-11-23 下午3:17 shinnlove.jinsheng Exp $$
 */
public class ControllerAroundInterceptor implements HandlerInterceptor {

    /** 控制器日志 */
    private static Logger logger = LoggerFactory.getLogger(ControllerAroundInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        HandlerMethod requestMethod = ((HandlerMethod) handler);
        MethodParameter[] methodParameters = requestMethod.getMethodParameters();
        Method method = requestMethod.getMethod();

        // 反射切入修改值

        LoggerUtil.info(logger, "进入控制器前置处理");

        // 放行请求
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        LoggerUtil.info(logger, "进入控制器后置处理");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        LoggerUtil.info(logger, "进入控制器模板渲染完处理");
    }

}