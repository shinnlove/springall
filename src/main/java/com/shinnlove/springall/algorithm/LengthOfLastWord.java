/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 考察基本split，但是Java中关于split符号哪些需要使用反斜杠。
 * 
 * 需要转移的字符有：
 * (    [     {    /    ^    -    $     ¦    }    ]    )    ?    *    +    .
 * 
 * Refer to this CSDN wiki: https://blog.csdn.net/Rex_WUST/article/details/100178494.
 * 
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