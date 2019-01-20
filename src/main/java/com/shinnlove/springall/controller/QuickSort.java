/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

/**
 * 快速排序算法。
 *
 * @author shinnlove.jinsheng
 * @version $Id: QuickSort.java, v 0.1 2019-01-19 12:52 shinnlove.jinsheng Exp $$
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arrays = new int[] { 2, 19, 31, 56, 127, 120, 15, 33, 79, 92 };

        print(arrays);

        sort(arrays, 0, arrays.length - 1);

        print(arrays);
    }

    /**
     * array[low] = key 是直接取最小。
     *
     * 三数取中：A：array[low]；B：array[high]；C：array[(low+high) / 2]。
     * ABC相加除以3取中。
     *
     * @param array     
     * @param low       
     * @param high
     * @return          最终返回high指针所在位置
     */
    public static int partition(int[] array, int low, int high) {
        //固定的切分方式
        int pivot = array[low];
        while (low < high) {
            while (low < high && array[high] >= pivot) {
                //从后半部分向前扫描
                high--;
            }
            array[low] = array[high];

            while (low < high && array[low] <= pivot) {
                // 从前半部分向后扫描
                low++;
            }
            array[high] = array[low];
        }
        array[high] = pivot;
        return high;
    }

    /**
     * 分治。
     *
     * @param array
     * @param low
     * @param high
     */
    public static void sort(int[] array, int low, int high) {
        if (low >= high) {
            return;
        }
        int index = partition(array, low, high);
        sort(array, low, index - 1);
        sort(array, index + 1, high);
    }

    public static void print(int[] array) {
        int len = array.length;
        if (len > 0) {
            for (int i = 0; i < len - 1; i++) {
                System.out.print(array[i] + " ");
            }
            System.out.print(array[len - 1]);
            System.out.println();
        }
    }

}
