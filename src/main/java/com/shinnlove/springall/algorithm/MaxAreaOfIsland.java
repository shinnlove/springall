/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 695. 岛屿的最大面积
 *
 * https://leetcode.cn/problems/max-area-of-island/description/
 *
 * @author Tony Zhao
 * @version $Id: MaxAreaOfIsland.java, v 0.1 2023-11-22 10:56 PM Tony Zhao Exp $$
 */
public class MaxAreaOfIsland {

    /** 横坐标、纵坐标的Pair数组: 分别向右、向下、向上、向左去探索、总共4次 */
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public int maxAreaOfIsland(int[][] grid) {
        int n = grid.length, m = grid[0].length;

        int max = 0;

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (grid[i][j] == 1) {
                    // 只有未遍历过的结点才做dfs遍历、返回的结果跟max相比较
                    max = Math.max(max, dfs(i, j, grid, n, m));
                }
            }
        }

        return max;
    }

    public int dfs(int x, int y, int[][] grid, int n, int m) {
        if (x < 0 || x >= n || y < 0 || y >= m || grid[x][y] == 0 || grid[x][y] == 2) {
            // x<0触达上边界
            // y<0触达左边界
            // x>=n触达右边界
            // y>=m触达下边界
            // 或者遇到水结点
            // 同时、被访问过的结点就不重复计入
            return 0;
        }

        // 没有访问过
        // 先标记一下这块区域被访问过，变成value=2
        grid[x][y] = 2;

        // 自身区域是1
        int area = 1;

        // 向4个方向探索
        for (int i = 0; i < 4; ++i) {

            // 分别向右、向下、向上、向左去探索、总共4次
            int tx = x + dx[i];
            int ty = y + dy[i];

            // 将dfs返回的边统计出来即可
            area += dfs(tx, ty, grid, n, m);
        }

        // 返回该层的统计
        return area;
    }

}