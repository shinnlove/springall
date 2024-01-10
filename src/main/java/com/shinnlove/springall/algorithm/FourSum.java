/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * leetcode 18. 四数之和
 * 
 * https://leetcode.cn/problems/4sum/description/
 * 
 * 小技巧：
 * a) 重复、最小的大于、最大的小于可以优化剪枝
 * b) 多个数字相加注意不要溢出整型，建议用long
 * 
 * @author Tony Zhao
 * @version $Id: FourSum.java, v 0.1 2024-01-09 12:02 PM Tony Zhao Exp $$
 */
public class FourSum {

    public List<List<Integer>> fourSum(int[] nums, int target) {

        List<List<Integer>> quadruplets = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return quadruplets;
        }

        Arrays.sort(nums);

        int length = nums.length;

        // 第一层循环
        for (int i = 0; i < length - 3; i++) {

            // 优化剪枝操作：如果最外层循环的下一个数和上一个数一样，直接推进第一层循环
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 优化剪枝操作：如果第一层循环当前数加上最小的三个数已经超过目标，对于已排序数组后续都不会有答案了
            if ((long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                break;
            }

            // 优化剪枝操作：如果当前数字加上整个数组中最大的3个数字都小于目标值，说明当前最外层循环太小了，继续推进到下一个
            if ((long) nums[i] + nums[length - 3] + nums[length - 2] + nums[length - 1] < target) {
                continue;
            }

            // 第二层循环
            // a) 相同数字
            // b) 最小的都大于目标
            // c) 最大的都小于目标
            for (int j = i + 1; j < length - 2; j++) {

                // 优化剪枝操作：首先j=i+1是第一次，j>i+1是第二层循环的第二次及以上循环，遇到相同数字就跳过
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                // 优化剪枝操作：如果第二层循环当前数字固定后，数组内最小的后续2个数加上都超过目标，则打断第二层循环
                if ((long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    break;
                }

                // 优化剪枝操作：如果当前第二层循环固定数字后，再加上数组中最大的两个数都小于目标，则推进第二层循环下个数字
                if ((long) nums[i] + nums[j] + nums[length - 2] + nums[length - 1] < target) {
                    continue;
                }

                // 对于剩下两个数字，就开始使用高低双指针逼近算法
                int left = j + 1, right = length - 1;

                // 直到两个指针相遇
                while (left < right) {

                    // 四数相加结果小心溢出整型
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];

                    // 一旦搜寻到目标，立刻加入列表。
                    if (sum == target) {
                        quadruplets.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // 相同数字跳过
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        // 继续缩小指针范围、可能还有其他解
                        left++;

                        // 相同数字跳过
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        // 继续缩小指针范围、可能还有其他解
                        right--;

                    } else if (sum < target) {
                        // 远未达目标
                        left++;
                    } else {
                        // 已超过目标
                        right--;
                    }
                }
            }
        }

        return quadruplets;
    }

}