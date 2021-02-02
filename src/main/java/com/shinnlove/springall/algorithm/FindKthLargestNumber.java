/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.PriorityQueue;

/**
 * LeetCode 215. 
 * 
 * wiki: https://leetcode-cn.com/problems/kth-largest-element-in-an-array/.
 * 
 * 堆排序或者选择交换。
 * 
 * @author Tony Zhao
 * @version $Id: FindKthLargestNumber.java, v 0.1 2021-02-02 6:47 PM Tony Zhao Exp $$
 */
public class FindKthLargestNumber {

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>((num1, num2) -> num1 - num2);
        for (int i = 0; i < nums.length; i++) {
            heap.add(nums[i]);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        return heap.poll();
    }

}