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
 * 带Header的HttpClient请求。
 *
 * @author shinnlove.jinsheng
 * @version $Id: HeaderHttpClient.java, v 0.1 2018-07-03 上午11:52 shinnlove.jinsheng Exp $$
 */
public class HeaderHttpClient {

    //使用httpClient发送请求，使用Jsoup分析网页
    public static void main(String[] args) throws Exception {
        //创建httpClient客户端
        HttpClient hClient = new DefaultHttpClient();
        //创建http发送请求对象，Httpget
        HttpGet hget = new HttpGet("http://www.itcast.cn");
        //设置请求头
        hget.setHeader(
            "Cookie",
            "login_sid_t=f39c57f474a4fbffeeac8b0d727c7310; "
                    + "YF-Ugrow-G0=169004153682ef91866609488943c77f; "
                    + "YF-V5-G0=cd5d86283b86b0d506628aedd6f8896e; WBStorage=7754ff192036c629|undefined;"
                    + " _s_tentry=-; YF-Page-G0=074bd03ae4e08433ef66c71c2777fd84; "
                    + "Apache=1025338456965.9829.1478277156276; "
                    + "SINAGLOBAL=1025338456965.9829.1478277156276; "
                    + "ULV=1478277156293:1:1:1:1025338456965.9829.1478277156276:; "
                    + "SUB=_2AkMvQDcef8NhqwJRmP4Uzm7mbYxwzA_EieLBAH7sJRMxHRl"
                    + "-yj9jqmwNtRBn0SIxPIgzk6P4Umq_twX_A70bVg..; "
                    + "SUBP=0033WrSXqPxfM72-Ws9jqgMF55529P9D9W5J2ZDKK_Q-h8ni.aX3E1Ci");
        hget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 "
                                     + "(KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");
        //设置连接超时，传递响应超时
        hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000)
            .setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000)
            .setParameter(ConnRouteParams.DEFAULT_PROXY, new HttpHost("121.31.71.63", 80));
        //发送请求
        HttpResponse response = hClient.execute(hget);
        //获取网页内容
        String content = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(content);
    }

}