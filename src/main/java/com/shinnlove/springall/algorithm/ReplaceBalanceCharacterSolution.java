/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * Nomura Task 3.
 * 
 * @author Tony Zhao
 * @version $Id: ReplaceBalanceCharacterSolution.java, v 0.1 2024-01-04 11:44 AM Tony Zhao Exp $$
 */
public class ReplaceBalanceCharacterSolution {

    public int replaceBalanceCharacterSolution(String row1, String row2) {

        if (row1 == null || row2 == null || row1.length() != row2.length()) {
            // cannot balanced
            return -1;
        }

        int len = row1.length();

        // statistic indicators:
        int firstRowWCount = 0;
        int firstRowRCount = 0;
        int firstRowQuestionOriginCount = 0;
        int firstRowQuestionRemainCount = 0;

        int secondRowWCount = 0;
        int secondRowRCount = 0;
        int secondRowQuestionOriginCount = 0;
        int secondRowQuestionRemainCount = 0;

        // do statistic and basic change one
        for (int i = 0; i < len; i++) {

            // 三种情况

            if (row1.charAt(i) == 'W') {
                // case 'W'

                firstRowWCount += 1;

                if (row2.charAt(i) == 'W') {
                    // same character on the same column string cannot be balanced.
                    return -1;
                } else if (row2.charAt(i) == 'R') {

                    secondRowRCount += 1;

                    // already balanced this column, continue;
                    continue;
                } else {
                    // there is a ?
                    secondRowQuestionOriginCount += 1;

                    // todo: change the character to R

                    // do statistic
                    secondRowRCount += 1;
                }

            } else if (row1.charAt(i) == 'R') {
                // case 'R'

                firstRowRCount += 1;

                if (row2.charAt(i) == 'R') {
                    // same character on the same column string cannot be balanced.
                    return -1;
                } else if (row2.charAt(i) == 'W') {

                    secondRowWCount += 1;

                    // already balanced this column, continue;
                    continue;
                } else {
                    // there is a ?
                    secondRowQuestionOriginCount += 1;

                    // todo: change the character to W

                    // do statistic
                    secondRowWCount += 1;
                }

            } else {
                // case ?
                firstRowQuestionOriginCount += 1;

                // just count

                if (row2.charAt(i) == 'R') {

                    secondRowRCount += 1;

                    // todo: change first row the character from ? to W

                    firstRowWCount += 1;

                } else if (row2.charAt(i) == 'W') {

                    secondRowWCount += 1;

                    // todo: change first row the character from ? to R

                    firstRowRCount += 1;

                } else {
                    secondRowQuestionOriginCount += 1;

                    // both case ? then both plus
                    firstRowQuestionRemainCount += 1;
                    secondRowQuestionRemainCount += 1;
                }

            }

        }

        // final statistic:

        int W2R1stDiffCount = firstRowWCount - firstRowRCount;
        int W2R2ndDiffCount = secondRowWCount - secondRowRCount;

        if (Math.abs(W2R1stDiffCount) > firstRowQuestionRemainCount
            || Math.abs(W2R2ndDiffCount) > secondRowQuestionRemainCount) {
            // either row characters' diff are so many that cannot balanced
            return -1;
        } else {
            if (W2R1stDiffCount > 0) {
                // first row W more than R

                // do balance
                firstRowRCount = firstRowWCount;
                firstRowQuestionRemainCount = firstRowQuestionRemainCount - W2R1stDiffCount;

            } else if (W2R1stDiffCount < 0) {
                // first row W less than R

                // do balance
                firstRowWCount = firstRowRCount;
                firstRowQuestionRemainCount = firstRowQuestionRemainCount + W2R1stDiffCount;
            }

            if (W2R2ndDiffCount > 0) {
                // second row W more than R

                // do balance
                secondRowRCount = secondRowWCount;
                secondRowQuestionRemainCount = secondRowQuestionRemainCount - W2R2ndDiffCount;

            } else if (W2R2ndDiffCount < 0) {
                // first row W less than R

                // do balance
                secondRowWCount = secondRowRCount;
                secondRowQuestionRemainCount = secondRowQuestionRemainCount + W2R2ndDiffCount;
            }
        }

        // global check:

        int eachRowWDiff = firstRowWCount - secondRowWCount;

        if (eachRowWDiff > 0) {
            // first row has more W

            // second row needs balance such W, also with R
            secondRowQuestionRemainCount = secondRowQuestionRemainCount - eachRowWDiff * 2;

        } else if (eachRowWDiff < 0) {
            // second row has more W

            // first row needs balance such W, also with R
            firstRowQuestionRemainCount = firstRowQuestionRemainCount + eachRowWDiff * 2;

        }

        int eachRowRDiff = firstRowRCount - secondRowRCount;

        if (eachRowRDiff > 0) {
            // first row has more R

            // second row needs balance such R, also with W
            secondRowQuestionRemainCount = secondRowQuestionRemainCount - eachRowRDiff * 2;

        } else if (eachRowRDiff < 0) {
            // second row has more R

            // first row needs balance
            firstRowQuestionRemainCount = firstRowQuestionRemainCount + eachRowRDiff * 2;
        }

        // either row doesn't have so many question marks for such replacements.
        if (firstRowQuestionRemainCount < 0 || secondRowQuestionRemainCount < 0) {
            return -1;
        }

        return firstRowQuestionOriginCount - firstRowQuestionRemainCount
               + secondRowQuestionOriginCount - secondRowQuestionRemainCount;
    }

    public static void main(String[] args) {

        ReplaceBalanceCharacterSolution rbc = new ReplaceBalanceCharacterSolution();

        String row11 = "W?WR?";
        String row12 = "R??W?";

        int result1 = rbc.replaceBalanceCharacterSolution(row11, row12);

        if (result1 == 0) {
            System.out.println("The string character is balanced.");
        } else if (result1 == -1) {
            System.out.println("The string character cannot balanced.");
        } else {
            System.out.println("To balance this string need " + result1 + " times replacements.");
        }

        String row21 = "R?R??";
        String row22 = "??W??";

        int result2 = rbc.replaceBalanceCharacterSolution(row21, row22);

        if (result2 == 0) {
            System.out.println("The string character is balanced.");
        } else if (result2 == -1) {
            System.out.println("The string character cannot balanced.");
        } else {
            System.out.println("To balance this string need " + result2 + " times replacements.");
        }

        String row31 = "?RW?WR";
        String row32 = "?WR?RW";

        int result3 = rbc.replaceBalanceCharacterSolution(row31, row32);

        if (result3 == 0) {
            System.out.println("The string character is already balanced.");
        } else if (result3 == -1) {
            System.out.println("The string character cannot balanced.");
        } else {
            System.out.println("To balance this string need " + result3 + " times replacements.");
        }
    }

}