/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.client;

import java.util.Map;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.service.handler.WXPayResponseHandler;
import com.shinnlove.springall.util.wxpay.sdkplus.service.paymode.context.WXPayModeContext;
import com.shinnlove.springall.util.wxpay.sdkplus.util.WXPayUtil;

/**
 * 微信支付抽象公共类。
 *
 * 这个类会派生出`主动请求类`和`被动通知类`，自身不持有通信结果的私有数据！
 * 因为主动请求后得到响应和被动通知得到响应都需要解析微信结果并进行签名验签，因此这个类实现{@link WXPayResponseHandler}。
 *
 * @author shinnlove.jinsheng
 * @version $Id: AbstractWXPayClient.java, v 0.1 2018-12-18 下午4:21 shinnlove.jinsheng Exp $$
 */
public abstract class AbstractWXPayClient implements WXPayResponseHandler {

    /** 微信支付全局配置 */
    protected final WXPayMchConfig wxPayMchConfig;

    /** 微信支付模式（0为普通商户模式、1为服务商模式） */
    protected WXPayModeContext     wxPayModeContext;

    /**
     * 必定要调用的构造函数。
     *
     * @param wxPayMchConfig
     */
    public AbstractWXPayClient(WXPayMchConfig wxPayMchConfig) {
        this.wxPayMchConfig = wxPayMchConfig;
        this.wxPayModeContext = new WXPayModeContext(wxPayMchConfig.getPayMode());
    }

    @Override
    public boolean isResponseSignatureValid(Map<String, String> responseData) {
        try {
            return WXPayUtil.isSignatureValid(responseData, wxPayMchConfig.getApiKey(),
                wxPayMchConfig.getSignType());
        } catch (Exception e) {
            // 打印签名验签错误，不要再往外抛出了
            return false;
        }
    }

    @Override
    public Map<String, String> processResponseXml(String xmlData) throws Exception {
        // 解码
        Map<String, String> respData = WXPayUtil.xmlToMap(xmlData);

        // 完整性
        if (!respData.containsKey(WXPayConstants.RETURN_CODE)) {
            throw new Exception(String.format("No `return_code` in XML: %s", xmlData));
        }

        // 通信返回码
        String return_code = respData.get(WXPayConstants.RETURN_CODE);
        if (!return_code.equals(WXPayConstants.SUCCESS)) {
            // 通信码失败或者无效值
            throw new Exception(String.format(
                "return_code value %s is failed or invalid in XML: %s", return_code, xmlData));
        }

        // 通信返回码OK看验签
        if (!isResponseSignatureValid(respData)) {
            // 验签不过
            throw new Exception(String.format("Invalid sign value in XML: %s", xmlData));
        }

        // 验签OK返回结果给业务层处理成功或失败
        return respData;
    }

    /**
     * Getter method for property wxPayMchConfig.
     *
     * @return property value of wxPayMchConfig
     */
    public WXPayMchConfig getWxPayMchConfig() {
        return wxPayMchConfig;
    }

}