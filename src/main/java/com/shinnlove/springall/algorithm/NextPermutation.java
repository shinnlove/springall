/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.Arrays;

/**
 * LeetCode 31.
 * 
 * wiki: https://leetcode-cn.com/problems/next-permutation/.
 * 
 * Tips: use two pointer to reverse array: https://leetcode-cn.com/problems/next-permutation/solution/xia-yi-ge-pai-lie-by-leetcode-solution/.
 * 
 * @author Tony Zhao
 * @version $Id: NextPermutation.java, v 0.1 2021-02-06 1:27 PM Tony Zhao Exp $$
 */
public class NextPermutation {

    public static void main(String[] args) {
        int[] nums1 = new int[] { 1, 2, 3 };
        int[] nums2 = new int[] { 3, 2, 1 };
        int[] nums3 = new int[] { 1, 1, 5 };
        int[] nums4 = new int[] { 1 };
        int[] nums5 = new int[] { 1, 1 };

        NextPermutation np = new NextPermutation();
        np.nextPermutation(nums1);
        np.nextPermutation(nums2);
        np.nextPermutation(nums3);
        np.nextPermutation(nums4);
        np.nextPermutation(nums5);

        for (int i = 0; i < nums1.length - 1; i++) {
            System.out.print(nums1[i] + ", ");
        }
        System.out.println(nums1[nums1.length - 1]);

        for (int i = 0; i < nums2.length - 1; i++) {
            System.out.print(nums2[i] + ", ");
        }
        System.out.println(nums2[nums2.length - 1]);

        for (int i = 0; i < nums3.length - 1; i++) {
            System.out.print(nums3[i] + ", ");
        }
        System.out.println(nums3[nums3.length - 1]);

        for (int i = 0; i < nums4.length - 1; i++) {
            System.out.print(nums4[i] + ", ");
        }
        System.out.println(nums4[nums4.length - 1]);

        for (int i = 0; i < nums5.length - 1; i++) {
            System.out.print(nums5[i] + ", ");
        }
        System.out.println(nums5[nums5.length - 1]);
    }

    /**
     * start: 1, 36, 28, 92, 9, 18
     *      => 1, 36, 28, 92, 18, 9
     *      => 1, 36, 92, 9, 18, 28
     *      => 1, 36, 92, 9, 28, 18
     *      => 1, 36, 92, 18, 9, 28
     *      => 1, 36, 92, 18, 28, 9 <=
     *      => 1, 36, 92, 28, 9, 18 <=
     *      => 1, 36, 92, 28, 18, 9
     * 
     * @param nums 
     */
    public void nextPermutation(int[] nums) {
        int len = nums.length;
        if (len <= 1) {
            return;
        }

        boolean allReverse = true;

        for (int i = len - 1; i > 0; i--) {

            if (nums[i] <= nums[i - 1]) {
                continue;
            }

            allReverse = false;

            int min = Integer.MAX_VALUE;
            int index = len - 1;
            for (int j = len - 1; j >= i; j--) {
                if (nums[j] > nums[i - 1] && nums[j] < min) {
                    min = nums[j];
                    index = j;
                }
            }

            // i-1 need to be replace with the smallest one which larger than it
            int temp = nums[i - 1];
            nums[i - 1] = min;
            nums[index] = temp;

            // tips: use two pointer reverse array
            Arrays.sort(nums, i, len);
            break;
        }
        if (allReverse) {
            Arrays.sort(nums);
        }
    }

}