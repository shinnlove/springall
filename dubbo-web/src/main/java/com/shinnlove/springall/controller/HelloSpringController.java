/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

import com.shinnlove.springall.dubbo.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shinnlove.springall.dubbo.service.consumer.HelloServiceConsumer;
import com.shinnlove.springall.util.log.LoggerUtil;

/**
 * 你好，spring mvc。
 *
 * @author shinnlove.jinsheng
 * @version $Id: HelloSpringController.java, v 0.1 2018-08-29 下午1:55 shinnlove.jinsheng Exp $$
 */
@RestController
@RequestMapping(value = "/hello")
public class HelloSpringController {

    /** log4j2日志 */
    private static final Logger  LOGGER = LoggerFactory.getLogger(HelloSpringController.class);

    @Autowired
    private HelloServiceConsumer rpcHelloService;

    @RequestMapping(value = "/log4j2", method = { RequestMethod.GET, RequestMethod.POST })
    public String sayHello() {
        LoggerUtil.info(LOGGER, "你好，我是log4j2的同步日志输出。");
        LoggerUtil.warn(LOGGER, "你好，我的英文名叫shinnlove。");
        LoggerUtil.error(LOGGER, new RuntimeException("这是我自定义的错误"));

        // 调用一下rpc服务
        rpcHelloService.callRpcService();

        return "This is log4j2 test.";
    }

}