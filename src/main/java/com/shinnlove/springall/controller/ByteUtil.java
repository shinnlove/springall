/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

/**
 * @author shinnlove.jinsheng
 * @version $Id: ByteUtil.java, v 0.1 2019-05-13 16:40 shinnlove.jinsheng Exp $$
 */
public class ByteUtil {

    public static String byteToBit(byte b) {
        return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) + (byte) ((b >> 5) & 0x1)
               + (byte) ((b >> 4) & 0x1) + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
               + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    public static void main(String[] args) {
        //        byte a = 0x35; // 0011 0101, 十六进制0x35=十进制53
        //
        //        byte b = 0x8; // 0000 1000，十六进制0x8
        //
        //        // 理论上 a|b是 0011 1101，十六进制0x3d=十进制61，刚好53+8=61
        //
        //        a = (byte) (a | 0x1 << 3);
        //
        //        System.out.println(byteToBit(a));
        //
        //        System.out.println(a); // 53

        byte c = 0x00;

        byte[] bb = new byte[2];

        //        System.out.println(bb[0]);

        int number = 175;

        number2bytes(number, 0, bb);

        //        System.out.println(bb[0]);

    }

    public static void number2bytes(int num, int index, byte[] ips) {

        byte t = (byte) 0x80;

        byte b = 0x01;

        b = (byte) (b | (t & 0xff));

        System.out.println(~t);
        System.out.println(b);

        for (int i = 7; i >= 0; i--) {

            int r = (num >>> i & 1);
            System.out.print(r);

            if (r == 1) {

                if (i == 7) {
                    b = (byte) (b & 0x80);
                } else {
                    b = (byte) ~(b | 0x1 << i);
                }

            }
        }

        System.out.println();
        System.out.println(b);

    }

}