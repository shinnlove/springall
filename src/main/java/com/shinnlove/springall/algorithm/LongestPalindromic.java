/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WIP.
 * 
 * @author Tony, Zhao
 * @version $Id: LongestPalindromic.java, v 0.1 2020-08-28 6:09 PM Tony, Zhao Exp $$
 */
public class LongestPalindromic {

    public static void main(String[] args) {
        LongestPalindromic lp = new LongestPalindromic();

        String test1 = "babad";
        String test2 = "cbbd";
        String test3 = "ac";
        String test4 = "abcba";

        String validate1 = "aba";
        String validate2 = "bb";
        String validate3 = "a";
        String validate4 = "abcba";

        String result1 = lp.longestPalindrome(test1);
        String result2 = lp.longestPalindrome(test2);
        String result3 = lp.longestPalindrome(test3);
        String result4 = lp.longestPalindrome(test4);

        System.out.println(validate1.equals(result1));
        System.out.println(validate2.equals(result2));
        System.out.println(validate3.equals(result3));
        System.out.println(result4);
    }

    public String longestPalindrome(String s) {
        int low = 0, high = 0;
        int strLength = s.length();

        if (strLength == 0) {
            return "";
        }

        // init
        StringBuilder sb = new StringBuilder();
        Map<String, List<Integer>> charMapPosList = new HashMap<>();
        char first = s.charAt(0);
        String firstKey = String.valueOf(new char[] { first });
        List<Integer> posList = new ArrayList<>();
        posList.add(0);
        charMapPosList.put(firstKey, posList);
        int maxPalindromeLength = 1;

        while (high < strLength - 1) {
            high += 1;
            char current = s.charAt(high);
            String key = String.valueOf(new char[] { current });

            if (charMapPosList.containsKey(key)) {
                // repeat should check palindromic
                while (low < high - 1 && !checkPalindrome(low, high, s)) {
                    low += 1;
                }

                // refresh max length
                if (high - low + 1 > maxPalindromeLength) {
                    maxPalindromeLength = high - low + 1;
                }

                sb.delete(0, sb.length());
                charMapPosList.clear();
                for (int i = low; i <= high; i++) {
                    String oneChar = String.valueOf(new char[] { s.charAt(i) });
                    sb.append(oneChar);

                    if (charMapPosList.containsKey(oneChar)) {
                        List<Integer> positionList = charMapPosList.get(oneChar);
                        positionList.add(i);
                    } else {
                        List<Integer> positionList = new ArrayList<>();
                        positionList.add(i);
                        charMapPosList.put(oneChar, positionList);
                    }
                }

            } else {
                List<Integer> positionList = new ArrayList<>();
                positionList.add(high);
                charMapPosList.put(key, positionList);
            }
        }

        while (low < high - 1 && !checkPalindrome(low, high, s)) {
            low += 1;
        }

        if (checkPalindrome(low, high, s) && high - low + 1 >= maxPalindromeLength) {
            sb.delete(0, sb.length());
            for (int i = low; i <= high; i++) {
                sb.append(String.valueOf(new char[] { s.charAt(i) }));
            }
        }

        // if no repeat, use first letter
        if (sb.length() == 0) {
            return String.valueOf(new char[] { s.charAt(0) });
        }

        return sb.toString();
    }

    public boolean checkPalindrome(int low, int high, String str) {
        String lowKey = String.valueOf(new char[] { str.charAt(low) });
        String highKey = String.valueOf(new char[] { str.charAt(high) });
        if (!lowKey.equalsIgnoreCase(highKey)) {
            return false;
        }

        while (low <= high) {
            low += 1;
            high -= 1;
            if (low > high) {
                break;
            }

            String recursiveLowKey = String.valueOf(new char[] { str.charAt(low) });
            String recursiveHighKey = String.valueOf(new char[] { str.charAt(high) });
            if (!recursiveLowKey.equalsIgnoreCase(recursiveHighKey)) {
                return false;
            }
        }

        return true;
    }

}