/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * 19. 删除链表的倒数第N个结点。
 * 
 * https://leetcode.cn/problems/remove-nth-node-from-end-of-list/description/
 * 
 * @author Tony Zhao
 * @version $Id: RemoveNthFromEnd.java, v 0.1 2023-11-23 9:48 PM Tony Zhao Exp $$
 */
public class RemoveNthFromEnd {

    /**
     * 这里用双指针同步进行的方式只遍历一遍链表，属于进阶的优化算法。
     * 
     * @param head      链表头结点
     * @param n         要删除的元素个数
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {

        // 创建一个哑结点，方便统一链表操作
        ListNode dummy = new ListNode(0, head);

        // first指针指向head，哪怕是null
        ListNode first = head;

        // second在差距未达到n之前，是指向哑结点的
        ListNode second = dummy;

        // 先first指针自己走
        for (int i = 0; i < n; ++i) {
            first = first.next;
            if (first == null) {
                // 如果给出的数量大于链表的长度、break，
                // 无所谓，因为second动也没动
                break;
            }
        }

        // 下面这个while是给链表还没走完的first和second同步走的
        while (first != null) {
            first = first.next;
            second = second.next;
        }

        // 开始删除节点
        // second.next是要删除节点、second.next.next是要删除节点的下个节点
        // 给到second节点的后继
        if (second.next == null) {
            // 一个节点都没有
            return head;
        }
        second.next = second.next.next;

        // 返回哑结点后的那个节点地址作为头指针
        return dummy.next;

    }

}