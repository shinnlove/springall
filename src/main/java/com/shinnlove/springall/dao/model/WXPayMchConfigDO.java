/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.dao.model;

/**
 * 微信支付商户数据库领域模型。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayMchConfigDO.java, v 0.1 2019-01-10 17:50 shinnlove.jinsheng Exp $$
 */
public class WXPayMchConfigDO {

    /** 主键id */
    private long   id;

    /** 微动平台商户id */
    private long   merchantId;

    /** V3版微信支付是MCHID */
    private String mchId;

    /** 微信服务商代理子商户号 */
    private String subMchId;

    /** 微信支付公众号appid */
    private String appId;

    /** 微信支付子公众号appid（服务商模式） */
    private String subAppId;

    /** 三方app secret */
    private String appSecret;

    /** 商户API密钥 */
    private String apiKey;

    /** p12文件 */
    private String certP12;

    /** 支付证书绝对路径 */
    private String sslCertPath;

    /** 支付密钥证书绝对路径 */
    private String sslKeyPath;

    /** 证书根地址 */
    private String rootcaPem;

    /** 微信支付种类：0是普通商户、1是服务商模式 */
    private int    payMode;

    /** 微信支付签名类型：1是MD5（默认类型）、2是HMACSHA256 */
    private int    signType;

    /** 当前商户是否使用沙箱环境进行测试 */
    private int    useSandbox;

    /** 商户微信支付是否可用 */
    private int    available;

    /** create time */
    private int    createTime;

    /** modify time */
    private int    modifyTime;

    /** remark */
    private String remark;

    public WXPayMchConfigDO() {
    }

    public WXPayMchConfigDO(long id, long merchantId, String mchId, String subMchId, String appId,
                            String subAppId, String appSecret, String apiKey, String certP12,
                            String sslCertPath, String sslKeyPath, String rootcaPem, int payMode,
                            int signType, int useSandbox, int available, int createTime,
                            int modifyTime, String remark) {
        this.id = id;
        this.merchantId = merchantId;
        this.mchId = mchId;
        this.subMchId = subMchId;
        this.appId = appId;
        this.subAppId = subAppId;
        this.appSecret = appSecret;
        this.apiKey = apiKey;
        this.certP12 = certP12;
        this.sslCertPath = sslCertPath;
        this.sslKeyPath = sslKeyPath;
        this.rootcaPem = rootcaPem;
        this.payMode = payMode;
        this.signType = signType;
        this.useSandbox = useSandbox;
        this.available = available;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.remark = remark;
    }

    /**
     * Getter method for property id.
     *
     * @return property value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for property id.
     *
     * @param id value to be assigned to property id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter method for property merchantId.
     *
     * @return property value of merchantId
     */
    public long getMerchantId() {
        return merchantId;
    }

    /**
     * Setter method for property merchantId.
     *
     * @param merchantId value to be assigned to property merchantId
     */
    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
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
     * Getter method for property sslCertPath.
     *
     * @return property value of sslCertPath
     */
    public String getSslCertPath() {
        return sslCertPath;
    }

    /**
     * Setter method for property sslCertPath.
     *
     * @param sslCertPath value to be assigned to property sslCertPath
     */
    public void setSslCertPath(String sslCertPath) {
        this.sslCertPath = sslCertPath;
    }

    /**
     * Getter method for property sslKeyPath.
     *
     * @return property value of sslKeyPath
     */
    public String getSslKeyPath() {
        return sslKeyPath;
    }

    /**
     * Setter method for property sslKeyPath.
     *
     * @param sslKeyPath value to be assigned to property sslKeyPath
     */
    public void setSslKeyPath(String sslKeyPath) {
        this.sslKeyPath = sslKeyPath;
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
     * Getter method for property payMode.
     *
     * @return property value of payMode
     */
    public int getPayMode() {
        return payMode;
    }

    /**
     * Setter method for property payMode.
     *
     * @param payMode value to be assigned to property payMode
     */
    public void setPayMode(int payMode) {
        this.payMode = payMode;
    }

    /**
     * Getter method for property signType.
     *
     * @return property value of signType
     */
    public int getSignType() {
        return signType;
    }

    /**
     * Setter method for property signType.
     *
     * @param signType value to be assigned to property signType
     */
    public void setSignType(int signType) {
        this.signType = signType;
    }

    /**
     * Getter method for property useSandbox.
     *
     * @return property value of useSandbox
     */
    public int getUseSandbox() {
        return useSandbox;
    }

    /**
     * Setter method for property useSandbox.
     *
     * @param useSandbox value to be assigned to property useSandbox
     */
    public void setUseSandbox(int useSandbox) {
        this.useSandbox = useSandbox;
    }

    /**
     * Getter method for property available.
     *
     * @return property value of available
     */
    public int getAvailable() {
        return available;
    }

    /**
     * Setter method for property available.
     *
     * @param available value to be assigned to property available
     */
    public void setAvailable(int available) {
        this.available = available;
    }

    /**
     * Getter method for property createTime.
     *
     * @return property value of createTime
     */
    public int getCreateTime() {
        return createTime;
    }

    /**
     * Setter method for property createTime.
     *
     * @param createTime value to be assigned to property createTime
     */
    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    /**
     * Getter method for property modifyTime.
     *
     * @return property value of modifyTime
     */
    public int getModifyTime() {
        return modifyTime;
    }

    /**
     * Setter method for property modifyTime.
     *
     * @param modifyTime value to be assigned to property modifyTime
     */
    public void setModifyTime(int modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * Getter method for property remark.
     *
     * @return property value of remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Setter method for property remark.
     *
     * @param remark value to be assigned to property remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}