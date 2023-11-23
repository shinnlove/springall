/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 200. 岛屿的数量
 *
 * https://leetcode.cn/problems/number-of-islands/description/
 *
 * @author Tony Zhao
 * @version $Id: NumIslands.java, v 0.1 2023-11-22 9:50 PM Tony Zhao Exp $$
 */
public class NumIslands {

    /**
     * 基于二维数组的通用DFS遍历方法。
     *
     * @param grid      图的二维数组形式、或纯二维数组
     * @param r         行下标
     * @param c         列下标
     */
    void dfs(char[][] grid, int r, int c) {

        // numbers of row and col
        int nr = grid.length;
        int nc = grid[0].length;

        // 下标超过左侧或上端、下标超过右侧或下端、或者遇到海洋(value == 0)直接返回
        if (r < 0 || c < 0 || r >= nr || c >= nc || grid[r][c] == '0') {
            return;
        }

        // 标记当前岛屿已经被访问过
        // 建议标记成2、不要标记0
        grid[r][c] = '0';

        // 同列上端深度递归
        dfs(grid, r - 1, c);

        // 同列下端深度递归
        dfs(grid, r + 1, c);

        // 同行左侧深度递归
        dfs(grid, r, c - 1);

        // 同行右侧深度递归
        dfs(grid, r, c + 1);
    }

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        // 用来作为遍历终点
        int nr = grid.length;
        int nc = grid[0].length;

        // 起始数量是0
        int num_islands = 0;
        for (int r = 0; r < nr; ++r) {
            for (int c = 0; c < nc; ++c) {

                // 当第一次遇到未扫描的岛屿时、岛屿数量加1、进一步深度dfs扫描
                if (grid[r][c] == '1') {
                    ++num_islands;

                    // 一次深度扫描全部返回后，很多grid[x][y]地址都已经被标记为'0'或'2'了
                    // 下次就会被挡在if外了
                    dfs(grid, r, c);
                }

            }
        }

        // 最后返回岛屿数量
        return num_islands;
    }

}