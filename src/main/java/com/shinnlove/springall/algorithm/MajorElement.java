/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * Simple example for find the major element.
 * 
 * @author Tony, Zhao
 * @version $Id: MajorElement.java, v 0.1 2020-09-21 4:56 PM Tony, Zhao Exp $$
 */
public class MajorElement {

    public int majorityElement(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return 0;
        }

        int count = 1;
        int current = nums[0];
        for (int i = 1; i < len; i++) {
            if (current == nums[i]) {
                count++;
            } else {
                count--;
                if (0 == count) {
                    count = 1;
                    current = nums[i];
                }
            }
        }

        return current;
    }

}