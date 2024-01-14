/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * leetcode 2095. 删除链表的中间节点
 * 
 * https://leetcode.cn/problems/delete-the-middle-node-of-a-linked-list/
 * 
 * 1AC，一次性accept。
 * 
 * @author Tony Zhao
 * @version $Id: DeleteMiddle.java, v 0.1 2024-01-14 12:21 PM Tony Zhao Exp $$
 */
public class DeleteMiddle {

    public ListNode deleteMiddle(ListNode head) {
        // 空列表或者只有一个结点，删除后都是null
        if (head == null || head.next == null) {
            return null;
        }

        // 如果只有两个结点，抹掉末尾节点后返回头指针
        if (head.next.next == null) {
            head.next = null;
            return head;
        }

        ListNode p1, p2, p3;
        p1 = p2 = p3 = head;
        while (p2.next != null && p2.next.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
        }

        if (p2.next == null) {
            // 奇数节点
            while (p3.next != p1) {
                p3 = p3.next;
            }

            p3.next = p1.next;

        } else {
            // 偶数节点
            p1.next = p1.next.next;
        }

        return head;
    }

}