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
        //        int result = msa.maxSubArray2(nums);
        System.out.println("Result is " + result);
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

    public int maxSubArray2(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return 0;
        }
        if (len == 1) {
            return nums[0];
        }

        boolean inSearching = false;
        int maxSum = 0;
        int tempSum = 0;
        int startIndex = -1;
        for (int i = 0; i < len; i++) {
            if (nums[i] > 0) {
                // larger than 0
                if (!inSearching) {
                    startIndex = i;
                }
                tempSum += nums[i];
                inSearching = true;
                continue;
            }

            // here smaller than 0
            if (!inSearching) {
                continue;
            }

            if (i < len - 1 && nums[i] + nums[i + 1] > 0) {
                tempSum += nums[i] + nums[i + 1];
                i += 1;
            } else {
                // no need this negative number
                inSearching = false;
                if (tempSum > maxSum) {
                    maxSum = tempSum;
                    tempSum = 0;
                    // reset to that point to do recursive
                    if (startIndex < len) {
                        i = startIndex;
                    }
                }
            }

        } // for

        if (startIndex == -1) {
            return 0;
        }

        return maxSum;
    }

}