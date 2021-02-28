/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * LeetCode 394.
 * <p>
 * wiki: https://leetcode-cn.com/problems/decode-string/.
 *
 * @author Tony Zhao
 * @version $Id: DecodeString.java, v 0.1 2021-02-28 5:22 PM Tony Zhao Exp $$
 */
public class DecodeString {

    public static void main(String[] args) {
        String str1 = "3[a]2[bc]";
        String result1 = "aaabcbc";

        String str2 = "3[a2[c]]";
        String result2 = "accaccacc";

        String str3 = "2[abc]3[cd]ef";
        String result3 = "abcabccdcdcdef";

        String str4 = "abc3[cd]xyz";
        String result4 = "abccdcdcdxyz";

        DecodeString ds = new DecodeString();
        String decodeResult1 = ds.decodeString(str1);
        String decodeResult2 = ds.decodeString(str2);
        String decodeResult3 = ds.decodeString(str3);
        String decodeResult4 = ds.decodeString(str4);

        System.out.println("Decode result1 is " + decodeResult1);
        System.out.println("Decode result2 is " + decodeResult2);
        System.out.println("Decode result3 is " + decodeResult3);
        System.out.println("Decode result4 is " + decodeResult4);
    }

    public String decodeString(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        int len = s.length();
        if (len == 1) {
            return "";
        }

        StringHolder sh = decodeWithNumber(s, len, 0);
        return sh.result;
    }

    public StringHolder decodeWithNumber(String s, int end, int startIndex) {
        String result = "";
        String temp = "";
        int loop = 0;
        boolean leftStarted = false;
        for (int i = startIndex; i < end; i++) {
            char c = s.charAt(i);
            int index = c;
            if (index >= 48 && index <= 57) {
                // only number
                if (!leftStarted) {
                    // need extract number and do repeat
                    NumberHolder nh = extractNumber(s, end, i);
                    loop = Integer.valueOf(nh.number);
                    leftStarted = true;
                    i += nh.length - 1;
                } else {
                    // left has already started, make a recursive call
                    StringHolder innerSH = decodeWithNumber(s, end, i);
                    temp += innerSH.result;
                    i = innerSH.index;
                }
            } else if (index == 91) {
                // left symbol [
                continue;
            } else if (index == 93) {
                // right symbol ], should do 
                for (int j = 0; j < loop; j++) {
                    result += temp;
                }
                if (i != end - 1) {
                    // follower still has content
                    StringHolder appenderSH = decodeWithNumber(s, end, i + 1);
                    result += appenderSH.result;
                    i = appenderSH.index;
                }

                // only return here
                return new StringHolder(result, i);
            } else {
                // characters, append to temp till meets with number or right symbol ]
                temp += c;
            }
        }

        // throw exception here
        return new StringHolder(result, -1);
    }

    public NumberHolder extractNumber(String s, int end, int startIndex) {
        String str = "";
        for (int i = startIndex; i < end; i++) {
            int c = s.charAt(i) - '0';
            if (c >= 0 && c <= 9) {
                str += String.valueOf(c);
            } else {
                return new NumberHolder(Integer.valueOf(str), str.length());
            }
        } // for
        return new NumberHolder(Integer.valueOf(str), str.length());
    }

    public static class NumberHolder {
        public int number;
        public int length;

        public NumberHolder(int number, int length) {
            this.number = number;
            this.length = length;
        }
    }

    public static class StringHolder {
        public String result;
        public int    index;

        public StringHolder(String result, int index) {
            this.result = result;
            this.index = index;
        }
    }

}