/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * leetcode 143. 重排链表
 * 
 * https://leetcode.cn/problems/reorder-list/
 * 
 * @author Tony Zhao
 * @version $Id: ReOrderList.java, v 0.1 2024-01-12 10:11 PM Tony Zhao Exp $$
 */
public class ReOrderList {

    /**
     * 将链表变成：L1->Ln->L2->Ln-1...
     * 
     * @param head 
     */
    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }

        // 先找到中间节点
        ListNode mid = middleNode(head);

        ListNode l1 = head;

        // 如果是奇数节点，如5个节点，一个在3，一个在5
        // 如果是偶数节点，如6个节点，一个在3，fast还在5，因为无法移动了，但是3也是mid
        // mid.next就是4这个节点
        ListNode l2 = mid.next;

        // 先把l1,l2这两个列表一分为二
        mid.next = null;

        // 反转l2链表
        l2 = reverseList(l2);

        // 合并链表
        mergeList(l1, l2);
    }

    /**
     * 找到链表的中间节点。
     * 
     * @param head 
     * @return
     */
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        // 直接判断2次不等于null，省的while中还进行if再判断
        // 如果是奇数节点，如5个节点，一个在3，一个在5
        // 如果是偶数节点，如6个节点，一个在3，fast还在5，因为无法移动了，但是3也是mid
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 反转链表基本功。
     * 
     * @param head 
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {

            // 声明下个要处理的结点为临时变量节点
            ListNode nextTemp = curr.next;

            // 指针反指
            curr.next = prev;

            // 移动指针prev到curr的位置
            prev = curr;

            // 下一个结点在临时变量中，给到curr
            curr = nextTemp;

        }

        return prev;
    }

    /**
     * 交替合并两个长度相同的链表。
     * 
     * @param l1 
     * @param l2
     */
    public void mergeList(ListNode l1, ListNode l2) {
        ListNode l1_tmp;
        ListNode l2_tmp;

        // while 两个链表都没有到尾指针空
        while (l1 != null && l2 != null) {

            // 这种循环做一组

            // 两个要处理的临时节点
            l1_tmp = l1.next;
            l2_tmp = l2.next;

            // 挂接l2到l1上
            l1.next = l2;
            // l1原来的第二个节点给到l1
            l1 = l1_tmp;

            // l1现在是第二个节点了，挂到l2头结点后
            l2.next = l1;
            // l2原来的第二个节点给到l2
            l2 = l2_tmp;
        }
    }

}