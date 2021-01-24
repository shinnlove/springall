/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * LeetCode 674.
 * 
 * wiki: https://leetcode-cn.com/problems/longest-continuous-increasing-subsequence/.
 * 
 * TODO: Issues to fix.
 * 
 * @author Tony Zhao
 * @version $Id: LongestContinuousIncreasingSubsequence.java, v 0.1 2021-01-24 8:21 PM Tony Zhao Exp $$
 */
public class LongestContinuousIncreasingSubsequence {

    public static void main(String[] args) {
        // test cases
        int[] nums1 = new int[] { 1, 3, 5, 4, 7 };
        int[] nums2 = new int[] { 2, 2, 2, 2, 2 };
        int[] nums3 = new int[] { 1, 3, 5, 7 };

        LongestContinuousIncreasingSubsequence lcis = new LongestContinuousIncreasingSubsequence();
        int result1 = lcis.findLengthOfLCIS(nums1);
        int result2 = lcis.findLengthOfLCIS(nums2);
        int result3 = lcis.findLengthOfLCIS(nums3);
        System.out.println("Longest continuous increasing subsequence is " + result1);
        System.out.println("Longest continuous increasing subsequence is " + result2);
        System.out.println("Longest continuous increasing subsequence is " + result3);
    }

    public int findLengthOfLCIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
        }

        int maxLength = 1;
        int currentLength = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                currentLength++;
                continue;
            }
            // if no larger than before
            if (currentLength > maxLength) {
                maxLength = currentLength;
                currentLength = 1;
            }
        }

        return currentLength > maxLength ? currentLength : maxLength;
    }

}