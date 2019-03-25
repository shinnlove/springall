/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.impl;

import com.shinnlove.springall.service.HelloService;

/**
 * 服务具体实现。
 *
 * @author shinnlove.jinsheng
 * @version $Id: HelloServiceImpl.java, v 0.1 2019-03-25 14:44 shinnlove.jinsheng Exp $$
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name + ".";
    }

}