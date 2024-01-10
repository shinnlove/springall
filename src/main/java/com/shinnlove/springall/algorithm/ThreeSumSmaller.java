/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.Arrays;

/**
 * leetcode 259. 较小的三数之和
 * 
 * https://leetcode.cn/problems/3sum-smaller/description/
 * 
 * @author Tony Zhao
 * @version $Id: ThreeSumSmaller.java, v 0.1 2024-01-09 2:28 PM Tony Zhao Exp $$
 */
public class ThreeSumSmaller {

    public int threeSumSmaller(int[] nums, int target) {
        Arrays.sort(nums);
        int count = 0;
        int length = nums.length;
        for (int i = 0; i < length - 2; i++) {

            int left = i + 1, right = length - 1;
            // while 循环直到 left 不再小于 right
            while (left < right) {
                if (nums[i] + nums[left] + nums[right] >= target) {
                    right--;
                } else {
                    // 固定left之后，right的最大值，只要right往左移动到遇到left，中间所有解答都是答案，因此相减
                    count += right - left;

                    // 推进left到下一个数字
                    left++;
                }
            }
        }
        return count;
    }

    public int threeSumSmallerBinarySearch(int[] nums, int target) {
        Arrays.sort(nums);
        int count = 0;
        int length = nums.length;
        for (int i = 0; i < length - 2; i++) {
            for (int j = i + 1; j < length - 1; j++) {
                int k = binarySearch(nums, j, target - nums[i] - nums[j]);
                count += k - j;
            }
        }
        return count;
    }

    public int binarySearch(int[] nums, int startIndex, int target) {
        int low = startIndex, high = nums.length - 1;
        while (low < high) {
            int mid = (high - low + 1) / 2 + low;
            if (nums[mid] < target) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

}