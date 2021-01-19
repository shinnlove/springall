/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.LinkedList;
import java.util.Queue;

import com.shinnlove.springall.algorithm.util.TreeNode;

/**
 * LeetCode 104.!! Sequence traversal of binary tree.
 * 
 * wiki: https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/.
 * 
 * Use stack to iterate traversal a binary tree.
 * 
 * Tips: if you want to use a queue, please use Queue<Element> queue = new LinkedList<Element>();
 * That's important.
 * 
 * If use DFS, please use recursive call,
 * if use BFS, please use Queue.
 * For first level, root has only one node, queue size equals one.
 * 
 * @author Tony Zhao
 * @version $Id: MaxDepthOfBinaryTree.java, v 0.1 2021-01-19 11:22 PM Tony Zhao Exp $$
 */
public class MaxDepthOfBinaryTree {

    public static void main(String[] args) {
        TreeNode three = new TreeNode(3);
        TreeNode nine = new TreeNode(9);
        TreeNode twenty = new TreeNode(20);
        TreeNode fifteen = new TreeNode(15);
        TreeNode seven = new TreeNode(7);

        three.left = nine;
        three.right = twenty;

        twenty.left = fifteen;
        twenty.right = seven;

        MaxDepthOfBinaryTree mdobt = new MaxDepthOfBinaryTree();
        int depth = mdobt.maxDepth(three);
        System.out.println("The tree's depth is " + depth);
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int depth = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // calculate current level nodes' number
            int size = queue.size();
            while (size-- > 0) {
                TreeNode newNode = queue.poll();
                if (newNode.left != null) {
                    queue.offer(newNode.left);
                }
                if (newNode.right != null) {
                    queue.offer(newNode.right);
                }
            }
            depth++;
        }

        return depth;
    }

}