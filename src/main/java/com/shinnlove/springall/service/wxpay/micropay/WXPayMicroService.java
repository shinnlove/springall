/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.micropay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 微信刷卡支付服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayMicroService.java, v 0.1 2019-01-11 20:30 shinnlove.jinsheng Exp $$
 */
@Service
public class WXPayMicroService {

    /**
     * 进行微信刷卡支付，尽可能等待用户微信侧的支付成功。
     *
     * @param orderId    
     * @param merchantId
     * @param payParams
     * @return
     */
    public Map<String, String> microPay(final long orderId, final long merchantId,
                                        final Map<String, String> payParams) {
        return new HashMap<>();
    }

}