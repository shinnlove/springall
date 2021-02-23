/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 51.
 * wiki: https://leetcode-cn.com/problems/n-queens/solution/.
 * 
 * Standard backtrack algorithms.
 * Time: O(n!)
 * Space: O(n)
 *
 * @author Tony Zhao
 * @version $Id: NQueens.java, v 0.1 2021-02-22 11:56 PM Tony Zhao Exp $$
 */
public class NQueens {

    public static void main(String[] args) {
        int N = 8;
        NQueens nq = new NQueens();
        List<List<String>> res = nq.solveNQueens(N);
        System.out.println(res);
    }

    public List<List<String>> solveNQueens(int n) {
        char[][] chess = initializeChessGrid(n);
        List<List<String>> res = new ArrayList<>();

        doSearch(n, chess, 0, res);

        return res;
    }

    private void doSearch(int n, char[][] chess, int row, List<List<String>> res) {
        if (row == n) {
            res.add(generateChessString(chess));
            return;
        }

        for (int j = 0; j < n; j++) {
            if (!isValid(n, chess, row, j)) {
                continue;
            }

            // placeholder
            chess[row][j] = 'Q';

            // do next recursive attempt
            doSearch(n, chess, row + 1, res);

            // revoke placeholder
            chess[row][j] = '.';
        }
    }

    /**
     * Only need to check same col position which belongs to the rows before.
     * And no need to check left down or right down side since no Queue has been put there.
     * 
     * @param n 
     * @param chess
     * @param row
     * @param col
     * @return
     */
    public boolean isValid(int n, char[][] chess, int row, int col) {
        // check col
        for (int i = 0; i < n; i++) {
            if (i == row) {
                continue;
            }
            if (chess[i][col] == 'Q') {
                return false;
            }
        }

        // check up-left, no need to use while
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (chess[i][j] == 'Q') {
                return false;
            }
        }

        // check up-right
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (chess[i][j] == 'Q') {
                return false;
            }
        }

        return true;
    }

    public List<String> generateChessString(char[][] chess) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < chess.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < chess[i].length; j++) {
                sb.append(chess[i][j]);
            }
            res.add(sb.toString());
        }
        return res;
    }

    public char[][] initializeChessGrid(int n) {
        char[][] chess = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                chess[i][j] = '.';
            }
        }
        return chess;
    }

}