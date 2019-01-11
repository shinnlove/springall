/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.request.bill;

import java.util.Map;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.base.WXPayRequestClient;

/**
 * 微信支付对账单客户端。
 *
 * @author shinnlove.jinsheng
 * @version $Id: DownloadBillClient.java, v 0.1 2018-12-18 下午5:35 shinnlove.jinsheng Exp $$
 */
public class DownloadBillClient extends WXPayRequestClient {

    /**
     * 构造函数。
     *
     * @param wxPayMchConfig
     */
    public DownloadBillClient(WXPayMchConfig wxPayMchConfig) {
        super(wxPayMchConfig);
    }

    @Override
    public void fillRequestDetailParams(Map<String, String> keyPairs, Map<String, String> payParams) {
        // 对账单需要的参数
        // 对账日期
        payParams.put(WXPayConstants.BILL_DATE, keyPairs.get(WXPayConstants.BILL_DATE));
    }

    @Override
    public void checkParameters(Map<String, String> keyPairs) throws Exception {
        // 公共校验
        if (keyPairs == null || keyPairs.size() == 0) {
            // 这里引入spring的jar替换成CollectionUtils.isEmpty()方法!RuntimeException改成具体的Exception
            throw new Exception("对账单下载入参不能为空");
        }

        // 微信对账单必填字段
        if (!keyPairs.containsKey(WXPayConstants.BILL_DATE)) {
            throw new Exception("对账单接口对账日期不能为空");
        }
    }

    @Override
    public boolean requestNeedCert() {
        // 下载对账单不需要证书
        return false;
    }

    @Override
    public String requestURLSuffix() {
        if (wxPayMchConfig.isUseSandbox()) {
            // 沙箱环境
            return WXPayConstants.SANDBOX_DOWNLOADBILL_URL_SUFFIX;
        } else {
            // 正式环境
            return WXPayConstants.DOWNLOADBILL_URL_SUFFIX;
        }
    }

}