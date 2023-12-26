/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 0-1背包问题。
 * 
 * @author Tony Zhao
 * @version $Id: Knapsack.java, v 0.1 2023-12-26 10:19 PM Tony Zhao Exp $$
 */
public class Knapsack {

    /**
     * 01背包问题取最大价值。
     * 
     * @param W         当前背包能装的最大重量
     * @param N         当前物品的数量，长度等于wt.length或val.length
     * @param wt        长度为N的重量数组、代表第i个物品的装量
     * @param val       长度为N的价值数组、代表第i个物品的价值
     * @return          第i个物品是否装入背包所获得的最大价值
     */
    public int knapsack(int W, int N, int[] wt, int[] val) {

        // VIP: 先要明确dp[i][w]数组的定义：
        // 对于前i个物品，当背包的容量为w时，可以装的最大价值是dp[i][w]
        int[][] dp = new int[N + 1][W + 1];

        // 这里base case为dp[0][..] = dp[..][0] = 0，但java整型数组初始化都已经是0了
        // 我们想计算的结果是dp[N][W]

        for (int i = 1; i <= N; i++) {
            for (int w = 1; w <= W; w++) {

                // 有时候能不能做选择，还要看实际情况限制，背包重量大小就是其中一个if
                // 能做选择了，才是状态转移方程之间的比较
                if (w - wt[i - 1] < 0) {

                    // 这种情况下只能选择不装入背包
                    dp[i][w] = dp[i - 1][w];

                } else {
                    // 装入或者不装入背包，择优

                    // 选择装入第i个物品获得的价值

                    // 这里存在数组索引偏移的问题，在dp table中，因为i和w都是从1开始循环的
                    // 而原始给出的重量数组、价值数组都是下标从0开始的，因而要减少1去获取对应的元素 
                    int put = dp[i - 1][w - wt[i - 1]] + val[i - 1];

                    // 不选择装入第i个商品
                    int ignore = dp[i - 1][w];

                    // 取两者价值大的
                    dp[i][w] = Math.max(put, ignore);
                }

            }
        }

        return dp[N][W];

    }

}