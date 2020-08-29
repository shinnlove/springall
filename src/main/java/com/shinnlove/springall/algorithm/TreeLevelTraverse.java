/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * https://github.com/azl397985856/leetcode/blob/master/problems/102.binary-tree-level-order-traversal.md
 * 
 * 时间复杂度：$O(N)$，其中N为树中节点总数。
 * 空间复杂度：$O(N)$，其中N为树中节点总数。
 * 
 * @author Tony, Zhao
 * @version $Id: TreeLevelTraverse.java, v 0.1 2020-08-29 9:35 AM Tony, Zhao Exp $$
 */
public class TreeLevelTraverse {

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(9);
        TreeNode node3 = new TreeNode(20);
        TreeNode node4 = new TreeNode(15);
        TreeNode node5 = new TreeNode(7);

        node1.left = node2;
        node1.right = node3;

        node2.left = null;
        node2.right = null;

        node3.left = node4;
        node3.right = node5;

        TreeLevelTraverse tt = new TreeLevelTraverse();
        List<List<Integer>> result = tt.levelOrder(node1);
        System.out.println(result);
    }

    public class NodeWrapper {
        TreeNode node;
        int      level;

        public NodeWrapper(TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> orderList = new ArrayList<>();
        List<Integer> levelList = new ArrayList<>();
        if (root == null) {
            return orderList;
        }

        Queue<NodeWrapper> queue = new ArrayBlockingQueue<>(100);
        queue.add(new NodeWrapper(root, 1));

        int i = 1;
        while (queue.peek() != null) {
            NodeWrapper current = queue.poll();
            TreeNode node = current.node;

            if (current.level == i) {
                levelList.add(node.val);
            } else {
                // warning: need duplicate, otherwise will be clear
                orderList.add(new ArrayList<>(levelList));
                levelList.clear();
                levelList.add(node.val);
                i++;
            }

            if (node.left != null) {
                queue.add(new NodeWrapper(node.left, i + 1));
            }
            if (node.right != null) {
                queue.add(new NodeWrapper(node.right, i + 1));
            }
        }

        // don't forget add last level node val
        orderList.add(levelList);

        return orderList;
    }

}