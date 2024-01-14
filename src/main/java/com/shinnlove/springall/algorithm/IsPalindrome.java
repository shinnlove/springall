/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.shinnlove.springall.algorithm.util.ListNode;

/**
 * leetcode 234. 回文链表
 * 
 * https://leetcode.cn/problems/palindrome-linked-list/
 * 
 * @author Tony Zhao
 * @version $Id: IsPalindrome.java, v 0.1 2024-01-14 3:23 PM Tony Zhao Exp $$
 */
public class IsPalindrome {

    public boolean isPalindrome(ListNode head) {

        List<Integer> numbers = new ArrayList<>();

        ListNode p = head;

        while (p != null) {
            numbers.add(p.val);
            p = p.next;
        }

        int len = numbers.size();
        if (len == 0) {
            return false;
        }

        int low = 0, high = len - 1;
        while (low < high) {
            if (!numbers.get(low).equals(numbers.get(high))) {
                return false;
            }
            low++;
            high--;
        }

        // len == 1
        return true;

    }

}