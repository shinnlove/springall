/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 15. Double low and high pointer movement algorithm.
 * 
 * wiki: https://leetcode-cn.com/problems/3sum/.
 * 
 * @author Tony Zhao
 * @version $Id: ThreeSum.java, v 0.1 2021-02-03 9:01 PM Tony Zhao Exp $$
 */
public class ThreeSum {

    public static void main(String[] args) {
        // test case
        int[] nums = new int[] { -1, 0, 1, 2, -1, -4 };
        int[] nums2 = new int[] { 0, 0, 0, 0 };

        ThreeSum ts = new ThreeSum();
        List<List<Integer>> result = ts.threeSum(nums);
        List<List<Integer>> result2 = ts.threeSum(nums2);
        System.out.println(result);
        System.out.println(result2);
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        int len = nums.length;
        if (len < 3) {
            return result;
        }

        Arrays.sort(nums);

        for (int i = 0; i < len - 2; i++) {

            // skip duplicate
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int start = i + 1;
            int end = len - 1;

            while (start < end) {
                if (nums[start] + nums[end] < -nums[i]) {
                    start++;
                } else if (nums[start] + nums[end] > -nums[i]) {
                    end--;
                } else {
                    List<Integer> oneResult = new ArrayList<>();
                    oneResult.add(nums[i]);
                    oneResult.add(nums[start]);
                    oneResult.add(nums[end]);
                    result.add(oneResult);
                    start += 1;
                    while (nums[start] == nums[start - 1] && start < end) {
                        // careful: next start should not equals to the previous start!
                        start += 1;
                    }
                }
            } // while

        } // for

        return result;
    }

}