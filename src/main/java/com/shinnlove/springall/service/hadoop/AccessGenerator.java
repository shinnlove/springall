/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.hadoop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;

/**
 * 模拟生成访问请求的类，给Map-Reduce任务跑。
 *
 * @author shinnlove.jinsheng
 * @version $Id: AccessGenerator.java, v 0.1 2019-01-02 20:46 shinnlove.jinsheng Exp $$
 */
public class AccessGenerator {

    public static void main(String[] args) throws IOException {
        // 模拟基本参数
        String[] remoteAddr = { "192.168.0.100", "192.168.0.101", "192.168.0.102" };
        String[] rtTime = { "21", "43", "67" };
        String[] requestMethod = { "GET", "POST" };
        String[] requestURL = { "http://www.aitaotu.com/silk", "https://www.baidu.com" };
        String[] refer = { "www.aitaotu.com", "www.baidu.com" };
        String[] status = { "200", "301", "302" };
        String[] sendBytes = { "24324", "2345324534" };

        File file = new File("/Users/zhaochensheng/Downloads/ccc.f");

        for (int i = 0; i < 10000; i++) {
            String line = getOne(remoteAddr) + getOne(rtTime) + getOne(requestMethod)
                          + getOne(requestURL) + getOne(refer) + getOne(status) + getOne(sendBytes);
            List<String> lines = new ArrayList<>();
            lines.add(line);
            FileUtils.writeLines(file, lines, true);
        }

    }

    public static String getOne(String[] array) {
        int length = array.length;
        Random r = new Random();
        int rand = r.nextInt(length);
        return array[rand] + " ";
    }

}