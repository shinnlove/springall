/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 这道题其实是Single Number的拓展问题：
 * 
 * 数组中有两个落单的整数、其他都是成对出现，找到这两个整数。
 * 
 * @author Tony, Zhao
 * @version $Id: TwoSingleNumber.java, v 0.1 2020-09-17 10:17 PM Tony, Zhao Exp $$
 */
public class TwoSingleNumber {

    public static void main(String[] args) {
        TwoSingleNumber tsn = new TwoSingleNumber();
        int[] numbers = new int[] { 2, 2, 5, 5, 7, 7, 13, 13, 21, 65, 67, 66, 65, 97, 67, 97 };

        int[] result = tsn.findTwoSingleNumbers(numbers);
        if (result.length > 0) {
            System.out.print("The two numbers are: ");
            for (int i = 0; i < result.length - 1; i++) {
                System.out.print(result[i] + ", ");
            }
            System.out.println(result[result.length - 1] + ".");
        } else {
            System.out.println("Array is empty.");
        }
    }

    public int[] findTwoSingleNumbers(int[] numbers) {
        int len = numbers.length;
        if (len == 0) {
            return new int[0];
        }

        int temp = numbers[0];
        for (int i = 1; i < len; i++) {
            temp ^= numbers[i];
        }

        // 如果至少有1位二进制位为1，则说明是两个数取异或的结果
        if (temp == 0) {
            return new int[0];
        }

        // 找到不为0的数位
        int sep = 1;
        while (0 == (temp & sep)) {
            // !!不断左移找到第一位不为0的二进制位
            sep = sep << 1;
        }

        // 结果集数组中只包含2个数
        int[] result = new int[2];
        for (int i = 0; i < len; i++) {
            if (0 == (numbers[i] & sep)) {
                // !!取与等于0的异或第一个数
                result[0] ^= numbers[i];
            } else {
                // !!取与不等于0的异或第二个数
                result[1] ^= numbers[i];
            }
        }

        return result;
    }

}