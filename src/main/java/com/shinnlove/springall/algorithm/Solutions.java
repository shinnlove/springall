/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tony Zhao
 * @version $Id: Solutions.java, v 0.1 2021-02-21 9:42 AM Tony Zhao Exp $$
 */
public class Solutions {

    public static void main(String[] args) {

        Set<String> set1 = new HashSet<>();
        set1.add("A");
        set1.add("B");
        set1.add("C");

        Set<String> set2 = new HashSet<>();
        set2.add("B");
        set2.add("E");
        set2.add("F");

        Set<String> intersection = new HashSet<>(set1);

        System.out.println("before retainAll collection result=" + intersection);

        // 返回原集合retainAll取交集之后、集合中元素是否有改变
        boolean result = intersection.retainAll(set2);

        System.out.println("retainAll result=" + result);
        System.out.println("after retainAll collection result=" + intersection);

        if (!intersection.isEmpty()) {
            System.out.println("Set1和Set2存在交集");
        } else {
            System.out.println("Set1和Set2不存在交集");
        }

    }

}