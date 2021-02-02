/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * LeetCode 6.
 * <p>
 * wiki: https://leetcode-cn.com/problems/zigzag-conversion/.
 * 
 * Solution from official leetcode site:
 * 
 * 方法二：按行访问
 * 思路
 *
 * 按照与逐行读取 Z 字形图案相同的顺序访问字符串。
 *
 * 算法
 *
 * 首先访问 行 0 中的所有字符，接着访问 行 1，然后 行 2，依此类推...
 *
 * 对于所有整数 kk，
 *
 * 行 00 中的字符位于索引 k \; (2 \cdot \text{numRows} - 2)k(2⋅numRows−2) 处;
 * 行 \text{numRows}-1numRows−1 中的字符位于索引 k \; (2 \cdot \text{numRows} - 2) + \text{numRows} - 1k(2⋅numRows−2)+numRows−1 处;
 * 内部的 行 ii 中的字符位于索引 k \; (2 \cdot \text{numRows}-2)+ik(2⋅numRows−2)+i 以及 (k+1)(2 \cdot \text{numRows}-2)- i(k+1)(2⋅numRows−2)−i 处;
 *
 * @author Tony Zhao
 * @version $Id: ZigZagConversion.java, v 0.1 2021-02-03 12:28 AM Tony Zhao Exp $$
 */
public class ZigZagConversion {

    public static void main(String[] args) {
        String str = "PAYPALISHIRING";

        ZigZagConversion zzc = new ZigZagConversion();

        // test cases
        String t1 = zzc.convert(str, 1);
        String t2 = zzc.convert(str, 2);
        String t3 = zzc.convert(str, 3);
        String t4 = zzc.convert(str, 4);
        String t5 = zzc.convert(str, 5);
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
        System.out.println(t4);
        System.out.println(t5);
    }

    public String convert(String s, int numRows) {
        if (numRows <= 1 || s.length() < 2) {
            return s;
        }

        int len = s.length();
        StringBuilder sb = new StringBuilder();

        if (numRows == 2) {
            for (int i = 0; i < len; i += 2) {
                sb.append(s.charAt(i));
            }
            for (int j = 1; j < len; j += 2) {
                sb.append(s.charAt(j));
            }
            return sb.toString();
        }

        int middleRow = numRows - 2;
        int groupN = numRows + middleRow;

        int kGroup = len / groupN;
        int remain = len % groupN;

        int cols = kGroup * (1 + middleRow);
        if (remain > 0) {
            cols += 1;
        }
        if (remain > numRows) {
            cols += remain - numRows;
        }

        char[][] zigZag = new char[numRows][cols];

        fillZigZag(zigZag, numRows, 0, 0, s, len);

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < cols; j++) {
                if (zigZag[i][j] != 0) {
                    sb.append(zigZag[i][j]);
                }
            }
        }

        return sb.toString();
    }

    private void fillZigZag(char[][] zigZag, int rows, int row, int col, String s, int len) {
        int orientation = 1;
        int index = 0;
        while (index < len) {
            zigZag[row][col] = s.charAt(index);

            row += orientation;
            if (orientation < 0) {
                col += 1;
            }

            if (row >= rows) {
                // exceed bottom
                row -= 2;
                col += 1;
                orientation = -1;
            } else if (row < 0) {
                // exceed top
                row += 2;
                col -= 1;
                orientation = 1;
            }
            index += 1;
        }
    }

    public String convert2(String s, int numRows) {

        if (numRows == 1)
            return s;

        StringBuilder ret = new StringBuilder();
        int n = s.length();
        int cycleLen = 2 * numRows - 2;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j + i < n; j += cycleLen) {
                ret.append(s.charAt(j + i));
                if (i != 0 && i != numRows - 1 && j + cycleLen - i < n)
                    ret.append(s.charAt(j + cycleLen - i));
            }
        }
        return ret.toString();
    }

}