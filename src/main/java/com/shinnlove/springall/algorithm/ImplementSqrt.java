/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 69. 实现一个整数的算术平方根，只返回整数部分。
 * 
 * 这道题考察折半查找，有2个难点：
 * 1. 最后返回的时候要check low指针相乘是否大于x，大于则返回low-1，否则就原样返回；
 * 2. low, high, mid都要用long类型，否则int类型容易溢出，当low+high溢出的时候!!
 * 
 * 最后强制类型转换，long to int 可能会丢精度，但是可以用(int)强转。
 * 
 * @author Tony, Zhao
 * @version $Id: ImplementSqrt.java, v 0.1 2020-09-15 5:30 PM Tony, Zhao Exp $$
 */
public class ImplementSqrt {

    public static void main(String[] args) {
        ImplementSqrt is = new ImplementSqrt();
        int result1 = is.mySqrt(10);
        System.out.println("Sqrt is " + result1);

        int result2 = is.mySqrt(1);
        System.out.println("Sqrt is " + result2);

        int result3 = is.mySqrt(2147395599);
        System.out.println("Sqrt is " + result3);

        int result4 = is.mySqrt(3);
        System.out.println("Sqrt is " + result4);

        int result5 = is.mySqrt(2147483647);
        System.out.println("Sqrt is " + result5);
    }

    public int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }

        long low = 1;
        long high = x;
        while (low < high) {
            long mid = (low + high) / 2;
            if (mid * mid == x) {
                return (int) mid;
            } else if (mid * mid > x) {
                high = (int) (mid - 1);
            } else {
                low = (int) (mid + 1);
            }
        }

        if (low * low > x) {
            return (int) (low - 1);
        }

        return (int) low;
    }

}