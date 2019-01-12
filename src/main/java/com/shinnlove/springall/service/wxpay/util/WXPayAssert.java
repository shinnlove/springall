/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.util;

import java.util.Map;

import com.shinnlove.springall.dao.model.WXPayRecord;
import com.shinnlove.springall.util.exception.SystemException;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;

/**
 * 微信支付业务通用工具。
 *
 * 用来校验字段、精简代码。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayAssert.java, v 0.1 2019-01-09 17:10 shinnlove.jinsheng Exp $$
 */
public class WXPayAssert {

    /**
     * 检测商户微信支付配置是否存在。
     *
     * @param config
     * @throws SystemException
     */
    public static void confExist(WXPayMchConfig config) throws SystemException {
        if (config == null) {
            throw new SystemException("商户未完善微信支付信息，请稍后再试");
        }
    }

    /**
     * 检测商户微信配置是否ready和可用。
     *
     * @param config
     * @throws SystemException
     */
    public static void confAvailable(WXPayMchConfig config) throws SystemException {
        confExist(config);
        if (!config.isAvailable()) {
            throw new SystemException("商户微信支付未开启，请联系商户配置");
        }
    }

    /**
     * 准备做支付的单子不能不存在、也不能已支付！
     *
     * @param record
     * @throws SystemException
     */
    public static void notPaid(WXPayRecord record) throws SystemException {
        if (record != null && record.getIsPaid() > 0) {
            throw new SystemException("当前订单已经完成微信支付，无需重复支付");
        }
    }

    /**
     * 统一下单参数校验。
     *
     * @param resp 
     * @throws SystemException
     */
    public static void checkPayResp(Map<String, String> resp) throws SystemException {

        // TODO：result_code不是SUCCESS时需要映射一把错误码原因：@see https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1

        wxRespCommonCheck(resp);

        if (!WXPayConstants.SUCCESS.equalsIgnoreCase(resp.get(WXPayConstants.RESULT_CODE))) {
            String failMsg = resp.get(WXPayConstants.RESULT_CODE);
            String des = resp.get(WXPayConstants.ERR_CODE_DES);
            throw new SystemException("微信支付业务返回码FAIL" + failMsg + des);
        }

        if (!resp.containsKey(WXPayConstants.PREPAY_ID)) {
            throw new SystemException("微信支付业务需返回统一下单prepay_id，当前不存在");
        }
        if ("".equals(resp.get(WXPayConstants.PREPAY_ID))) {
            throw new SystemException("微信支付业务需返回统一下单prepay_id为空");
        }

        String tradeType = resp.get(WXPayConstants.TRADE_TYPE);
        if (!WXPayConstants.JSAPI.equalsIgnoreCase(tradeType)) {
            throw new SystemException("微信支付返回支付类型错误，JSAPI支付交易类型返回必须是JSAPI");
        }

    }

    /**
     * 校验微信刷卡支付响应参数。
     *
     * result_code只要存在，返回给业务层自己处理，不做抛错。
     *
     * @param resp 
     * @throws SystemException
     */
    public static void checkMicroPayResp(Map<String, String> resp) throws SystemException {
        wxRespCommonCheck(resp);

        // 如果是其他类型的错误，直接默认刷卡支付失败，抛错
        String resultCode = resp.get(WXPayConstants.RESULT_CODE);
        String errCode = resp.get(WXPayConstants.ERR_CODE);

        if (WXPayConstants.FAIL.equalsIgnoreCase(resultCode)) {
            if (!WXPayConstants.USERPAYING.equalsIgnoreCase(errCode)
                && !WXPayConstants.SYSTEMERROR.equalsIgnoreCase(errCode)) {
                String errMsg = resp.get(WXPayConstants.ERR_CODE_DES);
                throw new SystemException("刷卡支付返回业务码状态错误：" + errMsg);
            }
        }
    }

    /**
     * 校验订单查询参数。
     *
     * @param resp 
     * @throws SystemException
     */
    public static void checkOrderQueryResp(Map<String, String> resp) throws SystemException {
        wxRespCommonCheck(resp);

    }

    /**
     * 微信响应公共字段校验。
     *
     * @param resp
     */
    private static void wxRespCommonCheck(Map<String, String> resp) {
        // 通信字段必须存在
        if (!resp.containsKey(WXPayConstants.RETURN_CODE)) {
            throw new SystemException("微信支付微信侧响应结果通信返回码不存在");
        }
        // 通信失败直接失败
        String returnCode = resp.get(WXPayConstants.RETURN_CODE);
        if (!WXPayConstants.SUCCESS.equalsIgnoreCase(returnCode)) {
            throw new SystemException("微信支付微信侧响应结果通信失败，请稍后再试");
        }
        // 业务字段也要存在
        if (!resp.containsKey(WXPayConstants.RESULT_CODE)) {
            throw new SystemException("微信支付微信侧响应结果业务返回码不存在");
        }
    }

}