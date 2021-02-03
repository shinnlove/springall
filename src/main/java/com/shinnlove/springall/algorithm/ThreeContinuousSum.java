/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * N+
 * 
 * 
 * 1,2,3,4,5,6,8
 * 
 * O(logN)
 * 
 * target = 15
 * 
 * n1 + n2 + n3 = 15
 * n1 + n2 + n3 = 17
 * 
 * 
 * equals 15 = 4 + 5 + 6
 * 
 * i = 0, 1 => N2 + N3 = 14, 2 => 12, 3 => 11, 4 => 10, 5 => 9, 6 => 8 ; 1, 6, 8
 * i = 1, 2 => N2 + N3 = 13, 3 => 10, ... ,    5 => 8 ; 2,5,8
 * 
 * @author Tony Zhao
 * @version $Id: Solutions.java, v 0.1 2021-02-03 3:08 PM Tony Zhao Exp $$
 */
public class ThreeContinuousSum {

    public static void main(String[] args) {
        // test case
        // 1 -> 2 -> 3 -> 5 -> 7 -> 3
        //         p1,p2

        int[] numbers = new int[] { 1, 2, 3, 4, 5, 6, 8 };
        int target = 15;
        ThreeContinuousSum s = new ThreeContinuousSum();

        List<String> result = s.findSumNumbers(numbers, target);
        System.out.println(result);
    }

    public List<String> findSumNumbers(int[] nums, int target) {
        List<String> result = new ArrayList<>();

        // Arrays.sort...
        int len = nums.length;
        if (len < 3) {
            // numbers less than three
            return result;
        }

        // from first to length -3, + length -2 + length -1
        for (int i = 0; i < len - 2; i++) {
            int searchSum = target - nums[i];

            // start from next number
            int start = i + 1;

            // end from last number
            int end = len - 1;

            while (nums[start] + nums[end] != searchSum && start < end) {
                // too small
                if (nums[start] + nums[end] < searchSum) {
                    start++;
                } else if (nums[start] + nums[end] > searchSum) {
                    // too big
                    end--;
                }
            } // while

            // if equals
            if (nums[start] + nums[end] == searchSum && i + 1 == start && start + 1 == end) {
                String answer = target + "=" + nums[i] + "," + nums[start] + "," + nums[end];
                result.add(answer);
            }

        } // for

        return result;
    }

}