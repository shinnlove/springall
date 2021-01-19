/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * LeetCode 141.
 * 
 * wiki: https://leetcode-cn.com/problems/linked-list-cycle/.
 * 
 * Set two pointer, p1 and p2,
 * p1 moves two step every time while p2 move only one step every time.
 * 
 * if p1 == null or p1.next == null means reach the list's tail pointer, that is,
 * the list has no circle then.
 * 
 * While p1 meets p2, p1 moves a circle more step than p2.
 * Suppose p2 moves k steps, then p2 moves 2k steps. 
 * Suppose s1 steps move into circle point, s2 steps when meet, 
 * then has the formula that: 
 * s1 + 2s2 + s3 = 2k
 * s1 + s2 = k
 * => s2 + s3 = k
 * => s1 = s3 
 * 
 * when meet, use another pointer step from head, when they meet, steps is into circle steps.
 * 
 * @author Tony Zhao
 * @version $Id: LinkedListCycle.java, v 0.1 2021-01-20 12:18 AM Tony Zhao Exp $$
 */
public class LinkedListCycle {

    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode p1, p2;
        p1 = p2 = head;
        while (p1 != null && p1.next != null) {
            p1 = p1.next.next;
            p2 = p2.next;
            if (p1 == p2) {
                return true;
            }
        }
        return false;
    }

}