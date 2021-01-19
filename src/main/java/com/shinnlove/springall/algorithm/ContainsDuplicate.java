/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 217.
 * 
 * Contains duplicate:
 * a) sort and see whether adjacent elements are the same
 * b) use hash to store element and see whether collection has duplicate one.
 * 
 * @author Tony Zhao
 * @version $Id: ContainsDuplicate.java, v 0.1 2021-01-19 6:23 PM Tony Zhao Exp $$
 */
public class ContainsDuplicate {

    public static void main(String[] args) {
        int[] nums = new int[] { 2, 4, 1, 8, 6, 7, 3, 5, 9, 6 };

        ContainsDuplicate cd = new ContainsDuplicate();
        boolean result = cd.containsDuplicate(nums);
        System.out.println("Array has duplicate element is " + result);
    }

    public boolean containsDuplicate2(int[] nums) {
        Set<Integer> numberSets = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (numberSets.contains(nums[i])) {
                return true;
            }
            numberSets.add(nums[i]);
        }
        return false;
    }

    public boolean containsDuplicate(int[] nums) {
        int len = nums.length;
        if (len == 0 || len == 1) {
            return false;
        }

        // bubbleSort(nums);
        quickSort(nums, 0, len - 1);
        for (int i = 0; i < len - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }

    public void bubbleSort(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            for (int j = len - 1; j > i; j--) {
                if (nums[j - 1] > nums[j]) {
                    int temp = nums[j - 1];
                    nums[j - 1] = nums[j];
                    nums[j] = temp;
                }
            }
        }
    }

    public void quickSort(int[] nums, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }

        int pivotIndex = partition(nums, startIndex, endIndex);
        quickSort(nums, startIndex, pivotIndex - 1);
        quickSort(nums, pivotIndex + 1, endIndex);
    }

    public int partition(int[] nums, int startIndex, int endIndex) {
        // optimize: three number select middle one
        int pivot = nums[startIndex];
        int low = startIndex, high = endIndex;

        while (low != high) {
            while (low < high && nums[high] > pivot) {
                high--;
            }
            while (low < high && nums[low] <= pivot) {
                low++;
            }

            if (low < high) {
                int temp = nums[low];
                nums[low] = nums[high];
                nums[high] = temp;
            }
        }

        // first low to startIndex,
        nums[startIndex] = nums[low];
        // start Index is the pivot
        // put pivot to low pointer position
        nums[low] = pivot;

        return low;
    }

}