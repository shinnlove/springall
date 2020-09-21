/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * TODOs: number overflow.
 * 
 * @author Tony, Zhao
 * @version $Id: FactorialTrailingZeroes.java, v 0.1 2020-09-21 5:01 PM Tony, Zhao Exp $$
 */
public class FactorialTrailingZeroes {

    public static void main(String[] args) {
        FactorialTrailingZeroes ftz = new FactorialTrailingZeroes();
        int count = ftz.trailingZeroes(20);
        System.out.println("Trailing zero number is " + count);
    }

    public int trailingZeroes(int n) {
        long result = calculate(n);
        long temp = result;
        int count = 0;
        while (temp > 0 && temp % 10 == 0) {
            temp = temp / 10;
            count++;
        }
        return count;
    }

    public long calculate(int n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return calculate(n - 1) * n;
        }
    }

}