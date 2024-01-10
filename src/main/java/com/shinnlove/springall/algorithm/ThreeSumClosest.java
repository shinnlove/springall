/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.Arrays;

/**
 * leetcode 16. 最接近的三数之和
 * 
 * https://leetcode.cn/problems/3sum-closest/description/
 * 
 * @author Tony Zhao
 * @version $Id: ThreeSumClosest.java, v 0.1 2024-01-09 11:03 AM Tony Zhao Exp $$
 */
public class ThreeSumClosest {

    public int threeSumClosest(int[] nums, int target) {

        Arrays.sort(nums);

        int len = nums.length;
        int closest = 10000000;

        // 枚举 a
        for (int i = 0; i < len; ++i) {

            // 小优化：保证和上一次枚举的元素不相等
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 使用双指针枚举 b 和 c
            int low = i + 1, high = len - 1;
            while (low < high) {

                int sum = nums[i] + nums[low] + nums[high];
                // 小优化：如果和为 target 直接返回答案
                if (sum == target) {
                    return target;
                }

                // 根据差值的绝对值来更新答案
                if (Math.abs(sum - target) < Math.abs(closest - target)) {
                    closest = sum;
                }

                // 调整指针看下一次是否有更接近的数
                if (sum > target) {
                    // 如果和大于 target，移动 c 对应的指针
                    int prevIndex = high - 1;

                    // 小优化：移动到下一个不相等的元素
                    while (low < prevIndex && nums[prevIndex] == nums[high]) {
                        --prevIndex;
                    }

                    high = prevIndex;
                } else {
                    // 如果和小于 target，移动 b 对应的指针
                    int succIndex = low + 1;

                    // 小优化：移动到下一个不相等的元素
                    while (succIndex < high && nums[succIndex] == nums[low]) {
                        ++succIndex;
                    }

                    low = succIndex;
                }

            } // while low < high
        } // for a

        return closest;
    }

}