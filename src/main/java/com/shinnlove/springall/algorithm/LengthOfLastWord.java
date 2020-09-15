/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * @author Tony, Zhao
 * @version $Id: LengthOfLastWord.java, v 0.1 2020-09-15 1:08 PM Tony, Zhao Exp $$
 */
public class LengthOfLastWord {

    public static void main(String[] args) {

    }

    public int lengthOfLastWord(String s) {
        if (s.trim().length() == 0) {
            return 0;
        }
        String[] words = s.split(" ");
        return words[words.length - 1].length();
    }

}