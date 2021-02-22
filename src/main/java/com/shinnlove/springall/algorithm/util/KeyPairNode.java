/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm.util;

/**
 * Common data structure for key pair node from LRU cache.
 * 
 * @author Tony Zhao
 * @version $Id: KeyPairNode.java, v 0.1 2021-02-22 11:39 PM Tony Zhao Exp $$
 */
public class KeyPairNode {

    public int key;
    public int value;

    public KeyPairNode() {
    }

    public KeyPairNode(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public KeyPairNode prev;
    public KeyPairNode next;

}