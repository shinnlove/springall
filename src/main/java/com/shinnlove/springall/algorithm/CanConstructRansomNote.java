/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * leetcode 383. 赎金信
 * 
 * https://leetcode.cn/problems/ransom-note/?envType=daily-question&envId=2024-01-07
 * 
 * 考察点：字符减去97，也就是小写字母a，就可以得到数组统计索引位置。
 * 
 * @author Tony Zhao
 * @version $Id: CanConstructRansomNote.java, v 0.1 2024-01-08 4:21 PM Tony Zhao Exp $$
 */
public class CanConstructRansomNote {

    public boolean canConstruct(String ransomNote, String magazine) {

        if (ransomNote == null || magazine == null || "".equals(ransomNote)
            || "".equals(magazine)) {
            return false;
        }

        int[] nums = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            nums[c - 'a'] += 1;
        }

        for (int i = 0; i < ransomNote.length(); i++) {
            char r = ransomNote.charAt(i);
            nums[r - 'a'] -= 1;
            if (nums[r - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        CanConstructRansomNote ccr = new CanConstructRansomNote();

        String ransomNote1 = "a";
        String magazine1 = "b";
        boolean result1 = ccr.canConstruct(ransomNote1, magazine1);
        System.out.println("magazine string has ransom note string's words, result=" + result1);

        String ransomNote2 = "aa";
        String magazine2 = "ab";
        boolean result2 = ccr.canConstruct(ransomNote2, magazine2);
        System.out.println("magazine string has ransom note string's words, result=" + result2);

        String ransomNote3 = "aa";
        String magazine3 = "aab";
        boolean result3 = ccr.canConstruct(ransomNote3, magazine3);
        System.out.println("magazine string has ransom note string's words, result=" + result3);
    }

}