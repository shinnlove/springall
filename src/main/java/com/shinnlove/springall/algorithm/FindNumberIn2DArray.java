/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * Sword to offer 04.
 * <p>
 * wiki: https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof/.
 * 
 * This is a problem aim on the search start position of this array!!
 *
 * @author Tony Zhao
 * @version $Id: FindNumberIn2DArray.java, v 0.1 2021-01-31 2:02 PM Tony Zhao Exp $$
 */
public class FindNumberIn2DArray {

    public static void main(String[] args) {
        int[][] numbers2d = new int[][] { { 1, 4, 7, 11, 15 }, { 2, 5, 8, 12, 19 },
                                          { 3, 6, 9, 16, 22 }, { 10, 13, 14, 17, 24 },
                                          { 18, 21, 23, 26, 30 } };
        int target = 5;

        int[][] numbers2d2 = new int[][] { { 1, 4, 7, 11, 15 }, { 2, 5, 8, 12, 19 },
                                           { 3, 6, 9, 16, 22 }, { 10, 13, 14, 17, 24 },
                                           { 18, 21, 23, 26, 30 } };
        int target2 = 20;

        int[][] numbers2d3 = new int[][] { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 },
                                           { 11, 12, 13, 14, 15 }, { 16, 17, 18, 19, 20 },
                                           { 21, 22, 23, 24, 25 } };
        int target3 = 19;

        FindNumberIn2DArray fni2a = new FindNumberIn2DArray();
        boolean result = fni2a.findNumberIn2DArray(numbers2d, target);
        boolean result2 = fni2a.findNumberIn2DArray(numbers2d2, target2);
        boolean result3 = fni2a.findNumberIn2DArray(numbers2d3, target3);
        System.out.println("Result1 is " + result);
        System.out.println("Result2 is " + result2);
        System.out.println("Result3 is " + result3);

    }

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        int rows = matrix.length;
        if (rows == 0) {
            return false;
        }

        int cols = matrix[0].length;
        if (cols == 0) {
            return false;
        }

        // very important: start from the right corner of this array

        int row = 0;
        int col = cols - 1;

        while (target != matrix[row][col]) {
            if (target > matrix[row][col]) {
                row++;
            } else if (target < matrix[row][col]) {
                col--;
            } else {
                return true;
            }
            if (row >= rows) {
                return false;
            }
            if (col < 0) {
                return false;
            }
        }

        // if reach here, only one possible is while break, that is matrix[row][col] equals target number!
        return true;
    }

}