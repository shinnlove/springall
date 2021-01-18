/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * Search in rotated sorted array.
 * 
 * 1,2,3,4,5,6,7 => 4,5,6,7,1,2,3
 * 
 * initial: head = 4, low = 0, high = 6, mid = 3
 * nums[3] = 7 > 4 => low = mid + 1 => low = 4, high = 6 => mid = 5
 * nums[5] = 2 < 4 => high = mid - 1 => high = 4, low = 4, low = high
 * array split position = low + 1
 * 
 * wiki: binary search: https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/662427/.
 * leetcode solution: https://leetcode-cn.com/problems/search-in-rotated-sorted-array/solution/yi-wen-dai-ni-shua-bian-er-fen-cha-zhao-dtadq/
 * 
 * @author Tony Zhao
 * @version $Id: SearchInRotatedSortedArray.java, v 0.1 2021-01-18 8:29 PM Tony Zhao Exp $$
 */
public class SearchInRotatedSortedArray {

    public static void main(String[] args) {
        int[] nums = new int[] { 3, 4, 5, 6, 7, 8, 9, 0, 1, 2 };

        SearchInRotatedSortedArray sir = new SearchInRotatedSortedArray();
        int pos1 = sir.search(nums, 4);
        System.out.println("Search position is pos=" + pos1);
        int pos2 = sir.search(nums, 6);
        System.out.println("Search position is pos=" + pos2);
        int pos3 = sir.search(nums, 9);
        System.out.println("Search position is pos=" + pos3);
        int pos4 = sir.search(nums, 1);
        System.out.println("Search position is pos=" + pos4);
        int pos5 = sir.search(nums, 3);
        System.out.println("Search position is pos=" + pos5);
        int pos6 = sir.search(nums, 2);
        System.out.println("Search position is pos=" + pos6);
    }

    /**
     * Let's see two cases: 
     * 
     * 3,4,1,2
     * low = 0, high = 3, low + high = 3 / 2 = 1, mid is the second position
     * a[0] = 3 < 4 => in the first section => low = mid + 1 = 1 + 1 = 2, low is the third position, high = 3,
     * still low < high, low + high = 2 + 3 = 5 / 2 = 2, mid = 2, 
     * a[2] = 1 < 3 => in the second section => high = mid - 1 = 2 - 1 = 1.
     * Now low = 2, high = 1, low < high exit while
     * low or high + 1 is the same start of second section.
     * 
     * 
     * 3,4,5,1,2
     * low = 0, high = 4, low + high = 4 / 2 = 2, mid is the third position
     * a[0] = 3 < a[mid], 5 => in the first section => low = mid + 1 = 3, low is the fourth position, high = 4
     * still low < high, low + high = 3 + 4 = 7 / 2 = 3, mid = 3
     * a[3] = 1 < 3 => in the second section => high = mid -1 = 3 - 1 = 2, low = 3
     * Now low < high exit while
     * still low or high + 1 is the same start of second section.
     * 
     * @param nums 
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int len = nums.length;
        int low = 0, high = len - 1;
        int head = nums[0];
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] >= head) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        // low points to the largest number
        int first = binarySearch(nums, 0, high, target);
        int second = binarySearch(nums, low, len - 1, target);
        if (first == -1 && second == -1) {
            return -1;
        } else {
            return first == -1 ? second : first;
        }
    }

    public int binarySearch(int[] nums, int low, int high, int target) {
        while (low <= high) {
            // plus low to ensure will not exceed range
            int mid = (low + high) / 2;
            if (nums[mid] < target) {
                low = mid + 1;
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

}