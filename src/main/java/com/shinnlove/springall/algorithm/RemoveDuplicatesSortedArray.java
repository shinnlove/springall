/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * leetcode 26. 删除有序数组中的重复项
 * 
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-array/description/
 * 
 * 这道题是easy的原因，是因为所有元素一旦重复就需要删除，不需要统计个数。
 * 
 * @author Tony, Zhao
 * @version $Id: RemoveDuplicatesSortedArray.java, v 0.1 2020-09-13 11:59 PM Tony, Zhao Exp $$
 */
public class RemoveDuplicatesSortedArray {

    public int removeDuplicates(int[] nums) {
        int len = nums.length;
        if (len == 0 || len == 1) {
            return len;
        }

        // unique number count
        int count = 1;

        // duplicate number count
        int duplicate = 0;

        // retrieve from the end of the array
        int prev = nums[len - 1];

        // check from the second last of the array
        for (int i = len - 2; i >= 0; i--) {
            if (nums[i] != prev) {
                count++;
                // modify target value for next comparison
                prev = nums[i];
            } else {
                // duplicate and need move elements
                duplicate++;

                // for compact array..
                // i position is duplicate position, j starts from i, end to len - 1 - duplicate
                for (int j = i; j <= len - 1 - duplicate; j++) {
                    nums[j] = nums[j + 1];
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        RemoveDuplicatesSortedArray rdsa = new RemoveDuplicatesSortedArray();

        int[] array1 = new int[] { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 };
        int result1 = rdsa.removeDuplicates(array1);
        System.out.println("after remove length is " + result1);

        int[] array2 = new int[] { 1, 1 };
        int result2 = rdsa.removeDuplicates(array2);
        System.out.println("after remove length is " + result2);
    }

}