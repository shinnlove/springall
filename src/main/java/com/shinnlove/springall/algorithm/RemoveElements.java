/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * leetcode 203. 移除链表元素
 * 
 * https://leetcode.cn/problems/remove-linked-list-elements/
 * 
 * @author Tony Zhao
 * @version $Id: RemoveElements.java, v 0.1 2024-01-14 1:31 PM Tony Zhao Exp $$
 */
public class RemoveElements {

    /**
     * 使用哑结点的移除链表节点方法。
     * 
     * @param head 
     * @param val
     * @return
     */
    public ListNode removeElements(ListNode head, int val) {

        // 如果用了dummy结点，其实不用判断head是否为null，因为不会进入循环，也统一处理了
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode p = dummy;
        while (p.next != null) {

            if (p.next.val == val) {
                p.next = p.next.next;
            } else {
                p = p.next;
            }

        }

        return dummy.next;
    }

}