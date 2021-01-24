/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * LeetCode 122.
 * 
 * wiki:https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/.
 * 
 * @author Tony Zhao
 * @version $Id: BestTimeToSellStockII.java, v 0.1 2021-01-25 12:12 AM Tony Zhao Exp $$
 */
public class BestTimeToSellStockII {

    public static void main(String[] args) {
        int[] stocks1 = new int[] { 7, 1, 5, 3, 6, 4 };
        int[] stocks2 = new int[] { 1, 2, 3, 4, 5 };
        int[] stocks3 = new int[] { 7, 6, 4, 3, 1 };

        int result1 = 7;
        int result2 = 4;
        int result3 = 0;

        BestTimeToSellStockII bts2 = new BestTimeToSellStockII();
        int calculateResult1 = bts2.maxProfit(stocks1);
        int calculateResult2 = bts2.maxProfit(stocks2);
        int calculateResult3 = bts2.maxProfit(stocks3);

        System.out.println("Result1 = " + calculateResult1);
        System.out.println("Result2 = " + calculateResult2);
        System.out.println("Result3 = " + calculateResult3);
    }

    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len == 0 || len == 1) {
            return 0;
        }

        int min = prices[0];
        int max = prices[0];
        int total = 0;
        boolean inTrans = true;

        for (int i = 1; i < len; i++) {
            if (prices[i] == max) {
                continue;
            }
            if (prices[i] > max) {
                max = prices[i];
                inTrans = true;
                continue;
            }
            // prices[i] < max
            total += max - min;
            min = max = prices[i];
        }

        if (inTrans) {
            total += max - min;
        }

        return total;
    }

}