/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * Leetcode 678.
 * 
 * wiki: https://leetcode-cn.com/problems/valid-parenthesis-string/.
 * 
 * @author Tony Zhao
 * @version $Id: Solutions.java, v 0.1 2021-02-05 3:59 PM Tony Zhao Exp $$
 */
public class ValidParenthesisString {

    public static void main(String[] args) {
        // test cases
        String parenthesisStr = "(())((())()()(*)(*()(())())())()()((()())((()))(*";
        String parenthesisStr2 = "(*))";

        ValidParenthesisString vps = new ValidParenthesisString();
        boolean result1 = vps.checkValidString(parenthesisStr);
        boolean result2 = vps.checkValidString(parenthesisStr2);
        System.out.println("Result1 is " + result1);
        System.out.println("Result2 is " + result2);
    }

    public boolean checkValidString(String s) {
        // judge
        int len = s.length();
        if (len == 0) {
            // empty string ok
            return true;
        }

        // validate len == 1 
        if (len == 1 && s.charAt(0) == '*') {
            return true;
        }

        // use stack to store indexes
        int[] symbolStack = new int[len];
        int[] commonStack = new int[len];

        // always plus self and store
        int symbolTop = -1;
        int commonTop = -1;

        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);

            // principle: stack store index to handle follow up common match
            // symbol stack only store left ( position
            // common stack only store common * position
            if (c == '(') {
                symbolStack[++symbolTop] = i;
            } else if (c == '*') {
                commonStack[++commonTop] = i;
            } else if (c == ')') {
                // priority to match left symbol
                if (symbolTop >= 0) {
                    symbolTop--;
                } else {
                    // no left (, scan right ), minus common symbol
                    if (commonTop >= 0) {
                        commonTop--;
                    } else {
                        // invalid start of right )
                        return false;
                    }
                }
            }

        } // for

        if (symbolTop >= 0) {
            // no enough common to match, direct fail
            if (symbolTop > commonTop) {
                return false;
            }

            // symbolTop <= commonTop
            // do check position
            while (symbolTop >= 0) {

                // compare position
                int symbolPos = symbolStack[symbolTop--];
                int commonPos = commonStack[commonTop--];

                if (symbolPos >= commonPos) {
                    // no follow common to match, direct fail
                    return false;
                }

            } // while
        }

        return true;
    }

}