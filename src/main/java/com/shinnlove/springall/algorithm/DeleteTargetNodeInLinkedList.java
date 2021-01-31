/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * Sword to offer 18. Delete target node in a given linked list.
 * 
 * wiki: https://leetcode-cn.com/problems/shan-chu-lian-biao-de-jie-dian-lcof/.
 * 
 * @author Tony Zhao
 * @version $Id: DeleteTargetNodeInLinkedList.java, v 0.1 2021-01-31 3:17 PM Tony Zhao Exp $$
 */
public class DeleteTargetNodeInLinkedList {

    public ListNode deleteNode(ListNode head, int val) {
        ListNode p1, p2;
        p1 = p2 = head;

        if (head == null) {
            return head;
        }

        int count = 1;
        while (val != p1.val && p1 != null) {
            p1 = p1.next;
            count++;
            if (count > 2) {
                p2 = p2.next;
            }
        }

        if (p1 == null) {
            return head;
        }

        if (p1 == head) {
            return head.next;
        }

        p2.next = p1.next;

        return head;
    }

}