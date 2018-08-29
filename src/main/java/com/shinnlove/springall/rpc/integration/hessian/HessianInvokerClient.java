/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.rpc.integration.hessian;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shinnlove.springall.service.UserService;

/**
 * Hessian调用客户端。
 *
 * @author shinnlove.jinsheng
 * @version $Id: HessianInvokerClient.java, v 0.1 2018-06-03 下午5:35 shinnlove.jinsheng Exp $$
 */
public class HessianInvokerClient {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(
            "/META-INF/spring/hessian-rpc-client.xml");
        UserService userService = (UserService) context.getBean("hessianUserService");
        System.out.println(userService.getEmailByName("qianqian"));
        System.out.println(userService.getEmailByName("bianbian"));
    }

}