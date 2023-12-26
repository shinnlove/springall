/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * leetcode 42. 接雨水
 * 
 * https://leetcode.cn/problems/trapping-rain-water/
 * 
 * @author Tony Zhao
 * @version $Id: Trap.java, v 0.1 2023-12-26 3:10 PM Tony Zhao Exp $$
 */
public class Trap {

    public int trap(int[] height) {

        // 整个长度
        int n = height.length;
        if (n < 3) {
            return 0;
        }

        // 从左往右看数组
        int[] leftMax = new int[n];
        // 高度数组最左侧就是从左往右起始比较点
        leftMax[0] = height[0];
        // 正序遍历到最后一个不断比较最大
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        // 从右往左看数组
        int[] rightMax = new int[n];
        // 高度数组最右侧就是从右往左起始比较点
        rightMax[n - 1] = height[n - 1];
        // 倒序遍历到最左边一个不断比较最大
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        // 开始左右观察统计结果
        int ans = 0;
        for (int i = 0; i < n; i++) {
            // 两侧取最小高度-自身高度就是积水量、加到整体积水量上
            ans += Math.min(leftMax[i], rightMax[i]) - height[i];
        }

        return ans;
    }

}