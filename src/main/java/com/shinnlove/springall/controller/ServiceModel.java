/**
 * bilibili.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

import java.io.Serializable;

/**
 * @author Tony Zhao
 * @version $Id: ServiceModel.java, v 0.1 2021-12-07 11:38 AM Tony Zhao Exp $$
 */
public class ServiceModel implements Serializable {

    private static final long serialVersionUID = 8793935297991439700L;

    private String            name;

    private Long              mid;

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
     * Getter method for property mid.
     *
     * @return property value of mid
     */
    public Long getMid() {
        return mid;
    }

    /**
     * Setter method for property mid.
     *
     * @param mid value to be assigned to property mid
     */
    public void setMid(Long mid) {
        this.mid = mid;
    }
}