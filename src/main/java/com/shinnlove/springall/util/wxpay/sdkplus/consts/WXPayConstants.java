/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.wxpay.sdkplus.consts;

/**
 * 微信支付常量。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayConstants.java, v 0.1 2018-12-18 下午11:15 shinnlove.jinsheng Exp $$
 */
public final class WXPayConstants {

    // 协议
    public static final String HTTP                                = "http://";
    public static final String HTTPS                               = "https://";

    // 域名
    public static final String DOMAIN_API                          = "api.mch.weixin.qq.com";
    public static final String DOMAIN_API2                         = "api2.mch.weixin.qq.com";
    public static final String DOMAIN_APIHK                        = "apihk.mch.weixin.qq.com";
    public static final String DOMAIN_APIUS                        = "apius.mch.weixin.qq.com";

    public static final String FAIL                                = "FAIL";
    public static final String SUCCESS                             = "SUCCESS";

    public static final String HMACSHA256                          = "HMAC-SHA256";
    public static final String MD5                                 = "MD5";

    // 正常环境
    public static final String MICROPAY_URL_SUFFIX                 = "/pay/micropay";
    public static final String UNIFIEDORDER_URL_SUFFIX             = "/pay/unifiedorder";
    public static final String ORDERQUERY_URL_SUFFIX               = "/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX                  = "/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX               = "/pay/closeorder";
    public static final String REFUND_URL_SUFFIX                   = "/secapi/pay/refund";
    public static final String REFUNDQUERY_URL_SUFFIX              = "/pay/refundquery";
    public static final String DOWNLOADBILL_URL_SUFFIX             = "/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX                   = "/payitil/report";
    public static final String SHORTURL_URL_SUFFIX                 = "/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX         = "/tools/authcodetoopenid";
    public static final String SENDREDPACK_URL_SUFFIX              = "/mmpaymkttransfers/sendredpack";

    // sandbox
    public static final String SANDBOX_MICROPAY_URL_SUFFIX         = "/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL_SUFFIX     = "/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL_SUFFIX       = "/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL_SUFFIX          = "/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL_SUFFIX       = "/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL_SUFFIX           = "/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL_SUFFIX      = "/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL_SUFFIX     = "/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL_SUFFIX           = "/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL_SUFFIX         = "/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL_SUFFIX = "/sandboxnew/tools/authcodetoopenid";
    public static final String SANDBOX_SENDREDPACK_URL_SUFFIX      = "/sandboxnew/mmpaymkttransfers/sendredpack";

    // 商户主体
    public static final String APPID                               = "appid";
    public static final String SUB_APPID                           = "sub_appid";
    public static final String MCH_ID                              = "mch_id";
    public static final String SUB_MCH_ID                          = "sub_mch_id";
    public static final String NONCE_STR                           = "nonce_str";
    public static final String SIGN_TYPE                           = "sign_type";
    public static final String SIGN                                = "sign";

    /** 统一下单交易类型 */
    public static final String TRADE_TYPE                          = "trade_type";
    /** 统一下单交易类型-H5网页支付 */
    public static final String JSAPI                               = "JSAPI";
    /** 统一下单交易类型-原生扫码支付 */
    public static final String NATIVE                              = "NATIVE";
    /** 统一下单交易类型-APP内支付 */
    public static final String APP                                 = "APP";
    /** 统一下单交易类型-WAP类型支付 */
    public static final String WAP                                 = "WAP";

    /** JSAPI支付关键必填字段 */
    public static final String OPENID                              = "openid";
    /** NATIVE支付关键必填字段 */
    public static final String PRODUCT_ID                          = "product_id";
    /** 统一下单公共必填字段——商户订单号 */
    public static final String OUT_TRADE_NO                        = "out_trade_no";
    /** 统一下单在微信侧的订单号（微信侧系统下单返回） */
    public static final String TRANSACTION_ID                      = "transaction_id";
    /** 统一下单公共必填字段——支付商品描述 */
    public static final String BODY                                = "body";
    /** 统一下单公共必填字段——支付总金额（单位：分） */
    public static final String TOTAL_FEE                           = "total_fee";
    /** 统一下单公共必填字段——支付回调通知地址 */
    public static final String NOTIFY_URL                          = "notify_url";
    /** 微信支付分配的终端设备号 */
    public static final String DEVICE_INFO                         = "device_info";
    /** 生成账单的ip地址 */
    public static final String SPBILL_CREATE_IP                    = "spbill_create_ip";
    /** 附加数据，原样返回 */
    public static final String ATTACH                              = "attach";
    /** 订单生成时间，yyyyMMddHHmmss，20091225091010，取自商户服务器 */
    public static final String TIME_START                          = "time_start";
    /** 订单失效时间，yyyyMMddHHmmss，格式同上，取自商户服务器 */
    public static final String TIME_EXPIRE                         = "time_expire";
    /** 下单售卖商品标签，不要随意修改这个字段 */
    public static final String GOODS_TAG                           = "goods_tag";

    /** 统一下单预支付id字段 */
    public static final String PREPAY_ID                           = "prepay_id";

    /** 微信支付退款申请退款金额必填字段——必填 */
    public static final String REFUND_FEE                          = "refund_fee";
    /** 商户侧退款单号——必填 */
    public static final String OUT_REFUND_NO                       = "out_refund_no";
    /** 商户侧退款操作员id——必填 */
    public static final String OP_USER_ID                          = "op_user_id";
    /** 商户侧退款操作员密码——选填 */
    public static final String OP_USER_PASSWD                      = "op_user_passwd";
    /** 微信退款查询id */
    public static final String REFUND_ID                           = "refund_id";

    /** 对账单日期 */
    public static final String BILL_DATE                           = "bill_date";

    // 微信公众号红包相关
    public static final String MCH_BILLNO                          = "mch_billno";
    public static final String SEND_NAME                           = "send_name";
    public static final String RE_OPENID                           = "re_openid";
    public static final String TOTAL_AMOUNT                        = "total_amount";
    public static final String TOTAL_NUM                           = "total_num";
    public static final String WISHING                             = "wishing";
    public static final String CLIENT_IP                           = "client_ip";
    public static final String ACT_NAME                            = "act_name";
    public static final String REMARK                              = "remark";
    /** 奇葩的字段wxappid，红包新网页文档和原pdf文档好像格格不入 */
    public static final String WXAPPID                             = "wxappid";

    // 微信支付响应返回字段

    /** 微信支付通信返回码 */
    public static final String RETURN_CODE                         = "return_code";
    /** 微信支付通信返回消息 */
    public static final String RETURN_MSG                          = "return_msg";
    /** 微信支付业务返回码 */
    public static final String RESULT_CODE                         = "result_code";
    /** 微信支付业务错误描述（目前此字段不知道是否还有） */
    public static final String ERR_CODE_DES                        = "err_code_des";

    // 刷卡支付

    /** 刷卡支付用户授权码（钱包-付款-条形码） */
    public static final String AUTH_CODE                           = "auth_code";
    /** 商品详情 */
    public static final String DETAIL                              = "detail";
    /** 指定支付方式：no-credit|禁止信用卡刷卡 */
    public static final String LIMIT_PAY                           = "limit_pay";
    /** 电子发票入口开放标识：Y|代表开电子发票 */
    public static final String RECEIPT                             = "receipt";
    /** 刷卡场景信息 */
    public static final String SCENE_INFO                          = "scene_info";
    /** 场景信息json中的key */
    public static final String STORE_INFO                          = "store_info";
    /** 门店id */
    public static final String ID                                  = "id";
    /** 门店名称 */
    public static final String NAME                                = "name";
    /** 门店行政区划码 */
    public static final String AREA_CODE                           = "area_code";
    /** 门店详细地址 */
    public static final String ADDRESS                             = "address";

    // 刷卡支付响应字段

    /** 刷卡支付错误码 */
    public static final String ERR_CODE                            = "err_code";

    // 刷卡支付状态机

    /** 用户支付中 */
    public static final String USERPAYING                          = "USERPAYING";
    /** 刷卡支付系统错误 */
    public static final String SYSTEMERROR                         = "SYSTEMERROR";

    // 订单查询相关字段

    /** 刷卡支付hold单状态：1-成功；2-系统错误或用户支付中，继续查询；0-查无此单 */
    public static final String MICRO_HOLD_STATUS                   = "micro_hold_status";
    /** 订单交易状态 */
    public static final String TRADE_STATE                         = "trade_state";
    /** 订单交易状态 */
    public static final String ORDERNOTEXIST                       = "ORDERNOTEXIST";

}