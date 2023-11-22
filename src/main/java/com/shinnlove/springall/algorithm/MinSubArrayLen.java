/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 27. 长度最小子数组
 * 
 * https://leetcode.cn/problems/minimum-size-subarray-sum/
 * 
 * 考点：滑动窗口
 * 时间复杂度：O(n)，其中 nnn 是数组的长度。指针 start 和 end 最多各移动 n 次
 * 空间复杂度：O(1)
 * 
 * @author Tony Zhao
 * @version $Id: MinSubArrayLen.java, v 0.1 2023-11-22 12:11 PM Tony Zhao Exp $$
 */
public class MinSubArrayLen {

    /**
     * 给定目标值，找出数组中连续元素超过给定值的最小数组长度。
     * 
     * @param target 
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {

        // for robust
        int n = nums.length;

        if (n == 0) {
            return 0;
        }

        // 默认是最大长度(当然等于最大长度，代表没有)
        int ans = Integer.MAX_VALUE;

        // 两个指针，都从第一个数值开始
        int start = 0, end = 0;

        // 当前指针之间的距离
        int sum = 0;

        // end还没有走到最后一个值
        while (end < n) {

            // 先加后判
            sum += nums[end];

            while (sum >= target) {

                // 可以直接使用Math.min来比较
                // 元素个数是 end - start + 1，最小是0
                // 如果小的话返回给ans
                ans = Math.min(ans, end - start + 1);

                // 往后移动start指针
                // 并同时扣除sum的元素
                sum -= nums[start];
                start++;
            }

            // 1. 如果加上; 2. 如果扣除后sum小于target了
            // 这时候再移动end指针
            end++;
        }

        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

}