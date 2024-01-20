/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode 986. 区间列表的交集
 * 
 * https://leetcode.cn/problems/interval-list-intersections/description/
 * 
 * @author Tony Zhao
 * @version $Id: IntervalIntersection.java, v 0.1 2024-01-17 3:04 PM Tony Zhao Exp $$
 */
public class IntervalIntersection {

    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {

        // 包含起始区间的数组
        List<int[]> ans = new ArrayList();

        // 都从第一个区间开始
        int i = 0, j = 0;

        // 这是一个且的关系，如果一行的区间数用完了，就不用再看了
        while (i < firstList.length && j < secondList.length) {

            // Let's check if A[i] intersects B[j].
            // lo - the start point of the intersection
            // hi - the end point of the intersection
            int lo = Math.max(firstList[i][0], secondList[j][0]);
            int hi = Math.min(firstList[i][1], secondList[j][1]);
            if (lo <= hi) {
                // 两者有交集，把这段区间加入
                ans.add(new int[] { lo, hi });
            }

            // Remove the interval with the smallest endpoint
            if (firstList[i][1] < secondList[j][1]) {
                // 看第一个列表下一个区间
                i++;
            } else {
                // 看第二个列表下一个区间
                j++;
            }

        }

        return ans.toArray(new int[ans.size()][]);
    }

    public static void main(String[] args) {

    }

    public void quickSort(int[] arr) {

        int len = arr.length;

        int middle = len / 2;

        partition(arr, 0, middle);
        partition(arr, middle, len - 1);

    }

    public void partition(int[] arr, int low, int high) {

        int middle = (low + high) / 2;
        int pivot = arr[middle];

        while (low < high) {

            while (arr[low] <= pivot) {
                low++;
            }

            while (arr[high] >= pivot) {
                high--;
            }

        }

    }

}