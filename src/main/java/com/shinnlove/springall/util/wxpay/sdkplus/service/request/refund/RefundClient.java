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
 * 微信支付-退款客户端。
 *
 * @author shinnlove.jinsheng
 * @version $Id: RefundClient.java, v 0.1 2018-12-18 下午5:34 shinnlove.jinsheng Exp $$
 */
public class RefundClient extends WXPayRequestClient {

    /**
     * 构造函数。
     *
     * @param wxPayMchConfig
     */
    public RefundClient(WXPayMchConfig wxPayMchConfig) {
        super(wxPayMchConfig);
    }

    @Override
    public void fillRequestDetailParams(Map<String, String> keyPairs, Map<String, String> payParams) {
        // 退款必填五大要素

        // 商户订单号
        payParams.put(WXPayConstants.OUT_TRADE_NO, keyPairs.get(WXPayConstants.OUT_TRADE_NO));
        // 商户退款单号
        payParams.put(WXPayConstants.OUT_REFUND_NO, keyPairs.get(WXPayConstants.OUT_REFUND_NO));
        // 总金额
        payParams.put(WXPayConstants.TOTAL_FEE, keyPairs.get(WXPayConstants.TOTAL_FEE));
        // 退款金额（支持部分退款）
        payParams.put(WXPayConstants.REFUND_FEE, keyPairs.get(WXPayConstants.REFUND_FEE));
        // 操作员（当前商户）
        payParams.put(WXPayConstants.OP_USER_ID, keyPairs.get(WXPayConstants.OP_USER_ID));

        // 退款其他选填参数
        if (keyPairs.containsKey(WXPayConstants.TRANSACTION_ID)) {
            // 微信订单号（有out_trade_no可以不用填）
            payParams.put(WXPayConstants.TRANSACTION_ID,
                keyPairs.get(WXPayConstants.TRANSACTION_ID));
        }
        if (keyPairs.containsKey(WXPayConstants.DEVICE_INFO)) {
            // 设备号
            payParams.put(WXPayConstants.DEVICE_INFO, keyPairs.get(WXPayConstants.DEVICE_INFO));
        }
        if (keyPairs.containsKey(WXPayConstants.OP_USER_PASSWD)) {
            // 操作员密码（当前商户登录密码），这个参数V2版本以后已经不用了
            payParams.put(WXPayConstants.OP_USER_PASSWD,
                keyPairs.get(WXPayConstants.OP_USER_PASSWD));
        }
    }

    @Override
    public void checkParameters(Map<String, String> keyPairs) throws Exception {
        // 公共校验
        if (keyPairs == null || keyPairs.size() == 0) {
            // 这里引入spring的jar替换成CollectionUtils.isEmpty()方法!RuntimeException改成具体的Exception
            throw new Exception("退款申请入参不能为空");
        }

        // 退款字段校验

        // 订单号至少有一个不能为空
        if (!keyPairs.containsKey(WXPayConstants.OUT_TRADE_NO)
            && !keyPairs.containsKey(WXPayConstants.TRANSACTION_ID)) {
            throw new Exception("退款申请接口out_trade_no和transaction_id至少填一个");
        }
        if (!keyPairs.containsKey(WXPayConstants.TOTAL_FEE)) {
            throw new Exception("退款申请接口订单总金额不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.REFUND_FEE)) {
            throw new Exception("退款申请接口退款金额不能为空");
        }
    }

    @Override
    public boolean requestNeedCert() {
        // 申请退款需要双向证书
        return true;
    }

    @Override
    public String payRequestURL() {
        if (wxPayMchConfig.isUseSandbox()) {
            // 沙箱环境
            return WXPayConstants.HTTPS + WXPayConstants.DOMAIN_API
                   + WXPayConstants.SANDBOX_REFUND_URL_SUFFIX;
        } else {
            // 正式环境
            return WXPayConstants.HTTPS + WXPayConstants.DOMAIN_API
                   + WXPayConstants.REFUND_URL_SUFFIX;
        }
    }

}