/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

import java.math.BigInteger;

/**
 * java超级大数类的应用。
 *
 * @author shinnlove.jinsheng
 * @version $Id: BigIntegerUtil.java, v 0.1 2019-01-19 16:05 shinnlove.jinsheng Exp $$
 */
public class BigIntegerUtil {

    public static void main(String[] args) {
        // 直接字符串构造
        BigInteger a = new BigInteger("66666666666666666666666");
        BigInteger b = new BigInteger("88888888888888888888888");

        // 从long类型读取一个数
        BigInteger c = BigInteger.valueOf(3141234123412L);

        fourArithmeticOperation(a, b);
    }

    /**
     * 两个大数做四则运算。
     *
     * @param a 
     * @param b
     */
    public static void fourArithmeticOperation(BigInteger a, BigInteger b) {
        // 加法
        BigInteger result = a.add(b);
        System.out.println(result.abs());

        // 减法
        BigInteger result2 = a.subtract(b);
        System.out.println(result2);

        // 乘法
        BigInteger result3 = a.multiply(b);
        System.out.println(result3.abs());

        // 除法
        BigInteger result4 = a.divide(b);
        System.out.println(result4.abs());

        // 取余
        BigInteger result5 = a.mod(b);
        System.out.println(result5.abs());

        // 指数
        BigInteger result6 = a.pow(2);
        System.out.println(result6);

        // 相等
        boolean result7 = a.equals(b);
        System.out.println(result7);

        // 最大和最小
        BigInteger result8 = a.max(b);
        BigInteger result9 = a.min(b);
        System.out.println("max=" + result8 + ", min=" + result9);

        // 比较，-1是小于、0是等于、1是大于
        int result10 = a.compareTo(b);
        System.out.println("compare result=" + result10);
    }

}
