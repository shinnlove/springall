/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * leetcode 50. Pow(x, n)
 * 
 * https://leetcode.cn/problems/powx-n/description/
 * 
 * 面试考点：
 * 
 * a) 能否实现logn复杂度的算法
 * b) 能否考虑所有特殊测试数据，如：
 *  测试x=0,1的情况
 *  测试n=0,1的情况
 *  测试n为负数的情况
 *  是否需要考虑溢出
 *  其他正常测试case
 *  
 * 进阶：还有根据整数和幂指特性分解成二进制来迭代的，
 * 这样的空间复杂度可以节省为O(1)，时间复杂度不变。
 * 
 * @author Tony Zhao
 * @version $Id: MyPower.java, v 0.1 2024-01-08 12:20 PM Tony Zhao Exp $$
 */
public class MyPow {

    public double myPow(double x, int n) {
        long N = n;
        // 如果是正数直接分治乘法、如果是负数则取倒数；幂指数还是要变正数、用Math.abs(N)也是可以的
        return N >= 0 ? quickMul(x, N) : 1.0 / quickMul(x, -N);
    }

    public double quickMul(double x, long N) {
        if (N == 0) {
            // 0次方就直接返回1
            return 1.0;
        }

        // 先算出向下取整的一半
        double y = quickMul(x, N / 2);

        // 再根据幂指数的奇偶性决定是否要补乘一个底数
        return N % 2 == 0 ? y * y : y * y * x;
    }

    /**
     * leetcode 题解视频ppt上的算法。
     * 
     * @param x 
     * @param n
     * @return
     */
    public double myPow2(double x, int n) {
        if (n == 0 || x == 1) {
            return 1;
        }
        if (n < 0) {
            return 1 / myPowHelper(x, Math.abs(n));
        }
        return myPowHelper(x, n);
    }

    /**
     * leetcode 题解视频ppt上的算法。
     * 
     * @param x 
     * @param n
     * @return
     */
    public double myPowHelper(double x, int n) {
        if (n == 1) {
            return x;
        }

        if (n % 2 != 0) {
            double half = myPowHelper(x, n / 2);
            return half * half * x;
        } else {
            double half = myPowHelper(x, n / 2);
            return half * half;
        }
    }

    public static void main(String[] args) {
        double x = 2;
        int n = 10;

        MyPow mp = new MyPow();
        double result = mp.myPow(2, 10);
        System.out.println("Pow(" + x + "," + n + "): " + x + "^" + n + ", result is=" + result);
    }

}