/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

/**
 * @author shinnlove.jinsheng
 * @version $Id: NumberUtil.java, v 0.1 2019-05-13 15:23 shinnlove.jinsheng Exp $$
 */
public class NumberUtil {

    public static void main(String[] args) {
        String ip = "172.168.5.1";
        checkAndCalculate(ip);
    }

    public static void checkAndCalculate(String ip) {
        // 空格校验省略

        byte[] ips = new byte[4];
        String[] sections = ip.split("\\.");
        for (int i = 0; i < sections.length; i++) {
            number2bytes(Integer.valueOf(sections[i]), i, ips);
        }

        System.out.println(bytes2int(ips));
    }

    public static void number2bytes(int num, int index, byte[] ips) {
        for (int i = 7; i >= 0; i--) {
            if ((num >>> i & 1) == 1) {
                ips[index] = (byte) (ips[index] | 0x1 << i);
            }
        }
    }

    public static int bytes2int(byte[] bytes) {
        int a = (bytes[0] & 0xff) << 24;
        int b = (bytes[1] & 0xff) << 16;
        int c = (bytes[2] & 0xff) << 8;
        int d = (bytes[3] & 0xff);
        return a | b | c | d;
    }

}