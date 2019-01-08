package com.shinnlove.springall.dao.mapper;

import com.shinnlove.springall.model.WxpayRecordDO;

public interface WxpayRecordMapper {
    int deleteByPrimaryKey(Long id);

    long insert(WxpayRecordDO record);

    int insertSelective(WxpayRecordDO record);

    WxpayRecordDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxpayRecordDO record);

    int updateByPrimaryKey(WxpayRecordDO record);
}