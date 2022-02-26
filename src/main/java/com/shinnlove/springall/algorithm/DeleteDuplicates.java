/**
 * Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.HashSet;
import java.util.Set;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * Leetcode 83. 
 * 
 * <p>https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/submissions/</p>
 * 
 * @author Tony Zhao
 * @version $Id: DeleteDuplicates.java, v 0.1 2022-02-26 11:15 PM Tony Zhao Exp $$
 */
public class DeleteDuplicates {

    public static void main(String[] args) {

    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }

        Set<Integer> temp = new HashSet<>();
        ListNode p1, previous;
        p1 = previous = head;
        while (previous.next != null || p1 != null) {
            if (!temp.contains(p1.val)) {
                temp.add(p1.val);
                if (previous != p1) {
                    previous = previous.next;
                }
                p1 = p1.next;
            } else {
                previous.next = p1.next;
                p1 = p1.next;
            }
        }

        return head;
    }

}