/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * @author Tony, Zhao
 * @version $Id: ReverseListNode.java, v 0.1 2020-09-08 2:22 PM Tony, Zhao Exp $$
 */
public class ReverseListNode {

    public class ListNode {
        int      val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode prev = head;
        ListNode cur = head.next;
        prev.next = null;
        while (cur != null) {
            // 无论是否还有下一个节点，先存着
            ListNode temp = cur.next;
            // 进行链表的反转，上一个节点是当前节点的下一个节点
            cur.next = prev;
            // prev指针前移
            prev = cur;
            // cur指针前移到下个节点
            cur = temp;
        }
        return prev;
    }

}