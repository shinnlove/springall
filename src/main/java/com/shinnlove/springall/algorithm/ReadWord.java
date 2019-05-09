/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author shinnlove.jinsheng
 * @version $Id: ReadWord.java, v 0.1 2019-05-07 23:05 shinnlove.jinsheng Exp $$
 */
public class ReadWord {

    private static final String SOURCE_FILE = "/Users/zhaochensheng/Downloads/abc.txt";
    private static final String RESULT_FILE = "/Users/zhaochensheng/Downloads/calculate.txt";

    public static void main(String[] args) {
        Map<String, Long> wordCount = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(SOURCE_FILE))) {
            String line;
            while (null != (line = br.readLine())) {
                String[] lineWords = line.split(" ");
                for (String s : lineWords) {
                    String oneWord = s.trim();
                    if (wordCount.containsKey(oneWord)) {
                        long num = wordCount.get(oneWord);
                        num += 1;
                        wordCount.put(oneWord, num);
                    } else {
                        wordCount.put(oneWord, 1L);
                    }
                }
            } // while
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        File result = new File(RESULT_FILE);

        try (BufferedWriter out = new BufferedWriter(new FileWriter(result))) {
            Iterator<Map.Entry<String, Long>> it = wordCount.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Long> entry = it.next();
                out.write(entry.getKey() + ":" + entry.getValue() + "\r\n");
            }
            out.flush();
        } catch (IOException e) {
        }
    }

}