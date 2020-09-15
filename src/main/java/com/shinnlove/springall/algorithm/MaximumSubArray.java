/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 这道题的思想很重要：
 * 
 * 因为如果都是正数，全部加起来就可以了，关键就在不要加到、或者尽可能加到比较小的负数。
 * 
 * 1.因为是连续的子数组，所以加起来的总值超过了最大值，就更新最大值；
 * 2.如果数组加起来的总值小于0，直接置为0，这是不需要的值=>因为如果子数组还有小于0的子数组部分，这必然不会是最大的子数组。
 * 
 * @author Tony, Zhao
 * @version $Id: MaximumSubArray.java, v 0.1 2020-09-15 2:58 PM Tony, Zhao Exp $$
 */
public class MaximumSubArray {

    public static void main(String[] args) {
        MaximumSubArray msa = new MaximumSubArray();
        int[] nums = new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
        int result = msa.maxSubArray(nums);
        System.out.println("Result is" + result);
    }

    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int max = nums[0];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            if (sum > max) {
                max = sum;
            }
            if (sum < 0) {
                sum = 0;
            }
        }
        return max;
    }

}