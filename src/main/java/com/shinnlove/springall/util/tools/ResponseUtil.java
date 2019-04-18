/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.util.tools;

import com.alibaba.fastjson.JSONObject;
import com.shinnlove.springall.util.consts.SystemConsts;

/**
 * 输出正确信息给请求方。
 *
 * @author shinnlove.jinsheng
 * @version $Id: ResponseUtil.java, v 0.1 2018-08-10 下午5:04 shinnlove.jinsheng Exp $$
 */
public class ResponseUtil {

    private ResponseUtil() {

    }

    /**
     * 响应前端结果。
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> JSONObject success(T data) {
        JSONObject result = new JSONObject();
        result.put(SystemConsts.SUCCESS, true);
        result.put(SystemConsts.ERROR_CODE, SystemConsts.NO_ERROR_CODE);
        result.put(SystemConsts.ERROR_MSG, SystemConsts.NO_ERROR_MSG);
        result.put(SystemConsts.DATA, data);
        return result;
    }

}