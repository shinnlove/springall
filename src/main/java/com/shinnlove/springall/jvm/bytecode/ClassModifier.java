/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jvm.bytecode;

/**
 * 解析与修改Class文件的公共类。
 *
 * 修改暂时只提供修改常量池常量的功能。
 *
 * @author shinnlove.jinsheng
 * @version $Id: ClassModifier.java, v 0.1 2019-03-09 13:36 shinnlove.jinsheng Exp $$
 */
public class ClassModifier {

    /** Class文件中常量池的起始偏移 */
    private static final int   CONSTANT_POOL_COUNT_INDEX = 8;

    /** CONSTANT_Utf8_info 常量的tag标志 */
    private static final int   CONSTANT_Utf8_info        = 1;

    /** 常量池中11种常量所占长度，CONSTANT_Utf8_info型常量除外，因为它不是定长的 */
    private static final int[] CONSTANT_ITEM_LENGTH      = { -1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5,
            5                                           };

    /** 无符号整型1，1个字节长度 */
    private static final int   u1                        = 1;
    /** 无符号整型2，2个字节长度 */
    private static final int   u2                        = 2;

    /** 类编译后的class字节码，javac编译器生成的字节码 */
    private byte[]             classByte;

    public ClassModifier(byte[] classByte) {
        this.classByte = classByte;
    }

    /**
     * 修改常量池指定字符串为目标字符串。
     * 
     * @param oldStr    原来class文件中的字符串
     * @param newStr    新的class文件中的字符串
     * @return
     */
    public byte[] modifyUTF8Constant(String oldStr, String newStr) {
        int cpc = getConstantPoolCount();
        // 全局游标偏移量
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;
        // 遍历常量池所有常量
        for (int i = 0; i < cpc; i++) {
            // 先取常量池标记
            int tag = ByteUtils.bytes2Int(classByte, offset, u1);

            if (tag == CONSTANT_Utf8_info) {
                // 处理不定长的`CONSTANT_Utf8_info`类型

                // 读出`CONSTANT_Utf8_info`长度
                int len = ByteUtils.bytes2Int(classByte, offset + u1, u2);

                offset += (u1 + u2);

                // 读出这么长的`CONSTANT_Utf8_info`字符串信息
                String str = ByteUtils.byte2String(classByte, offset, len);

                // 判断是否是要替换的内容
                if (str.equalsIgnoreCase(oldStr)) {

                    // 新字符串长度
                    byte[] strLen = ByteUtils.int2Bytes(newStr.length(), u2);

                    // 要替换字符串的字节数组
                    byte[] strBytes = ByteUtils.string2Bytes(newStr);

                    // 先替换长度
                    classByte = ByteUtils.bytesReplace(classByte, offset - u2, u2, strLen);
                    // 再替换内容
                    classByte = ByteUtils.bytesReplace(classByte, offset, len, strBytes);

                    return classByte;
                } else {
                    // 其他类型的字符集放过
                    offset += len;
                }
            } else {
                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        } // for

        return classByte;
    }

    /**
     * 读出类文件常量池计数。
     *
     * @return
     */
    public int getConstantPoolCount() {
        return ByteUtils.bytes2Int(classByte, CONSTANT_POOL_COUNT_INDEX, u2);
    }

}