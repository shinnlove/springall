/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.cache.domain.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 领域模型——商品类目。
 *
 * 被`Guava`缓存的领域模型需要实现`Serializable`接口吗？
 *
 * @author shinnlove.jinsheng
 * @version $Id: Category.java, v 0.1 2018-07-25 下午10:32 shinnlove.jinsheng Exp $$
 */
public class Category implements Serializable {

    private static final long serialVersionUID = -1006228414057832346L;

    /** 商品类目排序 */
    private int               sortId;

    /** 分类名称 */
    private String            name;

    public Category() {
    }

    public Category(int sortId, String name) {
        this.sortId = sortId;
        this.name = name;
    }

    /**
     * Getter method for property sortId.
     *
     * @return property value of sortId
     */
    public int getSortId() {
        return sortId;
    }

    /**
     * Setter method for property sortId.
     *
     * @param sortId value to be assigned to property sortId
     */
    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    /**
     * Getter method for property name.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property name.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}