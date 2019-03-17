/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jni;

/**
 * Java本地方法调用。
 *
 * @author shinnlove.jinsheng
 * @version $Id: JavaNativeHello.java, v 0.1 2019-03-17 20:58 shinnlove.jinsheng Exp $$
 */
public class JavaNativeHello {

    public static native void greeting();

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));
    }

}