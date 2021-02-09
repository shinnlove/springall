/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * LeetCode OJ。
 *
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 *
 * @author shinnlove.jinsheng
 * @version $Id: Solution1st.java, v 0.1 2019-04-08 13:21 shinnlove.jinsheng Exp $$
 */
public class TwoSumAnother {

    public static void main(String[] args) {
        int[] nums = new int[] { 2, 7, 11, 15 };
        TwoSumAnother s = new TwoSumAnother();
        int[] result = s.twoSum(nums, 9);

        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + ",");
        }
    }

    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (target == nums[i] + nums[j]) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }

        throw new IllegalArgumentException("没有找到对应数据");
    }

}