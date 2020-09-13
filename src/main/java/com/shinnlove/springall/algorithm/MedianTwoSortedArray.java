/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * First combine two arrays, then if the total length is odd,
 * then the median is the middle of the length,
 * otherwise the median is the middle-left and middle-right sum divide two.
 * 
 * @author Tony, Zhao
 * @version $Id: MedianTwoSortedArray.java, v 0.1 2020-09-12 5:11 PM Tony, Zhao Exp $$
 */
public class MedianTwoSortedArray {

    public static void main(String[] args) {
        MedianTwoSortedArray mts = new MedianTwoSortedArray();

        int[] nums1 = new int[] { 1, 3, 5, 7, 9 };
        int[] nums2 = new int[] { 2, 4, 6, 8, 10 };
        double result1 = mts.findMedianSortedArrays(nums1, nums2);
        System.out.println("1st the median of two sorted array is " + result1);

        int[] nums11 = new int[] { 1, 3 };
        int[] nums22 = new int[] { 2 };
        double result2 = mts.findMedianSortedArrays(nums11, nums22);
        System.out.println("2nd the median of two sorted array is " + result2);
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        int totalLength = m + n;
        if (totalLength == 0) {
            return 0;
        }

        int count = 0, left = 0, right = 0;
        int[] result = new int[totalLength];
        // left == m && right == n is all to end 
        while (left < m || right < n) {
            // m array is to end
            if (left == m) {
                result[count++] = nums2[right++];
                continue;
            }

            // n array is to end
            if (right == n) {
                result[count++] = nums1[left++];
                continue;
            }

            // common way
            if (nums1[left] <= nums2[right]) {
                result[count++] = nums1[left++];
            } else {
                result[count++] = nums2[right++];
            }

        } // while

        if (totalLength % 2 == 0) {
            int index = totalLength / 2 - 1;
            double a = Double.parseDouble(String.valueOf(result[index]));
            double b = Double.parseDouble(String.valueOf(result[index + 1]));

            return (a + b) / 2;
        } else {
            return Double.parseDouble(String.valueOf(result[totalLength / 2]));
        }
    }

}