/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * Sword to offer 05.
 * 
 * wiki: https://leetcode-cn.com/problems/ti-huan-kong-ge-lcof/.
 * 
 * The space char's integer number on ASCII table is 32. 
 * 
 * @author Tony Zhao
 * @version $Id: ReplaceSpace.java, v 0.1 2021-01-31 3:24 PM Tony Zhao Exp $$
 */
public class ReplaceSpace {

    public static void main(String[] args) {
        String str = "We are happy.";

        ReplaceSpace rs = new ReplaceSpace();
        String result = rs.replaceSpace(str);
        System.out.println("Result is " + result);
    }

    public String replaceSpace(String s) {
        int len = s.length();
        if (len == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            int ascNum = c;
            if (32 == ascNum) {
                sb.append("%20");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}