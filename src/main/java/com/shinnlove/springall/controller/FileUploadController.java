/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

import java.io.*;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.shinnlove.springall.util.code.SystemResultCode;
import com.shinnlove.springall.util.exception.SystemException;
import com.shinnlove.springall.util.log.LoggerUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理文件上传的控制器。
 *
 * @author shinnlove.jinsheng
 * @version $Id: FileUploadController.java, v 0.1 2019-04-10 20:28 shinnlove.jinsheng Exp $$
 */
@Controller
@RequestMapping(value = "/upload")
public class FileUploadController {

    /** log4j2日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    /**
     * 上传文件页面。
     *
     * @return
     */
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public String uploadPage() {
        return "uploadPage";
    }

    /**
     * 处理单个文件上传。
     *
     * @param file
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/singleUpload", method = RequestMethod.POST)
    public String handleSingleUploadFile(MultipartFile file, HttpServletRequest request,
                                         ModelMap model) {
        LoggerUtil.info(LOGGER, "处理单个文件上传。");

        // 文件要保存的路径（这里直接上传到tomcat部署的应用下）
        String path = request.getSession().getServletContext().getRealPath("WEB-INF") + "/uploads";
        // 遵从文件原来的名字
        String fileName = file.getOriginalFilename();

        // JDK1.7以前的文件类File
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        // 保存文件
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("filePath", request.getContextPath() + "/uploads/" + fileName);

        return "result";
    }

    /**
     * 处理多个文件上传。
     *
     * 当提交文件的时候，请求本身就被 {@link CommonsMultipartResolver} 解析成 {@link MultipartHttpServletRequest}了，
     * 因此可以直接通过把request转换成MultipartHttpServletRequest方式来getFile或getFiles。
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/multipartUpload", method = RequestMethod.POST)
    public String multipartUpload(HttpServletRequest request, ModelMap model) {
        LoggerUtil.info(LOGGER, "处理多个文件上传。");

        MultipartHttpServletRequest fileRequest = (MultipartHttpServletRequest) request;
        // 多文件获取则是使用MultipartHttpServletRequest.getFiles
        List<MultipartFile> fileList = fileRequest.getFiles("product-image");

        String path = request.getSession().getServletContext().getRealPath("WEB-INF") + "/uploads";

        for (MultipartFile file : fileList) {
            // 尊重原来的文件名
            String oneFileName = file.getOriginalFilename();
            // File并不支持autoClosable
            File ioFile = new File(path, oneFileName);
            try {
                file.transferTo(ioFile);
                model
                    .addAttribute("filePath", request.getContextPath() + "/uploads/" + oneFileName);
            } catch (IOException e) {
                model.addAttribute("result", false);
                model.addAttribute("msg", "文件异常，上传失败！");
            }
        }

        return "result";
    }

    /**
     * 下载文件示例。
     *
     * @param fileId        要下载的文件id，查询数据库换path和name
     * @param filePath      做演示用的filePath，省去查询数据库
     * @param request       
     * @param response
     */
    @RequestMapping(value = "/downloadFile", method = { RequestMethod.GET, RequestMethod.POST })
    public void downloadFileById(String fileId, String filePath, HttpServletRequest request,
                                 HttpServletResponse response) {

        // TODO：use fileId to query DB get the file's real path, name and suffix.

        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String currentPath = request.getSession().getServletContext().getRealPath("upload");
        String fileRealPath = currentPath + "/" + fileName;

        // 读取文件包个马甲送给`@ExceptionHandler`拦截
        try {

            // 设置响应头
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentType("application/octet-stream;charset=UTF-8");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

            // 文件流缓冲（注意生产环境文件可能在不同位置）
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileRealPath));

            // 20K的、优雅的、写到输出流里
            byte[] bytes = new byte[20480];
            int n = -1;
            while ((n = in.read(bytes, 0, bytes.length)) != -1) {
                outputStream.write(bytes, 0, n);
            }

            // 刷网卡缓冲
            outputStream.flush();
            outputStream.close();

        } catch (FileNotFoundException e) {
            throw new SystemException(SystemResultCode.SYSTEM_ERROR, e, "没有找到文件");
        } catch (Exception e) {
            throw new SystemException(SystemResultCode.SYSTEM_ERROR, e, "将文件写入网络IO流出错");
        }

    }
}