/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * LeetCode 61.
 * 
 * Warning: this problem has lots of edge cases.
 * 
 * Absolutely accepted by myself, here is the official solution for this problem:
 * https://leetcode-cn.com/problems/rotate-list/solution/xuan-zhuan-lian-biao-by-leetcode/
 * 
 * @author Tony Zhao
 * @version $Id: RotateList.java, v 0.1 2021-01-18 11:34 PM Tony Zhao Exp $$
 */
public class RotateList {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        l1.next = l2;
        RotateList rl = new RotateList();
        rl.rotateRight(l1, 1);
    }

    /**
     * null list no need to move
     * list length equals 1 no need to move
     * k equals to multiple times than length no need to move
     * if moves, need to redefine new head node and concat tail node to original head.
     * 
     * @param head 
     * @param k
     * @return
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || k == 0) {
            return head;
        }

        int len = 1;
        ListNode pointer = head;
        while (pointer.next != null) {
            len++;
            pointer = pointer.next;
        }
        // only one node always itself
        if (len == 1) {
            return head;
        }

        int places = k;
        if (k > len) {
            places = k % len;
        }
        // edge case: places equals 0, no need to move
        if (places == 0) {
            return head;
        }

        // keep paces
        ListNode p1, p2;
        p1 = p2 = head;

        int paces = len - places;
        if (paces == 0) {
            // no need to move cursor
            return p1;
        }
        while (paces > 0) {
            p1 = p1.next;
            paces--;
            if (paces < len - places - 1) {
                p2 = p2.next;
            }
        }

        // has move, need to redefine list new head
        if (p1 != head) {
            p2.next = null;
            pointer.next = head;
        }

        return p1;
    }

}