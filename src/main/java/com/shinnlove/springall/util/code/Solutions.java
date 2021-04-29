/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.util.code;

/**
 * @author Tony Zhao
 * @version $Id: Solutions.java, v 0.1 2021-03-10 1:07 PM Tony Zhao Exp $$
 */
public class Solutions {

    public static void main(String[] args) {
        // backstack
        // recursive call, input parameters
        // 4 orientations: i-1, j+1, j-1, i+1
        // dfs?
        // visited => flag = true
        // if mine == 1 => 
        // calculate zone number ?

        int[][] mx = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                   { 0, 1, 0, 0, 0, 0, 1, 1, 1, 1 },
                                   { 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
                                   { 0, 0, 0, 1, 1, 1, 1, 0, 0, 1 },
                                   { 0, 0, 0, 0, 1, 0, 1, 1, 1, 1 },
                                   { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 } };

        Solutions s = new Solutions();
        int result = s.findMineZoneNumbers(mx);
        System.out.println("mine zone number is " + result);
    }

    public int findMineZoneNumbers(int[][] mineZone) {
        // validation
        if (mineZone.length == 0) {
            return 0;
        }
        if (mineZone[0].length == 0) {
            return 0;
        }

        int rows = mineZone.length;
        int cols = mineZone[0].length;

        // visited flags array
        int[][] mineZoneVisitedFlag = new int[rows][cols];
        int mineZoneCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mineZoneVisitedFlag[i][j] == 1 || mineZone[i][j] == 0) {
                    if (mineZoneVisitedFlag[i][j] == 0) {
                        mineZoneVisitedFlag[i][j] = 1;
                    }
                    continue;
                }

                // if zone == 1
                mineZoneCount += 1;
                // mark self first
                mineZoneVisitedFlag[i][j] = 1;
                // mark adjacent
                checkAndMarkAdjacent(rows, cols, i, j, mineZone, mineZoneVisitedFlag);
            }
        }

        return mineZoneCount;
    }

    public void checkAndMarkAdjacent(int rows, int cols, int row, int col, int[][] mineZone,
                                     int[][] visitedFlag) {
        // from left to right, up to down
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            // exceed bound
            return;
        }

        // need to check upside
        if (row - 1 >= 0 && mineZone[row - 1][col] == 1 && visitedFlag[row - 1][col] == 0) {
            visitedFlag[row - 1][col] = 1;
            checkAndMarkAdjacent(rows, cols, row - 1, col, mineZone, visitedFlag);
        }

        // first need to check left
        if (col - 1 >= 0 && mineZone[row][col - 1] == 1 && visitedFlag[row][col - 1] == 0) {
            // mark left
            visitedFlag[row][col - 1] = 1;
            checkAndMarkAdjacent(rows, cols, row, col - 1, mineZone, visitedFlag);
        }

        // check right
        // within bound check
        if (col + 1 < cols && mineZone[row][col + 1] == 1) {
            // mark right
            visitedFlag[row][col + 1] = 1;
            checkAndMarkAdjacent(rows, cols, row, col + 1, mineZone, visitedFlag);
        }

        // check down, within boundary
        if (row + 1 < rows && mineZone[row + 1][col] == 1) {
            visitedFlag[row + 1][col] = 1;
            checkAndMarkAdjacent(rows, cols, row + 1, col, mineZone, visitedFlag);
        }
    }

}