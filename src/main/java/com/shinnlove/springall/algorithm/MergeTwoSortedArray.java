/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
 * 
 * Binary search + O(1) movement.
 * 
 * 虽然这道题是一次性提交成功，但是下面这个解法真的太巧妙了。
 * https://github.com/MisterBooo/LeetCodeAnimation/blob/master/0088-Merge-Sorted-Array/Article/0088-Merge-Sorted-Array.md
 * 从后往前比较，哪个大一定是放到最后一个位置，直到双指针中有一个到头，如果nums2还有剩余，一定在nums1的地方有空余。
 * 
 * @author Tony, Zhao
 * @version $Id: MergeTwoSortedArray.java, v 0.1 2020-09-15 2:13 PM Tony, Zhao Exp $$
 */
public class MergeTwoSortedArray {

    public static void main(String[] args) {
        MergeTwoSortedArray mtsa = new MergeTwoSortedArray();

        int[] nums1 = new int[] { 1, 2, 3, 0, 0, 0 };
        int[] nums2 = new int[] { 2, 5, 6 };
        int m = 3;
        int n = 3;

        int[] results = new int[] { 1, 2, 2, 3, 5, 6 };

        mtsa.merge(nums1, m, nums2, n);
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for (int i = 0; i < n; i++) {
            int pos = searchPosition(nums1, m + i, nums2[i]);
            for (int j = m + i; j > pos; j--) {
                nums1[j] = nums1[j - 1];
            }
            nums1[pos] = nums2[i];
        }
    }

    public int searchPosition(int[] nums, int n, int target) {
        if (n == 0) {
            return 0;
        }

        int low = 0, high = n - 1;
        while (low < high) {
            int middle = (low + high) / 2;
            if (nums[middle] == target) {
                return middle;
            }

            if (nums[middle] > target) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }

        if (nums[low] >= target) {
            return low;
        } else {
            return low + 1;
        }
    }

}