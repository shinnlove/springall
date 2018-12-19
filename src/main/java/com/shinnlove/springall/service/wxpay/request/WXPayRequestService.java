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
import org.springframework.stereotype.Service;

import com.shinnlove.springall.service.wxpay.report.WXPayDomainService;
import com.shinnlove.springall.service.wxpay.report.WXPayReportService;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayGlobalConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.http.WXPayRequestUtil;
import com.shinnlove.springall.util.wxpay.sdkplus.invoker.WXPayInvoker;
import com.shinnlove.springall.util.wxpay.sdkplus.service.handler.WXPayService;
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

    /** 微信支付全局配置 */
    private WXPayGlobalConfig  config;

    /** 微信支付域名服务 */
    private WXPayDomainService wxPayDomainService;

    /** 微信支付上报服务 */
    private WXPayReportService wxPayReportService;

    @Override
    public Map<String, String> doPayRequest(WXPayRequestClient client,
                                            Map<String, String> keyPairs, WXPayInvoker invoker)
                                                                                               throws Exception {
        // 一次微信请求的payParameters可以作为这个函数的局部变量栈上分配优化...
        Map<String, String> payParams = new HashMap<>();

        // Step1：校验支付入参
        client.checkParameters(keyPairs);

        // Step2：填写请求入参
        client.fillRequestParams(keyPairs, payParams);

        // Step3：请求地址
        String url = client.payRequestURL();

        // Step4：组装支付xml报文
        String reqBody = WXPayUtil.mapToXml(payParams);

        // Step5：是否需要证书
        boolean useCert = client.requestNeedCert();

        // 当前选择的请求域名
        String domain = wxPayDomainService.getDomain().getDomainName();

        // Step6：执行请求并且上报
        String respStr = "";
        try {
            respStr = WXPayRequestUtil.request(client.getWxPayMchConfig(), domain, url, reqBody,
                useCert, config.getHttpConnectionTimeoutMs(), config.getHttpReadTimeoutMs());
        } catch (UnknownHostException ex) {

        } catch (ConnectTimeoutException ex) {

        } catch (SocketTimeoutException ex) {

        } catch (Exception ex) {

        }
        // 域名统计
        wxPayDomainService.statistic();
        // 上报统计
        wxPayReportService.report();

        // Step7：解码请求结果
        final Map<String, String> response = client.processResponseXml(respStr);

        // Step8：回调外层
        invoker.doPayCallback(response);

        // PS：结果还是返回，如果外面想要...
        return response;
    }

}