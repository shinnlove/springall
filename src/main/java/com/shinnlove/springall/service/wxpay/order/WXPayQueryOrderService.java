/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.order;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinnlove.springall.dao.mch.MchWXPayConfigRepository;
import com.shinnlove.springall.dao.model.WXPayRecord;
import com.shinnlove.springall.dao.wxpay.WXPayRecordRepository;
import com.shinnlove.springall.service.wxpay.micropay.WXPayMicroService;
import com.shinnlove.springall.service.wxpay.request.WXPayRequestService;
import com.shinnlove.springall.util.code.SystemResultCode;
import com.shinnlove.springall.util.exception.SystemException;
import com.shinnlove.springall.util.log.LoggerUtil;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.order.QueryOrderClient;

/**
 * 微信支付订单查询服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayQueryOrderService.java, v 0.1 2018-12-21 19:33 shinnlove.jinsheng Exp $$
 */
@Service
public class WXPayQueryOrderService {

    /** log4j2日志 */
    private static final Logger      LOGGER = LoggerFactory.getLogger(WXPayMicroService.class);

    /** 微信配置仓储 */
    @Autowired
    private MchWXPayConfigRepository mchWXPayConfigRepository;

    /** 微信支付记录仓储 */
    @Autowired
    private WXPayRecordRepository    wxPayRecordRepository;

    /** 微信支付服务SDK */
    @Autowired
    private WXPayRequestService      wxPayRequestService;

    /**
     * 查询微信支付订单。
     *
     * TODO：是否需要建立一张订单id、商户id、支付记录id相关的表持久化在支付系统中做查询？这样订单系统查询的时候只要送订单id？
     *
     * 1、必须建立一张订单id、商户id、支付类型（jsapi、hwap、native或micropay）的支付表做持久化映射，并且全局标记一笔单子是否在支付系统中支付过!!
     * 2、对于订单是否已支付，先查询这张表，已支付直接返回；未支付则调用接口查询微信订单。
     *
     * @param orderId       订单id
     * @return
     */
    public Map<String, String> queryWXPayOrder(long orderId) {
        // 查询数据库
        WXPayRecord payRecord = wxPayRecordRepository.queryPayRecordByOrderId(orderId);
        if (payRecord == null) {
            // 这里最好抛错，因为有其他支付类型
            throw new SystemException("要查询订单的支付状态不存在，请确认是否有相关支付记录");
        }

        // 查询订单
        return doQueryWXPayOrder(orderId, payRecord.getMerchantId());
    }

    /**
     * 直接查询订单，不做订单是否存在于数据库的check。
     *
     * Warning：这个函数给刷卡支付的时候用。
     * 当用户输入密码时，时间不可预估，线程hold住循环10次查询。
     * 为了尽快发起订单查询动作，此时订单一定存在于数据库，减少DB的IO操作。
     *
     * @param orderId
     * @param merchantId
     * @return
     */
    public Map<String, String> queryWXPayOrder(long orderId, long merchantId) {
        // 直接查询订单
        return doQueryWXPayOrder(orderId, merchantId);
    }

    /**
     * 真正查询微信订单。
     *
     * @param orderId
     * @param merchantId
     * @return
     */
    private Map<String, String> doQueryWXPayOrder(long orderId, long merchantId) {
        // 模拟支付入参
        Map<String, String> payParams = new HashMap<>();
        payParams.put(WXPayConstants.OUT_TRADE_NO, String.valueOf(orderId));

        WXPayMchConfig mchConfig = mchWXPayConfigRepository.queryWXPayConfigByMchId(merchantId);

        Map<String, String> result = new HashMap<>();

        try {
            result = wxPayRequestService.doPayRequest(new QueryOrderClient(mchConfig), payParams,
                (resp) -> {
                    LoggerUtil.info(LOGGER, "查询微信订单orderId=", orderId, "返回resp=", resp);
                    addMicroPayStatus(resp);
                    return null;
                });
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(SystemResultCode.SYSTEM_ERROR, e, e.getMessage());
        }

        return result;
    }

    /**
     * 根据微信应答处理业务（这里可能是普通查询、也可能是刷卡查询，把交易状态一并带出来）。
     * 刷卡支付状态：2-系统错误、用户支付中则继续查询，0-不要查询了，1是成功。
     *
     * @param resp
     */
    private void addMicroPayStatus(final Map<String, String> resp) {
        String microPayStatus = "2";

        String returnCode = resp.get(WXPayConstants.RETURN_CODE);
        String resultCode = resp.get(WXPayConstants.RESULT_CODE);
        String errCode = resp.get(WXPayConstants.ERR_CODE);
        String tradeState = resp.get(WXPayConstants.TRADE_STATE);

        if (WXPayConstants.SUCCESS.equalsIgnoreCase(returnCode)
            && WXPayConstants.SUCCESS.equalsIgnoreCase(resultCode)) {
            if (WXPayConstants.SUCCESS.equalsIgnoreCase(tradeState)) {
                microPayStatus = "1";
            } else if (WXPayConstants.USERPAYING.equalsIgnoreCase(tradeState)) {
                microPayStatus = "2";
            }
        } else if (WXPayConstants.ORDERNOTEXIST.equalsIgnoreCase(errCode)) {
            microPayStatus = "0";
        }

        // 追加刷卡支付查询状态
        resp.put(WXPayConstants.MICRO_HOLD_STATUS, microPayStatus);
    }

}