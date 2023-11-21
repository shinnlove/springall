/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * 25. K个一组翻转链表。
 * 
 * https://leetcode.cn/problems/reverse-nodes-in-k-group/submissions/483795967/?company_slug=bytedance
 * 
 * @author Tony Zhao
 * @version $Id: ReverseKGroup.java, v 0.1 2023-11-21 8:27 PM Tony Zhao Exp $$
 */
public class ReverseKGroup {

    public ListNode reverseKGroup(ListNode head, int k) {

        // 没有结点、单结点、每次翻转1个结点、这三种情况都不需要翻转
        if (head == null || head.next == null || k <= 1) {
            return head;
        }

        // 统计当前有多少结点
        int currentNum = 0;
        ListNode currentPointer = head;
        while (currentPointer != null && currentNum < k) {
            currentPointer = currentPointer.next;
            currentNum++;
        }

        // 结点数量不足，返回当前的头结点即可，不做翻转
        if (currentNum < k) {
            return head;
        }

        // 先做翻转当前列表, do k-reverse

        // 1st -> 2nd -> 3rd -> 4th -> null
        //  f      l      t
        // step 0 -> 1

        // 1st <- 2nd -> 3rd -> 4th -> null
        //         f      l      t
        // step 1 -> 2

        // 1st <- 2nd <- 3rd -> 4th -> null
        //                f      l      t
        // step 2 -> 3

        // 1st <- 2nd <- 3rd <- 4th -> null
        //                       f      lt
        // step 3 -> 4

        ListNode former = head;
        ListNode latter = former.next;
        ListNode third = latter.next;
        // 已经移动了一次了(k=2、移动2次翻转2次)
        int currentStep = 0;

        while (currentStep < k - 1 && latter != null) {

            // do reverse
            latter.next = former;

            former = latter;
            latter = third;

            if (third != null) {
                third = third.next;
            }

            currentStep++;
        }

        // 再递归执行剩余列表、并将翻转后的头结点给到当前头结点的下一个
        head.next = reverseKGroup(latter, k);

        // return the original tail node as a new head node
        return former;

    }

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;

        ReverseKGroup rkg = new ReverseKGroup();
        ListNode head = rkg.reverseKGroup(n1, 2);
        while (head != null) {
            System.out.println("Node is " + head.val);
            head = head.next;
        }
    }

}