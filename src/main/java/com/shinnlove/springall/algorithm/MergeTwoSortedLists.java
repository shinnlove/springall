/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * @author Tony, Zhao
 * @version $Id: MergeTwoSortedArrays.java, v 0.1 2020-09-14 4:09 PM Tony, Zhao Exp $$
 */
public class MergeTwoSortedLists {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }

        ListNode head = new ListNode(0);
        ListNode current = head;
        ListNode left = l1;
        ListNode right = l2;
        while (left != null && right != null) {
            if (left.val <= right.val) {
                ListNode newNode = new ListNode(left.val);
                current.next = newNode;
                current = newNode;
                left = left.next;
            } else {
                ListNode newNode = new ListNode(right.val);
                current.next = newNode;
                current = newNode;
                right = right.next;
            }
        }

        while (left != null) {
            ListNode newNode = new ListNode(left.val);
            current.next = newNode;
            current = newNode;
            left = left.next;
        }

        while (right != null) {
            ListNode newNode = new ListNode(right.val);
            current.next = newNode;
            current = newNode;
            right = right.next;
        }

        current.next = null;

        return head.next;
    }

}