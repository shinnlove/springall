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
     * Tag=1：CONSTANT_Utf8_info，UTF-8编码的字符串，指向一个字符串
     * Tag=3：CONSTANT_Integer_info，整型字面量
     * Tag=4：CONSTANT_Float_info，浮点型字面量
     * Tag=5：CONSTANT_Long_info，长整型字面量
     * Tag=6：CONSTANT_Double_info，双精度浮点型字面量
     * Tag=7：CONSTANT_Class_info，类或接口的符号引用，指向一个类
     * Tag=8：CONSTANT_String_info，字符串类型字面量
     * Tag=9：CONSTANT_Fieldref_info，字段的符号引用
     * Tag=10：CONSTANT_Methodref_info，类中方法的符号引用
     * Tag=11：CONSTANT_InterfaceMethodref_info，接口中方法的符号引用
     * Tag=12：CONSTANT_NameAndType_info，字段或方法的部分符号引用
     * Tag=15：CONSTANT_MethodHandle_info，表示方法句柄
     * Tag=16：CONSTANT_MethodType_info，标识方法类型
     * Tag=18：CONSTANT_InvokeDynamic_info，表示一个动态方法调用点
     * 
     * @param oldStr    原来class文件中的字符串
     * @param newStr    新的class文件中的字符串
     * @return
     */
    public byte[] modifyUTF8Constant(String oldStr, String newStr) {
        int cpc = getConstantPoolCount();

        // 全局游标偏移量(偏移量定位到第11字节，也就是常量池第一个常量)
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;

        // 遍历常量池所有常量
        for (int i = 0; i < cpc; i++) {

            // Step1：每一次遍历常量池，第一个读出的必是u1类型的tag
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
     * 遍历整个类文件，有点像：`javap -verbose XXX.class`。
     */
    public void traverseClassFile() {
        int cpc = getConstantPoolCount();

        // 全局游标偏移量(偏移量定位到第11字节，也就是常量池第一个常量)
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;

        // 遍历常量池所有常量
        for (int i = 0; i < cpc; i++) {

            // Step1：每一次遍历常量池，第一个读出的必是u1类型的tag
            int tag = ByteUtils.bytes2Int(classByte, offset, u1);

            if (ClassFileConstants.CONSTANT_Utf8_info_TAG == tag) {
                // 处理不定长的`CONSTANT_Utf8_info`类型

                // 读完偏移量会自动定位到下一个常量池的偏移量
                offset = read_CONSTANT_Utf8_info(classByte, offset);

            } else if (ClassFileConstants.CONSTANT_Integer_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH[tag];

            } else if (ClassFileConstants.CONSTANT_Float_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH[tag];

            } else if (ClassFileConstants.CONSTANT_Long_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH[tag];

            } else if (ClassFileConstants.CONSTANT_Double_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH[tag];

            } else if (ClassFileConstants.CONSTANT_Class_info_TAG == tag) {

                // 读取类类型信息
                offset = read_CONSTANT_Class_info(classByte, offset);

            } else if (ClassFileConstants.CONSTANT_String_info_TAG == tag) {

                offset = read_CONSTANT_String_info(classByte, offset);

            } else if (ClassFileConstants.CONSTANT_Fieldref_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH[tag];

            } else if (ClassFileConstants.CONSTANT_Methodref_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH[tag];

            } else if (ClassFileConstants.CONSTANT_InterfaceMethodref_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH[tag];

            } else if (ClassFileConstants.CONSTANT_NameAndType_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH[tag];

            } else if (ClassFileConstants.CONSTANT_MethodHandle_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH[tag];

            } else if (ClassFileConstants.CONSTANT_MethodType_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH[tag];

            } else if (ClassFileConstants.CONSTANT_InvokeDynamic_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH[tag];

            }

        } // for

    }

    /**
     * 读出类文件常量池计数。
     *
     * 每个类的字段、方法各不相同，常量池数量也是不等的。
     * 在0xCAFEBABE和主次版本号8个字节后，第9个字节开始是一个u2类型的数据，代表常量池容量。
     *
     * 0~7是前8个字节，偏移量8就是第9个字节。
     *
     * @return
     */
    public int getConstantPoolCount() {
        return ByteUtils.bytes2Int(classByte, CONSTANT_POOL_COUNT_INDEX, u2);
    }

    /**
     * 读取utf8常量池信息。
     *
     * typedef struct CONSTANT_Utf8_info {
     *     u1   tag     1
     *     u2   length  1
     *     u1   bytes   length
     * }
     *
     * @param bytes     原字节数组
     * @param start
     * @return          返回读完后的偏移量
     */
    public int read_CONSTANT_Utf8_info(byte[] bytes, int start) {

        // 读出`CONSTANT_Utf8_info`长度length
        int length = ByteUtils.bytes2Int(bytes, start + u1, u2);

        start += (u1 + u2);

        // 读出这么长的`CONSTANT_Utf8_info`字符串信息bytes
        String str = ByteUtils.byte2String(bytes, start, length);

        // 输出这个字符串常量
        System.out.println(str);

        // 不定长，所以加上读出的length
        return start + length;
    }

    /**
     * 读取类类型信息。
     *
     * typedef struct CONSTANT_Class_info {
     *     u1   tag     1
     *     u2   index   全限定类名常量项索引位置
     * }
     *
     * @param bytes
     * @param start
     * @return
     */
    public int read_CONSTANT_Class_info(byte[] bytes, int start) {
        // 全限定类名常量项索引
        int cpcIndex = ByteUtils.bytes2Int(bytes, start + u1, u2);

        resolveConstantPool(bytes, cpcIndex);

        // 类信息占一个u1的tag，一个u2的索引
        return start + u1 + u2;
    }

    /**
     * 读取字符串常量型数据。
     *
     * typedef struct CONSTANT_String_info {
     *     u1   tag     1
     *     u2   index   指向字符串字面量的索引
     * }
     *
     * @param bytes
     * @param start
     * @return
     */
    public int read_CONSTANT_String_info(byte[] bytes, int start) {
        // 字符串字面量的索引
        int cpcIndex = ByteUtils.bytes2Int(bytes, start + u1, u2);

        // 解析字符串类型数据
        resolveConstantPool(bytes, cpcIndex);

        return start + u1 + u2;
    }

    /**
     * 从常量池某个给定位置读取u1类型的一个标记为，判定是什么数据。
     *
     * @param bytes
     * @param start
     * @return
     */
    public int read_CONSTANT_POOL_INFO_TAG(byte[] bytes, int start) {
        int tag = ByteUtils.bytes2Int(bytes, start, u1);
        return tag;
    }

    /**
     * 解析常量池常量。
     * 
     * @param bytes     给定class类字节文件
     * @param start     起始解析索引位置
     */
    public int resolveConstantPool(byte[] bytes, int start) {
        int tag = read_CONSTANT_POOL_INFO_TAG(bytes, start);
        switch (tag) {
            case ClassFileConstants.CONSTANT_Utf8_info_TAG:

                read_CONSTANT_Utf8_info(bytes, start);

                break;
            case ClassFileConstants.CONSTANT_Class_info_TAG:

                read_CONSTANT_Class_info(bytes, start);

                break;
            case ClassFileConstants.CONSTANT_String_info_TAG:

                read_CONSTANT_String_info(bytes, start);

                break;
            default:
                break;
        }

        return start;
    }

}