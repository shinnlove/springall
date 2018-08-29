/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.crawler;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient请求数据。
 *
 * @author shinnlove.jinsheng
 * @version $Id: MyHttpClient.java, v 0.1 2018-07-03 上午11:48 shinnlove.jinsheng Exp $$
 */
public class MyHttpClient {

    /**
     * 需求：使用httpClient爬取传智播客官方网站数据
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // 创建HttpClient对象
        HttpClient hClient = new DefaultHttpClient();

        // 设置响应时间，设置传智源码时间，设置代理服务器(不使用本机的IP爬取，以防止被服务器识别从而IP加入黑名单)
        hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000)
            .setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000)
            .setParameter(ConnRouteParams.DEFAULT_PROXY, new HttpHost("111.155.124.67", 8123));

        // 爬虫URL大部分都是get请求，创建get请求对象
        HttpGet hget = new HttpGet("http://www.itcast.cn/");
        // 向传智播客官方网站发送请求，获取网页源码
        HttpResponse response = hClient.execute(hget);
        // EntityUtils工具类把网页实体转换成字符串
        String content = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(content);

    }

}