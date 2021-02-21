/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * Common data structures.
 * 
 * @author Tony Zhao
 * @version $Id: Solutions.java, v 0.1 2021-02-19 6:55 PM Tony Zhao Exp $$
 */
public class JavaCommonDataStructures {

    public static void main(String[] args) {

        // used as stack
        Deque<Integer> dequeStack = new ArrayDeque<>(10);
        dequeStack.add(1);
        dequeStack.remove();

        dequeStack.addFirst(2);
        dequeStack.getFirst();

        dequeStack.addLast(3);
        dequeStack.getLast();

        // used as queue
        ListNode ln1 = new ListNode(1);
        ListNode ln2 = new ListNode(2);
        ListNode ln3 = new ListNode(3);
        Queue<ListNode> arrayDeque = new ArrayDeque<>(10);
        arrayDeque.offer(ln1);
        arrayDeque.poll();

        arrayDeque.add(ln2);
        arrayDeque.remove();

        // must give a specific capacity for array blocking queue
        Queue<ListNode> arrayBlockingQueue = new ArrayBlockingQueue<>(100);
        // if capacity is not given, it's integer's max value
        Queue<ListNode> linkedBlockingQueue = new LinkedBlockingQueue<>(1000);

        Map<String, String> hashMap = new HashMap<>();

        Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();

        Map<String, String> linkedHashMap = new LinkedHashMap<>();

        Map<String, String> treeMap = new TreeMap<>();

        Map<String, String> skipList = new ConcurrentSkipListMap<>();

    }

}