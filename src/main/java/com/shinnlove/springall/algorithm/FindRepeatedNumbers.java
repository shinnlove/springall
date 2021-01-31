/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * Sword to offer 03.
 * <p>
 * wiki: https://leetcode-cn.com/problems/shu-zu-zhong-zhong-fu-de-shu-zi-lcof/.
 *
 * @author Tony Zhao
 * @version $Id: FindRepeatedNumbers.java, v 0.1 2021-01-31 11:41 AM Tony Zhao Exp $$
 */
public class FindRepeatedNumbers {

    public static void main(String[] args) {
        int[] numbers = new int[] { 0, 1, 2, 3, 4, 11, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

        FindRepeatedNumbers frn = new FindRepeatedNumbers();
        int result = frn.findRepeatNumber(numbers);
        System.out.println("Result is " + result);
    }

    public int findRepeatNumber(int[] nums) {
        Set<Integer> alreadySeen = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (alreadySeen.contains(nums[i])) {
                return nums[i];
            }
            alreadySeen.add(nums[i]);
        }
        return -1;
    }

}