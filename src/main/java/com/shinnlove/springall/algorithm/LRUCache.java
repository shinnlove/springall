/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LRU cache class.
 * 
 * Use double linked list to store key value pair node and use reflect map to store node original address.
 * 
 * Accepted with only once submit.
 * 
 * @author Tony Zhao
 * @version $Id: LRUCache.java, v 0.1 2021-01-05 5:19 PM Tony Zhao Exp $$
 */
public class LRUCache {

    class KeyPairNode {
        int key;
        int value;

        public KeyPairNode() {
        }

        public KeyPairNode(int key, int value) {
            this.key = key;
            this.value = value;
        }

        KeyPairNode prev;
        KeyPairNode next;
    }

    private int                       capacity;
    private int                       currentNums;
    // always point to evict node
    private KeyPairNode               head;
    // always point to latest node
    private KeyPairNode               tail;

    // store node original address for fetch
    private Map<Integer, KeyPairNode> nodeAddressReflect = new ConcurrentHashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head = tail = null;
    }

    public int get(int key) {
        if (!nodeAddressReflect.containsKey(key)) {
            return -1;
        }
        KeyPairNode readNode = nodeAddressReflect.get(key);

        // refresh this
        refreshNode(readNode);

        return readNode.value;
    }

    public void put(int key, int value) {

        if (!nodeAddressReflect.containsKey(key)) {
            // a new key

            // step1: fetch or new node
            KeyPairNode newNode = new KeyPairNode(key, value);

            if (currentNums < capacity) {

                // new add and append to tail
                // step2: check head and tail pointer and their prev and next pointer
                if (head == null) {
                    // first node
                    head = tail = newNode;
                } else {
                    // append to tail
                    newNode.prev = tail;
                    tail.next = newNode;
                    tail = newNode;
                }

                // step3: plus total numbers
                currentNums++;

            } else {
                // current number equals capacity, evict a LRU key

                // append to tail
                newNode.prev = tail;
                tail.next = newNode;
                tail = newNode;

                // VIP: remove original key!
                nodeAddressReflect.remove(head.key);

                // move head ahead
                head = head.next;
            }

            // step4: put new node into reflect map
            nodeAddressReflect.put(key, newNode);

        } else {
            // already has this key, just get
            KeyPairNode nodeExist = nodeAddressReflect.get(key);

            // update this value
            nodeExist.value = value;

            // refresh after use
            refreshNode(nodeExist);
        }

    }

    /**
     * If refresh an existed node, the head pointer couldn't equals to the tail pointer.
     * 
     * @param current
     */
    private void refreshNode(KeyPairNode current) {
        if (current == tail) {
            return;
        }

        if (current == head) {
            // just move head pointer ahead
            head = current.next;
        } else if (current != head && current != tail) {
            // link node's previous and next node
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }

        // append to tail
        tail.next = current;
        current.prev = tail;

        current.next = null;
        tail = current;
    }

}