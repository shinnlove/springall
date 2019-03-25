/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.exception;

import java.util.List;
import java.util.Map;

/**
 * 系统内的基础异常接口。
 *
 * @author shinnlove.jinsheng
 * @version $Id: BaseException.java, v 0.1 2018-01-17 下午8:16 shinnlove.jinsheng Exp $$
 */
public interface BaseException {

    /**
     * 获取异常信息
     *
     * @return
     */
    Exception getException();

    /**
     * 获取自定义错误码的String类型code
     *
     * @return
     */
    String getResultCodeStr();

    /**
     * 获取最上层的错误原因（展示给前端看的）。
     *
     * @return
     */
    String getBrief();

    /**
     * 获取最根本的错误原因（也可以展示给前端看）。
     *
     * @return
     */
    String getRootCause();

    /**
     * 获取异常场景信息，若没有异常场景直接返回空字符串
     *
     * <P>返回场景集合List
     *
     * @return
     */
    List<String> getDigest();

    /**
     * 获取异常场景信息，若没有异常场景直接返回空字符串
     *
     * <P>返回场景集合List的string格式
     *
     * @return
     */
    String getDigestStr();

    /**
     * 增加开发需要的详情信息。
     *
     * @param key
     * @param value
     */
    void addDetail(String key, String value);

    /**
     * 获取开发者需要的错误码和错误信息Map。
     *
     * @return
     */
    List<Map<String, String>> getDetail();

    /**
     * 获取开发者需要的错误码和错误信息字符串。
     *
     * @return
     */
    String getDetailStr();

}
