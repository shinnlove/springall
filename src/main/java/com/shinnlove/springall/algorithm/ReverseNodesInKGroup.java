/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * LeetCode 25.
 * 
 * wiki: https://leetcode-cn.com/problems/reverse-nodes-in-k-group/.
 * 
 * @author Tony Zhao
 * @version $Id: ReverseNodeInKGroup.java, v 0.1 2021-01-31 11:26 AM Tony Zhao Exp $$
 */
public class ReverseNodesInKGroup {

    public static void main(String[] args) {

    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) {
            return head;
        }

        // p3 is next reverse k group's head
        ListNode p1, p2, p3;
        p1 = p2 = p3 = head;

        int count = 1;
        while (p1.next != null) {
            p1 = p1.next;
            count += 1;
            if (count == k) {
                p3 = p1.next;

                // do reverse

            }
        }

        return null;
    }


}