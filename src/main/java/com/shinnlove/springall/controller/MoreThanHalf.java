/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

/**
 * 超过数组中一半的数字。
 *
 * @author shinnlove.jinsheng
 * @version $Id: MoreThanHalf.java, v 0.1 2019-01-19 15:52 shinnlove.jinsheng Exp $$
 */
public class MoreThanHalf {

    public static void main(String[] args) {
        int[] array = new int[] { 1, 2, 3, 2, 2, 2, 5, 4, 2 };

        System.out.println(pickUpMoreThanHalf(array));
    }

    /**
     * 挑出数组中超过一半的数字。
     *
     * @param array
     * @return
     */
    public static int pickUpMoreThanHalf(int[] array) {
        int len = array.length;
        if (len <= 0) {
            return 0;
        }

        int count = 1;
        int value = array[0];

        for (int i = 1; i < len; i++) {
            // 处理当前数
            if (array[i] == value) {
                count++;
            } else {
                count--;
            }

            // 归零重新计数
            if (count == 0) {
                value = array[i];
                count = 1;
            }

            System.out.println(count);
        } // end for

        return value;
    }

}
