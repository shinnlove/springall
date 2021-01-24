/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * LeetCode 23.
 * 
 * wiki: https://leetcode-cn.com/problems/merge-k-sorted-lists/.
 * 
 * Perfect done by once commit and minimize space to merge k sorted lists.
 * 
 * @author Tony Zhao
 * @version $Id: MergeKSortedList.java, v 0.1 2021-01-25 1:11 AM Tony Zhao Exp $$
 */
public class MergeKSortedList {

    public class NodeHolder implements Comparable<NodeHolder> {
        public int      value;
        public int      index;
        public ListNode address;

        public NodeHolder(int value, int index, ListNode address) {
            this.value = value;
            this.index = index;
            this.address = address;
        }

        @Override
        public int compareTo(NodeHolder o) {
            if (this.value < o.value) {
                return -1;
            } else if (this.value == o.value) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        int len = lists.length;
        if (len == 0) {
            return null;
        }

        // first initialize
        List<NodeHolder> nodes = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            ListNode node = lists[i];
            if (node == null) {
                continue;
            }
            NodeHolder nh = new NodeHolder(node.val, i, node);
            nodes.add(nh);
        }

        // placeholder
        ListNode headHolder = new ListNode();
        ListNode currentCursor = headHolder;

        while (nodes.size() > 0) {
            Collections.sort(nodes);

            // concat list
            NodeHolder nh = nodes.remove(0);
            currentCursor.next = nh.address;
            currentCursor = currentCursor.next;

            if (nh.address.next != null) {
                ListNode successor = nh.address.next;
                NodeHolder supplement = new NodeHolder(successor.val, nh.index, successor);
                nodes.add(supplement);
            }
        }

        return headHolder.next;
    }

}