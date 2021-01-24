/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 43.
 * 
 * wiki: https://leetcode-cn.com/problems/multiply-strings/.
 * 
 * @author Tony Zhao
 * @version $Id: MultiplyStringNumbers.java, v 0.1 2021-01-24 4:11 PM Tony Zhao Exp $$
 */
public class MultiplyStringNumbers {

    public static void main(String[] args) {
        // test case:
        String number1 = "123";
        String number2 = "456";

        String result = "56088";

        MultiplyStringNumbers msn = new MultiplyStringNumbers();
        String calculateResult = msn.multiply(number1, number2);

        System.out.println("Calculate result is " + calculateResult + ", result is " + result);
    }

    /**
     * Since the time complexity is O(mn + n²), so let n < m is better.
     *
     * @param num1
     * @param num2
     * @return
     */
    public String multiply(String num1, String num2) {
        if (num1 == null || num2 == null) {
            return null;
        }

        int len1 = num1.length();
        int len2 = num2.length();

        if (len1 == 0 || len2 == 0) {
            return "";
        }

        String longer = len1 > len2 ? num1 : num2;
        String shorter = len1 > len2 ? num2 : num1;

        int longLen = longer.length();
        int shortLen = shorter.length();

        int sPointer = shortLen - 1;
        int bitCount = 0;

        List<String> onceResultList = new ArrayList<>();

        // start multiply
        while (sPointer >= 0) {

            // 1. once result store and cursor
            int[] results = new int[longLen + 1];
            int resultPos = longLen;
            // 2. current multiply number
            int stemp = getNumberFromStringByPos(shorter, sPointer--);
            // 3. exceed flag
            int exceedPos = 0;

            // 4. do calculation
            for (int lPointer = longLen - 1; lPointer >= 0; lPointer--) {
                int ltemp = getNumberFromStringByPos(longer, lPointer);

                int onceBitResult = stemp * ltemp + exceedPos;
                if (onceBitResult >= 10) {
                    onceBitResult %= 10;
                    exceedPos = onceBitResult / 10;
                } else {
                    exceedPos = 0;
                }
                results[resultPos--] = onceBitResult;
            } // for once multiply

            // 5. check exceed flag
            if (exceedPos > 0) {
                results[resultPos--] = exceedPos;
                exceedPos = 0;
            }

            // 6. scan to a string
            boolean allZero = true;
            boolean leadingZero = true;
            StringBuilder onceNumberString = new StringBuilder(results.length + bitCount);
            for (int k = 0; k < results.length; k++) {
                // skip leading zero
                if (results[k] == 0 && leadingZero) {
                    continue;
                }

                leadingZero = false;
                allZero = false;
                onceNumberString.append(results[k]);
            }

            // 7. move position by bit count
            if (bitCount > 0) {
                int bc = bitCount++;
                while (bc-- > 0) {
                    onceNumberString.append("0");
                }
            }

            if (allZero) {
                // skip this multiply calculation in while
                continue;
            }

            // 8. add to once calculation string
            onceResultList.add(onceNumberString.toString());

        } // while bit multiply

        // loop to add each number at last
        int loopLen = onceResultList.size();
        if (loopLen == 0) {
            return "0";
        }
        if (loopLen == 1) {
            return onceResultList.get(0);
        }

        // pick up the first number at top
        String totalSums = onceResultList.get(0);
        for (int k = 1; k < loopLen; k++) {
            String plusNumber = onceResultList.get(k);
            totalSums = addTwoStringNumbers(totalSums, plusNumber);
        }

        return totalSums;
    }

    public String addTwoStringNumbers(String num1, String num2) {
        if (num1 == null) {
            return num2;
        }
        if (num2 == null) {
            return num1;
        }

        int len1 = num1.length();
        int len2 = num2.length();

        if (len1 == 0) {
            return num2;
        }
        if (len2 == 0) {
            return num1;
        }

        int max = len1 > len2 ? len1 : len2;
        int[] sums = new int[max + 1];
        int highBit = 0;
        int pointer1 = len1 - 1;
        int pointer2 = len2 - 1;
        int pointer3 = max;
        while (pointer1 >= 0 && pointer2 >= 0) {
            int temp1 = getNumberFromStringByPos(num1, pointer1--);
            int temp2 = getNumberFromStringByPos(num2, pointer2--);
            int total = temp1 + temp2 + highBit;
            if (total >= 10) {
                total = total % 10;
                highBit = 1;
            } else {
                highBit = 0;
            }
            sums[pointer3--] = total;
        } // end while

        while (pointer1 >= 0) {
            int t = getNumberFromStringByPos(num1, pointer1--);
            int total = t + highBit;
            if (total >= 10) {
                total = total % 10;
                highBit = 1;
            } else {
                highBit = 0;
            }
            sums[pointer3--] = total;
        } // end pointer1

        while (pointer2 >= 0) {
            int t = getNumberFromStringByPos(num2, pointer2--);
            int total = t + highBit;
            if (total >= 10) {
                total = total % 10;
                highBit = 1;
            } else {
                highBit = 0;
            }
            sums[pointer3--] = total;
        } // end pointer2

        if (highBit > 0) {
            sums[pointer3--] = 1;
        }

        StringBuilder sb = new StringBuilder(max + 1);
        boolean allZero = true;
        boolean leadingZero = true;
        for (int k = 0; k < max + 1; k++) {
            if (sums[k] == 0 && leadingZero) {
                continue;
            }
            if (sums[k] != 0) {
                allZero = false;
                leadingZero = false;
            }

            if (!leadingZero) {
                sb.append(sums[k]);
            }
        } // for

        if (allZero) {
            return "0";
        }

        return sb.toString();
    }

    public int getNumberFromStringByPos(String target, int pos) {
        if (target == null || pos < 0 || pos >= target.length()) {
            return 0;
        }
        return target.charAt(pos) - 48;
    }

}