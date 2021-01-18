/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm.util;

/**
 * The common structure of ListNode for algorithm.
 * 
 * @author Tony Zhao
 * @version $Id: ListNode.java, v 0.1 2021-01-18 11:35 PM Tony Zhao Exp $$
 */
public class ListNode {

    public int      val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

}