/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.Arrays;

/**
 * 567. 字符串的排列
 * 
 * https://leetcode.cn/problems/permutation-in-string/
 * 
 * 
 * @author Tony Zhao
 * @version $Id: CheckInclusion.java, v 0.1 2023-11-22 12:58 PM Tony Zhao Exp $$
 */
public class CheckInclusion {

    /**
     * 判断 s2 是否包含 s1 的排列
     * 
     * @param s1 
     * @param s2
     * @return
     */
    public boolean checkInclusion(String s1, String s2) {

        int n = s1.length(), m = s2.length();
        if (n > m) {
            return false;
        }
        int[] cnt1 = new int[26];
        int[] cnt2 = new int[26];
        for (int i = 0; i < n; ++i) {
            ++cnt1[s1.charAt(i) - 'a'];
            ++cnt2[s2.charAt(i) - 'a'];
        }
        if (Arrays.equals(cnt1, cnt2)) {
            return true;
        }

        for (int i = n; i < m; ++i) {
            ++cnt2[s2.charAt(i) - 'a'];
            --cnt2[s2.charAt(i - n) - 'a'];
            if (Arrays.equals(cnt1, cnt2)) {
                return true;
            }
        }

        return false;

    }

}