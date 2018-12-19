/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.config;

import java.io.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.shinnlove.springall.util.wxpay.sdkplus.enums.WXPayMode;
import com.shinnlove.springall.util.wxpay.sdkplus.enums.WXPaySignType;

/**
 * 微信支付商户配置VO。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayMchConfig.java, v 0.1 2018-12-18 下午3:48 shinnlove.jinsheng Exp $$
 */
public class WXPayMchConfig {

    /** V3版微信支付是MCHID */
    private String        mchId;

    /** 微信服务商代理子商户号 */
    private String        subMchId;

    /** 微信支付公众号appid */
    private String        appId;

    /** 微信支付子公众号appid（服务商模式） */
    private String        subAppId;

    /** 三方app secret */
    private String        appSecret;

    /** 商户API密钥 */
    private String        apiKey;

    /** p12文件 */
    private String        certP12;

    /** 支付证书绝对路径 */
    private String        sslcertPath;

    /** 支付密钥证书绝对路径 */
    private String        sslkeyPath;

    /** 证书根地址 */
    private String        rootcaPem;

    /** 证书文件读入字节流 */
    private InputStream   certStream;

    /** 微信支付种类：0是普通商户、1是服务商模式 */
    private WXPayMode     payMode;

    /** 微信支付签名类型：1是MD5（默认类型）、2是HMACSHA256 */
    private WXPaySignType signType;

    /** 当前商户是否使用沙箱环境进行测试 */
    private boolean       useSandBox;

    /**
     * construct for reflect
     */
    public WXPayMchConfig() {
    }

    /**
     * 构造函数。
     *
     * @param mchId       
     * @param subMchId
     * @param appId
     * @param subAppId
     * @param appSecret
     * @param apiKey
     * @param certP12
     * @param sslcertPath
     * @param sslkeyPath
     * @param rootcaPem
     * @param payMode
     * @param signType
     * @param useSandBox
     */
    public WXPayMchConfig(String mchId, String subMchId, String appId, String subAppId,
                          String appSecret, String apiKey, String certP12, String sslcertPath,
                          String sslkeyPath, String rootcaPem, WXPayMode payMode,
                          WXPaySignType signType, boolean useSandBox) {
        this.mchId = mchId;
        this.subMchId = subMchId;
        this.appId = appId;
        this.subAppId = subAppId;
        this.appSecret = appSecret;
        this.apiKey = apiKey;
        this.certP12 = certP12;
        this.sslcertPath = sslcertPath;
        this.sslkeyPath = sslkeyPath;
        this.rootcaPem = rootcaPem;
        this.payMode = payMode;
        this.signType = signType;
        this.useSandBox = useSandBox;
        // 读入证书（NFS上需要转成二进制流）
        readCertData(sslcertPath);
    }

    /**
     * 根据证书地址读入证书文件二进制流。
     *
     * @param certPath
     */
    private void readCertData(String certPath) {
        //        String certPath = "/Users/zhaochensheng/Documents/Shinnlove/我的资料/微动证书/apiclient_cert.p12";
        InputStream fileStream = null;
        try {
            File file = new File(certPath);
            fileStream = new FileInputStream(file);
            // 文件数据
            byte[] fileData = new byte[(int) file.length()];
            // 读入
            fileStream.read(fileData);
            // 包装流
            certStream = new ByteArrayInputStream(fileData);
        } catch (Exception e) {
            // ...
            certStream = new ByteArrayInputStream(new byte[0]);
        } finally {
            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Getter method for property mchId.
     *
     * @return property value of mchId
     */
    public String getMchId() {
        return mchId;
    }

    /**
     * Setter method for property mchId.
     *
     * @param mchId value to be assigned to property mchId
     */
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    /**
     * Getter method for property subMchId.
     *
     * @return property value of subMchId
     */
    public String getSubMchId() {
        return subMchId;
    }

    /**
     * Setter method for property subMchId.
     *
     * @param subMchId value to be assigned to property subMchId
     */
    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    /**
     * Getter method for property appId.
     *
     * @return property value of appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Setter method for property appId.
     *
     * @param appId value to be assigned to property appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * Getter method for property subAppId.
     *
     * @return property value of subAppId
     */
    public String getSubAppId() {
        return subAppId;
    }

    /**
     * Setter method for property subAppId.
     *
     * @param subAppId value to be assigned to property subAppId
     */
    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    /**
     * Getter method for property appSecret.
     *
     * @return property value of appSecret
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * Setter method for property appSecret.
     *
     * @param appSecret value to be assigned to property appSecret
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    /**
     * Getter method for property apiKey.
     *
     * @return property value of apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Setter method for property apiKey.
     *
     * @param apiKey value to be assigned to property apiKey
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Getter method for property certP12.
     *
     * @return property value of certP12
     */
    public String getCertP12() {
        return certP12;
    }

    /**
     * Setter method for property certP12.
     *
     * @param certP12 value to be assigned to property certP12
     */
    public void setCertP12(String certP12) {
        this.certP12 = certP12;
    }

    /**
     * Getter method for property sslcertPath.
     *
     * @return property value of sslcertPath
     */
    public String getSslcertPath() {
        return sslcertPath;
    }

    /**
     * Setter method for property sslcertPath.
     *
     * @param sslcertPath value to be assigned to property sslcertPath
     */
    public void setSslcertPath(String sslcertPath) {
        this.sslcertPath = sslcertPath;
    }

    /**
     * Getter method for property sslkeyPath.
     *
     * @return property value of sslkeyPath
     */
    public String getSslkeyPath() {
        return sslkeyPath;
    }

    /**
     * Setter method for property sslkeyPath.
     *
     * @param sslkeyPath value to be assigned to property sslkeyPath
     */
    public void setSslkeyPath(String sslkeyPath) {
        this.sslkeyPath = sslkeyPath;
    }

    /**
     * Getter method for property rootcaPem.
     *
     * @return property value of rootcaPem
     */
    public String getRootcaPem() {
        return rootcaPem;
    }

    /**
     * Setter method for property rootcaPem.
     *
     * @param rootcaPem value to be assigned to property rootcaPem
     */
    public void setRootcaPem(String rootcaPem) {
        this.rootcaPem = rootcaPem;
    }

    /**
     * Getter method for property certStream.
     *
     * @return property value of certStream
     */
    public InputStream getCertStream() {
        return certStream;
    }

    /**
     * Setter method for property certStream.
     *
     * @param certStream value to be assigned to property certStream
     */
    public void setCertStream(InputStream certStream) {
        this.certStream = certStream;
    }

    /**
     * Getter method for property payMode.
     *
     * @return property value of payMode
     */
    public WXPayMode getPayMode() {
        return payMode;
    }

    /**
     * Setter method for property payMode.
     *
     * @param payMode value to be assigned to property payMode
     */
    public void setPayMode(WXPayMode payMode) {
        this.payMode = payMode;
    }

    /**
     * Getter method for property signType.
     *
     * @return property value of signType
     */
    public WXPaySignType getSignType() {
        return signType;
    }

    /**
     * Setter method for property signType.
     *
     * @param signType value to be assigned to property signType
     */
    public void setSignType(WXPaySignType signType) {
        this.signType = signType;
    }

    public boolean isUseSandBox() {
        return useSandBox;
    }

    /**
     * Setter method for property useSandBox.
     *
     * @param useSandBox value to be assigned to property useSandBox
     */
    public void setUseSandBox(boolean useSandBox) {
        this.useSandBox = useSandBox;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}