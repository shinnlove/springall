/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.report;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.stereotype.Service;

import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.domain.DomainStatistic;
import com.shinnlove.springall.util.wxpay.sdkplus.domain.WXPayDomain;

/**
 * 微信支付主备域名切换、统计与上报服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayDomainService.java, v 0.1 2018-12-19 下午9:19 shinnlove.jinsheng Exp $$
 */
@Service
public class WXPayDomainService {

    /** 上次切换到备用域名的时间(只有一个实例，所以只保存一份) */
    private long                         switchToAlternateDomainTime = 0;

    /** 使用备用域名最多时间上限(3分钟后固定切换回主域名尝试) */
    private final int                    MIN_SWITCH_PRIMARY_MSEC     = 3 * 60 * 1000;

    /** 主备域名统计数据的缓存 */
    private Map<String, DomainStatistic> domainData                  = new ConcurrentHashMap<>();

    public synchronized WXPayDomain getDomain() {
        // 主要域名
        DomainStatistic primaryDomain = domainData.get(WXPayConstants.DOMAIN_API);
        // 超时数量和dns解析错误数量小于2
        if (primaryDomain == null || primaryDomain.isGood()) {
            return new WXPayDomain(WXPayConstants.DOMAIN_API, true);
        }

        // 如果主域名经常超时，尝试采用备用域名
        long now = System.currentTimeMillis();
        if (switchToAlternateDomainTime == 0) {
            // 第一次切换成备用域名，直接使用备用域名
            switchToAlternateDomainTime = now;
            return new WXPayDomain(WXPayConstants.DOMAIN_API2, false);
        } else if (now - switchToAlternateDomainTime < MIN_SWITCH_PRIMARY_MSEC) {
            // 第二次及以上使用备用域名、还在3分钟内
            DomainStatistic alternateDomain = domainData.get(WXPayConstants.DOMAIN_API2);
            if (alternateDomain == null || alternateDomain.isGood()
                || alternateDomain.badCount() < primaryDomain.badCount()) {
                // 备用域名OK且超时、失败数量小于主域名，则继续用
                return new WXPayDomain(WXPayConstants.DOMAIN_API2, false);
            } else {
                // 否则切换回主域名
                return new WXPayDomain(WXPayConstants.DOMAIN_API, true);
            }
        } else {
            // 备用域名撑了3分钟以上，强制切换回主域名
            // 重置切换时间、两个域名的失败、超时数(如果主域名还起不来，超时2次后，继续路由到备用域名上)
            switchToAlternateDomainTime = 0;
            primaryDomain.resetCount();
            DomainStatistic alternateDomain = domainData.get(WXPayConstants.DOMAIN_API2);
            if (alternateDomain != null) {
                alternateDomain.resetCount();
            }

            return new WXPayDomain(WXPayConstants.DOMAIN_API, true);
        }
    }

    /**
     * 上报域名统计服务：线程安全的访问不同域名的`DomainStatistic`对象。
     *
     * @param domain
     * @param elapsedTimeMillis
     * @param ex
     */
    public synchronized void statistic(final String domain, long elapsedTimeMillis,
                                       final Exception ex) {
        DomainStatistic info = domainData.get(domain);
        if (info == null) {
            info = new DomainStatistic(domain);
            domainData.put(domain, info);
        }

        if (ex == null) {
            //success
            if (info.getSuccessCount() >= 2) {
                //continue succ, clear error count
                info.clearError();
            } else {
                info.successCountPlus();
            }
        } else if (ex instanceof ConnectTimeoutException) {
            info.clearSuccessAndDNS();
            info.connectionTimeoutCountPlus();
        } else if (ex instanceof UnknownHostException) {
            info.clearSuccess();
            info.dnsErrorCountPlus();
        } else {
            info.clearSuccess();
            info.otherErrorCountPlus();
        }
    }

}