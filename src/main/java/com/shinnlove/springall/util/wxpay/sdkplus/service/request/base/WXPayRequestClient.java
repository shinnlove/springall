/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.request.base;

import java.util.Map;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.http.WXPayRequestUtil;
import com.shinnlove.springall.util.wxpay.sdkplus.service.client.AbstractWXPayClient;
import com.shinnlove.springall.util.wxpay.sdkplus.service.handler.WXPayExecuteHandler;
import com.shinnlove.springall.util.wxpay.sdkplus.service.handler.WXPayParamsHandler;

/**
 * 微信支付主动请求抽象类。
 *
 * 各类请求参数处理接口：{@link WXPayParamsHandler}
 * 各类请求公共执行接口：{@link WXPayExecuteHandler}
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayRequestClient.java, v 0.1 2018-12-18 下午4:13 shinnlove.jinsheng Exp $$
 */
public abstract class WXPayRequestClient extends AbstractWXPayClient implements WXPayParamsHandler,
                                                                    WXPayExecuteHandler {

    /** 微信支付请求对象 */
    protected WXPayRequestUtil wxPayRequestExecutor;

    /**
     * 构造函数。
     *
     * @param wxPayMchConfig
     */
    public WXPayRequestClient(WXPayMchConfig wxPayMchConfig) {
        super(wxPayMchConfig);
    }

    @Override
    public void fillRequestParams(Map<String, String> keyPairs, final Map<String, String> payParams)
                                                                                                    throws Exception {
        // 策略模式上下文填写请求主体信息
        wxPayModeContext.fillRequestMainBodyParams(wxPayMchConfig, payParams);

        // 交给具体的子类完成其他请求必填参数
        fillRequestDetailParams(keyPairs, payParams);
    }

    /**
     * 抽象填入请求需要的具体字段信息。
     * 
     * @param keyPairs  
     * @param payParams
     */
    public abstract void fillRequestDetailParams(Map<String, String> keyPairs,
                                                 final Map<String, String> payParams);

    /**
     * Setter method for property wxPayRequestExecutor.
     *
     * @param wxPayRequestExecutor value to be assigned to property wxPayRequestExecutor
     */
    public void setWxPayRequestExecutor(WXPayRequestUtil wxPayRequestExecutor) {
        this.wxPayRequestExecutor = wxPayRequestExecutor;
    }

}