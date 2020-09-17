/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * 227. Basic calculator algorithm.
 * 
 * TODOs: Search number should independent implement as a function.
 * 
 * https://leetcode-cn.com/problems/basic-calculator-ii/solution/si-ze-yun-suan-by-yi-wen-statistics/
 * 
 * 方法1——先乘除后加减
 * 算法实际上很简单，但是细节上容易出错，主要有以下几点问题：
 *
 * 空格需要跳过，因此本题更适合while循环，while循环可以轻松地移动指针，到达目的地；
 * 利用Hash记录四个运算符号，后续代码编写可以省事儿很多；
 * 先计算乘法的过程中会遇到两种情况，一种是连乘或连除，一种是第一次碰到乘号或除号；
 * 计算完乘法或者除法以后，一旦遇到加号，进入下一次循环的时候对tmp进行判空；
 * 尾部数字处理，如果最后一个符号是加减，那么尾部数字需要扩展到cal数组中
 * 最后还存在一种情况，说好的是四则运算，结果字符中只有数字，这个时候还需要单独处理
 * 
 * @author Tony, Zhao
 * @version $Id: BasicCalculator.java, v 0.1 2020-09-16 11:11 PM Tony, Zhao Exp $$
 */
public class BasicCalculator {

    public static void main(String[] args) {
        BasicCalculator bc = new BasicCalculator();

        String input1 = "3+2*2";
        String input2 = " 3/2 ";
        String input3 = " 3+5 / 2 ";
        String input4 = "42+25";
        String input5 = "0-2147483647";
        String input6 = "1+1+1";
        String input7 = "0";
        String input8 = "1-1+1";
        String input9 = "2*3+4";
        String input10 = "1*2-3/4+5*6-7*8+9/10";

        int validation1 = 7;
        int validation2 = 1;
        int validation3 = 5;
        int validation4 = 67;
        int validation5 = -2147483647;
        int validation6 = 3;
        int validation7 = 0;
        int validation8 = 1;
        int validation9 = 10;
        int validation10 = -24;

        int result1 = bc.calculate(input1);
        System.out.println("result validation is " + validation1 + ", result is " + result1);

        int result2 = bc.calculate(input2);
        System.out.println("result validation is " + validation2 + ", result is " + result2);

        int result3 = bc.calculate(input3);
        System.out.println("result validation is " + validation3 + ", result is " + result3);

        int result4 = bc.calculate(input4);
        System.out.println("result validation is " + validation4 + ", result is " + result4);

        int result5 = bc.calculate(input5);
        System.out.println("result validation is " + validation5 + ", result is " + result5);

        int result6 = bc.calculate(input6);
        System.out.println("result validation is " + validation6 + ", result is " + result6);

        int result7 = bc.calculate(input7);
        System.out.println("result validation is " + validation7 + ", result is " + result7);

        int result8 = bc.calculate(input8);
        System.out.println("result validation is " + validation8 + ", result is " + result8);

        int result9 = bc.calculate(input9);
        System.out.println("result validation is " + validation9 + ", result is " + result9);

        int result10 = bc.calculate(input10);
        System.out.println("result validation is " + validation10 + ", result is " + result10);
    }

    public int calculate(String s) {
        if (s.length() == 0) {
            return 0;
        }
        String str = s.trim();
        int len = str.length();

        // handle numbers stack
        int[] numbers = new int[len];
        int nTop = 0;

        // handle symbols stack
        char[] symbols = new char[len];
        int sTop = 0;

        boolean prevNumber = false;
        for (int i = 0; i < len; i++) {
            char cur = str.charAt(i);
            // clear inner side space symbol
            while (cur == ' ' && i < len) {
                i++;
                cur = str.charAt(i);
            }

            if (cur == '+' || cur == '-') {
                // plus and minus
                symbols[sTop++] = cur;
                prevNumber = false;
            } else if (cur == '*' || cur == '/') {
                // should handle
                int prev = numbers[--nTop];
                // direct change to ascii code, a is 97, 3 is 51

                int j = i + 1;
                for (; j < len; j++) {
                    char next = str.charAt(j);
                    if (next != ' ') {
                        break;
                    }
                }

                int next = str.charAt(j) - '0';
                int newNumber = 0;
                if (cur == '*') {
                    newNumber = prev * next;
                } else {
                    if (next != 0) {
                        newNumber = (int) prev / next;
                    } else {
                        // didide zero
                        newNumber = 0;
                    }
                }
                // new handle number push stack
                numbers[nTop++] = newNumber;
                // next number has been handled, i+j
                // because middle space, i move to j position
                i = j;
                prevNumber = false;
            } else {
                // only handle numbers

                if (prevNumber == true) {
                    // consistence numbers
                    int prevNum = numbers[--nTop];
                    int current = cur - '0';
                    numbers[nTop++] = prevNum * 10 + current;
                } else {
                    numbers[nTop++] = cur - '0';
                }

                prevNumber = true;
            }
        } // for

        int total = 0;
        while (sTop > 0) {
            int latter = numbers[--nTop];
            char symbol = symbols[--sTop];

            if (symbol == '+') {
                total += latter;
            } else {
                total += 0 - latter;
            }
        }

        return total + numbers[0];
    }

}