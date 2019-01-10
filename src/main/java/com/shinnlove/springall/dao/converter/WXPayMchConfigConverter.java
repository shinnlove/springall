/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.dao.converter;

import com.shinnlove.springall.dao.model.WXPayMchConfigDO;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.enums.WXPayMode;
import com.shinnlove.springall.util.wxpay.sdkplus.enums.WXPaySignType;

/**
 * 设置成配置VO。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayMchConfigConverter.java, v 0.1 2019-01-10 17:52 shinnlove.jinsheng Exp $$
 */
public class WXPayMchConfigConverter {

    /**
     * DO模型转换成VO模型。
     *
     * @param configDO
     * @return
     */
    public static WXPayMchConfig toVO(WXPayMchConfigDO configDO) {
        WXPayMchConfig configVO = new WXPayMchConfig();
        configVO.setId(configDO.getId());
        configVO.setMerchantId(configDO.getMerchantId());
        configVO.setMchId(configDO.getMchId());
        configVO.setSubMchId(configDO.getSubMchId());
        configVO.setAppId(configDO.getAppId());
        configVO.setSubAppId(configDO.getSubAppId());
        configVO.setAppSecret(configDO.getAppSecret());
        configVO.setApiKey(configDO.getApiKey());
        configVO.setCertP12(configDO.getCertP12());
        configVO.setSslCertPath(configDO.getSslCertPath());
        configVO.setSslKeyPath(configDO.getSslKeyPath());
        configVO.setRootcaPem(configDO.getRootcaPem());

        // 枚举
        configVO.setPayMode(WXPayMode.parseByCode(configDO.getPayMode()));
        configVO.setSignType(WXPaySignType.parseByCode(configDO.getSignType()));

        // int转换boolean
        configVO.setUseSandbox(configDO.getUseSandbox() > 0 ? true : false);
        configVO.setAvailable(configDO.getAvailable() > 0 ? true : false);

        configVO.setCreateTime(configDO.getCreateTime());
        configVO.setModifyTime(configDO.getModifyTime());
        configVO.setRemark(configDO.getRemark());
        return configVO;
    }

    public static WXPayMchConfigDO toDO(WXPayMchConfig configVO) {
        WXPayMchConfigDO configDO = new WXPayMchConfigDO();

        return configDO;
    }

}