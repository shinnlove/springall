/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.rpc.integration.rmi.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shinnlove.springall.service.UserService;

/**
 * RMI协议客户端。
 *
 * @author shinnlove.jinsheng
 * @version $Id: RMIInvokerClient.java, v 0.1 2018-06-03 下午4:24 shinnlove.jinsheng Exp $$
 */
public class RMIInvokerClient {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
            "/META-INF/spring/rmi-client.xml");
        UserService userService = (UserService) context.getBean("userRmiServiceProxy");
        System.out.println(userService.getEmailByName("qianqian"));
        System.out.println(userService.getEmailByName("bianbian"));
    }

}