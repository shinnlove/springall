/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 66. Plus One.
 * 
 * wiki: https://leetcode-cn.com/problems/plus-one/submissions/.
 * 
 * @author Tony, Zhao
 * @version $Id: PlusOne.java, v 0.1 2020-09-15 6:22 PM Tony, Zhao Exp $$
 */
public class PlusOne {

    public static void main(String[] args) {
        PlusOne po = new PlusOne();

        int[] array = new int[] { 9 };

        int[] result = po.plusOne(array);
        System.out.print("result is [");
        for (int i = 0; i < result.length - 1; i++) {
            System.out.print(result[i]);
            System.out.print(", ");
        }
        System.out.println(result[result.length - 1] + "].");
    }

    public int[] plusOne(int[] digits) {
        int[] result = new int[digits.length + 1];

        int temp = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] + temp >= 10) {
                result[i + 1] = (digits[i] + temp) % 10;
                temp = (digits[i] + temp) / 10;
            } else {
                result[i + 1] = digits[i] + temp;
                temp = 0;
            }
        }

        if (temp != 0) {
            result[0] = temp;
        } else {
            if (result[0] == 0) {
                int[] tp = new int[digits.length];
                for (int i = 0; i < digits.length; i++) {
                    tp[i] = result[i + 1];
                }
                return tp;
            }
        }

        return result;
    }

}