/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * 445. 两数相加II
 * 
 * leetcode: https://leetcode.cn/problems/add-two-numbers-ii/
 * 
 * @author Tony Zhao
 * @version $Id: AddTwoNumbers.java, v 0.1 2024-01-13 3:56 PM Tony Zhao Exp $$
 */
public class AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        if (l1 == null && l2 == null) {
            return null;
        }

        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }

        ListNode num1 = reverseList(l1);
        ListNode num2 = reverseList(l2);

        int extra = 0;
        ListNode p1 = num1;
        ListNode p2 = num2;

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (p1 != null && p2 != null) {

            int sum = p1.val + p2.val + extra;
            extra = 0;

            if (sum > 9) {
                int remain = sum % 10;
                extra = (sum - remain) / 10;
                sum = sum % 10;
            }

            // 连接并挂载
            ListNode newNode = new ListNode(sum);
            current.next = newNode;
            current = newNode;

            p1 = p1.next;
            p2 = p2.next;

        }

        if (p1 != null && p2 == null) {
            while (p1 != null) {

                int sum = p1.val + extra;
                extra = 0;

                if (sum > 9) {
                    int remain = sum % 10;
                    extra = (sum - remain) / 10;
                    sum = sum % 10;
                }

                // 连接并挂载
                ListNode newNode = new ListNode(sum);
                current.next = newNode;
                current = newNode;

                p1 = p1.next;
            }
        }

        if (p1 == null && p2 != null) {
            while (p2 != null) {
                int sum = p2.val + extra;
                extra = 0;

                if (sum > 9) {
                    int remain = sum % 10;
                    extra = (sum - remain) / 10;
                    sum = sum % 10;
                }

                // 连接并挂载
                ListNode newNode = new ListNode(sum);
                current.next = newNode;
                current = newNode;

                p2 = p2.next;
            }
        }

        if (extra > 0) {
            // 连接并挂载
            ListNode newNode = new ListNode(extra);
            current.next = newNode;
            newNode.next = null;
        }

        return reverseList(dummy.next);
    }

    public ListNode reverseList(ListNode head) {

        if (head == null) {
            return null;
        }

        ListNode prev = head;
        ListNode cur = head.next;

        prev.next = null;

        while (cur != null) {

            ListNode temp = cur.next;

            cur.next = prev;

            prev = cur;

            cur = temp;
        }

        return prev;
    }

}