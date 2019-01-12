/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.service.request.micro;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.base.WXPayRequestClient;

/**
 * 微信软pos扫码支付客户端。
 *
 * @author shinnlove.jinsheng
 * @version $Id: MicroPayClient.java, v 0.1 2018-12-18 下午5:30 shinnlove.jinsheng Exp $$
 */
public class MicroPayClient extends WXPayRequestClient {

    /**
     * 构造函数。
     *
     * @param wxPayMchConfig
     */
    public MicroPayClient(WXPayMchConfig wxPayMchConfig) {
        super(wxPayMchConfig);
    }

    @Override
    public void checkParameters(Map<String, String> keyPairs) throws Exception {
        if (!keyPairs.containsKey(WXPayConstants.BODY)) {
            throw new Exception("刷卡支付接口商品描述不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.TOTAL_FEE)) {
            throw new Exception("刷卡支付接口总金额不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.SPBILL_CREATE_IP)) {
            throw new Exception("统一下单接口支付异步通知地址不能为空");
        }
        if (!keyPairs.containsKey(WXPayConstants.AUTH_CODE)) {
            throw new Exception("统一支付入参交易类型不能为空");
        }
    }

    @Override
    public void fillRequestDetailParams(Map<String, String> keyPairs, Map<String, String> payParams) {
        // 刷卡支付必填参数5个：订单号、授权码、商品主体信息、总金额、单子创建地址

        // 设置商户订单号，商户系统内部订单号，32个字符内、字母和数组，唯一性
        payParams.put(WXPayConstants.OUT_TRADE_NO, keyPairs.get(WXPayConstants.OUT_TRADE_NO));
        // 用户授权码
        payParams.put(WXPayConstants.AUTH_CODE, keyPairs.get(WXPayConstants.AUTH_CODE));
        // 商品描述
        payParams.put(WXPayConstants.BODY, keyPairs.get(WXPayConstants.BODY));
        // 总金额，单位为分，不能带小数点
        payParams.put(WXPayConstants.TOTAL_FEE, keyPairs.get(WXPayConstants.TOTAL_FEE));
        // 终端设备地址
        payParams.put(WXPayConstants.SPBILL_CREATE_IP,
            keyPairs.get(WXPayConstants.SPBILL_CREATE_IP));

        // 可选填参数：
        if (keyPairs.containsKey(WXPayConstants.DETAIL)) {
            // 商品详情
            payParams.put(WXPayConstants.DETAIL, keyPairs.get(WXPayConstants.DETAIL));
        }
        if (keyPairs.containsKey(WXPayConstants.ATTACH)) {
            // 附加数据，原样返回
            payParams.put(WXPayConstants.ATTACH, keyPairs.get(WXPayConstants.ATTACH));
        }
        if (keyPairs.containsKey(WXPayConstants.TIME_EXPIRE)) {
            // 订单失效时间，yyyyMMddHHmmss，格式同上，取自商户服务器
            payParams.put(WXPayConstants.TIME_EXPIRE, keyPairs.get(WXPayConstants.TIME_EXPIRE));
        }
        if (keyPairs.containsKey(WXPayConstants.TIME_START)) {
            // 订单生成时间，yyyyMMddHHmmss，20091225091010，取自商户服务器
            payParams.put(WXPayConstants.TIME_START, keyPairs.get(WXPayConstants.TIME_START));
        }
        if (keyPairs.containsKey(WXPayConstants.GOODS_TAG)) {
            // 下单售卖商品标签，不要随意修改这个字段
            payParams.put(WXPayConstants.GOODS_TAG, keyPairs.get(WXPayConstants.GOODS_TAG));
        }
        if (keyPairs.containsKey(WXPayConstants.RECEIPT)) {
            // 是否开发票：Y|开电子发票
            payParams.put(WXPayConstants.RECEIPT, keyPairs.get(WXPayConstants.RECEIPT));
        }

        if (keyPairs.containsKey(WXPayConstants.NAME)) {
            // 传入门店名，填写线下门店刷卡场景信息

            String id = keyPairs.get(WXPayConstants.ID) != null ? keyPairs.get(WXPayConstants.ID)
                : "666666";
            String name = keyPairs.get(WXPayConstants.NAME) != null ? keyPairs
                .get(WXPayConstants.NAME) : "体验消费旗舰店";
            String areaCode = keyPairs.get(WXPayConstants.AREA_CODE) != null ? keyPairs
                .get(WXPayConstants.AREA_CODE) : "201203";
            String address = keyPairs.get(WXPayConstants.ADDRESS) != null ? keyPairs
                .get(WXPayConstants.ADDRESS) : "上海市徐汇区霞飞路27号";

            // 门店信息...
            JSONObject obj = new JSONObject();
            obj.put(WXPayConstants.ID, id);
            obj.put(WXPayConstants.NAME, name);
            obj.put(WXPayConstants.AREA_CODE, areaCode);
            obj.put(WXPayConstants.ADDRESS, address);

            // 封装一下文档上无聊的key...
            JSONObject wrapper = new JSONObject();
            wrapper.put(WXPayConstants.STORE_INFO, obj);

            payParams.put(WXPayConstants.SCENE_INFO, wrapper.toJSONString());
        }
    }

    @Override
    public boolean requestNeedCert() {
        return false;
    }

    @Override
    public String requestURLSuffix() {
        if (wxPayMchConfig.isUseSandbox()) {
            // 沙箱环境
            return WXPayConstants.SANDBOX_MICROPAY_URL_SUFFIX;
        } else {
            // 正式环境
            return WXPayConstants.MICROPAY_URL_SUFFIX;
        }
    }

}