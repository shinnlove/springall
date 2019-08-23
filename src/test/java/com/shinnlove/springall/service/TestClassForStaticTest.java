/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.util.ReflectionUtils;

/**
 * @author shinnlove.jinsheng
 * @version $Id: TestClassForStaticTest.java, v 0.1 2019-08-23 16:22 shinnlove.jinsheng Exp $$
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TestClassForStatic.class })
public class TestClassForStaticTest {

    @Test
    public void 测试静态方法() {

        // prepare mock data
        TestClassForStatic mockObject = null;
        try {
            Constructor<TestClassForStatic> constructor = TestClassForStatic.class
                .getDeclaredConstructor();
            ReflectionUtils.makeAccessible(constructor);
            mockObject = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mockStatic(TestClassForStatic.class);
        when(TestClassForStatic.getInstance()).thenReturn(mockObject);

        // actual invoke
        TestClassForStatic target = TestClassForStatic.getInstance();
        Assert.assertEquals("hello", target.getInfo());
    }

}