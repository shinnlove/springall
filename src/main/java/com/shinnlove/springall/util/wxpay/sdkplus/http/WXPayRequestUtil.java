/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.http;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

/**
 * 执行微信支付网络请求服务。
 *
 * TODO：批量定时上报接口速率服务与主备域名切换容灾方案优化。
 *
 * 这个类HTTP请求执行的代码指令，并且每个商户会自己创建一个BasicHttpConnectionManager，就不做成Spring服务了，没有持久化必要。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayRequestUtil.java, v 0.1 2018-12-19 下午1:55 shinnlove.jinsheng Exp $$
 */
public class WXPayRequestUtil {

    /**
     * 真正做Http Post请求的函数。
     *
     * @param socketFactory     带SSL或者普通的Http Socket工厂
     * @param url
     * @param mchId             用来检测商户sdk使用情况
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @return
     * @throws IOException
     */
    public static String doHttpPost(SSLConnectionSocketFactory socketFactory, String url,
                                    String mchId, String data, int connectTimeoutMs,
                                    int readTimeoutMs) throws IOException {

        // http->`PlainConnectionSocketFactory.INSTANCE`等于`PlainConnectionSocketFactory.getSocketFactory()`
        // https->`SSLConnectionSocketFactory.getSocketFactory()`返回一个默认的SocketFactory
        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
            RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", socketFactory).build(), null, null, null);

        // 被`BasicHttpClientConnectionManager`管理的`HttpClient`在线程中创造，如果被多个线程共同调用，占用的时候就会抛出`IllegalStateException`
        // 在这里属于一个可栈上分配的局部变量
        HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager)
            .build();

        HttpPost httpPost = new HttpPost(url);
        // 使用`HttpClients.custom().setDefaultRequestConfig()`可以设置，不需要塞到
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs)
            .setConnectTimeout(connectTimeoutMs).build();
        httpPost.setConfig(requestConfig);

        // 将xml转成字符串，创建了一个字符串实体放入post中
        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 " + mchId); // TODO: 很重要，用来检测 sdk 的使用情况，要不要加上商户信息？
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }

    /**
     * 请求，只请求一次，不做重试。
     *
     * @param mchConfig        
     * @param domain
     * @param urlSuffix
     * @param uuid
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @param useCert 是否使用证书，针对退款、撤销等操作
     * @return
     * @throws Exception
     */
    private static String requestOnce(WXPayMchConfig mchConfig, final String domain,
                                      String urlSuffix, String uuid, String data,
                                      int connectTimeoutMs, int readTimeoutMs, boolean useCert)
                                                                                               throws Exception {
        String mchId = mchConfig.getMchId();
        String url = WXPayConstants.HTTPS + domain + urlSuffix;

        SSLConnectionSocketFactory socketFactory = SSLConnectionSocketFactory.getSocketFactory();
        if (useCert) {
            // 证书SSL通信
            socketFactory = buildMchSslSocketFactory(mchConfig);
        }

        return doHttpPost(socketFactory, url, mchId, data, connectTimeoutMs, readTimeoutMs);
    }

    /**
     * 调用requestOnce，拦截错误、上报结果的。
     *
     * @param mchConfig        
     * @param domain
     * @param urlSuffix
     * @param uuid
     * @param data
     * @param useCert
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @return
     * @throws Exception
     */
    public static String request(WXPayMchConfig mchConfig, String domain, String urlSuffix,
                                 String uuid, String data, boolean useCert, int connectTimeoutMs,
                                 int readTimeoutMs) throws Exception {
        // 做一次请求不做重试
        return requestOnce(mchConfig, domain, urlSuffix, uuid, data, connectTimeoutMs,
            readTimeoutMs, useCert);
    }

    /**
     * 构建商户Mch不同的SSL套接字工厂。
     *
     * @param mchConfig 
     * @return
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     */
    private static SSLConnectionSocketFactory buildMchSslSocketFactory(WXPayMchConfig mchConfig)
                                                                                                throws KeyStoreException,
                                                                                                CertificateException,
                                                                                                NoSuchAlgorithmException,
                                                                                                IOException,
                                                                                                UnrecoverableKeyException,
                                                                                                KeyManagementException {
        // 证书
        char[] password = mchConfig.getMchId().toCharArray();
        InputStream certStream = mchConfig.getCertStream();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(certStream, password);

        // 实例化密钥库 & 初始化密钥工厂
        // `KeyManagerFactory`->`KeyStore`->init
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
            .getDefaultAlgorithm());
        kmf.init(ks, password);

        // 创建 SSLContext
        // `SSLContext`->`KeyManagerFactory`、`trustManagers`、`SecureRandom`->init
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

        // 带证书的`SSLConnectionSocketFactory`(第二个形参是协议supportedProtocols，注意与`SSLContext.getInstance("TLS")`呼应)
        // `SSLConnectionSocketFactory`->`SSLContext`
        return new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null,
            new DefaultHostnameVerifier());
    }

}