/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * LeetCode 415. Add two string of numbers.
 * 
 * @author Tony Zhao
 * @version $Id: AddString.java, v 0.1 2021-01-19 10:40 PM Tony Zhao Exp $$
 */
public class AddString {

    public static void main(String[] args) {
        String number1 = "2315";
        String number2 = "6532";
        String total = "8847";
        AddString as = new AddString();
        String result = as.addStrings(number1, number2);
        System.out.println("result = " + result + ", total = " + total);

        String number3 = "1";
        String number4 = "9";
        String result2 = as.addStrings(number3, number4);
        System.out.println("result 2 = " + result2);
    }

    public String addStrings(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();

        if (len1 == 0) {
            return num2;
        }
        if (len2 == 0) {
            return num1;
        }

        int max = len1 > len2 ? len1 : len2;
        int[] sums = new int[max + 1];
        int highBit = 0;
        int pointer1 = len1 - 1;
        int pointer2 = len2 - 1;
        int pointer3 = max;
        while (pointer1 >= 0 && pointer2 >= 0) {
            int temp1 = num1.charAt(pointer1--) - 48;
            int temp2 = num2.charAt(pointer2--) - 48;
            int total = temp1 + temp2 + highBit;
            if (total >= 10) {
                total = total % 10;
                highBit = 1;
            } else {
                highBit = 0;
            }
            sums[pointer3--] = total;
        } // end while

        while (pointer1 >= 0) {
            int t = num1.charAt(pointer1--) - 48;
            int total = t + highBit;
            if (total >= 10) {
                total = total % 10;
                highBit = 1;
            } else {
                highBit = 0;
            }
            sums[pointer3--] = total;
        } // end pointer1

        while (pointer2 >= 0) {
            int t = num2.charAt(pointer2--) - 48;
            int total = t + highBit;
            if (total >= 10) {
                total = total % 10;
                highBit = 1;
            } else {
                highBit = 0;
            }
            sums[pointer3--] = total;
        } // end pointer2

        if (highBit > 0) {
            sums[pointer3--] = 1;
        }

        StringBuilder sb = new StringBuilder();
        boolean allZero = true;
        boolean leadingZero = true;
        for (int k = 0; k < max + 1; k++) {
            if (sums[k] == 0 && leadingZero) {
                k++;
            }
            if (sums[k] != 0) {
                allZero = false;
                leadingZero = false;
            }

            if (!leadingZero) {
                sb.append(sums[k]);
            }
        } // for

        if (allZero) {
            return "0";
        }

        return sb.toString();
    }

}