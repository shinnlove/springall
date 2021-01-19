/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm.util;

/**
 * Common data structure of tree node.
 * 
 * @author Tony Zhao
 * @version $Id: TreeNode.java, v 0.1 2021-01-19 2:56 PM Tony Zhao Exp $$
 */
public class TreeNode {

    public int      val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

}