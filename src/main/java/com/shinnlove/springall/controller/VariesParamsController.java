/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shinnlove.springall.util.code.SystemResultCode;
import com.shinnlove.springall.util.exception.SystemException;
import com.shinnlove.springall.util.json.JsonUtil;
import com.shinnlove.springall.util.log.LoggerUtil;

/**
 * 不同参数处理controller。
 *
 * @author shinnlove.jinsheng
 * @version $Id: VariesParamsController.java, v 0.1 2019-04-12 20:26 shinnlove.jinsheng Exp $$
 */
@RestController
@RequestMapping(value = "/channel")
public class VariesParamsController {

    /** log4j2日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloSpringController.class);

    /**
     * 前端将参数打成json方式传入，服务端解锁数据。
     *
     * @param requestStr
     * @return
     */
    @RequestMapping(value = "/update.json", method = { RequestMethod.GET, RequestMethod.POST })
    public String insertChannels(@RequestParam("channels") String requestStr) {

        try {
            JSONObject params = JSONObject.parseObject(requestStr);
            String parentChannelId = (String) params.get("parentChannelId");
            String parentChannelName = (String) params.get("parentChannelName");
            JSONArray dataLines = (JSONArray) params.get("subChannels");

            List<Map<String, String>> lines = new ArrayList<>();
            for (Object oneLine : dataLines) {
                JSONObject lineObj = JSONObject.parseObject(oneLine.toString());
                lines.add(JsonUtil.json2Map(lineObj, true));
            }

            System.out.println("接收到的渠道参数为parentChannelId=" + parentChannelId
                               + ", parentChannelName=" + parentChannelName + "，子渠道subChannels="
                               + lines);

        } catch (Exception e) {
            LoggerUtil.error(LOGGER, e, e);
            throw new SystemException(SystemResultCode.PARAM_INVALID, e, "传入的json数据格式不正确");
        }

        return "ok";
    }

}