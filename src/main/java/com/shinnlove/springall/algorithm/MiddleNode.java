/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * leetcode 876. 链表的中间结点
 * 
 * https://leetcode.cn/problems/middle-of-the-linked-list/
 * 
 * @author Tony Zhao
 * @version $Id: MiddleNode.java, v 0.1 2024-01-13 2:29 PM Tony Zhao Exp $$
 */
public class MiddleNode {

    /**
     * 双指针移动，简单判断法。
     * 
     * @param head 
     * @return
     */
    public ListNode middleNode(ListNode head) {

        if (head == null) {
            return null;
        }

        ListNode p1, p2;
        p1 = p2 = head;
        while (p2.next != null && p2.next.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
        }

        if (p2.next == null) {
            // 奇数节点走到末尾了
            return p1;
        }

        return p1.next;
    }

}