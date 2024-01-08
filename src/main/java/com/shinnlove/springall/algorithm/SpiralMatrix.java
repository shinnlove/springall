/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LeetCode 54.
 * 
 * wiki: https://leetcode-cn.com/problems/spiral-matrix/.
 * 
 * @author Tony Zhao
 * @version $Id: SpiralMatrix.java, v 0.1 2021-01-27 10:00 PM Tony Zhao Exp $$
 */
public class SpiralMatrix {

    public static void main(String[] args) {
        // test case
        int[][] matrix = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        int[][] matrix2 = new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        int[][] matrix3 = new int[][] { { 1 } };

        SpiralMatrix sm = new SpiralMatrix();
        List<Integer> result = sm.spiralOrder(matrix);
        List<Integer> result2 = sm.spiralOrder(matrix2);
        List<Integer> result3 = sm.spiralOrder(matrix3);
        System.out.println("Result1 is " + result);
        System.out.println("Result2 is " + result2);
        System.out.println("Result3 is " + result3);
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        // judge
        int rowLen = matrix.length;
        if (rowLen == 0) {
            return result;
        }

        int colLen = matrix[0].length;
        if (colLen == 0) {
            return result;
        }

        // initialize state flow
        Map<String, String> orientationMap = new HashMap<>();
        orientationMap.put("right", "down");
        orientationMap.put("down", "left");
        orientationMap.put("left", "up");
        orientationMap.put("up", "right");

        // initialize flag
        int[][] matrixFlag = new int[rowLen][colLen];

        int i = 0;
        int j = 0;
        String orientation = "right";

        while (matrixFlag[i][j] == 0) {

            if ("right".equals(orientation)) {
                // move
                while (j < colLen && matrixFlag[i][j] == 0) {
                    matrixFlag[i][j] = 1;
                    result.add(matrix[i][j]);
                    j += 1;
                }
                // rewind
                if (j > 0) {
                    j -= 1;
                }
                if (i < rowLen - 1) {
                    i += 1;
                }
                // change
                orientation = orientationMap.get(orientation);
            }

            if ("down".equals(orientation)) {
                // move
                while (i < rowLen && matrixFlag[i][j] == 0) {
                    matrixFlag[i][j] = 1;
                    result.add(matrix[i][j]);
                    i += 1;
                }
                // rewind
                if (i > 0) {
                    i -= 1;
                }
                if (j > 0) {
                    j -= 1;
                }
                // change
                orientation = orientationMap.get(orientation);
            }

            if ("left".equals(orientation)) {
                // move
                while (j >= 0 && matrixFlag[i][j] == 0) {
                    matrixFlag[i][j] = 1;
                    result.add(matrix[i][j]);
                    j -= 1;
                }
                // rewind
                if (j < colLen - 1) {
                    j += 1;
                }
                if (i > 0) {
                    i -= 1;
                }
                // change
                orientation = orientationMap.get(orientation);
            }

            if ("up".equals(orientation)) {
                // move
                while (i >= 0 && matrixFlag[i][j] == 0) {
                    matrixFlag[i][j] = 1;
                    result.add(matrix[i][j]);
                    i -= 1;
                }
                // rewind
                if (i < rowLen - 1) {
                    i += 1;
                }
                if (j < colLen - 1) {
                    j += 1;
                }
                orientation = orientationMap.get(orientation);
            }

        } // while

        // if the return type is int[], then loop to copy array
        // use Lists.toArray() instead directly ?
        int[] visit = new int[rowLen * colLen];
        for (int k = 0; k < rowLen * colLen; k++) {
            visit[k] = result.get(k);
        }

        return result;
    }

}