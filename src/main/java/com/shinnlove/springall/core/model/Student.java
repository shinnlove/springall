/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.core.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 领域模型对象。
 *
 * @author shinnlove.jinsheng
 * @version $Id: Student.java, v 0.1 2018-12-30 13:22 shinnlove.jinsheng Exp $$
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 706662977669982562L;

    private long              id;
    private String            name;
    private int               age;

    public Student() {
    }

    public Student(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
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
     * @see Object#toString() 
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(ToStringStyle.SHORT_PREFIX_STYLE);
    }

}