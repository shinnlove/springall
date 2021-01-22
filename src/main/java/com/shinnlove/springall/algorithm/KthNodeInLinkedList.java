/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * LeetCode
 * 
 * sword to offer problem 22.
 * 
 * wiki: https://leetcode-cn.com/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/
 * 
 * @author Tony Zhao
 * @version $Id: KthNodeInLinkedList.java, v 0.1 2021-01-22 4:34 PM Tony Zhao Exp $$
 */
public class KthNodeInLinkedList {

    public static void main(String[] args) {

        ListNode head = new ListNode();
        int k = 3;

        KthNodeInLinkedList kn = new KthNodeInLinkedList();
        ListNode targetNode = kn.getKthFromEnd(head, k);
        System.out.println(targetNode.val);
    }

    public ListNode getKthFromEnd(ListNode head, int k) {
        if (head == null) {
            return head;
        }

        int count = 1;

        ListNode current = head;
        while (current.next != null) {
            current = current.next;
            count++;
        }

        if (count < k) {
            return head;
        }

        current = head;
        int pointer = 0;
        while (pointer < count - k) {
            current = current.next;
            pointer++;
        }

        return current;
    }

}