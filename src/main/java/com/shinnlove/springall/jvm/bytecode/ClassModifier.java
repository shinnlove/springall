/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jvm.bytecode;

import java.util.HashMap;
import java.util.Map;

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
    private static final int                   CONSTANT_POOL_COUNT_INDEX = 8;

    /** 无符号整型1，1个字节长度 */
    private static final int                   u1                        = 1;
    /** 无符号整型2，2个字节长度 */
    private static final int                   u2                        = 2;
    /** 无符号整型4，4个字节长度 */
    private static final int                   u4                        = 4;

    /** 常量池中11种常量长度，其中CONSTANT_Utf8_info型常量除外，因为它不是定长的 */
    private static final Map<Integer, Integer> CONSTANT_ITEM_LENGTH      = new HashMap<>();

    static {
        // -1代表不定长
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_Utf8_info_TAG, -1);
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_Integer_info_TAG, 5);
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_Float_info_TAG, 5);
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_Long_info_TAG, 9);
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_Double_info_TAG, 9);
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_Class_info_TAG, 3);
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_String_info_TAG, 3);
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_Fieldref_info_TAG, 5);
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_Methodref_info_TAG, 5);
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_InterfaceMethodref_info_TAG, 5);
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_NameAndType_info_TAG, 5);
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_MethodHandle_info_TAG, 3);
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_MethodType_info_TAG, 3);
        CONSTANT_ITEM_LENGTH.put(ClassFileConstants.CONSTANT_InvokeDynamic_info_TAG, 5);
    }

    /** 类编译后的class字节码，javac编译器生成的字节码 */
    private byte[]                             classByte;

    /**
     * 构造器，传入类的字节码。
     *
     * @param classByte
     */
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

        // 全局游标偏移量(偏移量定位到第11字节，也就是常量池第一个常量)
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;

        // 遍历常量池所有常量，常量池计数器索引从1开始!!!
        for (int i = 1; i < cpc; i++) {

            // Step1：每一次遍历常量池，第一个读出的必是u1类型的tag
            int tag = ByteUtils.bytes2Int(classByte, offset, u1);

            if (tag == ClassFileConstants.CONSTANT_Utf8_info_TAG) {
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
                offset += CONSTANT_ITEM_LENGTH.get(tag);
            }
        } // for

        return classByte;
    }

    /**
     * 确定整个常量池结束的字节位置，返回的值是AccessFlag开始的字节数。
     *
     * 如返回offset = 60，则代表60字节、61字节是访问标识：
     * 因为在常量池结束之后，紧接着的两个字节代表访问标识，识别类或接口的访问信息。
     *
     * @return  常量池结束的字节位置
     */
    public int ConstantPoolEndAccessFlagOffset() {
        int cpc = getConstantPoolCount();

        // 全局游标偏移量(偏移量定位到第11字节，也就是常量池第一个常量)
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;

        // 常量池计数器索引从1开始!!!
        for (int i = 1; i < cpc; i++) {
            // Step1：每一次遍历常量池，第一个读出的必是u1类型的tag
            int tag = ByteUtils.bytes2Int(classByte, offset, u1);

            if (ClassFileConstants.CONSTANT_Utf8_info_TAG == tag) {
                offset += CONSTANT_Utf8_Length(classByte, offset);
            } else {
                offset += CONSTANT_ITEM_LENGTH.get(tag);
            }

        }

        return offset;
    }

    /**
     * 遍历整个类文件，有点像：`javap -verbose XXX.class`。
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
     */
    public void traverseConstantPool() {
        int cpc = getConstantPoolCount();

        // 全局游标偏移量(偏移量定位到第11字节，也就是常量池第一个常量)
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;

        // 遍历常量池所有常量，常量池从1开始!!!
        for (int i = 1; i < cpc; i++) {

            // Step1：每一次遍历常量池，第一个读出的必是u1类型的tag
            int tag = ByteUtils.bytes2Int(classByte, offset, u1);

            // 读完每个常量偏移量会自动定位到下一个常量池的偏移量
            if (ClassFileConstants.CONSTANT_Utf8_info_TAG == tag) {
                // 1.处理不定长的`CONSTANT_Utf8_info`类型
                offset = traverse_CONSTANT_Utf8_info(classByte, offset);
            } else if (ClassFileConstants.CONSTANT_Integer_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH.get(tag);

            } else if (ClassFileConstants.CONSTANT_Float_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH.get(tag);

            } else if (ClassFileConstants.CONSTANT_Long_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH.get(tag);

            } else if (ClassFileConstants.CONSTANT_Double_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH.get(tag);

            } else if (ClassFileConstants.CONSTANT_Class_info_TAG == tag) {
                // 7.读取类的类型信息
                offset = read_CONSTANT_Class_info(classByte, offset);
            } else if (ClassFileConstants.CONSTANT_String_info_TAG == tag) {
                // 8.处理字符串类型
                offset = traverse_CONSTANT_String_info(classByte, offset);
            } else if (ClassFileConstants.CONSTANT_Fieldref_info_TAG == tag) {
                // 9.类中字段的符号引用
                offset = read_CONSTANT_Fieldref_info(classByte, offset);
            } else if (ClassFileConstants.CONSTANT_Methodref_info_TAG == tag) {
                // 10.类中方法的符号引用
                offset = read_CONSTANT_Methodref_info(classByte, offset);
            } else if (ClassFileConstants.CONSTANT_InterfaceMethodref_info_TAG == tag) {
                // 11.接口中方法的符号引用
                offset = read_CONSTANT_Interface_Methodref_info(classByte, offset);
            } else if (ClassFileConstants.CONSTANT_NameAndType_info_TAG == tag) {
                // 12.字段或方法的部分符号引用
                offset = read_CONSTANT_NameAndType_info(classByte, offset);
            } else if (ClassFileConstants.CONSTANT_MethodHandle_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH.get(tag);

            } else if (ClassFileConstants.CONSTANT_MethodType_info_TAG == tag) {
                // 16.标识方法类型
                offset = read_CONSTANT_MethodType_info(classByte, offset);
            } else if (ClassFileConstants.CONSTANT_InvokeDynamic_info_TAG == tag) {

                // 非`CONSTANT_Utf8_info`常量、直接越过当前常量长度
                offset += CONSTANT_ITEM_LENGTH.get(tag);

            }

        } // for

    }

    /**
     * 访问类信息。
     *
     * @param offset    常量池结束后、访问标志开始前
     */
    public void traverseClassInfo(int offset) {
        // 如果accessFlag=33（十进制）、则代表十六进制0x0021、代表一个类是public，ACC_SUPER是JDK1.2后必须置为1的
        int accessFlag = ByteUtils.bytes2Int(classByte, offset, u2);

        offset += u2;

        // 类索引、父类索引、接口类型索引都指向`CONSTANT_Class_info`类型

        // 解析类索引
        int classIndex = ByteUtils.bytes2Int(classByte, offset, u2);
        resolveConstantPoolByIndex(classByte, classIndex);

        offset += u2;

        // 解析父类索引（特别注意：实现的接口不算是父类的，只在接口索引中!）
        int parentClassIndex = ByteUtils.bytes2Int(classByte, offset, u2);
        resolveConstantPoolByIndex(classByte, parentClassIndex);

        offset += u2;

        // 解析接口索引

        // 接口计数器
        int interfaceCount = ByteUtils.bytes2Int(classByte, offset, u2);
        System.out.println("该类实现了接口数interfaceCount=" + interfaceCount);

        offset += u2;

        // 循环遍历接口索引集合（注意数量不定!）
        for (int i = 0; i < interfaceCount; i++) {
            // 既然是索引的集合，那必然是u2类型的集合
            int oneIndex = ByteUtils.bytes2Int(classByte, offset, u2);
            resolveConstantPoolByIndex(classByte, oneIndex);

            // 遍历完一个索引，offset偏移两字节
            offset += u2;
        }

        // 解析字段表
        int fieldCount = ByteUtils.bytes2Int(classByte, offset, u2);
        System.out.println("这个类有" + fieldCount + "个字段");

        offset += u2;

        // 循环解析类的字段
        for (int i = 0; i < fieldCount; i++) {
            // u2类型的accessFlag
            int fieldAccessFlag = ByteUtils.bytes2Int(classByte, offset, u2);

            offset += u2;

            // u2类型的name_index简单名称
            int nameIndex = ByteUtils.bytes2Int(classByte, offset, u2);
            String fieldSimpleName = resolveConstantPoolByIndex(classByte, nameIndex);
            System.out.println("字段简单名称fieldSimpleName=" + fieldSimpleName);

            offset += u2;

            // u2类型的descriptor_index描述符、说明这个字段是什么类型的
            int descriptorIndex = ByteUtils.bytes2Int(classByte, offset, u2);
            String fieldDescriptor = resolveConstantPoolByIndex(classByte, descriptorIndex);
            System.out.println("字段类型描述fieldDescriptor=" + fieldDescriptor);

            offset += u2;

            // 解析属性数(不是每个字段都有属性的!!!如果没有给初始值就没有)
            int attributesCount = ByteUtils.bytes2Int(classByte, offset, u2);

            offset += u2;

            // 循环解析字段的属性表，每个属性都是一个attribute_info类型
            for (int j = 0; j < attributesCount; j++) {
                // 属性名索引序号
                int attributeNameIndex = ByteUtils.bytes2Int(classByte, offset, u2);
                // 关键的属性名
                String attributeName = resolveConstantPoolByIndex(classByte, attributeNameIndex);

                System.out.println("当前attributeName=" + attributeName);

                offset += u2;

                // 属性表长度大家都是占用u4字节的
                int attributeLength = ByteUtils.bytes2Int(classByte, offset, u4);
                System.out.println("attributeLength=" + attributeLength);

                offset += u4;

                // 根据不同的属性名解析不同的属性表
                switch (attributeName) {
                    case ClassFileConstants.ATTRIBUTE_CONSTANT_VALUE:
                        // ConstantValue类型attributeLength定长为2

                        // 固定值字面量索引
                        int constantValueIndex = ByteUtils.bytes2Int(classByte, offset, u2);
                        String constantValue = resolveConstantPoolByIndex(classByte,
                            constantValueIndex);

                        // 这里将会输出Micheal Jordan
                        System.out.println("constantValue=" + constantValue);

                        break;
                    default:
                        break;
                }

                // 共同偏移属性表长度个字节
                offset += attributeLength;

            } // for 属性表

        } // for 类字段

        // 类字段解析完应该解析方法了

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
     * 确定utf8常量池长度。
     *
     * @param bytes
     * @param start
     * @return
     */
    public int CONSTANT_Utf8_Length(byte[] bytes, int start) {
        // 读出`CONSTANT_Utf8_info`长度length
        int length = ByteUtils.bytes2Int(bytes, start + u1, u2);
        return u1 + u2 + length;
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
    public int traverse_CONSTANT_Utf8_info(byte[] bytes, int start) {
        // 读出这么长的`CONSTANT_Utf8_info`字符串信息bytes
        String str = read_CONSTANT_Utf8_info(bytes, start);

        // 输出这个字符串常量
        System.out.println(str);

        // 读出`CONSTANT_Utf8_info`长度length
        int length = ByteUtils.bytes2Int(bytes, start + u1, u2);

        start += (u1 + u2);

        // 不定长，所以加上读出的length
        return start + length;
    }

    /**
     * 读出`CONSTANT_Utf8_info`常量信息。
     *
     * @param bytes
     * @param start
     * @return
     */
    public String read_CONSTANT_Utf8_info(byte[] bytes, int start) {
        // 读出`CONSTANT_Utf8_info`长度length
        int length = ByteUtils.bytes2Int(bytes, start + u1, u2);

        start += (u1 + u2);

        // 读出这么长的`CONSTANT_Utf8_info`字符串信息bytes
        return ByteUtils.byte2String(bytes, start, length);
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
        int countIndex = ByteUtils.bytes2Int(bytes, start + u1, u2);

        resolveConstantPoolByIndex(bytes, countIndex);

        // 类信息占一个u1的tag，一个u2的索引
        return start + u1 + u2;
    }

    /**
     * 解析读出的字符串信息。
     *
     * @param bytes
     * @param start
     * @return
     */
    public String read_CONSTANT_String_info(byte[] bytes, int start) {
        // 字符串字面量的索引
        int countIndex = ByteUtils.bytes2Int(bytes, start + u1, u2);

        return resolveConstantPoolByIndex(bytes, countIndex);
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
    public int traverse_CONSTANT_String_info(byte[] bytes, int start) {
        // 字符串字面量的索引
        int countIndex = ByteUtils.bytes2Int(bytes, start + u1, u2);

        String result = resolveConstantPoolByIndex(bytes, countIndex);

        System.out.println("读到字符串常量result=" + result);

        return start + u1 + u2;
    }

    /**
     * 读取类字段的符号引用。
     *
     * typedef struct CONSTANT_Fieldref_info {
     *     u1   tag     1
     *     u2   index   指向声明字段的类或者接口描述符`CONSTANT_Class_info`索引项
     *     u2   index   指向字段描述符`CONSTANT_NameAndType`的索引项
     * }
     *
     * @param bytes
     * @param start
     * @return
     */
    public int read_CONSTANT_Fieldref_info(byte[] bytes, int start) {
        read_Interface_Method_Field_Name_Type(bytes, start);

        return start + u1 + u2 + u2;
    }

    /**
     * 读取类中方法的符号引用。
     *
     * typedef struct CONSTANT_Methodref_info {
     *     u1   tag     1
     *     u2   index   指向声明方法的类描述符`CONSTANT_Class_info`的索引项
     *     u2   index   指向名称及类型描述符`CONSTANT_NameAndType`的索引项
     * }
     *
     * @param bytes
     * @param start
     * @return
     */
    public int read_CONSTANT_Methodref_info(byte[] bytes, int start) {
        read_Interface_Method_Field_Name_Type(bytes, start);

        return start + u1 + u2 + u2;
    }

    /**
     * 读取接口中方法的符号引用。
     *
     * typedef struct CONSTANT_Interface_Methodref_info {
     *     u1   tag     1
     *     u2   index   指向声明方法的类描述符`CONSTANT_Class_info`的索引项
     *     u2   index   指向名称及类型描述符`CONSTANT_NameAndType`的索引项
     * }
     *
     * @param bytes
     * @param start
     * @return
     */
    public int read_CONSTANT_Interface_Methodref_info(byte[] bytes, int start) {
        read_Interface_Method_Field_Name_Type(bytes, start);

        return start + u1 + u2 + u2;
    }

    /**
     * 读取字段或方法的部分符号引用。
     *
     * typedef struct CONSTANT_NameAndType_info {
     *     u1   tag     1
     *     u2   index   指向
     *     u2   index   指向名称及类型描述符`CONSTANT_NameAndType`的索引项
     * }
     * 
     * @param bytes
     * @param start
     * @return
     */
    public int read_CONSTANT_NameAndType_info(byte[] bytes, int start) {
        read_Interface_Method_Field_Name_Type(bytes, start);

        return start + u1 + u2 + u2;
    }

    /**
     * 读取标识方法类型。
     *
     * typedef struct CONSTANT_MethodType_info {
     *     u1   tag     1
     *     u2   index   值必须对常量池的有效索引，常量池在索引处必须是`CONSTANT_Utf8_info`类型
     * }
     *
     * @param bytes
     * @param start
     * @return
     */
    public int read_CONSTANT_MethodType_info(byte[] bytes, int start) {
        // `CONSTANT_Utf8_info`常量索引
        int mtUtf8Index = ByteUtils.bytes2Int(bytes, start + u1, u2);

        resolveConstantPoolByIndex(bytes, mtUtf8Index);

        return start + u1 + u2;
    }

    /**
     * 字段、方法或接口常量解析。
     *
     * @param bytes
     * @param start
     */
    private void read_Interface_Method_Field_Name_Type(byte[] bytes, int start) {
        // `CONSTANT_Class_info`常量索引
        int classCountIndex = ByteUtils.bytes2Int(bytes, start + u1, u2);

        resolveConstantPoolByIndex(bytes, classCountIndex);

        // `CONSTANT_NameAndType`常量索引
        int nameTypeCountIndex = ByteUtils.bytes2Int(bytes, start + u1 + u2, u2);

        resolveConstantPoolByIndex(bytes, nameTypeCountIndex);
    }

    /**
     * 按给出索引序号解析常量池中第几个常量。
     *
     * @param bytes
     * @param index
     */
    public String resolveConstantPoolByIndex(byte[] bytes, int index) {
        int offsetIndex = searchConstantPoolByIndex(bytes, index);

        // 跳跃型解析常量池
        return resolveConstantPoolFromOffset(bytes, offsetIndex);
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
     * 从常量池中找出给定序号的常量，返回其偏移量位置offset给`resolveConstantPoolFromOffset`解析。
     *
     * 比如常量池一共66个常量，给出0x2A不是代表偏移量43，而是代表66个常量中第43个常量!!!
     *
     * @param bytes     类文件class字节码
     * @param index     常量池中的常量序号
     * @return          返回给定序号常量的索引值
     */
    public int searchConstantPoolByIndex(byte[] bytes, int index) {

        int cpc = getConstantPoolCount();

        int count = 0;

        // 全局游标偏移量(偏移量定位到第11字节，也就是常量池第一个常量)
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;

        // 遍历常量池所有常量，注意常量池从1开始!!!
        for (int i = 1; i < cpc; i++) {
            count++;

            // 计数从1开始
            if (index == count) {
                break;
            }

            // Step1：每一次遍历常量池，第一个读出的必是u1类型的tag
            int tag = ByteUtils.bytes2Int(bytes, offset, u1);

            if (ClassFileConstants.CONSTANT_Utf8_info_TAG == tag) {
                // 读取不定长的`CONSTANT_Utf8_info`的偏移字节量
                offset += CONSTANT_Utf8_Length(bytes, offset);
            } else {
                // 其他定长类型
                offset += CONSTANT_ITEM_LENGTH.get(tag);
            }

        } // for

        return offset;
    }

    /**
     * 从给定偏移位置解析常量池常量、通常如果是解析字符串、类名和UTF-8常量则可以接受返回值。
     * 
     * @param bytes     给定class类字节文件
     * @param start     起始解析索引位置
     * @return          需要字符串信息时的返回值
     */
    public String resolveConstantPoolFromOffset(byte[] bytes, int start) {
        String result = null;
        int tag = read_CONSTANT_POOL_INFO_TAG(bytes, start);
        switch (tag) {
            case ClassFileConstants.CONSTANT_Utf8_info_TAG:
                // 1.UTF-8编码的字符串
                result = read_CONSTANT_Utf8_info(bytes, start);
                break;
            case ClassFileConstants.CONSTANT_Class_info_TAG:
                // 7.类或接口的符号引用，指向一个类(将类名返回出来)
                read_CONSTANT_Class_info(bytes, start);
                break;
            case ClassFileConstants.CONSTANT_String_info_TAG:
                // 8.字符串类型字面量(将字符串信息返回出来)
                result = read_CONSTANT_String_info(bytes, start);
                break;
            default:
                break;
        }

        return result;
    }

}