/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * Leetcode 83. 删除排序链表中的重复元素
 * 
 * 但是这道题目采用了交换值的方式，这明显不太对，可以忽略这个解答。
 * 
 * @author Tony, Zhao
 * @version $Id: RemoveDuplicateFromSortedList.java, v 0.1 2020-09-15 5:05 PM Tony, Zhao Exp $$
 */
public class RemoveDuplicateFromSortedList {

    @Deprecated
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