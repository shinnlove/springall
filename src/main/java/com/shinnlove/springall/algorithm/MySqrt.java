/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * leetcode 69. x的平方根
 * 
 * https://leetcode.cn/problems/sqrtx/description/
 * 
 * 解法有很多种，如袖珍计算器、二分查找、牛顿迭代法等。
 * 
 * @author Tony Zhao
 * @version $Id: MySqrt.java, v 0.1 2024-01-08 10:15 PM Tony Zhao Exp $$
 */
public class MySqrt {

    /**
     * 二分查找方法求sqrt。
     * 
     * @param x 
     * @return
     */
    public int mySqrt(int x) {
        // 让右边界粗略的设置为x
        int left = 0, right = x, ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long) mid * mid <= x) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    /**
     * 使用e的Inx的方式来变换根号为1/2的指数，然后使用Math.exp方式来计算。
     * 
     * @param x 
     * @return
     */
    public int mySqrtPow(int x) {
        if (x == 0) {
            return 0;
        }

        int ans = (int) Math.exp(0.5 * Math.log(x));
        return (long) (ans + 1) * (ans + 1) <= x ? ans + 1 : ans;
    }

    /**
     * 牛顿迭代法是一种可以用来快速求解函数零点的方法。
     * 
     * @param x 
     * @return
     */
    public int mySqrtNewton(int x) {
        if (x == 0) {
            return 0;
        }

        double C = x, x0 = x;
        while (true) {
            double xi = 0.5 * (x0 + C / x0);
            if (Math.abs(x0 - xi) < 1e-7) {
                break;
            }
            x0 = xi;
        }
        return (int) x0;
    }

    public static void main(String[] args) {

        int x = 100;

        MySqrt ms = new MySqrt();
        int result = ms.mySqrt(x);

        System.out.println("Number " + x + " sqrt result is = " + result);

    }

}