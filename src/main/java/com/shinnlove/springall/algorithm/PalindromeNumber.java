/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * @author Tony, Zhao
 * @version $Id: PalindromeNumber.java, v 0.1 2020-08-28 10:50 PM Tony, Zhao Exp $$
 */
public class PalindromeNumber {

    public static void main(String[] args) {
        int test1 = 121;
        int test2 = -121;

        PalindromeNumber pn = new PalindromeNumber();
        boolean result1 = pn.isPalindrome(test1);
        boolean result2 = pn.isPalindrome(test2);

        System.out.println(result1);
        System.out.println(result2);
    }

    public boolean isPalindrome(int x) {
        if (x == 0) {
            return true;
        }

        if (x < 0) {
            return false;
        }

        int[] numbers = new int[16];
        int i = 0;
        while (x > 0) {
            numbers[i] = x % 10;
            x = x / 10;
            i += 1;
        }

        int len = i;
        int low = 0, high = 0;
        if (len % 2 == 0) {
            low = len / 2;
            high = low - 1;
        } else {
            int middle = (len - 1) / 2;
            low = middle - 1;
            high = middle + 1;
        }

        while (low > 0 && high < len - 1) {
            if (numbers[low] != numbers[high]) {
                return false;
            }
            low -= 1;
            high += 1;
        }

        if (low == 0) {
            if (numbers[low] != numbers[high]) {
                return false;
            }
        }

        return true;
    }

}