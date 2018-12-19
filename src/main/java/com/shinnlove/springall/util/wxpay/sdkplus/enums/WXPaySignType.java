/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.enums;

/**
 * 微信支付签约类型。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPaySignType.java, v 0.1 2018-12-18 下午11:19 shinnlove.jinsheng Exp $$
 */
public enum WXPaySignType {

    /** MD5方式 */
    MD5(1, "MD5"),

    /** HMACSHA256方式 */
    HMACSHA256(2, "HMACSHA256");

    private int    code;
    private String message;

    WXPaySignType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Getter method for property code.
     *
     * @return property value of code
     */
    public int getCode() {
        return code;
    }

    /**
     * Getter method for property message.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

}