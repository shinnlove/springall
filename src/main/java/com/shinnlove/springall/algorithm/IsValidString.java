/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 20.
 * 
 * wiki: https://leetcode-cn.com/problems/valid-parentheses/
 * 
 * @author Tony Zhao
 * @version $Id: IsValidString.java, v 0.1 2021-01-19 8:48 PM Tony Zhao Exp $$
 */
public class IsValidString {

    public static void main(String[] args) {
        String str1 = "([)]";
        String str2 = "{[]}";

        IsValidString ivs = new IsValidString();
        boolean result1 = ivs.isValid(str1);
        boolean result2 = ivs.isValid(str2);

        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);
    }

    public boolean isValid(String s) {
        int len = s.length();
        if (len == 0) {
            return true;
        }

        Map<String, String> reflection = new HashMap<>();
        reflection.put(")", "(");
        reflection.put("}", "{");
        reflection.put("]", "[");

        char[] charStack = new char[len];
        int top = 0;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                charStack[top++] = c;
            } else if (c == ')' || c == ']' || c == '}') {
                if (top > 0) {
                    String stackTop = String.valueOf(charStack[top - 1]);
                    String currentChar = String.valueOf(c);
                    String reflectChar = reflection.get(currentChar);
                    if (reflectChar.equalsIgnoreCase(stackTop)) {
                        top--;
                    } else {
                        charStack[top++] = c;
                    }
                } else {
                    charStack[top++] = c;
                }
            }
        } // for

        return top == 0 ? true : false;
    }

}