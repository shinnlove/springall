/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * 24. 两两交换链表中的结点。
 * 
 * https://leetcode.cn/problems/swap-nodes-in-pairs/description/?company_slug=bytedance
 * 
 * @author Tony Zhao
 * @version $Id: SwapPairs.java, v 0.1 2023-11-21 8:17 PM Tony Zhao Exp $$
 */
public class SwapPairs {

    public ListNode swapPairs(ListNode head) {

        // 空结点、或者只有一个单结点，直接返回不做翻转
        if (head == null || head.next == null) {
            return head;
        }

        // 定义一个指针指到头结点后方结点、也就是第二个节点
        ListNode newHead = head.next;

        // 将第三个结点、也就是第二个结点的尾指针(哪怕为空)给递归函数形参进行递归翻转
        // 返回一个翻转后的新列表头结点
        // 因为当前处理的结点也需要翻转，所以这个返回的结点挂在头结点后
        head.next = swapPairs(newHead.next);

        // 第二个结点指向原来的头结点
        newHead.next = head;

        // 返回第二个结点头指针作为新链表的头结点
        return newHead;

    }

}