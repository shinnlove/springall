/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jni;

/**
 * 雇员对象。
 *
 * @author shinnlove.jinsheng
 * @version $Id: Employee.java, v 0.1 2019-03-23 21:12 shinnlove.jinsheng Exp $$
 */
public class Employee {

    static {
        System.loadLibrary("employee");
    }

    /** 名字 */
    private String name;
    /** 工资 */
    private double salary;

    /**
     * construct for reflect
     */
    public Employee() {
    }

    /**
     * 构造器。
     * 
     * @param name
     * @param salary
     */
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public void print() {
        System.out.println("name=" + name + ", salary=" + salary);
    }

    /**
     * 员工调薪方法，属于一个类实例的本地方法。
     *
     * @param byPercent     调薪比例，如8%，则是108%
     */
    public native void raiseSalary(double byPercent);

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
     * Getter method for property salary.
     *
     * @return property value of salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Setter method for property salary.
     *
     * @param salary value to be assigned to property salary
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

}