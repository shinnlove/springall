/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * LeetCode 160.
 * 
 * wiki: https://leetcode-cn.com/problems/intersection-of-two-linked-lists/submissions/.
 * 
 * @author Tony Zhao
 * @version $Id: IntersectionOfTwoLinkedList.java, v 0.1 2021-01-24 11:58 PM Tony Zhao Exp $$
 */
public class IntersectionOfTwoLinkedList {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null) {
            return headA;
        }
        if (headB == null) {
            return headB;
        }

        ListNode p1, p2, pp1, pp2;
        p1 = pp1 = headA;
        p2 = pp2 = headB;

        int count1 = 1;
        int count2 = 1;
        while (p1.next != null) {
            p1 = p1.next;
            count1++;
        }
        while (p2.next != null) {
            p2 = p2.next;
            count2++;
        }

        int diff = count1 - count2;
        if (diff > 0) {
            // list1 longer than list2, first move pp1
            while (diff-- > 0) {
                pp1 = pp1.next;
            }
        } else {
            // list1 shorter than list2, first move pp2
            while (diff++ < 0) {
                pp2 = pp2.next;
            }
        }

        if (pp1 == pp2) {
            return pp1;
        }

        while (pp1.next != null) {
            pp1 = pp1.next;
            pp2 = pp2.next;
            if (pp1 == pp2) {
                return pp1;
            }
        }

        return null;
    }

}