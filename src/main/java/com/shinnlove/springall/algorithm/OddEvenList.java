/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * leetcode 328. 奇偶链表
 * 
 * https://leetcode.cn/problems/odd-even-linked-list/
 * 
 * @author Tony Zhao
 * @version $Id: OddEvenList.java, v 0.1 2024-01-13 3:01 PM Tony Zhao Exp $$
 */
public class OddEvenList {

    /**
     * 使用两个哑结点分别接收两个链表的各自奇偶结点。
     * 
     * @param head 
     * @return
     */
    public ListNode oddEvenList(ListNode head) {

        if (head == null) {
            return null;
        }

        ListNode odds = new ListNode(-1);
        ListNode evens = new ListNode(0);

        int count = 0;
        ListNode cur = head;
        ListNode oddCur = odds;
        ListNode evenCur = evens;

        while (cur != null) {

            count += 1;

            if (count % 2 != 0) {
                // odd
                oddCur.next = cur;
                oddCur = cur;
            } else {
                // even
                evenCur.next = cur;
                evenCur = cur;
            }

            cur = cur.next;

        } // while

        // exclude first node and concat two list
        oddCur.next = evens.next;
        // even tail set to null
        evenCur.next = null;

        return odds.next;
    }

}