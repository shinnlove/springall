/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * @author Tony, Zhao
 * @version $Id: RemoveDuplicateFromSortedList.java, v 0.1 2020-09-15 5:05 PM Tony, Zhao Exp $$
 */
public class RemoveDuplicateFromSortedList {

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }

        int temp = head.val;
        ListNode prev = head;
        ListNode current = head.next;
        while (current != null) {
            //     ↓       ↓       ↓
            //    prev   current  next
            if (current.val == temp) {
                // need remove
                prev.next = current.next;
                current = prev.next;
            } else {
                // no need remove, change number
                temp = current.val;
                prev = current;
                current = current.next;
            }
        }

        return head;
    }

}