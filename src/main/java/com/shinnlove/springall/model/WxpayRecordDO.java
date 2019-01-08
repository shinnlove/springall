package com.shinnlove.springall.model;

/**
 * 微信支付仓储。
 */
public class WxpayRecordDO {

    private long   id;

    private long   orderId;

    private long   merchantId;

    private int    isPaid;

    private String appId;

    private String mchId;

    private String deviceInfo;

    private String nonceStr;

    private String sign;

    private String body;

    private String attach;

    private int    totalFee;

    private String spbillCreateIp;

    private String timeStart;

    private String timeExpire;

    private String goodsTag;

    private String notifyUrl;

    private String tradeType;

    private String openId;

    private String prepayId;

    private String productId;

    private String codeUrl;

    private String errCode;

    private String errCodeDes;

    private int    createTime;

    private int    modifyTime;

    private String remark;

    public WxpayRecordDO() {
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

    /**
     * Getter method for property isPaid.
     *
     * @return property value of isPaid
     */
    public int getIsPaid() {
        return isPaid;
    }

    /**
     * Setter method for property isPaid.
     *
     * @param isPaid value to be assigned to property isPaid
     */
    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
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
     * Getter method for property notifyUrl.
     *
     * @return property value of notifyUrl
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * Setter method for property notifyUrl.
     *
     * @param notifyUrl value to be assigned to property notifyUrl
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
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
     * Getter method for property codeUrl.
     *
     * @return property value of codeUrl
     */
    public String getCodeUrl() {
        return codeUrl;
    }

    /**
     * Setter method for property codeUrl.
     *
     * @param codeUrl value to be assigned to property codeUrl
     */
    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
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

}