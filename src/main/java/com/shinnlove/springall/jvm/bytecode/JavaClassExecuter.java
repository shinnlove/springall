/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jvm.bytecode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.shinnlove.springall.jvm.classloader.HotSwapClassLoader;

/**
 * Java类执行类。
 *
 * @author shinnlove.jinsheng
 * @version $Id: JavaClassExecuter.java, v 0.1 2019-03-09 14:55 shinnlove.jinsheng Exp $$
 */
public class JavaClassExecuter {

    public static void main(String[] args) {
        String filePath = "/Users/zhaochensheng/Downloads/ActionServiceImpl.class";
        File file = new File(filePath);

        Long fileLength = file.length(); // 获取文件长度
        byte[] fileContent = new byte[fileLength.intValue()];
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ClassModifier cm = new ClassModifier(fileContent);

        // 遍历类文件
        cm.traverseClassFile();

        // 替换掉常量池的输出
        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System",
            "com.shinnlove.springall.jvm.bytecode.HackSystem");

    }

    public static String execute(byte[] classBytes) {
        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classBytes);

        // 替换掉常量池的输出
        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System",
            "com.shinnlove.springall.jvm.bytecode.HackSystem");

        // 使用自定义类加载器加载
        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class clazz = loader.loadByte(modiBytes);
        try {
            // 找到类中的main方法反射调用
            Method method = clazz.getMethod("main", new Class[] { String[].class });
            method.invoke(null, new String[] { null });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace(HackSystem.out);
        }
        return HackSystem.getBufferString();
    }
}