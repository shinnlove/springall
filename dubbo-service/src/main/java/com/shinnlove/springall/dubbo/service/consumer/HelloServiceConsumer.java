/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.dubbo.service.consumer;

import com.shinnlove.springall.dubbo.api.HelloService;

/**
 * dubbo服务消费者。
 *
 * @author shinnlove.jinsheng
 * @version $Id: HelloServiceConsumer.java, v 0.1 2019-03-25 18:45 shinnlove.jinsheng Exp $$
 */
public class HelloServiceConsumer {

    /** dubbo rpc服务 */
    private HelloService rpcHelloService;

    public void callRpcService() {
        String result = rpcHelloService.sayHello("tony");
        System.out.println("rpc service result: " + result);
    }

    /**
     * Setter method for property rpcHelloService.
     *
     * @param rpcHelloService value to be assigned to property rpcHelloService
     */
    public void setRpcHelloService(HelloService rpcHelloService) {
        this.rpcHelloService = rpcHelloService;
    }

}