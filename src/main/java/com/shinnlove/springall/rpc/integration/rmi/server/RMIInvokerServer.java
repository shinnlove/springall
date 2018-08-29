/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.rpc.integration.rmi.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * RMI协议服务端。
 * 
 * @author shinnlove.jinsheng
 * @version $Id: RmiInvokerServer.java, v 0.1 2018-06-03 下午4:27 shinnlove.jinsheng Exp $$
 */
public class RMIInvokerServer {

    public static void main(String[] args) throws InterruptedException {

        ApplicationContext context = new ClassPathXmlApplicationContext(
            "/META-INF/spring/rmi-server.xml");
        Thread.sleep(30000);
    }

}