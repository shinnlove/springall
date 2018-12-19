/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.report;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayGlobalConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.util.WXPayUtil;

/**
 * 微信支付上报服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayReportService.java, v 0.1 2018-12-19 下午9:02 shinnlove.jinsheng Exp $$
 */
@Service
public class WXPayReportService implements InitializingBean {

    /** 微信支付全局配置 */
    private WXPayGlobalConfig           config;

    /** 微信支付上报地址 */
    private static final String         REPORT_URL                    = "http://report.mch.weixin.qq.com/wxpay/report/default";
    // private static final String REPORT_URL = "http://127.0.0.1:5000/test";

    /** 上报连接超时时间 */
    private static final int            DEFAULT_CONNECTION_TIMEOUT_MS = 6 * 1000;

    /** 上报读取超时时间 */
    private static final int            DEFAULT_READ_TIMEOUT_MS       = 8 * 1000;

    /** 上报消息队列 */
    private LinkedBlockingQueue<String> reportMsgQueue                = null;

    /** 上报任务线程池 */
    private ExecutorService             executorService;

    /**
     * 上报结果就是将信息转成csv的String格式放入队列中。
     *
     * @param apiKey                        商户支付apiKey，用来生成签名（可有可无）
     * @param uuid
     * @param elapsedTimeMillis
     * @param firstDomain
     * @param primaryDomain
     * @param firstConnectTimeoutMillis
     * @param firstReadTimeoutMillis
     * @param firstHasDnsError
     * @param firstHasConnectTimeout
     * @param firstHasReadTimeout
     */
    public void report(String apiKey, String uuid, long elapsedTimeMillis, String firstDomain,
                       boolean primaryDomain, int firstConnectTimeoutMillis,
                       int firstReadTimeoutMillis, boolean firstHasDnsError,
                       boolean firstHasConnectTimeout, boolean firstHasReadTimeout) {
        long currentTimestamp = WXPayUtil.getCurrentTimestamp();
        ReportInfo reportInfo = new ReportInfo(uuid, currentTimestamp, elapsedTimeMillis,
            firstDomain, primaryDomain, firstConnectTimeoutMillis, firstReadTimeoutMillis,
            firstHasDnsError, firstHasConnectTimeout, firstHasReadTimeout);
        String data = reportInfo.toLineString(apiKey);
        WXPayUtil.getLogger().info("report {}", data);
        if (data != null) {
            reportMsgQueue.offer(data);
        }
    }

    /**
     * http 请求，都是Young GC一次性请求。
     *
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @return
     * @throws Exception
     */
    private String httpRequest(String data, int connectTimeoutMs, int readTimeoutMs)
                                                                                    throws Exception {
        // 默认单线程连接管理器、默认连接工厂、默认schema解析、默认dns
        BasicHttpClientConnectionManager connManager;
        connManager = new BasicHttpClientConnectionManager(RegistryBuilder
            .<ConnectionSocketFactory> create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https", SSLConnectionSocketFactory.getSocketFactory()).build(), null, null,
            null);
        HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager)
            .build();

        // 上报信息地址用post
        HttpPost httpPost = new HttpPost(REPORT_URL);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs)
            .setConnectTimeout(connectTimeoutMs).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 "); // TODO: 很重要，用来检测 sdk 的使用情况，要不要加上商户信息？
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 创建异步线程池，若自动上报则线程池while(true)处理队列。
        reportMsgQueue = new LinkedBlockingQueue<String>(config.getReportQueueMaxSize());

        // 添加处理线程(守护线程上报、JVM退出就不上报了)
        executorService = Executors.newFixedThreadPool(config.getReportWorkerNum(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true);
                    return t;
                }
            });

        // 自动上报就处理`reportMsgQueue`队列中的消息
        if (config.isAutoReport()) {
            WXPayUtil.getLogger().info("report worker num: {}", config.getReportWorkerNum());
            for (int i = 0; i < config.getReportWorkerNum(); ++i) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            // 先用 take 获取数据
                            try {
                                StringBuffer sb = new StringBuffer();
                                String firstMsg = reportMsgQueue.take();
                                WXPayUtil.getLogger().info("get first report msg: {}", firstMsg);
                                String msg = null;
                                sb.append(firstMsg); //会阻塞至有消息
                                int remainNum = config.getReportBatchSize() - 1;
                                for (int j = 0; j < remainNum; ++j) {
                                    WXPayUtil.getLogger().info("try get remain report msg");
                                    // msg = reportMsgQueue.poll();  // 不阻塞了
                                    msg = reportMsgQueue.take();
                                    WXPayUtil.getLogger().info("get remain report msg: {}", msg);
                                    if (msg == null) {
                                        // 没有消息了就不上报了
                                        break;
                                    } else {
                                        sb.append("\n");
                                        sb.append(msg);
                                    }
                                }
                                // 上报(httpclient-post上报)
                                httpRequest(sb.toString(), DEFAULT_CONNECTION_TIMEOUT_MS,
                                    DEFAULT_READ_TIMEOUT_MS);
                            } catch (Exception ex) {
                                WXPayUtil.getLogger().warn("report fail. reason: {}",
                                    ex.getMessage());
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * 上报信息对象。
     */
    public static class ReportInfo {

        /**
         * 布尔变量使用int。0为false，1为true。
         */

        // 基本信息
        private String  version = "v0";
        private String  sdk     = "wxpay java sdk v1.0";
        private String  uuid;                           // 交易的标识
        private long    timestamp;                      // 上报时的时间戳，单位秒
        private long    elapsedTimeMillis;              // 耗时，单位 毫秒

        // 针对主域名
        private String  firstDomain;                    // 第1次请求的域名
        private boolean primaryDomain;                  // 是否主域名
        private int     firstConnectTimeoutMillis;      // 第1次请求设置的连接超时时间，单位 毫秒
        private int     firstReadTimeoutMillis;         // 第1次请求设置的读写超时时间，单位 毫秒
        private int     firstHasDnsError;               // 第1次请求是否出现dns问题
        private int     firstHasConnectTimeout;         // 第1次请求是否出现连接超时
        private int     firstHasReadTimeout;            // 第1次请求是否出现连接超时

        public ReportInfo(String uuid, long timestamp, long elapsedTimeMillis, String firstDomain,
                          boolean primaryDomain, int firstConnectTimeoutMillis,
                          int firstReadTimeoutMillis, boolean firstHasDnsError,
                          boolean firstHasConnectTimeout, boolean firstHasReadTimeout) {
            this.uuid = uuid;
            this.timestamp = timestamp;
            this.elapsedTimeMillis = elapsedTimeMillis;
            this.firstDomain = firstDomain;
            this.primaryDomain = primaryDomain;
            this.firstConnectTimeoutMillis = firstConnectTimeoutMillis;
            this.firstReadTimeoutMillis = firstReadTimeoutMillis;
            this.firstHasDnsError = firstHasDnsError ? 1 : 0;
            this.firstHasConnectTimeout = firstHasConnectTimeout ? 1 : 0;
            this.firstHasReadTimeout = firstHasReadTimeout ? 1 : 0;
        }

        @Override
        public String toString() {
            return "ReportInfo{" + "version='" + version + '\'' + ", sdk='" + sdk + '\''
                   + ", uuid='" + uuid + '\'' + ", timestamp=" + timestamp + ", elapsedTimeMillis="
                   + elapsedTimeMillis + ", firstDomain='" + firstDomain + '\''
                   + ", primaryDomain=" + primaryDomain + ", firstConnectTimeoutMillis="
                   + firstConnectTimeoutMillis + ", firstReadTimeoutMillis="
                   + firstReadTimeoutMillis + ", firstHasDnsError=" + firstHasDnsError
                   + ", firstHasConnectTimeout=" + firstHasConnectTimeout
                   + ", firstHasReadTimeout=" + firstHasReadTimeout + '}';
        }

        /**
         * 转换成 csv 格式
         *
         * @return
         */
        public String toLineString(String key) {
            String separator = ",";
            Object[] objects = new Object[] { version, sdk, uuid, timestamp, elapsedTimeMillis,
                    firstDomain, primaryDomain, firstConnectTimeoutMillis, firstReadTimeoutMillis,
                    firstHasDnsError, firstHasConnectTimeout, firstHasReadTimeout };
            StringBuffer sb = new StringBuffer();
            for (Object obj : objects) {
                sb.append(obj).append(separator);
            }
            try {
                String sign = WXPayUtil.HMACSHA256(sb.toString(), key);
                sb.append(sign);
                return sb.toString();
            } catch (Exception ex) {
                return null;
            }

        }

    }

}