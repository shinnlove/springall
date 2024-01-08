/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * leetcode 8. 字符串转换整数(atoi)
 * 
 * https://leetcode.cn/problems/string-to-integer-atoi/description/
 * 
 * @author Tony Zhao
 * @version $Id: MyAtoI.java, v 0.1 2024-01-08 11:08 PM Tony Zhao Exp $$
 */
public class MyAtoI {

    public int myAtoi(String s) {
        char[] c = s.trim().toCharArray();

        if (c.length == 0) {
            return 0;
        }

        int res = 0, bndry = Integer.MAX_VALUE / 10;
        int i = 1, sign = 1;
        if (c[0] == '-') {
            sign = -1;
        } else if (c[0] != '+') {
            i = 0;
        }

        for (int j = i; j < c.length; j++) {
            if (c[j] < '0' || c[j] > '9') {
                break;
            }

            if (res > bndry || res == bndry && c[j] > '7') {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            res = res * 10 + (c[j] - '0');
        }

        return sign * res;
    }

    public static void main(String[] args) {

        MyAtoI mai = new MyAtoI();
        int result = mai.myAtoi("4193 with words");

        System.out.println("Number read in is " + result);

        int y = Integer.MAX_VALUE;
        int y2 = Integer.MIN_VALUE;

    }

}