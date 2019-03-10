/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jvm.bytecode;

/**
 * 类文件常量。
 *
 * @author shinnlove.jinsheng
 * @version $Id: ClassFileConstants.java, v 0.1 2019-03-09 16:49 shinnlove.jinsheng Exp $$
 */
public class ClassFileConstants {

    /**
     * ====================常量池类型区====================
     */

    /** Tag=1：CONSTANT_Utf8_info，UTF-8编码的字符串，指向一个字符串 */
    public static final int    CONSTANT_Utf8_info_TAG               = 1;

    /** Tag=3：CONSTANT_Integer_info，整型字面量 */
    public static final int    CONSTANT_Integer_info_TAG            = 3;

    /** Tag=4：CONSTANT_Float_info，浮点型字面量 */
    public static final int    CONSTANT_Float_info_TAG              = 4;

    /** Tag=5：CONSTANT_Long_info，长整型字面量 */
    public static final int    CONSTANT_Long_info_TAG               = 5;

    /** Tag=6：CONSTANT_Double_info，双精度浮点型字面量 */
    public static final int    CONSTANT_Double_info_TAG             = 6;

    /** Tag=7：CONSTANT_Class_info，类或接口的符号引用，指向一个类 */
    public static final int    CONSTANT_Class_info_TAG              = 7;

    /** Tag=8：CONSTANT_String_info，字符串类型字面量 */
    public static final int    CONSTANT_String_info_TAG             = 8;

    /** Tag=9：CONSTANT_Fieldref_info，字段的符号引用 */
    public static final int    CONSTANT_Fieldref_info_TAG           = 9;

    /** Tag=10：CONSTANT_Methodref_info，类中方法的符号引用 */
    public static final int    CONSTANT_Methodref_info_TAG          = 10;

    /** Tag=11：CONSTANT_InterfaceMethodref_info，接口中方法的符号引用 */
    public static final int    CONSTANT_InterfaceMethodref_info_TAG = 11;

    /** Tag=12：CONSTANT_NameAndType_info，字段或方法的部分符号引用 */
    public static final int    CONSTANT_NameAndType_info_TAG        = 12;

    /** Tag=15：CONSTANT_MethodHandle_info，表示方法句柄 */
    public static final int    CONSTANT_MethodHandle_info_TAG       = 15;

    /** Tag=16：CONSTANT_MethodType_info，标识方法类型 */
    public static final int    CONSTANT_MethodType_info_TAG         = 16;

    /** Tag=18：CONSTANT_InvokeDynamic_info，表示一个动态方法调用点 */
    public static final int    CONSTANT_InvokeDynamic_info_TAG      = 18;

    /**
     * ====================属性表类型区====================
     */

    /** 属性表固定项——ConstantValue，通知虚拟机自动为静态变量赋值 */
    public static final String ATTRIBUTE_CONSTANT_VALUE             = "ConstantValue";

    /** 属性表固定项——Code，函数字节码指令存放的结构体 */
    public static final String ATTRIBUTE_CODE                       = "Code";

}