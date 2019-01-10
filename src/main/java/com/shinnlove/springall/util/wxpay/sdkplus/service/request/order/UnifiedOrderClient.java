/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.request.order;

import java.util.Map;

import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.base.WXPayRequestClient;

/**
 * 微信支付-统一下单客户端。
 *
 * @author shinnlove.jinsheng
 * @version $Id: UnifiedOrderClient.java, v 0.1 2018-12-18 下午4:27 shinnlove.jinsheng Exp $$
 */
public class UnifiedOrderClient extends WXPayRequestClient {

    /**
     * 构造函数。
     *
     * @param wxPayMchConfig 
     */
    public UnifiedOrderClient(WXPayMchConfig wxPayMchConfig) {
        super(wxPayMchConfig);
    }

    @Override
    public void checkParameters(Map<String, String> keyPairs) throws Exception {
        // 公共校验
        if (keyPairs == null || keyPairs.size() == 0) {
            // 这里引入spring的jar替换成CollectionUtils.isEmpty()方法!RuntimeException改成具体的Exception
            throw new Exception("统一支付入参不能为空");
        }

        // 统一下单公共字段
        if (!keyPairs.containsKey(WXPayConstants.OUT_TRADE_NO)) {
            throw new Exception("统一下单接口商户订单号不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.BODY)) {
            throw new Exception("统一下单接口商品描述不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.TOTAL_FEE)) {
            throw new Exception("统一下单接口总金额不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.NOTIFY_URL)) {
            throw new Exception("统一下单接口支付异步通知地址不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.TRADE_TYPE)) {
            throw new Exception("统一支付入参交易类型不能为空");
        }
        // 不同交易类型必填字段
        String tradeType = keyPairs.get(WXPayConstants.TRADE_TYPE);
        switch (tradeType) {
            case WXPayConstants.JSAPI:
                if (!keyPairs.containsKey(WXPayConstants.OPENID)) {
                    throw new Exception("缺少用户的微信号openid，jsAPI支付下，此参数必须");
                }
                // break不能少，不然进入default
                break;
            case WXPayConstants.NATIVE:
                if (!keyPairs.containsKey(WXPayConstants.PRODUCT_ID)) {
                    throw new Exception("缺少原生扫码支付商品的product_id，NATIVE支付下，此参数必须");
                }
                // break不能少，不然进入default
                break;
            case WXPayConstants.APP:
                throw new Exception("统一支付目前不支持APP类型支付");
            case WXPayConstants.WAP:
                throw new Exception("统一支付目前不支持WAP类型支付");
            default:
                throw new Exception("统一支付入参支付类型不明确");
        }
    }

    @Override
    public void fillRequestDetailParams(Map<String, String> keyPairs,
                                        final Map<String, String> payParams) {
        // 统一下单的必填参数，其中appid公众账号id, mch_id商户号, noncestr随机字符串, spbill_create_ip订单生成机器的IP（weact服务器地址）, sign签名等5项由基类生成

        // 商品描述
        payParams.put(WXPayConstants.BODY, keyPairs.get(WXPayConstants.BODY));
        // 设置商户订单号，商户系统内部订单号，32个字符内、字母和数组，唯一性
        payParams.put(WXPayConstants.OUT_TRADE_NO, keyPairs.get(WXPayConstants.OUT_TRADE_NO));
        // 总金额，单位为分，不能带小数点
        payParams.put(WXPayConstants.TOTAL_FEE, keyPairs.get(WXPayConstants.TOTAL_FEE));
        // 支付通知地址，接收微信支付成功通知
        payParams.put(WXPayConstants.NOTIFY_URL, keyPairs.get(WXPayConstants.NOTIFY_URL));
        // 本函数处理的交易类型:JSAPI（JSAPI、NATIVE、APP三种）
        payParams.put(WXPayConstants.TRADE_TYPE, keyPairs.get(WXPayConstants.TRADE_TYPE));

        // 统一下单不同类型需要的参数
        if (keyPairs.containsKey(WXPayConstants.OPENID)) {
            // 用户的微信号openid,jsAPI下，此参数必须
            payParams.put(WXPayConstants.OPENID, keyPairs.get(WXPayConstants.OPENID));
        }
        if (keyPairs.containsKey(WXPayConstants.PRODUCT_ID)) {
            // 商品ID，只在trade_type为native时需要填写，id是二维码中商品ID，商户自行维护
            payParams.put(WXPayConstants.PRODUCT_ID, keyPairs.get(WXPayConstants.PRODUCT_ID));
        }

        // 统一下单的非必填参数，商户可根据实际情况选填
        if (keyPairs.containsKey(WXPayConstants.SPBILL_CREATE_IP)) {
            // 生成订单设备IP地址
            payParams.put(WXPayConstants.SPBILL_CREATE_IP,
                keyPairs.get(WXPayConstants.SPBILL_CREATE_IP));
        }
        if (keyPairs.containsKey(WXPayConstants.DEVICE_INFO)) {
            // 微信支付分配的终端设备号
            payParams.put(WXPayConstants.DEVICE_INFO, keyPairs.get(WXPayConstants.DEVICE_INFO));
        }
        if (keyPairs.containsKey(WXPayConstants.ATTACH)) {
            // 附加数据，原样返回
            payParams.put(WXPayConstants.ATTACH, keyPairs.get(WXPayConstants.ATTACH));
        }
        if (keyPairs.containsKey(WXPayConstants.TIME_START)) {
            // 订单生成时间，yyyyMMddHHmmss，20091225091010，取自商户服务器
            payParams.put(WXPayConstants.TIME_START, keyPairs.get(WXPayConstants.TIME_START));
        }
        if (keyPairs.containsKey(WXPayConstants.TIME_EXPIRE)) {
            // 订单失效时间，yyyyMMddHHmmss，格式同上，取自商户服务器
            payParams.put(WXPayConstants.TIME_EXPIRE, keyPairs.get(WXPayConstants.TIME_EXPIRE));
        }
        if (keyPairs.containsKey(WXPayConstants.GOODS_TAG)) {
            // 下单售卖商品标签，不要随意修改这个字段
            payParams.put(WXPayConstants.GOODS_TAG, keyPairs.get(WXPayConstants.GOODS_TAG));
        }

    }

    @Override
    public boolean requestNeedCert() {
        // 统一下单不需要证书
        return false;
    }

    @Override
    public String payRequestURL() {
        if (wxPayMchConfig.isUseSandbox()) {
            // 沙箱环境
            return WXPayConstants.HTTPS + WXPayConstants.DOMAIN_API
                   + WXPayConstants.SANDBOX_UNIFIEDORDER_URL_SUFFIX;
        } else {
            // 正式环境
            return WXPayConstants.HTTPS + WXPayConstants.DOMAIN_API
                   + WXPayConstants.UNIFIEDORDER_URL_SUFFIX;
        }
    }

    /**
     * 获取支付id。
     *
     * 这个函数应该放在外层平台封装层。
     *
     * @return
     */
    public String getPrepayId() {
        //        return payResult.get(WXPayConstants.PREPAY_ID);
        return null;
    }

}