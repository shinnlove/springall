/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * leetcode 82. 删除排序链表中的重复元素II
 * 
 * 这题是重复的元素都不要留下。
 * 
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-list-ii/description/
 * 
 * 本题重要思想：
 * 
 * a) 创建哑结点统一操作
 * b) 使用精妙的while循环处理指针和判断重复!
 * 
 * @author Tony Zhao
 * @version $Id: DeleteDuplicates2nd.java, v 0.1 2024-01-07 2:27 PM Tony Zhao Exp $$
 */
public class DeleteDuplicates2nd {

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }

        // 可能头结点也会被删除，先创建哑结点
        ListNode dummy = new ListNode(0, head);

        // 开始用cur指针遍历
        ListNode cur = dummy;

        while (cur.next != null && cur.next.next != null) {
            // 还有下一个节点、并且还有下一个节点也还有后继

            if (cur.next.val == cur.next.next.val) {
                // 两个节点值相等

                // 特别注意：这个代码的精彩就是，在while外定义x，利用x判断是否进入while调整指针的代码逻辑!
                // 同时，这个while还起到判断后续的值是否跟自己相等的作用!

                // 暂存这个值、并且往后找到不等于x的结点
                int x = cur.next.val;
                // 等于x的时候一直往下找、直到末尾或者不等于x
                while (cur.next != null && cur.next.val == x) {
                    // 一边找，一边把指针往后调整
                    // 假如在 1->1->1->2->3，当前cur = 哑结点
                    // 判断出cur.next.val == cur.next.next.val => 前两个结点值相等
                    // 执行完下面这句调整指针，哑结点直接指向第二个结点、忽略了第一个结点
                    // 而后第三个结点值也等于x，又进入while循环
                    // 直接又调整到第三个结点，但因为在while循环外定义的x，第三个结点值也是x，又进入while循环
                    // 直到3次进入while之后，cur.next 指向val=2这个结点
                    // 此时cur还在哑结点上，cur.next指针调整到了val=2这个结点、不满足再次进入while条件，会进入到else分支
                    cur.next = cur.next.next;
                }

            } else {
                // 直接往下移动
                cur = cur.next;
            }
        }

        return dummy.next;
    }

}