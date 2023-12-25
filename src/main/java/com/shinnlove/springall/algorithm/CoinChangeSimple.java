/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * leetcode 322. 零钱兑换简单暴力解法
 * <p>
 * https://leetcode.cn/problems/coin-change/
 * 
 * 这种动态规划的题目理解通常分三步走：
 * 
 * a) 暴力解法、递归与边界、子问题
 * b) 带备忘录的自顶向下的解法
 * c) 改造成迭代模式的自底向上的解法
 *
 * @author Tony Zhao
 * @version $Id: CoinChangeSimple.java, v 0.1 2023-12-25 2:59 PM Tony Zhao Exp $$
 */
public class CoinChangeSimple {

    public int coinChange(int[] coins, int amount) {

        // 初始化备忘录
        int[] memo = new int[amount + 1];

        // 小技巧：可以一把直接填充，不需要自己循环写
        // Arrays.fill(memo, -66);
        for (int i = 0; i < amount + 1; i++) {
            memo[i] = -66;
        }

        // 开始真正调用
        return coinChangeMemo(memo, coins, amount);
    }

    /**
     * 带备忘录的自顶向下解法。
     * <p>
     * 状态：目标金额amount
     * 选择：coins数组中列出的所有硬币面额
     * 函数的定义：凑出总金额amount，至少需要coinChange(coins, amount)枚硬币
     * base case: amount == 0时，需要0枚硬币；amount < 0时，不可能凑出
     *
     * @param memo   零钱凑整备忘录
     * @param coins  有几种硬币面额
     * @param amount 当前递归要凑出的面额数
     * @return 需要的最小硬币数
     */
    public int coinChangeMemo(int[] memo, int[] coins, int amount) {
        // 两个叶子结点边界条件
        if (amount == 0) {
            // 不需要硬币
            return 0;
        }
        if (amount < 0) {
            // 无解
            return -1;
        }

        // 初始化memo是-66的值(-1已代表无解，不能再初始化-1)
        if (memo[amount] != -66) {
            // 命中则直接返回
            return memo[amount];
        }

        // 有三种硬币、三种硬币产生的子问题和当前不用硬币父问题的最小值
        int min = Integer.MAX_VALUE;

        // 三种硬币会产生子问题
        for (int coin : coins) {

            // 定义某种硬币的子问题
            int subProblem = coinChangeMemo(memo, coins, amount - coin);

            // 该子问题已经无解(用作递归层级很深的时候退出)
            if (subProblem == -1) {
                continue;
            }

            // 看看是当前直接凑的数量少还是[子问题+1]凑的数量小
            // 小的结果给到min收集一波
            min = Math.min(min, subProblem + 1);

        }

        // 可能当前递归层级：子问题都无解，直接返回无解、或者采用子问题的最小解
        int result = min == Integer.MAX_VALUE ? -1 : min;

        // 这一层递归的结果存到备忘录再返回
        memo[amount] = result;

        return result;
    }

    /**
     * 暴力递归解法。
     * <p>
     * 状态：目标金额amount
     * 选择：coins数组中列出的所有硬币面额
     * 函数的定义：凑出总金额amount，至少需要coinChange(coins, amount)枚硬币
     * base case: amount == 0时，需要0枚硬币；amount < 0时，不可能凑出
     *
     * @param coins  有几种硬币面额
     * @param amount 当前递归要凑出的面额数
     * @return 需要的最小硬币数
     */
    public int coinChangeNative(int[] coins, int amount) {

        // 两个叶子结点边界条件
        if (amount == 0) {
            // 不需要硬币
            return 0;
        }
        if (amount < 0) {
            // 无解
            return -1;
        }

        // 有三种硬币、三种硬币产生的子问题和当前不用硬币父问题的最小值
        int min = Integer.MAX_VALUE;

        // 三种硬币会产生子问题
        for (int coin : coins) {

            // 定义某种硬币的子问题
            int subProblem = coinChangeNative(coins, amount - coin);

            // 该子问题已经无解(用作递归层级很深的时候退出)
            if (subProblem == -1) {
                continue;
            }

            // 看看是当前直接凑的数量少还是[子问题+1]凑的数量小
            // 小的结果给到min收集一波
            min = Math.min(min, subProblem + 1);

        }

        // 可能当前递归层级：子问题都无解，直接返回无解、或者采用子问题的最小解
        return min == Integer.MAX_VALUE ? -1 : min;
    }

}