/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 463. 岛屿的周长
 *
 * https://leetcode.cn/problems/island-perimeter/description/
 *
 * @author Tony Zhao
 * @version $Id: IslandPerimeter.java, v 0.1 2023-11-22 10:23 PM Tony Zhao Exp $$
 */
public class IslandPerimeter {

    /** 横坐标、纵坐标的Pair数组: 分别向右、向下、向上、向左去探索、总共4次 */
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public int islandPerimeter(int[][] grid) {
        int n = grid.length, m = grid[0].length;

        int ans = 0;

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (grid[i][j] == 1) {
                    // 只有未遍历过的结点才做dfs遍历、才把边统计给到ans
                    ans += dfs(i, j, grid, n, m);
                }
            }
        }

        return ans;
    }

    public int dfs(int x, int y, int[][] grid, int n, int m) {
        if (x < 0 || x >= n || y < 0 || y >= m || grid[x][y] == 0) {
            // VIP: 所以在算法题里，很多事情等价计算，也是一种简化的解法
            // x<0触达上边界
            // y<0触达左边界
            // x>=n触达右边界
            // y>=m触达下边界
            // 或者遇到水结点、这样的边也算
            return 1;
        }

        // 基于下面的操作、被访问过的就算作0条边需要统计
        if (grid[x][y] == 2) {
            return 0;
        }

        // 没有访问过
        // 先标记一下这块区域被访问过，变成value=2
        grid[x][y] = 2;

        // 开始统计这块区域的边
        // 但是因为统计这块区域的时候、可能还有其他区域没有遍历到、不能那么快确定是水、还是被访问过的区域
        // 因此还要dfs访问
        int res = 0;
        for (int i = 0; i < 4; ++i) {
            // 分别向右、向下、向上、向左去探索、总共4次
            int tx = x + dx[i];
            int ty = y + dy[i];

            // 将dfs返回的边统计出来即可
            res += dfs(tx, ty, grid, n, m);
        }

        // 返回该层的边数量统计
        // 其实要么就是0要么就是1
        return res;
    }

}