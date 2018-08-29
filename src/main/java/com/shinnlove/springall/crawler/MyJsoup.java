/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Jsoup工具的使用。
 *
 * @author shinnlove.jinsheng
 * @version $Id: MyJsoup.java, v 0.1 2018-07-03 上午11:56 shinnlove.jinsheng Exp $$
 */
public class MyJsoup {

    /*
     * 需求：使用Jsoup解析网页源码
     */
    public static void main(String[] args) throws Exception {
        // 使用jsoup向服务器发送请求
        Document doc = Jsoup.connect("http://www.itcast.cn").get();
        // Jsoup使用类型css,Jquery选择器方式获取元素节点
        // Elements elements = doc.getElementsByTag("a");
        // System.out.println(elements.text());
        Elements elements = doc.select("ul.nav_txt a");
        // 循环元素
        for (Element element : elements) {
            System.out.println(element.text() + ":" + element.attr("href"));
        }

    }

}