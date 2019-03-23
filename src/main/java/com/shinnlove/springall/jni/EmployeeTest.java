/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jni;

/**
 * 测试类实例的本地方法。
 *
 * @author shinnlove.jinsheng
 * @version $Id: EmployeeTest.java, v 0.1 2019-03-23 21:31 shinnlove.jinsheng Exp $$
 */
public class EmployeeTest {

    public static void main(String[] args) {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Tony", 33000);
        staff[1] = new Employee("Shinn", 50000);
        staff[2] = new Employee("Adam", 500000);

        for (Employee e : staff) {
            e.raiseSalary(10);
        }

        for (Employee e : staff) {
            e.print();
        }
    }

}