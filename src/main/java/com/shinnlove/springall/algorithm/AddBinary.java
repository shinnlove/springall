/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 67. Add binary.
 * 
 * @author Tony, Zhao
 * @version $Id: AddBinary.java, v 0.1 2020-09-17 5:29 PM Tony, Zhao Exp $$
 */
public class AddBinary {

    public static void main(String[] args) {
        AddBinary ab = new AddBinary();

        String a = "1010";
        String b = "1011";
        String validation = "10101";

        String result = ab.addBinary(a, b);
        System.out.println("Result is " + result + ", validation is " + validation);
    }

    public String addBinary(String a, String b) {
        int aLen = a.length();
        int bLen = b.length();
        int left = aLen - 1;
        int right = bLen - 1;

        int max = Math.max(aLen, bLen);
        char[] reverse = new char[max + 1];
        int rTop = 0;
        int temp = 0;
        while (left >= 0 && right >= 0) {
            int numLeft = a.charAt(left) - '0';
            int numRight = b.charAt(right) - '0';
            int result = numLeft + numRight + temp;
            if (result == 3) {
                reverse[rTop++] = '1';
                temp = 1;
            } else if (result == 2) {
                reverse[rTop++] = '0';
                temp = 1;
            } else if (result == 1) {
                reverse[rTop++] = '1';
                temp = 0;
            } else {
                reverse[rTop++] = '0';
                temp = 0;
            }
            left--;
            right--;
        } // while

        if (left > 0) {
            while (left >= 0) {
                int leftNumber = a.charAt(left) - '0';
                int result = leftNumber + temp;
                if (result == 2) {
                    reverse[rTop++] = '0';
                    temp = 1;
                } else if (result == 1) {
                    reverse[rTop++] = '1';
                    temp = 0;
                } else {
                    reverse[rTop++] = '0';
                    temp = 0;
                }
                left--;
            }
        }

        if (right > 0) {
            while (right >= 0) {
                int rightNumber = b.charAt(right) - '0';
                int result = rightNumber + temp;
                if (result == 2) {
                    reverse[rTop++] = '0';
                    temp = 1;
                } else if (result == 1) {
                    reverse[rTop++] = '1';
                    temp = 0;
                } else {
                    reverse[rTop++] = '0';
                    temp = 0;
                }
                right--;
            }
        }

        StringBuilder sb = new StringBuilder();
        if (temp > 0) {
            sb.append("1");
        }
        while (rTop > 0) {
            sb.append(reverse[--rTop]);
        }

        return sb.toString();
    }

}