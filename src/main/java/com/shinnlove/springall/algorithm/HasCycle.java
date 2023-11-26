/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * 141. 环形链表
 * 
 * https://leetcode.cn/problems/linked-list-cycle/description/
 * 
 * @author Tony Zhao
 * @version $Id: HasCycle.java, v 0.1 2023-11-26 12:12 PM Tony Zhao Exp $$
 */
public class HasCycle {

    public boolean hasCycle(ListNode head) {

        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;

        ListNode p1 = dummyHead, p2 = dummyHead;

        while (p1 != null) {
            // 前面指针先移动一步
            p1 = p1.next;

            // 前面指针先移动一步后如果为null、代表已经到达尾指针，不存在有环
            if (p1 == null) {
                return false;
            }

            // 同时移动一步
            p1 = p1.next;
            p2 = p2.next;

            // 如果相等，刚好成环
            if (p1 == p2) {
                return true;
            }
        }

        return false;
    }

}