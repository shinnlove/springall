/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * LeetCode 231.
 * 
 * wiki: https://leetcode-cn.com/problems/power-of-two/
 * 
 * edge case: 0 or minus number is not any number power of 2.
 * 
 * @author Tony Zhao
 * @version $Id: PowerOfTwo.java, v 0.1 2021-01-19 6:01 PM Tony Zhao Exp $$
 */
public class PowerOfTwo {

    public static void main(String[] args) {
        // 100000 & 11111 = 0
        int n = 32;
        // 10010 & 10001 = 10000
        int m = 18;
        PowerOfTwo pot = new PowerOfTwo();
        boolean result1 = pot.isPowerOfTwo(n);
        boolean result2 = pot.isPowerOfTwo(m);
        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);
    }

    public boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }
        return 0 == (n & (n - 1));
    }

}