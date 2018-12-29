/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.shinnlove.springall.core.model.Student;
import com.shinnlove.springall.service.KafkaProducerService;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloSpringController.class);

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @RequestMapping(value = "/log4j2", method = { RequestMethod.GET, RequestMethod.POST })
    public String sayHello() {
        LoggerUtil.info(LOGGER, "你好，我是log4j2的同步日志输出。");
        LoggerUtil.warn(LOGGER, "你好，我的英文名叫shinnlove。");
        LoggerUtil.error(LOGGER, new RuntimeException("这是我自定义的错误"));
        return "This is log4j2 test.";
    }

    @RequestMapping(value = "/kafka", method = { RequestMethod.GET, RequestMethod.POST })
    public String sendKafkaMsg() {
        Student student = new Student(1, "shinnlove", 25, "15021237551", "三好学生");
        String info = JSON.toJSONString(student);
        long offset = kafkaProducerService.sendMsg("first", info);
        return "消息已经发送到kafka，消息在分区中的位置offset=" + offset;
    }

}