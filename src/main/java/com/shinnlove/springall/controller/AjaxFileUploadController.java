/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shinnlove.springall.util.code.SystemResultCode;
import com.shinnlove.springall.util.consts.BizResultConsts;
import com.shinnlove.springall.util.exception.SystemException;
import com.shinnlove.springall.util.log.LoggerUtil;
import com.shinnlove.springall.util.tools.ResponseUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用AJAX（XMLHttpRequest对象）提交文件并json方式响应的控制器。
 *
 * @author shinnlove.jinsheng
 * @version $Id: AjaxFileUploadController.java, v 0.1 2019-04-18 16:19 shinnlove.jinsheng Exp $$
 */
@RestController
@RequestMapping(value = "/ajax")
public class AjaxFileUploadController {

    /** log4j2日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxFileUploadController.class);

    /**
     * 单个文件上传。
     *
     * @param file      解析后的文件对象
     * @param request   HTTP文件流请求对象
     * @return
     */
    @RequestMapping(value = "/singleUpload", method = { RequestMethod.GET, RequestMethod.POST })
    public JSONObject singleUpload(MultipartFile file, HttpServletRequest request) {

        LoggerUtil.info(LOGGER, "处理单个文件上传。request=" + request);

        // 文件要保存的路径（这里直接上传到tomcat部署的应用下）
        String path = request.getSession().getServletContext().getRealPath("WEB-INF") + "/uploads";
        // 遵从文件原来的名字
        String fileName = file.getOriginalFilename();

        // 文件id和地址
        Map<String, String> uploadResult = new HashMap<>();
        uploadResult.put(BizResultConsts.FILE_ID, UUID.randomUUID().toString());
        uploadResult.put(BizResultConsts.FILE_URL, request.getContextPath() + "/uploads/"
                                                   + fileName);

        // JDK1.7以前的文件类File
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        // 保存文件
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new SystemException(SystemResultCode.FILE_UPLOAD_ERROR, e, e.getMessage());
        }

        return ResponseUtil.success(JSONObject.parseObject(JSON.toJSONString(uploadResult)));
    }

}