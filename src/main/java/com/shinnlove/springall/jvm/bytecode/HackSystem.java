/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jvm.bytecode;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * 为JavaClass劫持java.lang.System提供支持。
 * 除了out和err外，其余的都直接转发给System处理。
 *
 * @author shinnlove.jinsheng
 * @version $Id: HackSystem.java, v 0.1 2019-03-09 14:45 shinnlove.jinsheng Exp $$
 */
public class HackSystem {

    public final static InputStream     in     = System.in;

    public static ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    public final static PrintStream     out    = new PrintStream(buffer);

    public final static PrintStream     err    = out;

    public static String getBufferString() {
        return buffer.toString();
    }

    public static void clearBuffer() {
        buffer.reset();
    }

    public static SecurityManager getSecurityManager() {
        return System.getSecurityManager();
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static void arrayCopy(Object src, int srcPos, Object dest, int destPos, int length) {
        System.arraycopy(src, srcPos, dest, destPos, length);
    }

    public static int identityHashCode(Object x) {
        return System.identityHashCode(x);
    }

}