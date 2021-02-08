/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * LeetCode .
 * 
 * @author Tony Zhao
 * @version $Id: Solution.java, v 0.1 2021-02-04 1:27 PM Tony Zhao Exp $$
 */
public class SmallestMissingPositiveInteger {

    public static void main(String[] args) {
        int[] A = new int[] { 1, 3, 6, 4, 1, 2 };
        int[] A2 = new int[] { 1, 2, 3 };
        int[] A3 = new int[] { -1, -3 };
        SmallestMissingPositiveInteger s = new SmallestMissingPositiveInteger();
        int number = s.solution(A);
        int number2 = s.solution(A2);
        int number3 = s.solution(A3);
        System.out.println("First positive number is " + number);
        System.out.println("First positive number2 is " + number2);
        System.out.println("First positive number3 is " + number3);
    }

    public int solution(int[] A) {
        // write your code in Java SE 8
        int len = A.length;
        if (len == 0) {
            return 1;
        }

        quickSort(A, 0, len - 1);
        int count = 1;
        int index = binarySearchPositive(A);
        for (int i = index + 1; i < len; i++) {
            if (i > 0 && A[i] == A[i - 1]) {
                continue;
            }
            if (A[i] == count) {
                count++;
                continue;
            }

            return A[count - 1] + 1;
        }

        if (A[len - 1] > 0) {
            return A[len - 1] + 1;
        } else {
            return 1;
        }
    }

    public int binarySearchPositive(int[] A) {
        int start = 0;
        int end = A.length - 1;

        while (start <= end) {
            int mid = (start + end) / 2;
            if (A[mid] < 0) {
                start = mid + 1;
            } else if (A[mid] > 0) {
                end = mid - 1;
            } else {
                return mid;
            }
        }

        return end;
    }

    public void quickSort(int[] arr, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }
        int pivotIndex = partition(arr, startIndex, endIndex);
        quickSort(arr, startIndex, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, endIndex);
    }

    private int partition(int[] arr, int startIndex, int endIndex) {
        int pivot = arr[startIndex];
        int left = startIndex;
        int right = endIndex;

        while (left != right) {
            while (left < right && arr[right] > pivot) {
                right--;
            }
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            if (left < right) {
                int p = arr[left];
                arr[left] = arr[right];
                arr[right] = p;
            }
        }

        arr[startIndex] = arr[left];
        arr[left] = pivot;

        return left;
    }

}