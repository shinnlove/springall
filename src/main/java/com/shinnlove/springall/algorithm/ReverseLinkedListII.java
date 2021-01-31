/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * LeetCode 92. Reverse a linked list from m to n.
 * <p>
 * wiki: https://leetcode-cn.com/problems/reverse-linked-list-ii/.
 *
 * @author Tony Zhao
 * @version $Id: ReverseLinkedListII.java, v 0.1 2021-01-31 3:50 PM Tony Zhao Exp $$
 */
public class ReverseLinkedListII {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;

        ReverseLinkedListII rl2 = new ReverseLinkedListII();
        ListNode head = rl2.reverseBetween(n1, 2, 4);
        while (head != null) {
            System.out.println("Node is " + head.val);
            head = head.next;
        }
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (m < 0 || m >= n) {
            // m equals n means there is no reverse
            return head;
        }

        ListNode p1, sp, ep, prev;
        p1 = sp = ep = prev = head;

        int startNode = 1;
        int count = 1;
        while (p1 != null) {
            // focus start position
            if (count == m) {
                sp = p1;
                startNode = count;
            }

            // focus end position
            if (count == n) {
                ep = p1;
            }

            p1 = p1.next;
            count++;

        } // while

        if (--count < n) {
            // invalid input n
            return head;
        }

        // 1->2->3->4->5->NULL, m = 2, n = 4
        //    |     |
        //    sp    ep
        // 1 -> 2 <- 3 <- 4   5
        // 1-> 4 -> 3 -> 2 -> 5 -> NULL 

        // sp.prev.next = ep
        // cur = sp.next
        // sp.next = null
        for (int i = 1; i < startNode - 1; i++) {
            prev = prev.next;
        }

        // reverse from the next node of start point
        ListNode targetSuc = sp;
        ListNode cur = sp.next;
        while (cur != ep) {
            ListNode tempNext = cur.next;
            cur.next = targetSuc;
            targetSuc = cur;
            cur = tempNext;
        }

        // step1. store last tail temporarily
        ListNode tail = ep.next;
        // step2. link ep to its prev in reverse order, fatal step!
        ep.next = targetSuc;
        // step2. link last tail to start tail
        sp.next = tail;
        if (sp == head) {
            head = ep;
        } else {
            prev.next = ep;
        }

        return head;
    }

}