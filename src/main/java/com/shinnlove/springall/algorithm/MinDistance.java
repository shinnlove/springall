/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * leetcode 72. 编辑距离
 * 
 * wiki: https://leetcode.cn/problems/edit-distance/description/
 * 
 * @author Tony Zhao
 * @version $Id: MinDistance.java, v 0.1 2023-12-26 2:22 PM Tony Zhao Exp $$
 */
public class MinDistance {

    /**
     * 自底向上的迭代解法。
     * 
     * @param word1         被转换的字符串
     * @param word2         目标字符串
     * @return
     */
    public int minDistance(String word1, String word2) {

        int len1 = word1.length();
        int len2 = word2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        // 初始化base case，也就是第一个字符串走完、或者第二个字符串走完
        for (int i = 1; i <= len1; i++) {
            // 如果是已经达到目标字符串word2起始位置，
            // 则word1剩余从i往前的都是需要删掉的元素
            // 因此第一列从上到下都是需要删掉的，总共i坐标次
            dp[i][0] = i;
        }

        // 初始化base case
        for (int j = 1; j <= len2; j++) {
            // 如果是已经达到目标字符串word1起始位置，
            // 则word1剩余从word2的j位置开始都是需要新增插入的元素
            // 则从第一行从左到右都是需要新增的，总共j坐标次
            dp[0][j] = j;
        }

        // 自底向上求解
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {

                // 特别注意：这里数组指针右移、
                // 访问字符串的时候下标要相应左移一位，因此-1
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // 两个字符相同，则指针分别往前走、略过当前字符，下标都-1
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 两个字符不同，则一共有三种操作方式：插入、删除或替换，
                    // 需要分别看下他们三者的方式的代价
                    // 仅i减少代表word1删了字符
                    // 仅j减少代表word1增加字符
                    // i和j一起减少，代价+1，代表实施了一次字符串替换

                    int delete = dp[i - 1][j] + 1;
                    int insert = dp[i][j - 1] + 1;
                    int replace = dp[i - 1][j - 1] + 1;

                    dp[i][j] = min(delete, insert, replace);
                }
            }
        } // for

        // 储存着整个word1和word2的最小编辑距离
        return dp[len1][len2];
    }

    private int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

}