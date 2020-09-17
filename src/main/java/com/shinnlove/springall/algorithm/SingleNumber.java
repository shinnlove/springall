/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 136. Single Number.
 * 
 * 思路：相同数字位数一样就会为0，这样如果一个数出现偶数次，一定会抵消为0，剩下的那个数字就是落单的数字。
 * 
 * 题解：数组中所有数字两两异或运算。
 * 
 * @author Tony, Zhao
 * @version $Id: SingleNumber.java, v 0.1 2020-09-17 9:54 PM Tony, Zhao Exp $$
 */
public class SingleNumber {

    public static void main(String[] args) {
        SingleNumber sn = new SingleNumber();

        int[] numbers1 = new int[] { 2, 2, 1 };
        int result1 = sn.singleNumber(numbers1);
        System.out.println("Result is " + result1);

        int[] numbers2 = new int[] { 4, 1, 2, 1, 2 };
        int result2 = sn.singleNumber(numbers2);
        System.out.println("Result is " + result2);
    }

    public int singleNumber(int[] nums) {
        int total = nums[0];
        for (int i = 1; i < nums.length; i++) {
            total = total ^ nums[i];
        }
        return total;
    }

}