/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tony Zhao
 * @version $Id: Solutions.java, v 0.1 2021-01-27 1:16 PM Tony Zhao Exp $$
 */
public class FindPrimeNumbers {

    public static void main(String[] args) {
        // 20 => 2,3,5,7,9,11,13,17,19
        FindPrimeNumbers s = new FindPrimeNumbers();
        List<Integer> result = s.pickNumbers(100);
        System.out.println(result);
    }

    public List<Integer> pickNumbers(int N) {

        // 2, 3, 5, 7, 11, 13, 17, 19
        // 50  5 sqrt 2 => 2 * 5 * 5
        // 60  7 sqrt 7/8 => 2 * 3 * 10
        List<Integer> nums = new ArrayList<>();

        if (N >= 2) {
            nums.add(2);
        }

        // except 1, start from 3
        for (int i = 3; i <= N; i++) {
            boolean isOne = true;
            int bound = (int) Math.sqrt(N);
            for (int j = 0; j < nums.size(); j++) {
                // only divide by 1 or itself
                // divide prime number found
                int p = nums.get(j);
                if (p > bound) {
                    break;
                }

                if (i % p == 0) {
                    isOne = false;
                    break;
                }
            }
            if (isOne) {
                nums.add(i);
            }
        }

        return nums;
    }

}