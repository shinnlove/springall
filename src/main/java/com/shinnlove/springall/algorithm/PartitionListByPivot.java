/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * 面试题 02.04.得物. 线性表、分割链表
 *
 * https://leetcode.cn/problems/partition-list-lcci/description/?company_slug=de-wu
 *
 * @author Tony Zhao
 * @version $Id: PartitionListByPivot.java, v 0.1 2023-11-23 11:23 AM Tony Zhao Exp $$
 */
public class PartitionListByPivot {

    public ListNode partition(ListNode head, int x) {

        // 创建哑结点、方便处理head==null
        ListNode small = new ListNode(0);
        // 尾指针为了链接大链表
        ListNode smallHead = small;

        // 创建哑结点、方便处理head==null
        ListNode large = new ListNode(0);
        // 尾指针为了切断链接，置为null
        ListNode largeHead = large;

        // 直接将head指针作为遍历指针来用，节省空间开销
        while (head != null) {
            if (head.val < x) {

                // 只关注小于值的结点挂到小链表后边去
                small.next = head;
                small = small.next;
            } else {

                // 大于等于的直接挂到大链表后边去
                large.next = head;
                large = large.next;
            }

            // 继续遍历
            head = head.next;
        }

        // 后边链表的结点尾指针置为null
        large.next = null;

        // 将后半段拼接到前半段
        small.next = largeHead.next;

        // 直接返回哑结点的后继、方便head == null的时候统一处理
        return smallHead.next;

    }

}