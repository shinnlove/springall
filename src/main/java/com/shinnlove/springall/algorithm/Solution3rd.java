/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 *
 * Input: "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 * Example 2:
 *
 * Input: "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 * Example 3:
 *
 * Input: "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 *              Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * @author shinnlove.jinsheng
 * @version $Id: Solution3rd.java, v 0.1 2019-04-08 14:16 shinnlove.jinsheng Exp $$
 */
public class Solution3rd {

    public static void main(String[] args) {
        String input1 = "abcabcbb";
        String input2 = "bbbbb";
        String input3 = "pwwkew";
        String input4 = " ";
        String input5 = "au";
        String input6 = "aab";
        String input7 = "dvdf";
        String input8 = "tmmzuxt";

        Solution3rd s = new Solution3rd();

        System.out.println(s.lengthOfLongestSubstring(input1));
        System.out.println(s.lengthOfLongestSubstring(input2));
        System.out.println(s.lengthOfLongestSubstring(input3));
        System.out.println(s.lengthOfLongestSubstring(input4));
        System.out.println(s.lengthOfLongestSubstring(input5));
        System.out.println(s.lengthOfLongestSubstring(input6));
        System.out.println(s.lengthOfLongestSubstring(input7));
        System.out.println(s.lengthOfLongestSubstring(input8));
    }

    /**
     * pos永远在重新开始统计的位置，字符串长度用i-pos即可得出。
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0;

        if (s.length() == 0) {
            return maxLength;
        }

        // a~z => 97~122
        Map<Integer, Integer> charMap = new HashMap<>();
        for (int k = 0; k < 256; k++) {
            charMap.put(k, 0);
        }

        int pos = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int key = (int) c;

            if (charMap.get(key) == 0) {
                // 打标
                charMap.put(key, 1);
                continue;
            }

            // 统计最长序列
            int currentLength = i - pos;
            if (currentLength > maxLength) {
                maxLength = currentLength;
            }

            // 移动pos

            // 不是因为pos这个字符重复的，打标取消
            if (c != s.charAt(pos)) {
                charMap.put((int) s.charAt(pos), 0);
            }

            int j = ++pos;

            // 从当前游标后边位置开始
            for (; j < i; j++) {

                // 标记位恢复
                charMap.put((int) s.charAt(j), 0);

                if (c == s.charAt(j)) {
                    pos = j;

                    // !!!如果最后一个字符与当前字母相等，则pos定位到当前字母，如果不等，则就在当前字母前一个位置
                    if (s.charAt(i) == s.charAt(j)) {
                        pos = i;
                    }
                }
            }

        } // end for

        int last = s.length() - pos;
        return last > maxLength ? last : maxLength;
    }
}