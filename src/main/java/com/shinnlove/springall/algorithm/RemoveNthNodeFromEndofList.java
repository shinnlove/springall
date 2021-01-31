/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * LeetCode 19.
 * 
 * wiki: https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/.
 * 
 * @author Tony Zhao
 * @version $Id: RemoveNthNodeFromEndofList.java, v 0.1 2021-01-31 11:09 AM Tony Zhao Exp $$
 */
public class RemoveNthNodeFromEndofList {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return head;
        }

        ListNode p1, p2, p3;
        p1 = p2 = p3 = head;
        int count = 1;
        while (p1 != null && count <= n) {
            p1 = p1.next;
            count++;
        }

        if (p1 == null && count < n) {
            return head;
        }

        int flag = 0;
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
            flag++;
            count++;
            if (flag > 1) {
                p3 = p3.next;
            }
        }

        if (p2 == p3) {
            return head.next;
        }

        p3.next = p2.next;

        return head;
    }

}