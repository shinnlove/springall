/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Slide window: 
 * https://github.com/azl397985856/leetcode/blob/master/problems/3.longest-substring-without-repeating-characters.md
 * https://github.com/azl397985856/leetcode/blob/master/thinkings/slide-window.md
 * 
 * Warning: map's entrySet has interface iterator, could be convert to specific entry type.
 * 
 * @author Tony, Zhao
 * @version $Id: LongestSubstring.java, v 0.1 2020-08-28 4:59 PM Tony, Zhao Exp $$
 */
public class LongestSubstring {

    public static void main(String[] args) {

        String test1 = "abcabcbb";
        String test2 = "bbbbb";
        String test3 = "pwwkew";

        int validate1 = 3;
        int validate2 = 1;
        int validate3 = 3;

        LongestSubstring ls = new LongestSubstring();
        int result1 = ls.lengthOfLongestSubstring(test1);
        int result2 = ls.lengthOfLongestSubstring(test2);
        int result3 = ls.lengthOfLongestSubstring(test3);

        System.out.println(validate1 == result1);
        System.out.println(validate2 == result2);
        System.out.println(validate3 == result3);

    }

    public int lengthOfLongestSubstring(String s) {
        int maxNoRepeatLength = 0;
        int low = 0, high = 0, length = s.length();
        Map<String, Integer> strPosMap = new HashMap<>();

        if (length == 0) {
            return maxNoRepeatLength;
        }

        // init set
        char first = s.charAt(0);
        String firstKey = String.valueOf(new char[]{first});
        strPosMap.put(firstKey, 0);
        int currentNoRepeatLength = maxNoRepeatLength = 1;

        while (high < length - 1) {
            high += 1;
            char c = s.charAt(high);
            String key = String.valueOf(new char[]{c});

            if (strPosMap.containsKey(key)) {
                // char repeated, should move low
                if (currentNoRepeatLength > maxNoRepeatLength) {
                    maxNoRepeatLength = currentNoRepeatLength;
                }
                low = strPosMap.get(key) + 1;

                // update key pos by high pointer
                strPosMap.put(key, high);

                // Important: remove all keys in map smaller than low pointer pos
                Iterator iterator = strPosMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iterator.next();
                    Integer checkPos = entry.getValue();
                    if (checkPos < low) {
                        iterator.remove();
                    }
                }

                // refresh currentLength
                currentNoRepeatLength = high - low + 1;

            } else {
                strPosMap.put(key, high);
                currentNoRepeatLength += 1;
            }
        }

        if (currentNoRepeatLength > maxNoRepeatLength) {
            return currentNoRepeatLength;
        }

        return maxNoRepeatLength;
    }

}