/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jvm.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.shinnlove.springall.jvm.model.Student;
import com.shinnlove.springall.jvm.service.StudentService;

/**
 * 类加载器常见的用途有：类隔离和热部署。
 * 热部署必须是新的代码进行一版编译后，class字节码文件改变了，被新的类加载器加载。
 *
 * @author shinnlove.jinsheng
 * @version $Id: TestClassLoader.java, v 0.1 2019-03-07 10:08 shinnlove.jinsheng Exp $$
 */
public class TestClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException,
                                          InstantiationException {

        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {

                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                InputStream is = getClass().getResourceAsStream(fileName);
                if (is == null) {
                    return super.loadClass(name);
                }

                try {
                    // 文件流字节大小的缓冲区
                    byte[] b = new byte[is.available()];
                    is.read(b);

                    return defineClass(name, b, 0, b.length);

                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        // 自定义类加载器加载一个
        Class<?> serviceClazz = myLoader
            .loadClass("com.shinnlove.wechatpay.service.StudentService");
        Object serviceObj = serviceClazz.newInstance();

        Class<?> studentClazz = myLoader.loadClass("com.shinnlove.wechatpay.service.Student");
        Object studentObj = studentClazz.newInstance();

        // class还是全类名一样
        System.out.println(serviceObj.getClass());

        // classLoader是当前类名，因为当前类定义了类加载器
        System.out.println(serviceObj.getClass().getClassLoader());

        // 与系统默认AppClassLoader加载的StudentService不同
        System.out.println(serviceObj instanceof StudentService);

        // instanceof不行，肯定转型不行!
        try {
            StudentService ss = (StudentService) serviceObj;
        } catch (ClassCastException e) {
            System.out.println("无法转型：" + e.getMessage());
        }

        // 对AppClassLoader加载的类进行方法调用
        Student s = new Student(1, 25, "tony");
        StudentService service = new StudentService();
        System.out.println(service.plusStudentAge(s));

        // 对AppClassLoader加载的类进行反射
        try {
            Class<?> oriClazz = StudentService.class;
            Method m = oriClazz.getMethod("plusStudentAge", Student.class);
            m.setAccessible(true);
            Object result = m.invoke(service, s);
            System.out.println(result);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 自己加载的类，无法代码转型(无法直接调用其方法)，尝试反射调用(这样肯定是可以的)
        // 而如果在invoke的时候，传入AppClassLoader加载的类作为方法形参，直接抛出：
        // `java.lang.IllegalArgumentException: argument type mismatch`拒绝方法调用
        // 可见不同ClassLoader访问彼此的类没有意义，也做不到，就像两个JVM程序里的类一样。
        try {
            Method m = serviceClazz.getMethod("plusStudentAge", studentClazz);
            m.setAccessible(true);
            Object result = m.invoke(serviceObj, studentObj);
            System.out.println(result);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 总是返回`sun.misc.Launcher$AppClassLoader`
        System.out.println(getSystemClassLoader());

        // 线程上下文类加载器，在Launcher启动时，总是塞入`sun.misc.Launcher$AppClassLoader`
        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        System.out.println(ccl);

    }

}