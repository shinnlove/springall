/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.dao.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 微信支付记录VO。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayRecord.java, v 0.1 2018-12-21 下午1:51 shinnlove.jinsheng Exp $$
 */
public class WXPayRecord {

    private long   id;

    private long    orderId;

    private long    merchantId;

    private boolean isPaid;

    private String appId;

    private String  mchId;

    private String  deviceInfo;

    private String  nonceStr;

    private String sign;

    private String body;

    private String attach;

    private String outTradeNo;

    private int    totalFee;

    private String spbillCreateIp;

    private String timeStart;

    private String timeExpire;

    private String goodsTag;

    private String notifyURL;

    private String tradeType;

    private String openId;

    private String prepayId;

    private String productId;

    private String codeURL;

    private String errCode;

    private String errCodeDes;

    private int    createTime;

    private int    modifyTime;

    private String remark;

    public WXPayRecord() {
    }

    public WXPayRecord(long orderId, long merchantId) {
        this.orderId = orderId;
        this.merchantId = merchantId;
    }

    public WXPayRecord(long id, long orderId, long merchantId, boolean isPaid, String appId,
                       String mchId, String deviceInfo, String nonceStr, String sign, String body,
                       String attach, String outTradeNo, int totalFee, String spbillCreateIp,
                       String timeStart, String timeExpire, String goodsTag, String notifyURL,
                       String tradeType, String openId, String prepayId, String productId,
                       String codeURL, String errCode, String errCodeDes, int createTime,
                       int modifyTime, String remark) {
        this.id = id;
        this.orderId = orderId;
        this.merchantId = merchantId;
        this.isPaid = isPaid;
        this.appId = appId;
        this.mchId = mchId;
        this.deviceInfo = deviceInfo;
        this.nonceStr = nonceStr;
        this.sign = sign;
        this.body = body;
        this.attach = attach;
        this.outTradeNo = outTradeNo;
        this.totalFee = totalFee;
        this.spbillCreateIp = spbillCreateIp;
        this.timeStart = timeStart;
        this.timeExpire = timeExpire;
        this.goodsTag = goodsTag;
        this.notifyURL = notifyURL;
        this.tradeType = tradeType;
        this.openId = openId;
        this.prepayId = prepayId;
        this.productId = productId;
        this.codeURL = codeURL;
        this.errCode = errCode;
        this.errCodeDes = errCodeDes;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.remark = remark;
    }

    public WXPayRecord(long orderId, long merchantId, boolean isPaid, String appId, String mchId,
                       String deviceInfo, String nonceStr, String sign, String body, String attach,
                       String outTradeNo, int totalFee, String spbillCreateIp, String timeStart,
                       String timeExpire, String goodsTag, String notifyURL, String tradeType,
                       String openId, String prepayId, String productId, String codeURL,
                       String errCode, String errCodeDes, int createTime, int modifyTime,
                       String remark) {
        this.orderId = orderId;
        this.merchantId = merchantId;
        this.isPaid = isPaid;
        this.appId = appId;
        this.mchId = mchId;
        this.deviceInfo = deviceInfo;
        this.nonceStr = nonceStr;
        this.sign = sign;
        this.body = body;
        this.attach = attach;
        this.outTradeNo = outTradeNo;
        this.totalFee = totalFee;
        this.spbillCreateIp = spbillCreateIp;
        this.timeStart = timeStart;
        this.timeExpire = timeExpire;
        this.goodsTag = goodsTag;
        this.notifyURL = notifyURL;
        this.tradeType = tradeType;
        this.openId = openId;
        this.prepayId = prepayId;
        this.productId = productId;
        this.codeURL = codeURL;
        this.errCode = errCode;
        this.errCodeDes = errCodeDes;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.remark = remark;
    }

    /**
     * Getter method for property id.
     *
     * @return property value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for property id.
     *
     * @param id value to be assigned to property id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter method for property orderId.
     *
     * @return property value of orderId
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * Setter method for property orderId.
     *
     * @param orderId value to be assigned to property orderId
     */
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    /**
     * Getter method for property merchantId.
     *
     * @return property value of merchantId
     */
    public long getMerchantId() {
        return merchantId;
    }

    /**
     * Setter method for property merchantId.
     *
     * @param merchantId value to be assigned to property merchantId
     */
    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    /**
     * Setter method for property paid.
     *
     * @param paid value to be assigned to property paid
     */
    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    /**
     * Getter method for property appId.
     *
     * @return property value of appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Setter method for property appId.
     *
     * @param appId value to be assigned to property appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * Getter method for property mchId.
     *
     * @return property value of mchId
     */
    public String getMchId() {
        return mchId;
    }

    /**
     * Setter method for property mchId.
     *
     * @param mchId value to be assigned to property mchId
     */
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    /**
     * Getter method for property deviceInfo.
     *
     * @return property value of deviceInfo
     */
    public String getDeviceInfo() {
        return deviceInfo;
    }

    /**
     * Setter method for property deviceInfo.
     *
     * @param deviceInfo value to be assigned to property deviceInfo
     */
    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    /**
     * Getter method for property nonceStr.
     *
     * @return property value of nonceStr
     */
    public String getNonceStr() {
        return nonceStr;
    }

    /**
     * Setter method for property nonceStr.
     *
     * @param nonceStr value to be assigned to property nonceStr
     */
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    /**
     * Getter method for property sign.
     *
     * @return property value of sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * Setter method for property sign.
     *
     * @param sign value to be assigned to property sign
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * Getter method for property body.
     *
     * @return property value of body
     */
    public String getBody() {
        return body;
    }

    /**
     * Setter method for property body.
     *
     * @param body value to be assigned to property body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Getter method for property attach.
     *
     * @return property value of attach
     */
    public String getAttach() {
        return attach;
    }

    /**
     * Setter method for property attach.
     *
     * @param attach value to be assigned to property attach
     */
    public void setAttach(String attach) {
        this.attach = attach;
    }

    /**
     * Getter method for property outTradeNo.
     *
     * @return property value of outTradeNo
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    /**
     * Setter method for property outTradeNo.
     *
     * @param outTradeNo value to be assigned to property outTradeNo
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    /**
     * Getter method for property totalFee.
     *
     * @return property value of totalFee
     */
    public int getTotalFee() {
        return totalFee;
    }

    /**
     * Setter method for property totalFee.
     *
     * @param totalFee value to be assigned to property totalFee
     */
    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * Getter method for property spbillCreateIp.
     *
     * @return property value of spbillCreateIp
     */
    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    /**
     * Setter method for property spbillCreateIp.
     *
     * @param spbillCreateIp value to be assigned to property spbillCreateIp
     */
    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    /**
     * Getter method for property timeStart.
     *
     * @return property value of timeStart
     */
    public String getTimeStart() {
        return timeStart;
    }

    /**
     * Setter method for property timeStart.
     *
     * @param timeStart value to be assigned to property timeStart
     */
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    /**
     * Getter method for property timeExpire.
     *
     * @return property value of timeExpire
     */
    public String getTimeExpire() {
        return timeExpire;
    }

    /**
     * Setter method for property timeExpire.
     *
     * @param timeExpire value to be assigned to property timeExpire
     */
    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    /**
     * Getter method for property goodsTag.
     *
     * @return property value of goodsTag
     */
    public String getGoodsTag() {
        return goodsTag;
    }

    /**
     * Setter method for property goodsTag.
     *
     * @param goodsTag value to be assigned to property goodsTag
     */
    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    /**
     * Getter method for property notifyURL.
     *
     * @return property value of notifyURL
     */
    public String getNotifyURL() {
        return notifyURL;
    }

    /**
     * Setter method for property notifyURL.
     *
     * @param notifyURL value to be assigned to property notifyURL
     */
    public void setNotifyURL(String notifyURL) {
        this.notifyURL = notifyURL;
    }

    /**
     * Getter method for property tradeType.
     *
     * @return property value of tradeType
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * Setter method for property tradeType.
     *
     * @param tradeType value to be assigned to property tradeType
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * Getter method for property openId.
     *
     * @return property value of openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * Setter method for property openId.
     *
     * @param openId value to be assigned to property openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * Getter method for property prepayId.
     *
     * @return property value of prepayId
     */
    public String getPrepayId() {
        return prepayId;
    }

    /**
     * Setter method for property prepayId.
     *
     * @param prepayId value to be assigned to property prepayId
     */
    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    /**
     * Getter method for property productId.
     *
     * @return property value of productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Setter method for property productId.
     *
     * @param productId value to be assigned to property productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Getter method for property codeURL.
     *
     * @return property value of codeURL
     */
    public String getCodeURL() {
        return codeURL;
    }

    /**
     * Setter method for property codeURL.
     *
     * @param codeURL value to be assigned to property codeURL
     */
    public void setCodeURL(String codeURL) {
        this.codeURL = codeURL;
    }

    /**
     * Getter method for property errCode.
     *
     * @return property value of errCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * Setter method for property errCode.
     *
     * @param errCode value to be assigned to property errCode
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    /**
     * Getter method for property errCodeDes.
     *
     * @return property value of errCodeDes
     */
    public String getErrCodeDes() {
        return errCodeDes;
    }

    /**
     * Setter method for property errCodeDes.
     *
     * @param errCodeDes value to be assigned to property errCodeDes
     */
    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    /**
     * Getter method for property createTime.
     *
     * @return property value of createTime
     */
    public int getCreateTime() {
        return createTime;
    }

    /**
     * Setter method for property createTime.
     *
     * @param createTime value to be assigned to property createTime
     */
    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    /**
     * Getter method for property modifyTime.
     *
     * @return property value of modifyTime
     */
    public int getModifyTime() {
        return modifyTime;
    }

    /**
     * Setter method for property modifyTime.
     *
     * @param modifyTime value to be assigned to property modifyTime
     */
    public void setModifyTime(int modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * Getter method for property remark.
     *
     * @return property value of remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Setter method for property remark.
     *
     * @param remark value to be assigned to property remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}