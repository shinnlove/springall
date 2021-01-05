/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * @author Tony, Zhao
 * @version $Id: ReverseInteger.java, v 0.1 2020-09-12 6:07 PM Tony, Zhao Exp $$
 */
public class ReverseInteger {

    public static void main(String[] args) {
        int number = 964632451;
        ReverseInteger ri = new ReverseInteger();
        int result = ri.reverse(number);
        System.out.println("Reversed number is " + result);
    }

    public int reverse(int x) {
        if (x == 0) {
            return x;
        }
        StringBuilder sb = new StringBuilder();
        String numStr = String.valueOf(x);
        int index = 0;
        if (x < 0) {
            index = 1;
        }
        // 反着读
        for (int i = numStr.length() - 1; i >= index; i--) {
            char c = numStr.charAt(i);
            sb.append(c);
        }
        String result = sb.toString();
        long y = Long.valueOf(result);
        // 题目有要求，溢出的时候返回为0，所以这里考虑溢出情况的判断
        // 注意带符号整型32位是2^31-1
        if (y > Math.pow(2, 31) - 1 || y < -Math.pow(2, 31)) {
            return 0;
        }
        int z = Integer.valueOf(String.valueOf(y));
        if (x < 0) {
            return -z;
        }
        return z;
    }

}