/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service;

/**
 * @author shinnlove.jinsheng
 * @version $Id: TestClassForStatic.java, v 0.1 2019-08-23 16:22 shinnlove.jinsheng Exp $$
 */
public final class TestClassForStatic {

    private static TestClassForStatic instance = new TestClassForStatic();

    private TestClassForStatic() {

    }

    public static TestClassForStatic getInstance() {
        return instance;
    }

    public String getInfo() {
        return "hello";
    }

}