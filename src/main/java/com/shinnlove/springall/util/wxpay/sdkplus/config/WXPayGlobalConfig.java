/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.shinnlove.springall.util.wxpay.sdkplus.domain.WXPayDomain;

/**
 * 微信支付全局配置。
 *
 * 本类包含主备域名切换、超时时间控制、重试次数等微信支付通信的全局配置（后续做成动态可切换配置）。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayGlobalConfig.java, v 0.1 2018-12-19 下午4:33 shinnlove.jinsheng Exp $$
 */
public class WXPayGlobalConfig {

    /** http连接超时数 */
    private int         httpConnectionTimeoutMs;

    /** http读取超时数 */
    private int         httpReadTimeoutMs;

    /** 上报线程数 */
    private int         reportWorkerNum;

    /** 一次上报批量数 */
    private int         reportBatchSize;

    /** 健康上报缓存消息的最大数量，会有线程去独立上报。粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受 */
    private int         reportQueueMaxSize;

    /** 是否自动上报（不自动就写到持久化缓存或者MongoDB里） */
    private boolean     autoReport;

    /** 微信支付域名 */
    private WXPayDomain domain;

    /**
     * 这个可以做成spring bean来配置，或者spring-cloud的git配置。
     *
     * @param httpConnectionTimeoutMs
     * @param httpReadTimeoutMs
     * @param reportWorkerNum
     * @param reportBatchSize
     * @param reportQueueMaxSize
     * @param autoReport
     * @param domain
     */
    public WXPayGlobalConfig(int httpConnectionTimeoutMs, int httpReadTimeoutMs,
                             int reportWorkerNum, int reportBatchSize, int reportQueueMaxSize,
                             boolean autoReport, WXPayDomain domain) {
        this.httpConnectionTimeoutMs = httpConnectionTimeoutMs;
        this.httpReadTimeoutMs = httpReadTimeoutMs;
        this.reportWorkerNum = reportWorkerNum;
        this.reportBatchSize = reportBatchSize;
        this.reportQueueMaxSize = reportQueueMaxSize;
        this.autoReport = autoReport;
        this.domain = domain;
    }

    /**
     * Getter method for property httpConnectionTimeoutMs.
     *
     * @return property value of httpConnectionTimeoutMs
     */
    public int getHttpConnectionTimeoutMs() {
        return httpConnectionTimeoutMs;
    }

    /**
     * Setter method for property httpConnectionTimeoutMs.
     *
     * @param httpConnectionTimeoutMs value to be assigned to property httpConnectionTimeoutMs
     */
    public void setHttpConnectionTimeoutMs(int httpConnectionTimeoutMs) {
        this.httpConnectionTimeoutMs = httpConnectionTimeoutMs;
    }

    /**
     * Getter method for property httpReadTimeoutMs.
     *
     * @return property value of httpReadTimeoutMs
     */
    public int getHttpReadTimeoutMs() {
        return httpReadTimeoutMs;
    }

    /**
     * Setter method for property httpReadTimeoutMs.
     *
     * @param httpReadTimeoutMs value to be assigned to property httpReadTimeoutMs
     */
    public void setHttpReadTimeoutMs(int httpReadTimeoutMs) {
        this.httpReadTimeoutMs = httpReadTimeoutMs;
    }

    /**
     * Getter method for property reportWorkerNum.
     *
     * @return property value of reportWorkerNum
     */
    public int getReportWorkerNum() {
        return reportWorkerNum;
    }

    /**
     * Setter method for property reportWorkerNum.
     *
     * @param reportWorkerNum value to be assigned to property reportWorkerNum
     */
    public void setReportWorkerNum(int reportWorkerNum) {
        this.reportWorkerNum = reportWorkerNum;
    }

    /**
     * Getter method for property reportBatchSize.
     *
     * @return property value of reportBatchSize
     */
    public int getReportBatchSize() {
        return reportBatchSize;
    }

    /**
     * Setter method for property reportBatchSize.
     *
     * @param reportBatchSize value to be assigned to property reportBatchSize
     */
    public void setReportBatchSize(int reportBatchSize) {
        this.reportBatchSize = reportBatchSize;
    }

    /**
     * Getter method for property reportQueueMaxSize.
     *
     * @return property value of reportQueueMaxSize
     */
    public int getReportQueueMaxSize() {
        return reportQueueMaxSize;
    }

    /**
     * Setter method for property reportQueueMaxSize.
     *
     * @param reportQueueMaxSize value to be assigned to property reportQueueMaxSize
     */
    public void setReportQueueMaxSize(int reportQueueMaxSize) {
        this.reportQueueMaxSize = reportQueueMaxSize;
    }

    public boolean isAutoReport() {
        return autoReport;
    }

    /**
     * Setter method for property autoReport.
     *
     * @param autoReport value to be assigned to property autoReport
     */
    public void setAutoReport(boolean autoReport) {
        this.autoReport = autoReport;
    }

    /**
     * Getter method for property domain.
     *
     * @return property value of domain
     */
    public WXPayDomain getDomain() {
        return domain;
    }

    /**
     * Setter method for property domain.
     *
     * @param domain value to be assigned to property domain
     */
    public void setDomain(WXPayDomain domain) {
        this.domain = domain;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}