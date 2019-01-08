/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.order;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinnlove.springall.dao.mch.MchWXPayConfigRepository;
import com.shinnlove.springall.dao.model.WXPayRecord;
import com.shinnlove.springall.dao.wxpay.WXPayRecordRepository;
import com.shinnlove.springall.service.wxpay.request.WXPayRequestService;
import com.shinnlove.springall.util.code.SystemResultCode;
import com.shinnlove.springall.util.exception.SystemException;
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
     * @param orderId       订单id
     * @return
     */
    public Map<String, String> queryWXPayOrder(long orderId) {

        // 查询订单
        WXPayRecord payRecord = wxPayRecordRepository.queryPayRecordByOrderId(orderId);
        if (payRecord == null) {
            // 这里最好抛错，因为有其他支付类型
            throw new SystemException("要查询订单的支付状态不存在，请确认是否有相关支付记录");
        }
        long merchantId = payRecord.getMerchantId();

        // 模拟支付入参
        Map<String, String> payParams = new HashMap<>();
        payParams.put(WXPayConstants.OUT_TRADE_NO, String.valueOf(orderId));

        WXPayMchConfig mchConfig = mchWXPayConfigRepository.queryWXPayConfigByMchId(merchantId);

        QueryOrderClient client = new QueryOrderClient(mchConfig);

        Map<String, String> result = new HashMap<>();

        try {
            result = wxPayRequestService.doPayRequest(client, payParams, (resp) -> {
                // 根据微信应答处理业务
                System.out.println(resp);
                return null;
            });
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(SystemResultCode.SYSTEM_ERROR, e);
        }

        return result;
    }
}