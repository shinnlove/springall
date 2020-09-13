/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tony, Zhao
 * @version $Id: ValidParentheses.java, v 0.1 2020-09-14 12:30 AM Tony, Zhao Exp $$
 */
public class ValidParentheses {

    public static void main(String[] args) {
        ValidParentheses vp = new ValidParentheses();

        String str1 = "()";
        boolean result1 = vp.isValid(str1);
        System.out.println("String " + str1 + " is " + result1);

        String str2 = "()[]{}";
        boolean result2 = vp.isValid(str2);
        System.out.println("String " + str2 + " is " + result2);

        String str3 = "([)]";
        boolean result3 = vp.isValid(str3);
        System.out.println("String " + str3 + " is " + result3);
    }

    public boolean isValid(String s) {
        if (s.length() == 0) {
            return true;
        }
        if (s.length() == 1) {
            return false;
        }

        Map<String, String> reflectMap = new HashMap<>();
        reflectMap.put(")", "(");
        reflectMap.put("}", "{");
        reflectMap.put("]", "[");

        int top = 0;
        char[] array = new char[10000];
        array[top++] = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (top > 0 && reflectMap.get(String.valueOf(s.charAt(i))) != null
                && reflectMap.get(String.valueOf(s.charAt(i)))
                    .equalsIgnoreCase(String.valueOf(array[top - 1]))) {
                // stack top match pop stack
                --top;
            } else {
                // stack top mismatch push stack
                array[top++] = s.charAt(i);
            }
        }

        if (top > 0) {
            return false;
        } else {
            return true;
        }
    }

}