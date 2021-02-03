/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * LeetCode 48. Rotate Image.
 * 
 * wiki: https://leetcode-cn.com/problems/rotate-image/.
 * 
 * @author Tony Zhao
 * @version $Id: RotateImage.java, v 0.1 2021-02-03 10:48 PM Tony Zhao Exp $$
 */
public class RotateImage {

    public static void main(String[] args) {
        // test cases: 
        int[][] matrix = new int[][] { { 5, 1, 9, 11 }, { 2, 4, 8, 10 }, { 13, 3, 6, 7 },
                                       { 15, 14, 12, 16 } };

        int[][] matrix2 = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

        int[][] matrix3 = new int[][] { { 1, 2 }, { 3, 4 } };

        RotateImage ri = new RotateImage();
        ri.rotate(matrix);
        ri.rotate2(matrix);
        ri.rotate(matrix2);
        ri.rotate(matrix3);

        System.out.println("The image1 after rotate is: ");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + ", ");
            }
            System.out.println();
        }

        System.out.println("The image2 after rotate is: ");
        for (int i = 0; i < matrix2.length; i++) {
            for (int j = 0; j < matrix2[i].length; j++) {
                System.out.print(matrix2[i][j] + ", ");
            }
            System.out.println();
        }

        System.out.println("The image3 after rotate is: ");
        for (int i = 0; i < matrix3.length; i++) {
            for (int j = 0; j < matrix3[i].length; j++) {
                System.out.print(matrix3[i][j] + ", ");
            }
            System.out.println();
        }
    }

    public void rotate2(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - 1 - i; j++) {

                // please draw axis to find regulation!

                // first element
                int temp = matrix[i][j];
                // 
                matrix[i][j] = matrix[n - j - 1][i];
                matrix[n - j - 1][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = temp;
            }
        }
    }

    public void rotate(int[][] matrix) {
        int len = matrix.length;
        if (len <= 1) {
            return;
        }
        int total = len / 2;
        if (len % 2 != 0) {
            total += 1;
        }

        rotateEdge(matrix, 0, 0, 0, total, len);
    }

    /**
     * Rotate the given array's four edges with start row and col.
     * 
     * @param matrix        
     * @param startRow
     * @param startCol
     * @param currentRotate
     * @param totalRotate
     * @param originalLen
     */
    private void rotateEdge(int[][] matrix, int startRow, int startCol, int currentRotate,
                            int totalRotate, int originalLen) {
        int iteration = originalLen - currentRotate * 2;
        if (iteration <= 1) {
            return;
        }

        // always do once fast rotate
        // store first
        int tt = matrix[startRow][startCol];
        // fourth to first
        matrix[startRow][startCol] = matrix[iteration - 1][startCol];
        // third to fourth
        matrix[iteration - 1][startCol] = matrix[iteration - 1][iteration - 1];
        // second to third
        matrix[iteration - 1][iteration - 1] = matrix[startRow][iteration - 1];
        // one to second
        matrix[startRow][iteration - 1] = tt;

        if (currentRotate + 1 == totalRotate) {
            // only four vertexes and has done.
            return;
        }

        // top to right
        // if 6*6, start 0,0
        // total rotate = 6/2 = 3, current = 0, first start 0, 1, 2, 3, 4, 5
        // total = 3, current = 1, second start 1, 2, 3, 4
        // total = 3, current = 2, third start 2, 3
        for (int col = startCol + 1, row = startRow; col < iteration - 1; col++, row++) {
            int temp = matrix[startRow][col];
            matrix[startRow][col] = matrix[row + 1][iteration - 1];
            matrix[row + 1][iteration - 1] = temp;
        }

        // top to bottom
        for (int tCol = startCol + 1, bCol = iteration - 1; tCol < iteration - 1; tCol++, bCol--) {
            int temp = matrix[startRow][tCol];
            matrix[startRow][tCol] = matrix[iteration - 1][bCol - 1];
            matrix[iteration - 1][bCol - 1] = temp;
        }

        // top to left
        for (int tCol = startCol + 1, row = iteration - 1; tCol < iteration - 1; tCol++, row--) {
            int temp = matrix[startRow][tCol];
            matrix[startRow][tCol] = matrix[row - 1][startCol];
            matrix[row - 1][startCol] = temp;
        }

        // rotate next
        rotateEdge(matrix, ++startRow, ++startCol, ++currentRotate, totalRotate, originalLen);
    }

}