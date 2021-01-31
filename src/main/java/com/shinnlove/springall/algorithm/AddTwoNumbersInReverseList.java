/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * LeetCode 2.
 * 
 * wiki: https://leetcode-cn.com/problems/add-two-numbers/.
 * 
 * @author Tony Zhao
 * @version $Id: AddTwoNumbersInReverseList.java, v 0.1 2021-01-05 6:41 PM Tony Zhao Exp $$
 */
public class AddTwoNumbersInReverseList {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // judgement
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }

        // initialize
        ListNode l1current = l1;
        ListNode l2current = l2;

        ListNode newHead = null;
        ListNode newCurrent = null;
        int tempNumber = 0;

        while (l1current != null && l2current != null) {
            int total = tempNumber + l1current.val + l2current.val;
            if (total >= 10) {
                total = total - 10;
                tempNumber = 1;
            } else {
                tempNumber = 0;
            }
            ListNode newNode = new ListNode(total);
            if (newCurrent == null) {
                // first node
                newHead = newCurrent = newNode;
            } else {
                newCurrent.next = newNode;
                newCurrent = newNode;
            }
            l1current = l1current.next;
            l2current = l2current.next;
        }

        while (l1current != null) {
            int total = tempNumber + l1current.val;
            if (total >= 10) {
                total = total - 10;
                tempNumber = 1;
            } else {
                tempNumber = 0;
            }
            ListNode newNode = new ListNode(total);
            newCurrent.next = newNode;
            newCurrent = newNode;
            l1current = l1current.next;
        }

        while (l2current != null) {
            int total = tempNumber + l2current.val;
            if (total >= 10) {
                total = total - 10;
                tempNumber = 1;
            } else {
                tempNumber = 0;
            }
            ListNode newNode = new ListNode(total);
            newCurrent.next = newNode;
            newCurrent = newNode;
            l2current = l2current.next;
        }

        // last exceed position
        if (tempNumber != 0) {
            ListNode newNode = new ListNode(tempNumber);
            newCurrent.next = newNode;
        }

        return newHead;
    }

}