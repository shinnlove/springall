/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.util.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

/**
 * Json公共类。
 *
 * @author shinnlove.jinsheng
 * @version $Id: JsonUtil.java, v 0.1 2019-04-12 20:38 shinnlove.jinsheng Exp $$
 */
public class JsonUtil {

    /**
     * 将简单的kv结构json转成map结构
     * @param jsonObject
     * @param needEmpty 是否保留值为空的查询条件
     * @return
     */
    public static Map<String, String> json2Map(JSONObject jsonObject, boolean needEmpty) {
        if (jsonObject == null) {
            return null;
        }
        HashMap<String, String> result = new HashMap<String, String>();
        Set<String> keys = jsonObject.keySet();
        for (String key : keys) {
            String value = jsonObject.getString(key);
            if ((value != null && !"".equals(value)) || needEmpty) {
                result.put(key, value);
            }
        }
        return result;
    }

}