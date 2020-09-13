/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * @author Tony, Zhao
 * @version $Id: RemoveDuplicatesSortedArray.java, v 0.1 2020-09-13 11:59 PM Tony, Zhao Exp $$
 */
public class RemoveDuplicatesSortedArray {

    public static void main(String[] args) {
        RemoveDuplicatesSortedArray rdsa = new RemoveDuplicatesSortedArray();

        int[] array1 = new int[] { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 };
        int result1 = rdsa.removeDuplicates(array1);
        System.out.println("after remove length is " + result1);

        int[] array2 = new int[] { 1, 1 };
        int result2 = rdsa.removeDuplicates(array2);
        System.out.println("after remove length is " + result2);
    }

    public int removeDuplicates(int[] nums) {
        int len = nums.length;
        if (len == 0 || len == 1) {
            return len;
        }

        int count = 1;
        int duplicate = 0;
        int prev = nums[len - 1];
        for (int i = len - 2; i >= 0; i--) {
            if (nums[i] != prev) {
                count++;
                prev = nums[i];
            } else {
                duplicate++;
                for (int j = i; j <= len - 1 - duplicate; j++) {
                    nums[j] = nums[j + 1];
                }
            }
        }

        return count;
    }

}