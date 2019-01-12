/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.micropay;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.shinnlove.springall.dao.mch.MchWXPayConfigRepository;
import com.shinnlove.springall.dao.model.WXPayRecord;
import com.shinnlove.springall.dao.wxpay.WXPayRecordRepository;
import com.shinnlove.springall.service.wxpay.order.WXPayQueryOrderService;
import com.shinnlove.springall.service.wxpay.request.WXPayRequestService;
import com.shinnlove.springall.service.wxpay.util.WXPayAssert;
import com.shinnlove.springall.util.code.SystemResultCode;
import com.shinnlove.springall.util.exception.SystemException;
import com.shinnlove.springall.util.log.LoggerUtil;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.micro.MicroPayClient;

/**
 * 微信刷卡支付服务。
 *
 * 刷卡支付中包含微信订单查询服务，因为当验密支付的时候，第一次microPay返回的是USERPAYING的状态。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayMicroService.java, v 0.1 2019-01-11 20:30 shinnlove.jinsheng Exp $$
 */
@Service
public class WXPayMicroService {

    /** log4j2日志 */
    private static final Logger      LOGGER = LoggerFactory.getLogger(WXPayMicroService.class);

    /** 平台支付层的id常量 */
    private static final String      PAY_ID = "pay_id";

    /** tx-template */
    @Autowired
    private TransactionTemplate      transactionTemplate;

    /** 微信支付记录仓储 */
    @Autowired
    private WXPayRecordRepository    wxPayRecordRepository;

    /** 微信配置仓储 */
    @Autowired
    private MchWXPayConfigRepository mchWXPayConfigRepository;

    /** 微信支付服务SDK */
    @Autowired
    private WXPayRequestService      wxPayRequestService;

    /** 微信支付订单查询服务 */
    @Autowired
    private WXPayQueryOrderService   wxPayQueryOrderService;

    /**
     * 进行微信刷卡支付，尽可能等待用户微信侧的支付成功。
     *
     * TODO：JSAPI和刷卡的前置校验商户微信支付配置的代码可以统一一下。
     *
     * @param orderId    
     * @param merchantId
     * @param payParams
     * @return
     */
    public Map<String, String> microPay(final long orderId, final long merchantId,
                                        final Map<String, String> payParams) {
        // ready?
        WXPayMchConfig mchConfig = mchWXPayConfigRepository.queryWXPayConfigByMchId(merchantId);
        WXPayAssert.confAvailable(mchConfig);

        // 首付?(db-uk幂等)
        WXPayRecord record = wxPayRecordRepository.queryPayRecordByOrderId(orderId);
        if (record == null) {
            record = buildWXPayRecord(orderId, merchantId, payParams);
            wxPayRecordRepository.insertRecord(record);
        }

        Map<String, String> result = new HashMap<>();

        try {
            // 做刷卡支付
            result = wxPayRequestService.doPayRequest(
                new MicroPayClient(mchConfig),
                payParams,
                (resp) -> {

                    LoggerUtil.info(LOGGER, "刷卡支付resp=", resp);

                    WXPayAssert.checkMicroPayResp(resp);

                    String returnCode = resp.get(WXPayConstants.RETURN_CODE);
                    String resultCode = resp.get(WXPayConstants.RESULT_CODE);
                    String errCode = resp.get(WXPayConstants.ERR_CODE);

                    // 支付失败返回奇怪错误码直接默认本次刷卡支付失败!!
                    if (WXPayConstants.SUCCESS.equalsIgnoreCase(returnCode)
                        && WXPayConstants.FAIL.equalsIgnoreCase(resultCode)) {
                        if (!WXPayConstants.USERPAYING.equalsIgnoreCase(errCode)
                            && !WXPayConstants.SYSTEMERROR.equalsIgnoreCase(errCode)) {
                            throw new SystemException("刷卡支付状态错误");
                        }
                    }

                    return null;
                });
        } catch (SystemException e) {
            // 有马甲
            throw e;
        } catch (Exception e) {
            // 超时、dns解析、签名验签、稀奇古怪错误等统统穿上马甲扔给切面
            throw new SystemException(SystemResultCode.WXPAY_MICRO_PAY_ERROR, e, e.getMessage());
        }

        // 第一次刷卡支付的返回码和业务结果
        String returnCode = result.get(WXPayConstants.RETURN_CODE);
        String resultCode = result.get(WXPayConstants.RESULT_CODE);

        // 刷卡支付如果成功直接盖章平台支付id并返回
        if (WXPayConstants.SUCCESS.equalsIgnoreCase(returnCode)
            && WXPayConstants.SUCCESS.equalsIgnoreCase(resultCode)) {
            result.put(PAY_ID, String.valueOf(record.getId()));
            return result;
        }

        // 代码走到这里，一定是刷卡支付没有双SUCCESS（很有可能用户支付中），需要开始循环查询订单状态
        // TODO：这样做是hold住了主线程，导致商户侧订单系统请求支付系统一直waiting。
        //  可以直接返回用户支付中给订单系统，然后让其定时查询（最多30秒不行撤单）确定是否支付成功。
        //  这样如下的代码可以放在异步线程中去做，以DB状态为衔接点。
        int retryCount = 10;
        while (retryCount > 0) {

            // 特别注意：这里每一次的查询出错，都可以忽略；等到下一个周期再做查询

            try {

                Map<String, String> queryResult = wxPayQueryOrderService.queryWXPayOrder(orderId,
                    merchantId);

                String holdStatus = queryResult.get(WXPayConstants.MICRO_HOLD_STATUS);
                int hs = Integer.valueOf(holdStatus);

                if (hs == 1) {
                    // 成功，根据订单查询结果修改第一次刷卡支付字段，并直接函数返回...不再循环!!
                    result.put(WXPayConstants.RETURN_CODE, WXPayConstants.SUCCESS);
                    result.put(WXPayConstants.RESULT_CODE, WXPayConstants.SUCCESS);

                    return result;

                } else if (hs == 2) {
                    sleepSilently(2);
                } else {
                    // 其他情况一律异常不再重新查询
                    break;
                }

            } catch (Exception e) {
                LoggerUtil.error(LOGGER, e, "刷卡支付验密中查询第", retryCount, "次出错，e=", e);
                sleepSilently(2);
            }

            retryCount--;

        } // end while

        return result;
    }

    /**
     * 安静地等待。
     *
     * @param timeout
     */
    private void sleepSilently(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            LoggerUtil.error(LOGGER, e, e);
        }
    }

    /**
     * 新创建一条微信支付记录。
     *
     * @param orderId
     * @param merchantId
     * @param payParams
     * @return
     */
    private WXPayRecord buildWXPayRecord(long orderId, long merchantId,
                                         Map<String, String> payParams) {
        WXPayRecord record = new WXPayRecord(orderId, merchantId);
        // 做个塞字段示例
        record.setOpenId(payParams.get(WXPayConstants.OPENID));
        return record;
    }

}