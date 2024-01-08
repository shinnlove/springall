/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * leetcode 59. 螺旋矩阵II
 * 
 * https://leetcode.cn/problems/spiral-matrix-ii/
 * 
 * @author Tony Zhao
 * @version $Id: GenerateMatrix.java, v 0.1 2024-01-07 9:52 PM Tony Zhao Exp $$
 */
public class GenerateMatrix {

    /**
     * 顺时针螺旋方式生成矩阵平方数。
     * 
     * @param n 
     * @return
     */
    public int[][] generateMatrix(int n) {

        int left = 0, right = n - 1, top = 0, bottom = n - 1;
        int[][] mat = new int[n][n];
        int num = 1, target = n * n;

        while (num <= target) {
            for (int i = left; i <= right; i++) {
                // left to right.
                mat[top][i] = num++;
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                // top to bottom.
                mat[i][right] = num++;
            }
            right--;

            for (int i = right; i >= left; i--) {
                // right to left.
                mat[bottom][i] = num++;
            }
            bottom--;

            for (int i = bottom; i >= top; i--) {
                // bottom to top.
                mat[i][left] = num++;
            }
            left++;
        }

        return mat;
    }

}
