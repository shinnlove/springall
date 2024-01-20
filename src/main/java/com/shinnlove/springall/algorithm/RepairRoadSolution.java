/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * Coding Task 1.
 * 
 * @author Tony Zhao
 * @version $Id: RepairRoadSolution.java, v 0.1 2024-01-04 11:41 AM Tony Zhao Exp $$
 */
public class RepairRoadSolution {

    public int repairRoadSolution(String s) {

        if (s == null) {
            return 0;
        }

        int count = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            // if start with 'X'
            if (s.charAt(i) == 'X') {
                // handle consequent three characters within the right bound of array..
                for (int j = i; j < Math.min(i + 3, n); j++) {

                    // change character from X to .
                    char[] chars = s.toCharArray();
                    chars[j] = '.';
                    s = new String(chars);
                }

                // update repair count
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {

        RepairRoadSolution rrs = new RepairRoadSolution();

        String s1 = ".X..X";
        int count1 = rrs.repairRoadSolution(s1);
        System.out.println("repair " + s1 + "road, the count is=" + count1);

        String s2 = "X.XXXXX.X.";
        int count2 = rrs.repairRoadSolution(s2);
        System.out.println("repair " + s2 + "road, the count is=" + count2);

        String s3 = "XX.XXX..";
        int count3 = rrs.repairRoadSolution(s3);
        System.out.println("repair " + s3 + "road, the count is=" + count3);

        String s4 = "XXXX";
        int count4 = rrs.repairRoadSolution(s4);
        System.out.println("repair " + s4 + "road, the count is=" + count4);

    }

}