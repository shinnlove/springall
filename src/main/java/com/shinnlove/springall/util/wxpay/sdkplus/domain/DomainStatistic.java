/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 微信域名统计。
 *
 * @author shinnlove.jinsheng
 * @version $Id: DomainStatistic.java, v 0.1 2018-12-19 下午9:19 shinnlove.jinsheng Exp $$
 */
public class DomainStatistic {

    /** 域名 */
    private final String domain;
    /** 成功数 */
    private int          successCount           = 0;
    /** 连接超时数 */
    private int          connectionTimeoutCount = 0;
    /** DNS解析错误 */
    private int          dnsErrorCount          = 0;
    /** 其他请求失败 */
    private int          otherErrorCount        = 0;

    /**
     * 构造函数。
     *
     * @param domain
     */
    public DomainStatistic(String domain) {
        this.domain = domain;
    }

    public void successCountPlus() {
        ++successCount;
    }

    public void connectionTimeoutCountPlus() {
        ++connectionTimeoutCount;
    }

    public void dnsErrorCountPlus() {
        ++dnsErrorCount;
    }

    public void otherErrorCountPlus() {
        ++otherErrorCount;
    }

    public void resetCount() {
        successCount = connectionTimeoutCount = dnsErrorCount = otherErrorCount = 0;
    }

    public void clearError() {
        connectionTimeoutCount = dnsErrorCount = otherErrorCount = 0;
    }

    public void clearSuccess() {
        successCount = 0;
    }

    public void clearSuccessAndDNS() {
        successCount = dnsErrorCount = 0;
    }

    public boolean isGood() {
        return connectionTimeoutCount <= 2 && dnsErrorCount <= 2;
    }

    public int badCount() {
        return connectionTimeoutCount + dnsErrorCount * 5 + otherErrorCount / 4;
    }

    /**
     * Getter method for property domain.
     *
     * @return property value of domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Getter method for property successCount.
     *
     * @return property value of successCount
     */
    public int getSuccessCount() {
        return successCount;
    }

    /**
     * Setter method for property successCount.
     *
     * @param successCount value to be assigned to property successCount
     */
    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    /**
     * Getter method for property connectionTimeoutCount.
     *
     * @return property value of connectionTimeoutCount
     */
    public int getConnectionTimeoutCount() {
        return connectionTimeoutCount;
    }

    /**
     * Setter method for property connectionTimeoutCount.
     *
     * @param connectionTimeoutCount value to be assigned to property connectionTimeoutCount
     */
    public void setConnectionTimeoutCount(int connectionTimeoutCount) {
        this.connectionTimeoutCount = connectionTimeoutCount;
    }

    /**
     * Getter method for property dnsErrorCount.
     *
     * @return property value of dnsErrorCount
     */
    public int getDnsErrorCount() {
        return dnsErrorCount;
    }

    /**
     * Setter method for property dnsErrorCount.
     *
     * @param dnsErrorCount value to be assigned to property dnsErrorCount
     */
    public void setDnsErrorCount(int dnsErrorCount) {
        this.dnsErrorCount = dnsErrorCount;
    }

    /**
     * Getter method for property otherErrorCount.
     *
     * @return property value of otherErrorCount
     */
    public int getOtherErrorCount() {
        return otherErrorCount;
    }

    /**
     * Setter method for property otherErrorCount.
     *
     * @param otherErrorCount value to be assigned to property otherErrorCount
     */
    public void setOtherErrorCount(int otherErrorCount) {
        this.otherErrorCount = otherErrorCount;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}