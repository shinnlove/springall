/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Leetcode 242.
 * 
 * wiki: https://leetcode-cn.com/problems/valid-anagram/.
 * 
 * @author Tony Zhao
 * @version $Id: ValidAnagram.java, v 0.1 2021-02-02 11:50 PM Tony Zhao Exp $$
 */
public class ValidAnagram {

    public static void main(String[] args) {
        // test case:
        String s1 = "anagram";
        String s2 = "nagaram";

        ValidAnagram va = new ValidAnagram();
        boolean result = va.isAnagram(s1, s2);
        System.out.println("Result is " + result);
    }

    public boolean isAnagram(String s, String t) {
        Map<String, Integer> charMap = new HashMap<>();

        int sLen = s.length();
        int tLen = t.length();

        if (sLen != tLen || sLen < 0) {
            return false;
        }

        for (int i = 0; i < sLen; i++) {
            String cs = String.valueOf(s.charAt(i));
            int val = 1;
            if (charMap.containsKey(cs)) {
                int cVal = charMap.get(cs);
                val = cVal + 1;
            }
            charMap.put(cs, val);
        }

        for (int i = 0; i < tLen; i++) {
            String cs = String.valueOf(t.charAt(i));
            if (!charMap.containsKey(cs)) {
                return false;
            }
            int val = charMap.get(cs);
            charMap.put(cs, val - 1);
        }

        for (Map.Entry<String, Integer> entry : charMap.entrySet()) {
            if (entry.getValue() != 0) {
                return false;
            }
        }

        return true;
    }

}