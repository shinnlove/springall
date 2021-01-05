/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 70. 考察递归推理能力，还有优化能力。
 * 
 * TODOs: Time Limit Exceed.
 * 
 * 要么递归、要么排列组合，把问题分解为递归的前提是要有分解问题或数学归纳的想法，找到问题转化的表达式。
 * 
 * https://blog.csdn.net/Kikitious_Du/article/details/79057803
 * 
 * 如果N = 45，就会超过时间限制，所以这道题目还要优化！
 * 
 * @author Tony, Zhao
 * @version $Id: ClimbingStairs.java, v 0.1 2020-09-15 5:23 PM Tony, Zhao Exp $$
 */
public class ClimbingStairs {

    public static void main(String[] args) {
        ClimbingStairs cs = new ClimbingStairs();
        int result = cs.climbStairs(10);
        System.out.println("Distinct ways: " + result);
    }

    public int climbStairs(int n) {
        if (n == 1) {
            // only 1
            return 1;
        }
        if (n == 2) {
            // 1+1 or 2
            return 2;
        }

        // 少一步和两步的组合
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

}