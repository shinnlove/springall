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

    /**
     * 无参java本地方法。
     */
    public static native void greeting();

    /**
     * 带参数的打印数据方法。
     *
     * @param width         所打印数据的位数
     * @param precision     数据精度
     * @param x             一个浮点数据
     * @return
     */
    public static native int print(int width, int precision, double x);

    /**
     * 传入一个字符串，将字符串拷贝一遍返回。
     *
     * @param origin        需要拷贝的原始字符串
     * @return
     */
    public static native String doubleString(String origin);

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));
    }

}