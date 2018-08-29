/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.rpc.integration.httpinvoker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shinnlove.springall.service.UserService;

/**
 * HttpInvoker服务调用。
 *
 * @author shinnlove.jinsheng
 * @version $Id: HttpInvokerClient.java, v 0.1 2018-06-03 下午5:04 shinnlove.jinsheng Exp $$
 */
public class HttpInvokerClient {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
            "/META-INF/spring/httpinvoker-rpc-client.xml");
        UserService userService = (UserService) context.getBean("userServiceProxy");
        System.out.println(userService.getEmailByName("qianqian"));
        System.out.println(userService.getEmailByName("bianbian"));
    }

}