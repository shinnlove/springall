/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jvm.classloader;

/**
 * @author shinnlove.jinsheng
 * @version $Id: HotSwapClassLoader.java, v 0.1 2019-03-09 13:31 shinnlove.jinsheng Exp $$
 */
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class<?> loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }

}