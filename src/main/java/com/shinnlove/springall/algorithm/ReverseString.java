/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 344. reverse a given string with O(1) complexity.
 * 
 * 1, 2, 3, 4, 5, 6, 7
 * 
 * String to char[] use toCharArray
 * char[] to String use String.valueOf
 * 
 * @author Tony Zhao
 * @version $Id: ReverseString.java, v 0.1 2021-01-18 8:16 PM Tony Zhao Exp $$
 */
public class ReverseString {

    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "beauty";
        char[] sc1 = s1.toCharArray();
        char[] sc2 = s2.toCharArray();
        ReverseString rs = new ReverseString();
        rs.reverseString(sc1);
        rs.reverseString(sc2);
        System.out.println(String.valueOf(sc1));
        System.out.println(String.valueOf(sc2));
    }

    public void reverseString(char[] s) {
        int len = s.length;
        int end = len / 2;
        for (int i = 0; i < end; i++) {
            char temp = s[i];
            s[i] = s[len - 1 - i];
            s[len - 1 - i] = temp;
        }
    }

}