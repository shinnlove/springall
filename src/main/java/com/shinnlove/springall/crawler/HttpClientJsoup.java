/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.crawler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 使用Jsoup解析爬虫结果。
 *
 * @author shinnlove.jinsheng
 * @version $Id: HttpClientJsoup.java, v 0.1 2018-07-03 上午11:54 shinnlove.jinsheng Exp $$
 */
public class HttpClientJsoup {

    /***
     * 需求：使用httpClient爬取传智播客官方网站数据
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // 创建HttpClient对象
        HttpClient hClient = new DefaultHttpClient();

        // 设置响应时间，设置传智源码时间，设置代理服务器
        /*hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000)
                .setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000)
                .setParameter(ConnRouteParams.DEFAULT_PROXY, new HttpHost("111.155.124.67", 8123));*/

        // 爬虫URL大部分都是get请求，创建get请求对象
        HttpGet hget = new HttpGet("http://www.itcast.cn/");
        hget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 "
                                     + "(KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");
        // 向传智播客官方网站发送请求，获取网页源码
        HttpResponse response = hClient.execute(hget);
        // EntityUtils工具类把网页实体转换成字符串
        String content = EntityUtils.toString(response.getEntity(), "utf-8");
        // 使用Jsoup解析网页内容
        Document document = Jsoup.parse(content);
        // 获取文档标题
        String title = document.title();
        System.out.println(title);

        Elements elements = document.select("ul.nav_txt a");
        System.out.println(elements);
        for (Element element : elements) {
            System.out.println(element.text() + ":" + element.attr("href"));
        }
    }

}