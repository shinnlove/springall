/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shinnlove.springall.util.consts.SystemConsts;
import com.shinnlove.springall.util.exception.SystemException;
import com.shinnlove.springall.util.log.LoggerUtil;

/**
 * 通用控制器层异常拦截。
 *
 * 好处：这是为了解决在`@Controller`中对Service的调用时进行try...catch...的重复代码而生，切忌代码要精简，不要bao皮过长。
 *
 * 使用方式：
 * @ControllerAdvice 注解打了 @Component注解，spring配置web-home.xml中有
 * <context:component-scan base-package="com.shinnlove.presto"/> 则会开启了@Component扫描，
 * 肯定能扫描到并配置这个`ControllerAdvice`。
 *
 * 注意事项：这里`@ControllerAdvice`必须匹配你的`Controller`返回结果!!!
 * a) Controller是json出去（实体序列化出去），这里也要@ResponseBody，
 * b) Controller是void，这里形参加入`ModelMap`，并且把对应的错误结果放入`ModelMap`中。
 *
 * @author shinnlove.jinsheng
 * @version $Id: CommonExceptionAdvice.java, v 0.1 2018-08-10 下午4:05 shinnlove.jinsheng Exp $$
 */
@ControllerAdvice
public class CommonExceptionAdvice {

    /** 控制器公用日志 */
    protected static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

    @ExceptionHandler(SystemException.class)
    @ResponseBody
    public Object handleSystemException(SystemException e) {
        LoggerUtil.error(logger, e, e);
        return buildExceptionResult(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handlerException(Exception e) {
        LoggerUtil.error(logger, e, e);
        return buildExceptionResult(e);
    }

    /**
     * 构建异常返回结果
     *
     * @param e
     * @return
     */
    public JSONObject buildExceptionResult(Exception e) {
        JSONObject result = new JSONObject();
        result.put(SystemConsts.SUCCESS, false);
        result.put(SystemConsts.ERROR_CODE, SystemConsts.SYSTEM_ERROR);
        result.put(SystemConsts.ERROR_MSG, e.getMessage());
        return result;
    }

    /**
     * 构建异常返回结果
     *
     * @param e
     * @return
     */
    public JSONObject buildExceptionResult(SystemException e) {
        JSONObject result = new JSONObject();
        result.put(SystemConsts.SUCCESS, false);
        result.put(SystemConsts.ERROR_CODE, e.getResultCode().getCode());
        result.put(SystemConsts.ERROR_MSG, e.getResultCode().getMessage() + "，" + e.getDigestStr());
        return result;
    }

}