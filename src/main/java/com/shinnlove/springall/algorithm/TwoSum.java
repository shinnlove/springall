/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tony, Zhao
 * @version $Id: TwoSum.java, v 0.1 2020-08-20 12:00 AM Tony, Zhao Exp $$
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] testData = new int[]{3, 2, 4};
        int[] testData2 = new int[]{0, 4, 3, 0};
        int[] testData3 = new int[]{-1, -2, -3, -4, -5};
        int target = 6;
        int target2 = 0;
        int target3 = -8;

        int[] result = twoSum(testData, target);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }

        int[] result2 = twoSum(testData2, target2);
        for (int i = 0; i < result2.length; i++) {
            System.out.println(result2[i]);
        }

        int[] result3 = twoSum(testData3, target3);
        for (int i = 0; i < result3.length; i++) {
            System.out.println(result3[i]);
        }
    }

    public static int[] twoSum(int[] nums, int target) {

        Map<Integer, List<Integer>> dataMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (dataMap.containsKey(nums[i])) {
                List<Integer> positionList = dataMap.get(nums[i]);
                positionList.add(i);
            } else {
                List<Integer> newList = new ArrayList<>();
                newList.add(i);
                dataMap.put(nums[i], newList);
            }
        }

        for (int j = 0; j < nums.length; j++) {
            int diff = target - nums[j];
            if (dataMap.containsKey(diff)) {
                List<Integer> candidates = dataMap.get(diff);
                if (candidates.size() == 1 && candidates.get(0) == j) {
                    continue;
                }

                for (Integer k : candidates) {
                    if (k != j) {
                        return new int[]{j, k};
                    }
                }
            }
        }

        return new int[]{0, 0};
    }


}