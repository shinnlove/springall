/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 带Header的Jsoup。
 *
 * @author shinnlove.jinsheng
 * @version $Id: MyHeaderJsoup.java, v 0.1 2018-07-03 上午11:59 shinnlove.jinsheng Exp $$
 */
public class MyHeaderJsoup {

    public static void main(String[] args) throws IOException {
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like "
                           + "Gecko) Chrome/44.0.2403.157 Safari/537.36";
        //准备cookie信息
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("TC-Ugrow-G0", "968b70b7bcdc28ac97c8130dd353b55e");
        maps.put("SUB", "2AkMvfeeDf8NhqwJRmP0dzGvhZY5yywvEieLBAH7sJRMxHRl-yT9jqmAHtRAgR4BQZgBIE"
                        + "-Xz-jsqjVftcUdtrA..");
        maps.put("SUBP", "0033WrSXqPxfM72-Ws9jqgMF55529P9D9WhBu3bohh6dYkXbY_GUs5d8");
        //获取网页dom对象
        Document doc = Jsoup.connect("http://www.itcast.cn/").userAgent(userAgent).cookies(maps)
            .get();
        //获取文档标签
        String title = doc.title();
        System.out.println(title);
        //获取网页元素
        Elements elements = doc.select("div.qrcode-text");
        System.out.println(elements.text());
    }

}