/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * LeetCode 46. The recall algorithm.
 * 
 * wiki: https://leetcode-cn.com/problems/permutations/.
 * 
 * @author Tony Zhao
 * @version $Id: Permutation.java, v 0.1 2021-01-28 10:59 AM Tony Zhao Exp $$
 */
public class Permutation {

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int len = nums.length;
        boolean[] visited = new boolean[len];
        Deque<Integer> path = new ArrayDeque<>();
        dfs(nums, len, path, visited, result);
        return result;
    }

    /**
     * A recursive call to search permutation use stack and visited flag.
     * 
     * If length equals stack size, it means reach every numbers in array.
     * This would be a possible answer to this question.
     * 
     * @param nums 
     * @param len
     * @param path
     * @param visited
     * @param result
     */
    private void dfs(int[] nums, int len, Deque<Integer> path, boolean[] visited,
                     List<List<Integer>> result) {
        if (len == path.size()) {
            // to end, break recursive
            result.add(new ArrayList<>(path));
            return;
        }

        // 1. most outer loop every number will start its permutation
        // 2. each inner recursive loop will scan every unused number as its level's first number
        // 3. add it to stack end, make it visited
        // 4. do next recursive call till end
        // 5. when exist recursive call, remove this number from stack, make it unvisited
        // 6. loop to try next number as its level first number
        for (int i = 0; i < len; i++) {
            if (visited[i]) {
                continue;
            }
            path.offerLast(nums[i]);
            visited[i] = true;
            dfs(nums, len, path, visited, result);
            visited[i] = false;
            path.pollLast();
        }

    }

}