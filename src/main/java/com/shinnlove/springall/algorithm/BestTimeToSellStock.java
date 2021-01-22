/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * LeetCode 121. Best time to sell stock.
 * 
 * wiki: https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
 * 
 * Strategy: lowest buy in and highest sell out.
 * 
 * @author Tony Zhao
 * @version $Id: BestTimeToSellStock.java, v 0.1 2021-01-22 4:44 PM Tony Zhao Exp $$
 */
public class BestTimeToSellStock {

    public static void main(String[] args) {
        int[] stockPrices = new int[] { 7, 1, 5, 3, 6, 4 };

        BestTimeToSellStock bts = new BestTimeToSellStock();
        int result = bts.maxProfit(stockPrices);

    }

    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }

        int len = prices.length;
        int historyMin = prices[0];
        int deltaMax = 0;

        for (int i = 0; i < len; i++) {
            if (prices[i] < historyMin) {
                historyMin = prices[i];
                continue;
            }
            if (prices[i] - historyMin > deltaMax) {
                deltaMax = prices[i] - historyMin;
            }
        }
        return deltaMax;
    }

}