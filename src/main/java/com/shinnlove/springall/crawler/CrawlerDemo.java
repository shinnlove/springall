/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.crawler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 爬虫demo。
 *
 * @author shinnlove.jinsheng
 * @version $Id: CrawlerDemo.java, v 0.1 2018-07-03 上午11:56 shinnlove.jinsheng Exp $$
 */
public class CrawlerDemo {

    public static void main(String[] args) throws ClientProtocolException, IOException {
        // 创建httpClient客户端
        HttpClient hClient = new DefaultHttpClient();
        // 创建http发送请求对象，Httpget
        HttpGet hget = new HttpGet("http://www.qiakr.com/admin/index.htm");
        // 发送请求
        HttpResponse response = hClient.execute(hget);
        // 获取网页内容
        String content = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(content);
        // 使用Jsoup解析网页内容
        Document document = Jsoup.parse(content);

        // 使用元素选择器选择网页的内容
        // Jsoup选择器用法：http://www.open-open.com/jsoup/selector-syntax.htm
        //        Elements elements = document.select("a[href*=/admin5/register.html#/account]");
        Elements elements = document.select("input.btn-blue");
        System.out.println(elements);
        System.out.println(elements.text());
        System.out.println(elements.html());
        System.out.println(elements.val());

    }

}