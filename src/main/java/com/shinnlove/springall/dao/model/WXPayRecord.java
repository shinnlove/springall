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

    private long   mId;

    private String appId;

    private String mch_id;

    private String device_info;

    private String nonce_str;

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

    public WXPayRecord(long mId, String appId, String mch_id, String device_info, String nonce_str,
                       String sign, String body, String attach, String outTradeNo, int totalFee,
                       String spbillCreateIp, String timeStart, String timeExpire, String goodsTag,
                       String notifyURL, String tradeType, String openId, String prepayId,
                       String productId, String codeURL, String errCode, String errCodeDes,
                       int createTime, int modifyTime, String remark) {
        this.mId = mId;
        this.appId = appId;
        this.mch_id = mch_id;
        this.device_info = device_info;
        this.nonce_str = nonce_str;
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

    public WXPayRecord(long id, long mId, String appId, String mch_id, String device_info,
                       String nonce_str, String sign, String body, String attach,
                       String outTradeNo, int totalFee, String spbillCreateIp, String timeStart,
                       String timeExpire, String goodsTag, String notifyURL, String tradeType,
                       String openId, String prepayId, String productId, String codeURL,
                       String errCode, String errCodeDes, int createTime, int modifyTime,
                       String remark) {
        this.id = id;
        this.mId = mId;
        this.appId = appId;
        this.mch_id = mch_id;
        this.device_info = device_info;
        this.nonce_str = nonce_str;
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
     * Getter method for property mId.
     *
     * @return property value of mId
     */
    public long getmId() {
        return mId;
    }

    /**
     * Setter method for property mId.
     *
     * @param mId value to be assigned to property mId
     */
    public void setmId(long mId) {
        this.mId = mId;
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
     * Getter method for property mch_id.
     *
     * @return property value of mch_id
     */
    public String getMch_id() {
        return mch_id;
    }

    /**
     * Setter method for property mch_id.
     *
     * @param mch_id value to be assigned to property mch_id
     */
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    /**
     * Getter method for property device_info.
     *
     * @return property value of device_info
     */
    public String getDevice_info() {
        return device_info;
    }

    /**
     * Setter method for property device_info.
     *
     * @param device_info value to be assigned to property device_info
     */
    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    /**
     * Getter method for property nonce_str.
     *
     * @return property value of nonce_str
     */
    public String getNonce_str() {
        return nonce_str;
    }

    /**
     * Setter method for property nonce_str.
     *
     * @param nonce_str value to be assigned to property nonce_str
     */
    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
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