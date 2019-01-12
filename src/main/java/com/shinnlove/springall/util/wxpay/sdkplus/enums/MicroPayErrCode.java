/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.enums;

import com.shinnlove.springall.util.enums.BaseEnum;

/**
 * 刷卡支付错误码与信息枚举。
 *
 * @author shinnlove.jinsheng
 * @version $Id: MicroPayErrCode.java, v 0.1 2019-01-12 11:18 shinnlove.jinsheng Exp $$
 */
public enum MicroPayErrCode implements BaseEnum {

    USERPAYING("USERPAYING", ""), TRADE_ERROR("TRADE_ERROR", "支付确认失败，用户取消支付");

    private String code;
    private String message;

    /**
     * 枚举构造。
     *
     * @param code
     * @param message
     */
    MicroPayErrCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 通过CODE获取ENUM的名字
     * @param code  枚举code
     * @return      缓存类型枚举
     */
    public static MicroPayErrCode parseByCode(String code) {
        for (MicroPayErrCode type : MicroPayErrCode.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }

}