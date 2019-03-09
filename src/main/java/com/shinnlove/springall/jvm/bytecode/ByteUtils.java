/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jvm.bytecode;

/**
 * 字节处理公共类。
 *
 * @author shinnlove.jinsheng
 * @version $Id: ByteUtils.java, v 0.1 2019-03-09 13:53 shinnlove.jinsheng Exp $$
 */
public class ByteUtils {

    /**
     * 给定一个字节数组，从起始位置读取给定长度的字节数，转成整型。
     *
     * @param b
     * @param start
     * @param len       这是字节数，不是bit位，所以要乘以8
     * @return
     */
    public static int bytes2Int(byte[] b, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int n = ((int) b[i]) & 0xff;
            n <<= (--len) * 8;
            sum = n + sum;
        }
        return sum;
    }

    /**
     * 给定一个整数、给定字节数组长度，将整数转成定长字节数组表示。
     *
     * @param value
     * @param len
     * @return
     */
    public static byte[] int2Bytes(int value, int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[len - i - 1] = (byte) ((value >> 8 * i) & 0xff);
        }
        return b;
    }

    /**
     * 给定一个字节数组，读取从起始位置到给定长度的字节，转成字符串。
     *
     * @param b
     * @param start
     * @param len
     * @return
     */
    public static String byte2String(byte[] b, int start, int len) {
        return new String(b, start, len);
    }

    /**
     * 给定一个字符串，返回字符串的字节数组。
     *
     * @param str
     * @return
     */
    public static byte[] string2Bytes(String str) {
        return str.getBytes();
    }

    /**
     * 字节数组替换。
     *
     * @param originalBytes
     * @param offset
     * @param len
     * @param replaceBytes
     * @return
     */
    public static byte[] bytesReplace(byte[] originalBytes, int offset, int len, byte[] replaceBytes) {
        byte[] newBytes = new byte[originalBytes.length + (replaceBytes.length - len)];
        System.arraycopy(originalBytes, 0, newBytes, 0, offset);
        System.arraycopy(replaceBytes, 0, newBytes, 0, offset);
        System.arraycopy(originalBytes, offset + len, newBytes, offset + replaceBytes.length,
            originalBytes.length - offset - len);
        return newBytes;
    }

}