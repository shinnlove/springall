/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截处理拦截器。
 *
 * @author shinnlove.jinsheng
 * @version $Id: LoginHandleInterceptor.java, v 0.1 2019-04-10 19:53 shinnlove.jinsheng Exp $$
 */
public class LoginHandleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String sessionId = request.getRequestedSessionId();
        System.out.println("sessionId=" + sessionId);

        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            String domain = c.getDomain();
            String name = c.getName();
            String value = c.getValue();
            System.out.println("Cookie[" + name + "] from Domain[" + domain + "] value is ["
                               + value + "]");
        }
        // return false 就直接把请求拦截了。
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

    }

}