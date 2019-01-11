/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.request.refund;

import java.util.Map;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.base.WXPayRequestClient;

/**
 * 微信支付-退款查询客户端。
 *
 * @author shinnlove.jinsheng
 * @version $Id: RefundQueryClient.java, v 0.1 2018-12-18 下午5:34 shinnlove.jinsheng Exp $$
 */
public class RefundQueryClient extends WXPayRequestClient {

    /**
     * 构造函数。
     *
     * @param wxPayMchConfig
     */
    public RefundQueryClient(WXPayMchConfig wxPayMchConfig) {
        super(wxPayMchConfig);
    }

    @Override
    public void fillRequestDetailParams(Map<String, String> keyPairs, Map<String, String> payParams) {
        // 退款查询需要的参数

        // 设置商户订单号，商户系统内部订单号，32个字符内、字母和数组，唯一性
        payParams.put(WXPayConstants.OUT_TRADE_NO, keyPairs.get(WXPayConstants.OUT_TRADE_NO));
        if (keyPairs.containsKey(WXPayConstants.OUT_REFUND_NO)) {
            // 商户侧退款单号
            payParams.put(WXPayConstants.OUT_REFUND_NO, keyPairs.get(WXPayConstants.OUT_REFUND_NO));
        }
        if (keyPairs.containsKey(WXPayConstants.TRANSACTION_ID)) {
            // 微信侧订单号
            payParams.put(WXPayConstants.TRANSACTION_ID,
                keyPairs.get(WXPayConstants.TRANSACTION_ID));
        }
        if (keyPairs.containsKey(WXPayConstants.REFUND_ID)) {
            // 微信侧退款单号
            payParams.put(WXPayConstants.REFUND_ID, keyPairs.get(WXPayConstants.REFUND_ID));
        }
    }

    @Override
    public void checkParameters(Map<String, String> keyPairs) throws Exception {
        // 做退款查询的校验参数
        if (keyPairs == null || keyPairs.size() == 0) {
            // 这里引入spring的jar替换成CollectionUtils.isEmpty()方法!RuntimeException改成具体的Exception
            throw new Exception("退款查询入参不能为空");
        }
        // 订单号、退款号等至少有一个不能为空
        if (!keyPairs.containsKey(WXPayConstants.OUT_TRADE_NO)
            && !keyPairs.containsKey(WXPayConstants.OUT_REFUND_NO)
            && !keyPairs.containsKey(WXPayConstants.TRANSACTION_ID)
            && !keyPairs.containsKey(WXPayConstants.REFUND_ID)) {
            throw new Exception("退款查询接口out_trade_no、out_refund_no、transaction_id或refund_id至少填一个");
        }
    }

    @Override
    public boolean requestNeedCert() {
        // 查询退款不需要证书
        return false;
    }

    @Override
    public String requestURLSuffix() {
        if (wxPayMchConfig.isUseSandbox()) {
            // 沙箱环境
            return WXPayConstants.SANDBOX_REFUNDQUERY_URL_SUFFIX;
        } else {
            // 正式环境
            return WXPayConstants.REFUNDQUERY_URL_SUFFIX;
        }
    }

}