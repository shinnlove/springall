/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.enums;

/**
 * 切面日志打印类型。
 *
 * @author shinnlove.jinsheng
 * @version $Id: LogType.java, v 0.1 2018-11-23 下午2:56 shinnlove.jinsheng Exp $$
 */
public enum LogType {

    /** 打印详情 */
    DETAIL("detail"),

    /** 打印摘要 */
    DIGEST("digest"),

    /** 打印结果集数量 */
    SIZE("size"),

    /** 打印匹配关键打字的 */
    KEYWORD("keyword");

    private String type;

    LogType(String type) {
        this.type = type;
    }

    /**
     * Getter method for property type.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }

}