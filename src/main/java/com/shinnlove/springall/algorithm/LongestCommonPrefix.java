/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 14. 题目考察数组边界的处理。
 * 
 * @author Tony, Zhao
 * @version $Id: LongestCommonPrefix.java, v 0.1 2020-09-15 6:06 PM Tony, Zhao Exp $$
 */
public class LongestCommonPrefix {

    public static void main(String[] args) {
        LongestCommonPrefix lcp = new LongestCommonPrefix();
        String[] array = new String[] { "aa", "a" };
        String result = lcp.longestCommonPrefix(array);
        System.out.println("Longest common prefix is " + result);
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }

        int pos = searchIndex(strs);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pos; i++) {
            sb.append(strs[0].charAt(i));
        }

        return sb.toString();
    }

    public int searchIndex(String[] strs) {
        int low = 0;
        while (true) {
            if (strs[0].length() <= low) {
                break;
            }
            char c = strs[0].charAt(low);
            for (int i = 0; i < strs.length; i++) {
                if (strs[i].length() == low) {
                    return low;
                }
                if (strs[i].charAt(low) != c) {
                    return low;
                }
            }
            low++;
        }
        return low;
    }

}