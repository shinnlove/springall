/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jvm.service;

import com.shinnlove.springall.jvm.model.Student;

/**
 * @author shinnlove.jinsheng
 * @version $Id: StudentService.java, v 0.1 2019-03-09 11:29 shinnlove.jinsheng Exp $$
 */
public class StudentService {

    public int plusStudentAge(Student s) {
        return s.getAge() + 2;
    }

}