/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.dao.converter;

import com.shinnlove.springall.dao.model.WXPayRecord;
import com.shinnlove.springall.model.WxpayRecordDO;

/**
 * 业务模型和仓储模型转换。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayRecordConverter.java, v 0.1 2019-01-08 21:51 shinnlove.jinsheng Exp $$
 */
public class WXPayRecordConverter {

    /**
     * 转成DO对象。
     *
     * @param recordDO
     * @return
     */
    public static WXPayRecord toVO(WxpayRecordDO recordDO) {
        WXPayRecord recordVO = new WXPayRecord();

        return recordVO;
    }

    /**
     * 转成VO对象。
     *
     * @param recordVO
     * @return
     */
    public static WxpayRecordDO toDO(WXPayRecord recordVO) {
        WxpayRecordDO recordDO = new WxpayRecordDO();

        return recordDO;
    }

}