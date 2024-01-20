/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.Objects;

/**
 * 2023-01-15 携程面试题目：很长很长的字符串代表数字相加。
 * 
 * @author Tony Zhao
 * @version $Id: XieChengStringNumberAddTesting.java, v 0.1 2024-01-15 2:28 PM Tony Zhao Exp $$
 */
public class XieChengStringNumberAddTesting {

    public String addTwoStringNumber(String number1, String number2) {

        if (Objects.isNull(number1) && Objects.isNull(number2)) {
            return null;
        }

        if (Objects.isNull(number1) && Objects.nonNull(number2)) {
            return number2;
        }

        if (Objects.nonNull(number1) && Objects.isNull(number2)) {
            return number1;
        }

        int len1 = number1.length();
        int len2 = number2.length();

        int p1 = len1 - 1;
        int p2 = len2 - 1;

        // 进位
        int extra = 0;
        StringBuilder s = new StringBuilder();

        while (p1 >= 0 && p2 >= 0) {

            int n1 = number1.charAt(p1) - '0';
            int n2 = number2.charAt(p2) - '0';

            int result = n1 + n2 + extra;
            // clear
            extra = 0;

            if (result > 9) {
                int remain = result % 10;
                extra = (result - remain) / 10;
                result = remain;
            }

            s.append(result);

            p1--;
            p2--;

        } // while

        // number1 is longer
        while (p1 >= 0) {

            int n1 = number1.charAt(p1) - '0';

            int result = n1 + extra;
            // clear
            extra = 0;

            if (result > 9) {
                int remain = result % 10;
                extra = (result - remain) / 10;
                result = remain;
            }

            s.append(result);

            p1--;
        }

        // number2 is longer
        while (p2 >= 0) {

            int n2 = number2.charAt(p2) - '0';

            int result = n2 + extra;
            // clear
            extra = 0;

            if (result > 9) {
                int remain = result % 10;
                extra = (result - remain) / 10;
                result = remain;
            }

            s.append(result);

            p2--;
        }

        // extra

        if (extra > 0) {
            s.append(extra);
        }

        StringBuilder finalResult = new StringBuilder();
        String reverse = s.toString();
        int len3 = reverse.length();
        for (int i = len3 - 1; i >= 0; i--) {
            finalResult.append(reverse.charAt(i));
        }

        return finalResult.toString();
    }

    public static void main(String[] args) {

        XieChengStringNumberAddTesting sr = new XieChengStringNumberAddTesting();

        String number1 = "123343556";
        String number2 = "997345317";
        String validateResult = "1120688873";

        String result = sr.addTwoStringNumber(number1, number2);

        System.out.println(result.equals(validateResult));

        String number3 = "832756923";
        String number4 = "623485235";
        String validateResult2 = "1456242158";

        String result2 = sr.addTwoStringNumber(number3, number4);

        System.out.println(result2.equals(validateResult2));

    }

}