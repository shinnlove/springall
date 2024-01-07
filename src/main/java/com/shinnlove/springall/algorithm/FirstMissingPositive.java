/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 41. 缺失的最小正整数。
 * 
 * https://leetcode.cn/problems/first-missing-positive/description/
 * 
 * 本题最重要的2点思想：
 * 
 * a) 如果有正整数缺失，区间一定在数组长度1~数组长度N+1；
 * b) 数组下标可以用来做哈希索引。
 * 
 * @author Tony Zhao
 * @version $Id: FirstMissingPositive.java, v 0.1 2024-01-07 12:30 PM Tony Zhao Exp $$
 */
public class FirstMissingPositive {

    public int firstMissingPositive(int[] nums) {

        int len = nums.length;
        if (len == 0) {
            return 1;
        }

        // Step1: check whether 1 existed
        boolean existed = false;
        for (int i = 0; i < len; i++) {
            if (nums[i] == 1) {
                existed = true;
                break;
            }
        }

        if (!existed) {
            return 1;
        }

        // Step2: scan and skip negative numbers and too bigger numbers
        for (int i = 0; i < len; i++) {
            if (nums[i] <= 0 || nums[i] > len) {
                nums[i] = 1;
            }
        }

        // Step3: mark and adjust to minus
        for (int i = 0; i < len; i++) {
            int index = Math.abs(nums[i]) - 1;
            // special warning: nums[index] might be negative, should use abs func!
            nums[index] = -Math.abs(nums[index]);
        }

        // remember index small than number, should plus 1
        for (int i = 0; i < len; i++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }

        return len + 1;
    }

    public static void main(String[] args) {
        int[] nums = new int[] { 3, 1 };

        FirstMissingPositive fmp = new FirstMissingPositive();
        int number = fmp.firstMissingPositive(nums);
        System.out.println("Missing smallest positive number is = " + number + ".");

    }

}