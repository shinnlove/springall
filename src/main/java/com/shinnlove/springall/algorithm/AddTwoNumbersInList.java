/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * LeetCode 2.
 *
 * wiki: https://leetcode-cn.com/problems/add-two-numbers/.
 *
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Example:
 *
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 *
 * @author shinnlove.jinsheng
 * @version $Id: Solution2nd.java, v 0.1 2019-04-08 13:32 shinnlove.jinsheng Exp $$
 */
public class AddTwoNumbersInList {

    public static void main(String[] args) {

        int[] nums1 = new int[] { 2, 4, 3 };
        int[] nums2 = new int[] { 5, 6, 4 };

        AddTwoNumbersInList s = new AddTwoNumbersInList();

        ListNode result = s.addTwoNumbers(s.createList(nums1), s.createList(nums2));

        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        // 结果链表头指针
        ListNode head = new ListNode(0);
        head.next = null;

        // 当前链表指针
        ListNode current = head;

        // java必须统计头节点
        int count = 0;

        // 进位
        int addition = 0;

        while (l1 != null || l2 != null) {

            int num1 = 0;
            int num2 = 0;

            // l1链表结束了
            if (l1 != null) {
                num1 = l1.val;
            }

            // l2链表结束了
            if (l2 != null) {
                num2 = l2.val;
            }

            // 叠加
            int sum = num1 + num2 + addition;

            // 位数与余数
            int number = sum % 10;
            addition = sum / 10;

            if (count == 0) {
                // 头节点特殊处理
                head.val = number;
            } else {
                // 开启新结点
                ListNode newNode = new ListNode(number);
                newNode.next = null;

                // 挂接结点
                current.next = newNode;

                // 游标定位
                current = newNode;
            }

            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }

            count += 1;

        } // end while

        if (addition > 0) {
            // 开启新结点
            ListNode newNode = new ListNode(addition);
            newNode.next = null;
            current.next = newNode;
        }

        return head;
    }

    public ListNode createList(int[] nums) {
        ListNode head = new ListNode(0);
        head.next = null;

        ListNode current = head;

        if (nums.length == 0) {
            return head;
        }

        for (int i = 0; i < nums.length; i++) {
            if (0 == i) {
                current.val = nums[i];
            } else {
                ListNode newNode = new ListNode(nums[i]);
                newNode.next = null;
                current.next = newNode;
                current = newNode;
            }
        }

        return head;
    }

}