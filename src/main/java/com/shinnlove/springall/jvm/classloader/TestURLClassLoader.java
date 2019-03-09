/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jvm.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.shinnlove.springall.jvm.model.Student;
import com.shinnlove.springall.jvm.service.StudentService;

/**
 * @author shinnlove.jinsheng
 * @version $Id: TestURLClassLoader.java, v 0.1 2019-03-09 11:30 shinnlove.jinsheng Exp $$
 */
public class TestURLClassLoader {

    public static void main(String[] args) {

        URLClassLoader loader = null;
        try {
            URL url = new URL("file:~/Downloads/springall.jar");
            loader = new URLClassLoader(new URL[] { url });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Class cl = null;
        try {
            cl = Class.forName("com.shinnlove.springall.storm.topology.MainJob", true, loader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        StudentService ss = null;
        try {
            ss = (StudentService) cl.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 查看这个类是谁加载的
        System.out.println(ss.getClass().getClassLoader());

        // 调用方法
        System.out.println(ss.plusStudentAge(new Student()));

    }

}