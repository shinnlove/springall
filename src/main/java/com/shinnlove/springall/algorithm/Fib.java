/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * leetcode 509. 斐波那契数
 *
 * https://leetcode.cn/problems/fibonacci-number/description/
 *
 * @author Tony Zhao
 * @version $Id: Fib.java, v 0.1 2023-12-25 2:33 PM Tony Zhao Exp $$
 */
public class Fib {

    public int fib(int n) {

        // 一种带备忘录的递归解法，不带备忘录就是暴力穷举解法，时间复杂度会非常高

        // 特别注意：如果将memo定义在这个函数上，作为全局变量；
        // 那一定还要再写一个迭代函数，包含下面的内容：
        // 保证memo数组会被传入到第二个函数中作为全局变量可改变操作
        // 如果需要初始化不是0的数组也可以放在调用前先初始化
        // 所以最优方式是再写一个函数，而不是使用全局变量

        int[] memo = new int[31];

        return calFib(memo, n);
    }

    public int calFib(int[] memo, int n) {

        // 叶子节点子问题
        if (n == 0)
            return 0;
        else if (n == 1)
            return 1;

        // 校验备忘录，java基础整型数组初始化都是0
        if (memo[n] != 0) {
            return memo[n];
        }

        // 迭代计算
        int result = calFib(memo, n - 1) + calFib(memo, n - 2);

        // 存一下备忘录
        memo[n] = result;

        return result;
    }

    public static void main(String[] args) {
        Fib fib = new Fib();

        int n = 10;
        int result = fib.fib(n);

        System.out.println("N=" + n + ", result=" + result);
    }

}