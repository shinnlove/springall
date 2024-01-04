/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.Arrays;

/**
 * Coding Task 2.
 * 
 * @author Tony Zhao
 * @version $Id: WarehouseSolution.java, v 0.1 2023-12-31 6:32 PM Tony Zhao Exp $$
 */
public class WarehouseSolution {

    public int warehouseSolution(int[] boxes) {
        int len = boxes.length;

        // for robust, 0, 1 no need to rearrange...
        if (len <= 1) {
            return 0;
        }

        // only two boxes case
        if (len == 2) {
            if (boxes[1] - boxes[0] == 1) {
                return 0;
            } else {
                return 1;
            }
        }

        // first of all, sort array..
        Arrays.sort(boxes);

        // for common case:

        // initialize variables
        int leftSpan = boxes[1] - boxes[0];
        int minSpan = Integer.MAX_VALUE;
        int minIndex = -1;

        // skip the first and the last
        for (int i = 1; i < boxes.length - 1; i++) {

            int rightSpan = boxes[i + 1] - boxes[i];
            int totalSpan = leftSpan + rightSpan;

            // for memo
            if (totalSpan < minSpan) {
                minSpan = totalSpan;
                minIndex = i;
            }

            // for next iteration
            leftSpan = rightSpan;

        } // for first seek

        int leftMoveIndex = 0;
        int rightMoveIndex = len - 1;

        int startIndex = minIndex;
        int endIndex = minIndex;

        // expand start
        while ((boxes[startIndex] - boxes[startIndex - 1] == 1) && startIndex > leftMoveIndex) {
            startIndex -= 1;
            if (startIndex == leftMoveIndex) {
                break;
            }
        }

        // expand end
        while ((boxes[endIndex + 1] - boxes[endIndex] == 1) && endIndex < rightMoveIndex) {
            endIndex += 1;
            if (endIndex == rightMoveIndex) {
                break;
            }
        }

        // initialize variable
        int movementCount = 0;

        // initialize both side move diff
        int leftMoveDiff = Integer.MAX_VALUE;
        int rightMoveDiff = Integer.MAX_VALUE;

        // initialize both side put span diff

        int leftPutDiff = Integer.MIN_VALUE;
        int rightPutDiff = Integer.MIN_VALUE;

        if (startIndex != leftMoveIndex) {
            leftPutDiff = boxes[startIndex] - boxes[startIndex - 1];
        }

        if (endIndex != rightMoveIndex) {
            rightPutDiff = boxes[endIndex + 1] - boxes[endIndex];
        }

        while (startIndex != leftMoveIndex || endIndex != rightMoveIndex) {

            if (startIndex == leftMoveIndex) {
                leftMoveDiff = 0;
            } else {
                leftMoveDiff = boxes[leftMoveIndex + 1] - boxes[leftMoveIndex];
            }

            if (endIndex == rightMoveIndex) {
                rightMoveDiff = 0;
            } else {
                rightMoveDiff = boxes[rightMoveIndex] - boxes[rightMoveIndex - 1];
            }

            // determine which to move
            if (leftMoveDiff >= rightMoveDiff) {
                // move left
                leftMoveIndex += 1;
            } else {
                // move right
                rightMoveIndex -= 1;
            }

            movementCount += 1;

            // determine where to put
            if (leftPutDiff <= rightPutDiff) {
                // put left
                leftPutDiff--;

                while (leftPutDiff == 1) {
                    // if left coherent, move startIndex
                    startIndex -= 1;

                    if (leftMoveIndex >= startIndex) {
                        leftPutDiff = Integer.MAX_VALUE;
                        break;
                    }

                    // re-count leftPutDiff
                    leftPutDiff = boxes[startIndex] - boxes[startIndex - 1];
                }

            } else {
                // put right
                rightPutDiff--;

                while (rightPutDiff == 1) {
                    // if right coherent, move endIndex
                    endIndex += 1;

                    if (rightMoveIndex <= endIndex) {
                        rightPutDiff = Integer.MAX_VALUE;
                        break;
                    }

                    // re-count rightPutDiff
                    rightPutDiff = boxes[endIndex + 1] - boxes[endIndex];
                }

            }

        }

        return movementCount;
    }

    public String printArray(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < boxes.length - 1; i++) {
            sb.append(boxes[i] + ",");
        }
        sb.append(boxes[boxes.length - 1]);

        return sb.toString();
    }

    public static void main(String[] args) {
        WarehouseSolution ws = new WarehouseSolution();

        int[] array1 = new int[] { 6, 4, 1, 7, 10 };
        int result1 = ws.warehouseSolution(array1);
        System.out
            .println("Min movements from [" + ws.printArray(array1) + "] result is = " + result1);

        int[] array2 = new int[] { 1, 3, 8, 10, 12, 13, 17, 19 };
        int result2 = ws.warehouseSolution(array2);
        System.out
            .println("Min movements from [" + ws.printArray(array2) + "] result is = " + result2);

        int[] array3 = new int[] { 2, 5, 9, 14, 15 };
        int result3 = ws.warehouseSolution(array3);
        System.out
            .println("Min movements from [" + ws.printArray(array3) + "] result is = " + result3);

        int[] array4 = new int[] { 2, 5, 9, 13, 15, 17 };
        int result4 = ws.warehouseSolution(array4);
        System.out
            .println("Min movements from [" + ws.printArray(array4) + "] result is = " + result4);

        int[] array5 = new int[] { 2, 7, 9, 11, 15, 17, 18, 21, 22 };
        int result5 = ws.warehouseSolution(array5);
        System.out
            .println("Min movements from [" + ws.printArray(array5) + "] result is = " + result5);
    }

}