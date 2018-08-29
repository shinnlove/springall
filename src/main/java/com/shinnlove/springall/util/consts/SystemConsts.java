/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.consts;

/**
 * 系统常用公共量。
 *
 * @author shinnlove.jinsheng
 * @version $Id: SystemConsts.java, v 0.1 2018-08-10 下午4:09 shinnlove.jinsheng Exp $$
 */
public final class SystemConsts {

    private SystemConsts() {

    }

    /** 是否成功(兼容字段，某些老系统必须要判断success=true) */
    public static final String SUCCESS       = "success";

    /** 错误码，0代表成功，其他都有错误信息 */
    public static final String ERROR_CODE    = "errCode";

    /** 错误信息，0的时候是ok，其他都各自信息 */
    public static final String ERROR_MSG     = "errMsg";

    /** 没有错误的code */
    public static final String NO_ERROR_CODE = "0";

    /** 没有错误的信息 */
    public static final String NO_ERROR_MSG  = "ok";

    /** 数据字段，如果是查询类型的 */
    public static final String DATA          = "data";

    /** 系统错误统一用-1返回 */
    public static final String SYSTEM_ERROR  = "-1";

}