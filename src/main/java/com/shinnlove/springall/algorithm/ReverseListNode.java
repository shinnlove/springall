/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * 206. Reverse List Node.
 * 
 * https://leetcode.cn/problems/reverse-linked-list/description/
 * 
 * 单链表反转链表，这里不额外开辟新链表进行拷贝。
 * 使用一个临时结点存储当前结点的下一个阶段、用之前的一个指针存储第一次开始后标记的结点，
 * 然后在当前操作位置对前、中、后三个结点进行指针反转。
 * 但是不要忘记head结点给予的新结点尾指针next一定要变成NULL，因为是反转后新链表的尾指针。
 * 
 * @author Tony, Zhao
 * @version $Id: ReverseListNode.java, v 0.1 2020-09-08 2:22 PM Tony, Zhao Exp $$
 */
public class ReverseListNode {

    public ListNode reverseList(ListNode head) {

        if (head == null) {
            return head;
        }

        // 一前一后两个指针
        ListNode prev = head;
        ListNode cur = head.next;

        // 头节点的尾指针先变null
        prev.next = null;

        // 只要还有结点
        while (cur != null) {

            // 无论是否还有下一个节点，先存着
            ListNode temp = cur.next;

            // 进行链表的反转，上一个节点是当前节点的下一个节点
            cur.next = prev;

            // prev指针前移到cur
            prev = cur;

            // cur指针前移到下个节点、也就是最先保存的那个节点
            cur = temp;
        }

        // 最后返回后指针指向的尾结点作为新头结点，此时cur一定为null
        return prev;
    }

}