/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jni;

/**
 * 加载本地方法库。
 *
 * 本地方法库不区分大小写，驼峰式完全为了好看。
 * 这里将自定义的lib文件放到用户目录下了，反正就本用户使用。
 *
 * @author shinnlove.jinsheng
 * @version $Id: NativeHelloTest.java, v 0.1 2019-03-17 20:59 shinnlove.jinsheng Exp $$
 */
public class NativeHelloTest {

    static {
        System.loadLibrary("JavaNativeHello");
    }

    public static void main(String[] args) {
        // 1.调用无参方法
        JavaNativeHello.greeting();

        // 2.调用带参数和返回值方法
        int count = JavaNativeHello.print(8, 4, 3.14);
        System.out.println("result1=" + count);
        count += JavaNativeHello.print(8, 4, count);
        System.out.println("result2=" + count);

        // 3.调用带字符串形参和返回值的函数
        String result = JavaNativeHello.doubleString("丝倩我爱你");
        System.out.println("原生方法返回的字符串str=" + result);
    }

}