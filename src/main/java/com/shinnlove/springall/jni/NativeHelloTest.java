/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jni;

/**
 * 加载本地方法库。
 *
 * 本地方法库不区分大小写，驼峰式完全为了好看。
 *
 * @author shinnlove.jinsheng
 * @version $Id: NativeHelloTest.java, v 0.1 2019-03-17 20:59 shinnlove.jinsheng Exp $$
 */
public class NativeHelloTest {

    static {
        System.loadLibrary("JavaNativeHello");
    }

    public static void main(String[] args) {
        JavaNativeHello.greeting();
    }

}