/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * LeetCode 283. 
 * 
 * Wiki: https://leetcode-cn.com/problems/move-zeroes/.
 * 
 * @author Tony Zhao
 * @version $Id: MoveZeroes.java, v 0.1 2021-01-22 4:15 PM Tony Zhao Exp $$
 */
public class MoveZeroes {

    public static void main(String[] args) {
        int[] numbers = new int[] { 0, 1, 0, 3, 12 };
        int[] numbers2 = new int[] { 0, 1 };

        MoveZeroes mz = new MoveZeroes();
        mz.moveZeroes(numbers);
        mz.moveZeroes(numbers2);

        for (int i = 0; i < numbers.length - 1; i++) {
            System.out.print(numbers[i] + ",");
        }
        System.out.println(numbers[numbers.length - 1]);

        for (int i = 0; i < numbers2.length - 1; i++) {
            System.out.print(numbers2[i] + ",");
        }
        System.out.println(numbers2[numbers2.length - 1]);
    }

    public void moveZeroes(int[] nums) {
        if (nums.length == 0) {
            return;
        }
        int len = nums.length;
        int numberCheckStartPos = 0;
        int earliestZeroPos = -1;

        for (int i = 0; i < len; i++) {
            if (nums[i] == 0) {
                // move zero pointer
                if (numberCheckStartPos == 0) {
                    numberCheckStartPos = i;
                }

                for (int j = numberCheckStartPos + 1; j < len; j++) {
                    if (nums[j] != 0) {
                        // find one number not equals zero
                        numberCheckStartPos = j;
                        // exchange
                        nums[i] = nums[j];
                        nums[j] = 0;
                        break;
                    } else {
                        // update the next zero position as earliest zero
                        earliestZeroPos = j;
                    }
                    // stop search till end
                    if (j == len - 1) {
                        break;
                    }
                } // for search number != 0

            } // if meets zero

        } // for

        if (nums[len - 1] != 0 && earliestZeroPos >= 0) {
            nums[earliestZeroPos] = nums[len - 1];
            nums[len - 1] = 0;
        }
    }

}