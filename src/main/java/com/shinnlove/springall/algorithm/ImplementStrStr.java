/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 28. Implement strStr.
 * 
 * https://leetcode.com/problems/implement-strstr/
 * 
 * For the purpose of this problem, we will return 0 when needle is an empty string. 
 * This is consistent to C's strstr() and Java's indexOf().
 * 
 * TODOs: This question has not been solved since limit exceed.
 * 
 * @author Tony, Zhao
 * @version $Id: ImplementStrStr.java, v 0.1 2020-09-15 1:19 PM Tony, Zhao Exp $$
 */
public class ImplementStrStr {

    public static void main(String[] args) {
        ImplementStrStr iss = new ImplementStrStr();

        String haystack = "mississippi";
        String needle = "issip";
        int result = iss.strStr(haystack, needle);
        System.out.println("result " + result);
    }

    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }
        if (haystack.length() == 0 || needle.length() > haystack.length()) {
            return -1;
        }
        int len = needle.length();

        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {

                // pre-check
                if (i + len - 1 < haystack.length()
                    && haystack.charAt(i + len - 1) != needle.charAt(len - 1)) {
                    continue;
                }

                int j = 1;
                while (i + j < haystack.length() && haystack.length() > j && needle.length() > j
                       && haystack.charAt(i + j) == needle.charAt(j)) {
                    j++;
                }

                if (j == needle.length()) {
                    return i;
                }
            } // if
        } // for

        return -1;
    }

}