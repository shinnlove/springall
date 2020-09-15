/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * @author Tony, Zhao
 * @version $Id: RemoveElement.java, v 0.1 2020-09-15 1:11 PM Tony, Zhao Exp $$
 */
public class RemoveElement {

    public static void main(String[] args) {

    }

    public int removeElement(int[] nums, int val) {
        int duplicate = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] == val) {
                duplicate++;
                if (i == nums.length - 1) {
                    continue;
                } else {
                    for (int j = i + 1; j <= nums.length - duplicate; j++) {
                        nums[j - 1] = nums[j];
                    }
                }
            }
        }
        return nums.length - duplicate;
    }

}