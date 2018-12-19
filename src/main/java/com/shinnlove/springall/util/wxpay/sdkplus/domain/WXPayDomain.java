/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 微信支付域名。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayDomain.java, v 0.1 2018-12-19 下午8:08 shinnlove.jinsheng Exp $$
 */
public class WXPayDomain {

    /** 域名 */
    public String  domainName;
    /** 该域名是否为主域名。例如:api.mch.weixin.qq.com为主域名 */
    public boolean primaryDomain;

    public WXPayDomain(String domainName, boolean primaryDomain) {
        this.domainName = domainName;
        this.primaryDomain = primaryDomain;
    }

    /**
     * Getter method for property domainName.
     *
     * @return property value of domainName
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * Setter method for property domainName.
     *
     * @param domainName value to be assigned to property domainName
     */
    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public boolean isPrimaryDomain() {
        return primaryDomain;
    }

    /**
     * Setter method for property primaryDomain.
     *
     * @param primaryDomain value to be assigned to property primaryDomain
     */
    public void setPrimaryDomain(boolean primaryDomain) {
        this.primaryDomain = primaryDomain;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}