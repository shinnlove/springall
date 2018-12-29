/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.core.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学生model类。
 *
 * @author shinnlove.jinsheng
 * @version $Id: Student.java, v 0.1 2018-12-29 17:05 shinnlove.jinsheng Exp $$
 */
public class Student implements Serializable {

    private static final long serialVersionUID = -7630163535073871493L;

    private long              id;
    private String            name;
    private int               age;
    private String            telphone;
    private String            remark;
    private String            from;

    public Student() {
    }

    public Student(long id, String name, int age, String telphone, String remark) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.telphone = telphone;
        this.remark = remark;
    }

    public Student(long id, String name, int age, String telphone, String remark, String from) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.telphone = telphone;
        this.remark = remark;
        this.from = from;
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
     * Getter method for property age.
     *
     * @return property value of age
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter method for property age.
     *
     * @param age value to be assigned to property age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Getter method for property telphone.
     *
     * @return property value of telphone
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * Setter method for property telphone.
     *
     * @param telphone value to be assigned to property telphone
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone;
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
     * Getter method for property from.
     *
     * @return property value of from
     */
    public String getFrom() {
        return from;
    }

    /**
     * Setter method for property from.
     *
     * @param from value to be assigned to property from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(ToStringStyle.SHORT_PREFIX_STYLE);
    }

}