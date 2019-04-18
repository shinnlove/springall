/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.code;

import com.shinnlove.springall.util.enums.BaseEnum;

/**
 * 系统码枚举类。
 *
 * @author shinnlove.jinsheng
 * @version $Id: SystemResultCode.java, v 0.1 2018-01-17 下午8:12 shinnlove.jinsheng Exp $$
 */
public enum SystemResultCode implements BaseEnum {

    /** ---------------------- 公用类:0×× ---------------------- */

    /** 成功 */
    SUCCESS("0", "成功"),

    /** 系统异常 */
    SYSTEM_ERROR("10001", "系统异常"),

    /** 完成 */
    FINISHED("10002", "完成"),

    /** 无效的参数 */
    ILLEGAL_ARGUMENT("10003", "无效的参数"),

    /** 参数异常 */
    PARAM_INVALID("10004", "参数异常"),

    /** 服务调用出错 */
    SERVICE_INVOKE_ERROR("10005", "服务调用出错。"),

    /** 数据访问出错 */
    DB_ACCESS_ERROR("10006", "数据访问出错"),

    /** 数据库SQL查询出错 */
    DB_QUERY_ERROR("10007", "数据库SQL查询出错"),

    /** 数据SQL执行出错 */
    DB_EXECUTE_ERROR("10008", "数据SQL执行出错"),

    /** 文件上传出错 */
    FILE_UPLOAD_ERROR("10009", "文件上传出错"),

    ;

    /** 错误码 */
    private String code;

    /** 错误描述 */
    private String message;

    /**
     * 枚举构造函数。
     *
     * @param code
     * @param message
     */
    SystemResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过code获取枚举
     *
     * @param code 代码
     * @return 枚举
     */
    public static SystemResultCode getEnumByCode(String code) {
        for (SystemResultCode entry : SystemResultCode.values()) {
            if (entry.getCode().equals(code)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * @see BaseEnum#getCode()
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @see BaseEnum#getMessage()
     */
    @Override
    public String getMessage() {
        return message;
    }

}