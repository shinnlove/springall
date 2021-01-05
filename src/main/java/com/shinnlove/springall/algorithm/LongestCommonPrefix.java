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

    /**
     * 用第一个字符串比较，取指针慢慢叠加，但注意边界值。
     * 
     * @param strs 
     * @return return position
     */
    public int searchIndex(String[] strs) {
        int low = 0;
        while (true) {
            if (strs[0].length() <= low) {
                break;
            }
            char c = strs[0].charAt(low);
            for (int i = 0; i < strs.length; i++) {
                if (strs[i].length() == low) {
                    // 长度不够直接返回
                    return low;
                }
                if (strs[i].charAt(low) != c) {
                    // 不匹配也直接返回
                    return low;
                }
            }
            low++;
        }
        return low;
    }

}