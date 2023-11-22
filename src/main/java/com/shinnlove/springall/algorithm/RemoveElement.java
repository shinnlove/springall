/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 27. 移除元素
 * 
 * 移除数组中给定值的元素。
 * 
 * https://leetcode.cn/problems/remove-element/?company_slug=bytedance
 * 
 * @author Tony, Zhao
 * @version $Id: RemoveElement.java, v 0.1 2020-09-15 1:11 PM Tony, Zhao Exp $$
 */
public class RemoveElement {

    public static void main(String[] args) {

    }

    public int removeElement(int[] nums, int val) {
        int duplicate = 0;
        // 从后往前遍历
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] == val) {
                duplicate++;
                if (i == nums.length - 1) {
                    // 本来目标值就是最后一个元素不用管
                    continue;
                } else {
                    // i这个位置后边的元素都往前移动覆盖掉i
                    // 但是优化空间就是要减去重复的元素
                    for (int j = i + 1; j <= nums.length - duplicate; j++) {
                        nums[j - 1] = nums[j];
                    }
                }
            }
        }
        return nums.length - duplicate;
    }

}