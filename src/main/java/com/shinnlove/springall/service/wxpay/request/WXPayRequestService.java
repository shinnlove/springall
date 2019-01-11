/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.request;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinnlove.springall.service.wxpay.callback.WXPayBizCallback;
import com.shinnlove.springall.service.wxpay.config.WXPayGlobalConfigService;
import com.shinnlove.springall.service.wxpay.handler.WXPayService;
import com.shinnlove.springall.service.wxpay.report.WXPayDomainService;
import com.shinnlove.springall.service.wxpay.report.WXPayReportService;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayGlobalConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.domain.WXPayDomain;
import com.shinnlove.springall.util.wxpay.sdkplus.http.WXPayRequestUtil;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.base.WXPayRequestClient;
import com.shinnlove.springall.util.wxpay.sdkplus.util.WXPayUtil;

/**
 * 微信支付请求受理服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayRequestService.java, v 0.1 2018-12-19 下午9:54 shinnlove.jinsheng Exp $$
 */
@Service
public class WXPayRequestService implements WXPayService {

    /** 微信支付全局配置服务 */
    @Autowired
    private WXPayGlobalConfigService wxPayGlobalConfigService;

    /** 微信支付域名服务 */
    @Autowired
    private WXPayDomainService       wxPayDomainService;

    /** 微信支付上报服务 */
    @Autowired
    private WXPayReportService       wxPayReportService;

    /**
     * @see WXPayService#doPayRequest(com.shinnlove.springall.util.wxpay.sdkplus.service.request.base.WXPayRequestClient, java.util.Map, com.shinnlove.springall.service.wxpay.callback.WXPayBizCallback)
     */
    @Override
    public Map<String, String> doPayRequest(WXPayRequestClient client,
                                            Map<String, String> keyPairs, WXPayBizCallback callback)
                                                                                                    throws Exception {
        // 一次微信请求的payParameters可以作为这个函数的局部变量栈上分配优化...
        Map<String, String> payParams = new HashMap<>();

        // Step1：校验支付入参
        client.checkParameters(keyPairs);

        // Step2：填写请求入参
        client.fillRequestParams(keyPairs, payParams);

        // Step3：组装支付xml报文
        String reqBody = WXPayUtil.mapToXml(payParams);

        // 请求入参
        WXPayMchConfig mchConfig = client.getWxPayMchConfig();
        WXPayDomain domain = wxPayDomainService.getDomain();
        String url = client.requestURLSuffix();
        boolean useCert = client.requestNeedCert();

        // Step4：执行请求并且上报
        String respStr = requestAndReport(mchConfig, domain, url, reqBody, useCert);

        // Step5：解码请求结果
        final Map<String, String> response = client.processResponseXml(respStr);

        // Step6：回调外层
        callback.doPayCallback(response);

        // PS：结果还是返回，如果外面想要...
        return response;
    }

    /**
     * 做请求与自动上报，发现错误之后会拦截上报然后抛出。
     *
     * @param mchConfig 
     * @param domain
     * @param url
     * @param reqBody
     * @param useCert
     * @return
     * @throws Exception
     */
    private String requestAndReport(WXPayMchConfig mchConfig, WXPayDomain domain, String url,
                                    String reqBody, boolean useCert) throws Exception {
        WXPayGlobalConfig config = wxPayGlobalConfigService.getGlobalConfig();
        // 统计信息
        long elapsedTimeMillis = 0;
        long startTimestampMs = WXPayUtil.getCurrentTimestampMs();
        int connectTimeoutMs = config.getHttpConnectionTimeoutMs();
        int readTimeoutMs = config.getHttpReadTimeoutMs();
        boolean firstHasDnsErr = false;
        boolean firstHasConnectTimeout = false;
        boolean firstHasReadTimeout = false;
        Exception exception = null;

        String respStr = "";
        try {
            respStr = WXPayRequestUtil.request(mchConfig, domain.getDomainName(), url, "", reqBody,
                useCert, connectTimeoutMs, readTimeoutMs);
        } catch (UnknownHostException ex) {
            // 处理DNS Error
            exception = ex;
            firstHasDnsErr = true;
        } catch (ConnectTimeoutException ex) {
            // 处理连接超时
            exception = ex;
            firstHasConnectTimeout = true;
        } catch (SocketTimeoutException ex) {
            // 处理端口读取超时
            exception = ex;
            firstHasReadTimeout = true;
        } catch (Exception ex) {
            // 处理其他未知错误
            exception = ex;
        } finally {
            elapsedTimeMillis = WXPayUtil.getCurrentTimestampMs() - startTimestampMs;
        }

        // 域名统计
        wxPayDomainService.statistic(domain.getDomainName(), elapsedTimeMillis, exception);

        // 上报统计
        wxPayReportService.report(mchConfig.getApiKey(), "", elapsedTimeMillis,
            domain.getDomainName(), domain.isPrimaryDomain(), connectTimeoutMs, readTimeoutMs,
            firstHasDnsErr, firstHasConnectTimeout, firstHasReadTimeout);

        if (exception != null) {
            throw exception;
        }

        return respStr;
    }

}