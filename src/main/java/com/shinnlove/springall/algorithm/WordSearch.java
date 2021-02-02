/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * LeetCode 79.
 * <p>
 * wiki: https://leetcode-cn.com/problems/word-search/.
 *
 * @author Tony Zhao
 * @version $Id: WordSearch.java, v 0.1 2021-02-02 9:04 PM Tony Zhao Exp $$
 */
public class WordSearch {

    public static void main(String[] args) {
        char[][] board = new char[][] { { 'A', 'B', 'C', 'E' }, { 'S', 'F', 'C', 'S' },
                                        { 'A', 'D', 'E', 'E' } };
        String word = "SEE";

        WordSearch ws = new WordSearch();
        boolean result = ws.exist(board, word);
        System.out.println("Search word found is " + result);
    }

    public boolean exist(char[][] board, String word) {
        boolean exist = true;

        int rows = board.length;
        if (rows == 0) {
            return exist;
        }

        int cols = board[0].length;
        if (cols == 0) {
            return exist;
        }

        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (doSearch(board, rows, cols, visited, i, j, word, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean doSearch(char[][] board, int rows, int cols, boolean[][] visited,
                             int currentRow, int currentCol, String word, int searchIndex) {

        if (currentRow < 0 || currentRow >= rows || currentCol < 0 || currentCol >= cols) {
            return false;
        }

        char c = word.charAt(searchIndex);

        // here direct return false break this recursive call
        if (c != board[currentRow][currentCol]) {
            return false;
        }

        // if equals and logics below:

        if (word.length() == searchIndex + 1) {
            // really find
            return true;
        }

        // visited this row and col!!
        visited[currentRow][currentCol] = true;

        // search next, there are four cases:

        // up
        if (currentRow > 0 && !visited[currentRow - 1][currentCol]) {
            boolean upResult = doSearch(board, rows, cols, visited, currentRow - 1, currentCol,
                word, searchIndex + 1);
            if (upResult) {
                return true;
            }
        }

        // right
        if (currentCol < cols - 1 && !visited[currentRow][currentCol + 1]) {
            boolean rightResult = doSearch(board, rows, cols, visited, currentRow, currentCol + 1,
                word, searchIndex + 1);
            if (rightResult) {
                return true;
            }
        }

        // down
        if (currentRow < rows - 1 && !visited[currentRow + 1][currentCol]) {
            boolean downResult = doSearch(board, rows, cols, visited, currentRow + 1, currentCol,
                word, searchIndex + 1);
            if (downResult) {
                return true;
            }
        }

        if (currentCol > 0 && !visited[currentRow][currentCol - 1]) {
            // left
            boolean leftResult = doSearch(board, rows, cols, visited, currentRow, currentCol - 1,
                word, searchIndex + 1);
            if (leftResult) {
                return true;
            }
        }

        // if reach here, four orientation no meet, end this path, recall mark flag for this row and col
        visited[currentRow][currentCol] = false;

        return false;
    }

}