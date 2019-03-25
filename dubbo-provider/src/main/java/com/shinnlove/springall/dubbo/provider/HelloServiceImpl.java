/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.dubbo.provider;

import com.shinnlove.springall.dubbo.api.HelloService;

/**
 * 服务提供者具体的服务实现。
 *
 * @author shinnlove.jinsheng
 * @version $Id: HelloServiceImpl.java, v 0.1 2019-03-25 16:23 shinnlove.jinsheng Exp $$
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name + ".";
    }

}