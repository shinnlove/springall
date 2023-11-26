/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * 142. 环形链表II
 * 
 * https://leetcode.cn/problems/linked-list-cycle-ii/?envType=study-plan-v2&envId=top-100-liked
 * 
 * 找到链表中入环结点。
 * 
 * p1 走2步、p2 走1步
 * 第一次相遇的时候，头结点到环s1，环到相遇节点s2，某个节点到环点s3
 * 
 * s1 + 2s2 + s3 = 2 (s1 + s2)
 * => s1 + 2s2 + s3 = 2s1 + 2s2
 * => s3 = s1
 * 
 * => 相遇的时候，开始弄一个头指针开始往后移动并计数，直到遇到p2就可以知道有多长了。
 * 
 * @author Tony Zhao
 * @version $Id: DetectCycle.java, v 0.1 2023-11-26 5:47 PM Tony Zhao Exp $$
 */
public class DetectCycle {

    public ListNode detectCycle(ListNode head) {

        if (head == null) {
            return null;
        }

        ListNode p1 = head;
        ListNode p2 = head;

        while (p1 != null) {

            p1 = p1.next;
            p2 = p2.next;

            // p1 再多走一步

            if (p1 != null) {
                p1 = p1.next;
            }

            // 检测是否相等
            if (p1 == p2) {
                ListNode p3 = head;

                while (p2 != null && p2 != p3) {
                    p2 = p2.next;
                    p3 = p3.next;
                }

                if (p2 == p3) {
                    return p3;
                }
            }

        }

        return null;
    }

}