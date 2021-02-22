/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm.data.structure;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Tony Zhao
 * @version $Id: JavaReadFileAndRegulations.java, v 0.1 2021-02-21 9:42 AM Tony Zhao Exp $$
 */
public class JavaReadFileAndRegulations {

    public static void main(String[] args) {
        File f = new File("/Users/zhaochensheng/Downloads/abc.txt");
        if (!f.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(new FileInputStream(f)))) {
            Map<String, String> readMap = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                readOneLine(line, readMap);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readOneLine(String line, Map<String, String> readMap) {

        String reg = "";
        Pattern p = Pattern.compile(reg);

        System.out.println(line);
    }

}