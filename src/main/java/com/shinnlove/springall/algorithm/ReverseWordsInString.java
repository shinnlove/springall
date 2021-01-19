/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * LeetCode 557.
 * 
 * wiki: https://leetcode-cn.com/problems/reverse-words-in-a-string-iii/
 * 
 * @author Tony Zhao
 * @version $Id: ReverseWordsInString.java, v 0.1 2021-01-19 5:27 PM Tony Zhao Exp $$
 */
public class ReverseWordsInString {

    public static void main(String[] args) {
        String input = "Let's take LeetCode contest";
        String output = "s'teL ekat edoCteeL tsetnoc";
        ReverseWordsInString rwis = new ReverseWordsInString();
        String result = rwis.reverseWords(input);
        System.out.println("result = " + result);
    }

    public String reverseWords(String s) {
        if (s.length() == 0) {
            return s;
        }

        StringBuilder sb = new StringBuilder();
        String[] splits = s.split(" ");
        for (int i = 0; i < splits.length; i++) {
            char[] temp = splits[i].toCharArray();
            splits[i] = String.valueOf(reverseCharArray(temp));
        }

        for (int k = 0; k < splits.length - 1; k++) {
            sb.append(splits[k]).append(" ");
        }
        sb.append(splits[splits.length - 1]);

        return sb.toString();
    }

    public char[] reverseCharArray(char[] chars) {
        int len = chars.length;
        int mid = len / 2;
        for (int i = 0; i < mid; i++) {
            char temp = chars[i];
            chars[i] = chars[len - 1 - i];
            chars[len - 1 - i] = temp;
        }
        return chars;
    }

}