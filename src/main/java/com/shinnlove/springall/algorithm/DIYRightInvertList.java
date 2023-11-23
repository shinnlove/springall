/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * Coding testing by company bytedance tiktok international short video department.
 *
 * @author Tony Zhao
 * @version $Id: DIYRightInvertList.java, v 0.1 2023-11-22 2:45 PM Tony Zhao Exp $$
 */
public class DIYRightInvertList {

    public ListNode rightInvertList(ListNode head, int k) {

        // 只有一个结点、无结点、空翻转
        if (head == null || head.next == null || k <= 0) {
            return head;
        }

        // 先统计一下链表长度

        int nodeCount = 1;
        ListNode scan = head;

        while (scan.next != null) {
            scan = scan.next;
            nodeCount++;
        }

        /**
         * p1, ..., last -> null
         */

        // 先定位到要翻转的结点
        ListNode splitHead = head;
        for (int i = 0; i < nodeCount - k; i++) {
            splitHead = splitHead.next;
        }

        // node = 5, k = 2, diff = 3
        // 1 -> 2 -> 3 -> 4 -> 5 -> null
        //           p1
        // head -> 链表1
        // p1 -> 链表2
        // 链表2翻转, .next -> 链表1反转

        ListNode newHead = reverseLink(splitHead);

        // no need to reverse
//        ListNode latterLinkHead = reverseLink(splitHead);

        // concat

        ListNode scan2 = newHead;
        while (scan2.next != null) {
            scan2 = scan2.next;
        }

        scan2.next = head;

        // last step. cut original link tail
        ListNode sweepHead = head;
        for (int i = 0; i < nodeCount - k - 1; i++) {
            sweepHead = sweepHead.next;
        }
        // 解除环
        sweepHead.next = null;

        return newHead;
    }

    public ListNode reverseLink(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        // 1         ->    2    ->  3
        // head,p1,      p2         p3(null)

        // p1 <---  p2
        //        p1, p2

        // 1   ->   2  ->  3 -> null
        // null <-  1 <- 2, 3 -> null
        // null <-  1 <- 2 <- 3

        ListNode p1 = head;
        ListNode p2 = p1.next;
        ListNode p3 = p2.next;

        int count = 0;

        while (p2 != null) {

            // reverse
            p2.next = p1;

            if (count == 0) {
                // init once for tail pointer
                p1.next = null;
            }

            // sync
            p1 = p2;

            p2 = p3;

            if (p3 != null && p3.next != null) {
                // avoid NPE
                p3 = p3.next;
            }

            count++;
        }

        return p1;
    }

    public static void main(String[] args) {

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;

        DIYRightInvertList cc = new DIYRightInvertList();

        ListNode newLinkHead = cc.rightInvertList(node1, 2);
        ListNode pp = newLinkHead;
        while (pp != null) {
            System.out.println(pp.val);
            pp = pp.next;
        }

    }


}