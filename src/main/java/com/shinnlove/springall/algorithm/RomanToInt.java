/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * I -> 1
 * V -> 5
 * X -> 10
 * L -> 50
 * C -> 100
 * D -> 500
 * M -> 1000
 * 
 * @author Tony, Zhao
 * @version $Id: RomanToInt.java, v 0.1 2020-09-13 10:31 PM Tony, Zhao Exp $$
 */
public class RomanToInt {

    public static void main(String[] args) {
        RomanToInt rti = new RomanToInt();

        String r1 = "IX";
        int result1 = rti.RomanToInt(r1);
        System.out.println(result1);

        String r2 = "LVIII";
        int result2 = rti.RomanToInt(r2);
        System.out.println(result2);
    }

    public int RomanToInt(String s) {
        Map<String, Integer> romanMap = new HashMap<>();
        romanMap.put("I", 1);
        romanMap.put("V", 5);
        romanMap.put("X", 10);
        romanMap.put("L", 50);
        romanMap.put("C", 100);
        romanMap.put("D", 500);
        romanMap.put("M", 1000);

        int len = s.length();
        if (len == 0) {
            return 0;
        }
        if (len == 1) {
            return romanMap.get(String.valueOf(s.charAt(0)));
        }

        int total = 0;
        for (int i = 0; i < len; i++) {
            if (i < len - 1 && romanMap.get(String.valueOf(s.charAt(i))) < romanMap
                .get(String.valueOf(s.charAt(i + 1)))) {
                total += romanMap.get(String.valueOf(s.charAt(i + 1)))
                         - romanMap.get(String.valueOf(s.charAt(i)));
                i++;
            } else {
                total += romanMap.get(String.valueOf(s.charAt(i)));
            }
        }

        return total;
    }

}