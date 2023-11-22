/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 3. 无重复字符的最长子串
 * 
 * https://leetcode.cn/problems/longest-substring-without-repeating-characters/description/?company_slug=bytedance
 * 
 * @author Tony Zhao
 * @version $Id: LengthOfLongestSubstring.java, v 0.1 2023-11-22 1:04 PM Tony Zhao Exp $$
 */
public class LengthOfLongestSubstring {

    public int lengthOfLongestSubstring(String s) {

        // for robust
        if (s.length() == 0) {
            return 0;
        }

        // 字符与位置的映射
        Map<Character, Integer> map = new HashMap<Character, Integer>();

        // max代表历史最长的子串
        int max = 0;

        // left是左指针
        int left = 0;

        // i是模拟右指针、从0开始、小于length
        for (int i = 0; i < s.length(); i++) {

            if (map.containsKey(s.charAt(i))) {
                // 说明已有重复元素

                // 当前存储的曾经left字符的最早位置，+1代表向右移动一格
                // 作为新的left
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }

            // 1st. 将当前字符和下标放入map
            // 2nd. 更新重复的字符应该在字符串中的新位置
            map.put(s.charAt(i), i);

            // 字符串长度 = 当前位置减去左指针+1
            max = Math.max(max, i - left + 1);
        }

        return max;

    }

}