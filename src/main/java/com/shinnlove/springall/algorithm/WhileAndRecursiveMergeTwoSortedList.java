/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * head1: 1 -> 3 -> 7
 * head2: 2 -> 5 -> 6
 * 
 * @author Tony Zhao
 * @version $Id: Solutions.java, v 0.1 2021-01-28 1:56 PM Tony Zhao Exp $$
 */
public class WhileAndRecursiveMergeTwoSortedList {

    public ListNode mergeTwoList(ListNode l1, ListNode l2) {

        // 1 -> 3 -> 7 -> NULL
        // |      
        // h1
        // c1

        // 2 -> 5 -> 6 -> NULL

        // 1 -> 2 -> 3 -> 5 -> 6 -> 7

        // judge

        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        ListNode newHead = new ListNode(-1);
        ListNode currentAppend = newHead;

        ListNode current1 = l1;
        ListNode current2 = l2;

        //        doMerge(newCurrent, l1, l2)

        while (current1 != null || current2 != null) {

            int number1 = current1 == null ? Integer.MAX_VALUE : current1.val;
            int number2 = current2 == null ? Integer.MAX_VALUE : current2.val;

            if (number1 <= number2) {
                currentAppend.next = current1;
                current1 = current1.next;
            } else {
                currentAppend.next = current2;
                current2 = current2.next;
            }

            currentAppend = currentAppend.next;
        }

        return newHead.next;
    }

    private void doMerge(ListNode newCurrent, ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return;
        }

        int number1 = l1 == null ? Integer.MAX_VALUE : l1.val;
        int number2 = l2 == null ? Integer.MAX_VALUE : l2.val;

        if (number1 <= number2) {
            newCurrent.next = l1;
            l1 = l1.next;
        } else {
            newCurrent.next = l2;
            l2 = l2.next;
        }
        newCurrent = newCurrent.next;

        doMerge(newCurrent, l1, l2);
    }

}