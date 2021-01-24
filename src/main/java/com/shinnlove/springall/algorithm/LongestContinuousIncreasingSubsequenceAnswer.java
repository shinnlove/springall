/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * The answer of LeetCode 674.
 * 
 * 方法一：贪心
 * 
 * 对于下标范围 [l,r][l,r] 的连续子序列，如果对任意 l \le i<rl≤i<r 都满足 \textit{nums}[i]<\textit{nums}[i+1]nums[i]<nums[i+1]，则该连续子序列是递增序列。
 *
 * 假设数组 \textit{nums}nums 的长度是 nn，对于 0<l \le r<n-10<l≤r<n−1，如果下标范围 [l,r][l,r] 的连续子序列是递增序列，则考虑 \textit{nums}[l-1]nums[l−1] 和 \textit{nums}[r+1]nums[r+1]。
 *
 * 如果 \textit{nums}[l-1]<\textit{nums}[l]nums[l−1]<nums[l]，则将 \textit{nums}[l-1]nums[l−1] 加到 \textit{nums}[l]nums[l] 的前面，可以得到更长的连续递增序列.
 *
 * 如果 \textit{nums}[r+1]>\textit{nums}[r]nums[r+1]>nums[r]，则将 \textit{nums}[r+1]nums[r+1] 加到 \textit{nums}[r]nums[r] 的后面，可以得到更长的连续递增序列。
 *
 * 基于上述分析可知，为了得到最长连续递增序列，可以使用贪心的策略得到尽可能长的连续递增序列。做法是使用记录当前连续递增序列的开始下标和结束下标，遍历数组的过程中每次比较相邻元素，根据相邻元素的大小关系决定是否需要更新连续递增序列的开始下标。
 *
 * 具体而言，令 \textit{start}start 表示连续递增序列的开始下标，初始时 \textit{start}=0start=0，然后遍历数组 \textit{nums}nums，进行如下操作。
 *
 * 如果下标 i>0i>0 且 \textit{nums}[i] \le \textit{nums}[i-1]nums[i]≤nums[i−1]，则说明当前元素小于或等于上一个元素，因此 \textit{nums}[i-1]nums[i−1] 和 \textit{nums}[i]nums[i] 不可能属于同一个连续递增序列，必须从下标 ii 处开始一个新的连续递增序列，因此令 \textit{start}=istart=i。如果下标 i=0i=0 或 \textit{nums}[i]>\textit{nums}[i-1]nums[i]>nums[i−1]，则不更新 \textit{start}start 的值。
 *
 * 此时下标范围 [\textit{start},i][start,i] 的连续子序列是递增序列，其长度为 i-\textit{start}+1i−start+1，使用当前连续递增序列的长度更新最长连续递增序列的长度。
 *
 * 遍历结束之后，即可得到整个数组的最长连续递增序列的长度。
 *
 * 复杂度分析
 *
 * 时间复杂度：O(n)O(n)，其中 nn 是数组 \textit{nums}nums 的长度。需要遍历数组一次。
 *
 * 空间复杂度：O(1)O(1)。额外使用的空间为常数。
 *
 * @author Tony Zhao
 * @version $Id: LongestContinuousIncreasingSubsequenceAnswer.java, v 0.1 2021-01-24 9:04 PM Tony Zhao Exp $$
 */
public class LongestContinuousIncreasingSubsequenceAnswer {

    public static void main(String[] args) {
        // test cases
        int[] nums1 = new int[] { 1, 3, 5, 4, 7 };
        int[] nums2 = new int[] { 2, 2, 2, 2, 2 };
        int[] nums3 = new int[] { 1, 3, 5, 7 };

        LongestContinuousIncreasingSubsequenceAnswer lcisa = new LongestContinuousIncreasingSubsequenceAnswer();
        int result1 = lcisa.findLengthOfLCIS(nums1);
        int result2 = lcisa.findLengthOfLCIS(nums2);
        int result3 = lcisa.findLengthOfLCIS(nums3);
        System.out.println("Longest continuous increasing subsequence is " + result1);
        System.out.println("Longest continuous increasing subsequence is " + result2);
        System.out.println("Longest continuous increasing subsequence is " + result3);
    }

    public int findLengthOfLCIS(int[] nums) {
        int ans = 0;
        int n = nums.length;
        int start = 0;
        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] <= nums[i - 1]) {
                start = i;
            }
            ans = Math.max(ans, i - start + 1);
        }
        return ans;
    }

}