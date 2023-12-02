/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * 160. 相交链表
 * 
 * https://leetcode.cn/problems/intersection-of-two-linked-lists/?envType=study-plan-v2&envId=top-100-liked
 * 
 * 采用双指针移动法。
 * 
 * @author Tony Zhao
 * @version $Id: GetIntersectionNode.java, v 0.1 2023-11-26 7:33 PM Tony Zhao Exp $$
 */
public class GetIntersectionNode {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        if (headA == null || headB == null) {
            // 没有任何相交的可能
            return null;
        }

        if (headA == headB) {
            return headA;
        }

        // Step1: 先对两个链表做差值统计
        int countA = 0;
        ListNode a1 = headA;
        ListNode a2 = headA;

        int countB = 0;
        ListNode b1 = headB;
        ListNode b2 = headB;

        while (a1 != null) {
            a1 = a1.next;
            countA++;
        }

        while (b1 != null) {
            b1 = b1.next;
            countB++;
        }

        // 算出结点差值
        int diff = countA >= countB ? countA - countB : countB - countA;

        // Step2: 多的链表先移动这么多差值节点
        if (countA >= countB) {
            while (diff > 0) {
                a2 = a2.next;
                diff--;
            }
        } else {
            while (diff > 0) {
                b2 = b2.next;
                diff--;
            }
        }

        // Step3: 先看看两个头指针是否相等了(一条链表直接相交于另一条头结点的情况)
        if (a2 == b2) {
            return a2;
        }

        // Step4: 往后继续看
        while (a2 != null && b2 != null) {

            a2 = a2.next;
            b2 = b2.next;

            if (a2 == b2) {
                return a2;
            }

        }

        // 没有相交直接返回null
        return null;
    }

}