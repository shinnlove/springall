/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * Leetcode 292.
 * 
 * This problem focus on mathematical induction.
 * 
 * 1 => true
 * 2 => true
 * 3 => true
 * 4 => false
 * 5 => true, first 1, then take 1~3
 * 6 => true, first 2, then take 1~3
 * 7 => true, first 3, then the other become take 4
 * 8 => false, first take 1~3, the other become 5~7, the other could win
 * 9 => true, first 1, then the other become take 8
 * 10 => true, first 2, then the other become take 8
 * 11 => true, first 3, then the other become take 8
 * 12 => false, first take 1~3, the other become 9~11, the other could win
 * 
 * @author Tony Zhao
 * @version $Id: NimGame.java, v 0.1 2021-01-19 6:13 PM Tony Zhao Exp $$
 */
public class NimGame {

    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }

}