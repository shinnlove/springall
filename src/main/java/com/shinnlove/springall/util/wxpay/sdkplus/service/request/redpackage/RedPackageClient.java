/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.request.redpackage;

import java.util.Map;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.base.WXPayRequestClient;
import com.shinnlove.springall.util.wxpay.sdkplus.util.WXPayUtil;

/**
 * 微信支付-商户发红包请求客户端。
 *
 * @author shinnlove.jinsheng
 * @version $Id: RedPackageClient.java, v 0.1 2018-12-18 下午5:35 shinnlove.jinsheng Exp $$
 */
public class RedPackageClient extends WXPayRequestClient {

    /**
     * 构造函数。
     *
     * @param wxPayMchConfig
     */
    public RedPackageClient(WXPayMchConfig wxPayMchConfig) {
        super(wxPayMchConfig);
    }

    @Override
    public void checkParameters(Map<String, String> keyPairs) throws Exception {
        // 公共校验
        if (keyPairs == null || keyPairs.size() == 0) {
            // 这里引入spring的jar替换成CollectionUtils.isEmpty()方法!RuntimeException改成具体的Exception
            throw new Exception("微信商户红包入参不能为空");
        }

        // 商户微信红包必填字段
        if (!keyPairs.containsKey(WXPayConstants.MCH_BILLNO)) {
            throw new Exception("微信商户红包接口mch_billno不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.SEND_NAME)) {
            throw new Exception("微信商户红包接口send_name不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.RE_OPENID)) {
            throw new Exception("微信商户红包接口re_openid不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.TOTAL_AMOUNT)) {
            throw new Exception("微信商户红包接口total_amount不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.TOTAL_NUM)) {
            throw new Exception("微信商户红包接口total_num不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.WISHING)) {
            throw new Exception("微信商户红包接口wishing不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.CLIENT_IP)) {
            throw new Exception("微信商户红包接口client_ip不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.ACT_NAME)) {
            throw new Exception("微信商户红包接口act_name不能为空");
        }
        if (WXPayUtil.mbStringLen(keyPairs.get(WXPayConstants.ACT_NAME)) > 32) {
            throw new Exception("微信商户红包接口act_name字段不能超过32位");
        }
        if (!keyPairs.containsKey(WXPayConstants.REMARK)) {
            throw new Exception("微信商户红包接口remark不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.WXAPPID)) {
            throw new Exception("微信商户红包接口wxappid不能为空");
        }
    }

    @Override
    public void fillRequestDetailParams(Map<String, String> keyPairs, Map<String, String> payParams) {
        // 发微信商户红包必填参数(字段含义请参考文档)
        payParams.put(WXPayConstants.MCH_BILLNO, keyPairs.get(WXPayConstants.MCH_BILLNO));
        payParams.put(WXPayConstants.SEND_NAME, keyPairs.get(WXPayConstants.SEND_NAME));
        payParams.put(WXPayConstants.RE_OPENID, keyPairs.get(WXPayConstants.RE_OPENID));
        payParams.put(WXPayConstants.TOTAL_AMOUNT, keyPairs.get(WXPayConstants.TOTAL_AMOUNT));
        payParams.put(WXPayConstants.TOTAL_NUM, keyPairs.get(WXPayConstants.TOTAL_NUM));
        payParams.put(WXPayConstants.WISHING, keyPairs.get(WXPayConstants.WISHING));
        payParams.put(WXPayConstants.CLIENT_IP, keyPairs.get(WXPayConstants.CLIENT_IP));
        payParams.put(WXPayConstants.ACT_NAME, keyPairs.get(WXPayConstants.ACT_NAME));
        payParams.put(WXPayConstants.REMARK, keyPairs.get(WXPayConstants.REMARK));
        payParams.put(WXPayConstants.WXAPPID, keyPairs.get(WXPayConstants.WXAPPID));
    }

    @Override
    public boolean requestNeedCert() {
        return false;
    }

    @Override
    public String requestURLSuffix() {
        if (wxPayMchConfig.isUseSandbox()) {
            // 沙箱环境
            return WXPayConstants.SANDBOX_SENDREDPACK_URL_SUFFIX;
        } else {
            // 正式环境
            return WXPayConstants.SENDREDPACK_URL_SUFFIX;
        }
    }

}