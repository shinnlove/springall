/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 827. 最大人工岛
 *
 * https://leetcode.cn/problems/making-a-large-island/description/
 *
 * todo: 研究一下怎么搞，写一下代码注释。
 *
 * @author Tony Zhao
 * @version $Id: LargestIsland.java, v 0.1 2023-11-22 11:45 PM Tony Zhao Exp $$
 */
public class LargestIsland {

    static int[] d = { 0, -1, 0, 1, 0 };

    public int largestIsland(int[][] grid) {
        int n = grid.length, res = 0;
        int[][] tag = new int[n][n];
        Map<Integer, Integer> area = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && tag[i][j] == 0) {
                    int t = i * n + j + 1;
                    area.put(t, dfs(grid, i, j, tag, t));
                    res = Math.max(res, area.get(t));
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    int z = 1;
                    Set<Integer> connected = new HashSet<Integer>();
                    for (int k = 0; k < 4; k++) {
                        int x = i + d[k], y = j + d[k + 1];
                        if (!valid(n, x, y) || tag[x][y] == 0 || connected.contains(tag[x][y])) {
                            continue;
                        }
                        z += area.get(tag[x][y]);
                        connected.add(tag[x][y]);
                    }
                    res = Math.max(res, z);
                }
            }
        }
        return res;
    }

    public int dfs(int[][] grid, int x, int y, int[][] tag, int t) {
        int n = grid.length, res = 1;
        tag[x][y] = t;
        for (int i = 0; i < 4; i++) {
            int x1 = x + d[i], y1 = y + d[i + 1];
            if (valid(n, x1, y1) && grid[x1][y1] == 1 && tag[x1][y1] == 0) {
                res += dfs(grid, x1, y1, tag, t);
            }
        }
        return res;
    }

    public boolean valid(int n, int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }

}