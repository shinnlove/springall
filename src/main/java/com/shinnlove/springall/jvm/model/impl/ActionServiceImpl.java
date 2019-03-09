/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jvm.model.impl;

import com.shinnlove.springall.jvm.model.ActionService;

/**
 * 动作服务实现类。
 *
 * @author shinnlove.jinsheng
 * @version $Id: ActionServiceImpl.java, v 0.1 2019-03-09 16:23 shinnlove.jinsheng Exp $$
 */
public class ActionServiceImpl implements ActionService {

    private static final String FULL_NAME = "Micheal Jordan";

    private long                id;

    private String              address;

    private float               score;

    private double              money;

    @Override
    public String sayHello(String toWho) {
        return "你好，" + toWho;
    }

    public static void main(String[] args) {
        System.out.println("你好");
    }

}