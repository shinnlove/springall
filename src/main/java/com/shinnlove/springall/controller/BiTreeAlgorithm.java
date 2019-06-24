/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.controller;

/**
 * 二叉树遍历算法题。
 * 
 * @author shinnlove.jinsheng
 * @version $Id: BiTreeAlgorithm.java, v 0.1 2019-06-24 13:58 shinnlove.jinsheng Exp $$
 */
public class BiTreeAlgorithm {

    public static void main(String[] args) {
        // 创建一棵树，步骤省略
        BiTreeNode root = createBiTree();

        // 从小到大输出序列
        ViewBiTreeData(root);

        // 找到具体值
        searchNode(root, 20);
    }

    static class BiTreeNode {
        int        data;
        BiTreeNode lchild; // 左孩子
        BiTreeNode rchild; // 右孩子

        public BiTreeNode(int data) {
            this.data = data;
        }
    }

    /**
     * 创建一棵树。
     * 叶子节点左右孩子都为null；任意节点左孩子值一定小于根、小于右孩子。
     * 
     * @return 
     */
    static BiTreeNode createBiTree() {
        // 省略创建代码...
        return new BiTreeNode(10);
    }

    static void ViewBiTreeData(BiTreeNode root) {
        // 1. TODO：从大到小输出这些数值
    }

    static BiTreeNode searchNode(BiTreeNode root, int targetValue) {
        // 2. TODO：给定数值找到对应节点，或者输出null
        return null;
    }

}