/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * @author Tony, Zhao
 * @version $Id: SearchInsertPosition.java, v 0.1 2020-09-14 4:19 PM Tony, Zhao Exp $$
 */
public class SearchInsertPosition {

    public static void main(String[] args) {
        SearchInsertPosition sip = new SearchInsertPosition();

        int[] array = new int[] { 1, 3, 5, 6 };
        int target1 = 2;
        int target2 = 0;

        int result1 = sip.searchInsert(array, target1);
        System.out.println("result is " + result1);

        int result2 = sip.searchInsert(array, target2);
        System.out.println("result is " + result2);
    }

    public int searchInsert(int[] nums, int target) {
        if (nums.length == 0) {
            return 0;
        }

        int low = 0, high = nums.length - 1;
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