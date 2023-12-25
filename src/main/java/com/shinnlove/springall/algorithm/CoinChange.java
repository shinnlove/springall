/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.Arrays;

/**
 * leetcode 322. 零钱兑换
 * 
 * https://leetcode.cn/problems/coin-change/
 * 
 * 这里直接采用动态规划的自底向上的迭代解法，先初始化掉子问题的辅助空间。
 * 
 * @author Tony Zhao
 * @version $Id: CoinChange.java, v 0.1 2023-12-25 2:59 PM Tony Zhao Exp $$
 */
public class CoinChange {

    public int coinChange(int[] coins, int amount) {

        // dp 数组全部初始化为特殊值 amount+1
        // 代表是不可能有比数额还多的硬币来凑数的(哪怕没有1块钱的硬币)

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);

        // base case、叶子结点情形
        dp[0] = 0;

        // dp是金额、对每个金额数额进行从小到大的判断
        for (int i = 0; i < dp.length; i++) {

            // 开始用硬币面额凑当前金额
            for (int coin : coins) {

                // 当前数额已很小、无法用任何一个面额凑出，子问题无解跳过
                if (i - coin < 0) {
                    continue;
                }

                // 如果有解、状态转移方程
                // 这个转移方程是对当前这一层迭代的level、进行做选择得到的下一层递归收益+做选择的实际成本或收益
                // 对迭代到的当前硬币面额不做拆分就是dp[i]
                // 对迭代到的当前硬币面额做拆分就是dp[i-coin]+1、1是当前这枚面值的硬币
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);

            }

        } // for

        // 最后看目标金额面额是否是初始值
        // 初始值代表无解，凑不出这个面额；否则就是计算出的数额
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

}