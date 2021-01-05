/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Integer to Roman number.
 * 
 * 主要考察每一位数字在做转换的过程中低位、中间位和高位的转换，有一个转换函数。
 * 
 * @author Tony, Zhao
 * @version $Id: IntToRoman.java, v 0.1 2020-09-13 11:17 PM Tony, Zhao Exp $$
 */
public class IntToRoman {

    public static void main(String[] args) {
        IntToRoman itr = new IntToRoman();

        int number1 = 1994;
        String result1 = itr.intToRoman(number1);
        System.out.println(result1);

    }

    public String intToRoman(int num) {
        Map<String, Integer> romanMap = new HashMap<>();
        romanMap.put("I", 1);
        romanMap.put("V", 5);
        romanMap.put("X", 10);
        romanMap.put("L", 50);
        romanMap.put("C", 100);
        romanMap.put("D", 500);
        romanMap.put("M", 1000);

        StringBuilder sb = new StringBuilder();

        int thousand = num / 1000;
        int hundred = (num - thousand * 1000) / 100;
        int ten = (num - thousand * 1000 - hundred * 100) / 10;
        int last = num % 10;

        if (thousand != 0) {
            for (int i = 0; i < thousand; i++) {
                sb.append("M");
            }
        }

        if (hundred != 0) {
            sb.append(numberToRomanString(hundred, "C", "D", "M"));
        }

        if (ten != 0) {
            sb.append(numberToRomanString(ten, "X", "L", "C"));
        }

        if (last != 0) {
            sb.append(numberToRomanString(last, "I", "V", "X"));
        }

        return sb.toString();
    }

    /**
     *  Change the number to roman string.
     *
     * @param number    the number on target position, e.g. hundred, means 300
     * @param low       the roman number on this position, e.g. I, X, C
     * @param middle    the roman number on this position, e.g. V, L, D
     * @param high      the roman number on this position, e.g. X, C, M
     * @return
     */
    public String numberToRomanString(int number, String low, String middle, String high) {
        StringBuilder sb = new StringBuilder();
        if (number == 4) {
            return low + middle;
        }
        if (number == 5) {
            return middle;
        }
        if (number == 9) {
            return low + high;
        }

        if (number < 5) {
            for (int i = 0; i < number; i++) {
                sb.append(low);
            }
        } else {
            sb.append(middle);
            for (int i = 0; i < number - 5; i++) {
                sb.append(low);
            }
        }

        return sb.toString();
    }

}