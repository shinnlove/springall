/**
 * Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 17. 电话号码的组合
 * 
 * https://leetcode.cn/problems/letter-combinations-of-a-phone-number/description/?company_slug=bytedance
 * 
 * @author Tony Zhao
 * @version $Id: LettersCombination.java, v 0.1 2023-11-21 3:08 PM Tony Zhao Exp $$
 */
public class LettersCombination {

    //在外面定义这些变量，可以减少backTrack函数的参数，免得参数太长，头晕

    //定义res，用来装结果
    List<String>  res   = new ArrayList<>();

    //定义track，用来装满足条件的单个字符串
    StringBuilder track = new StringBuilder();

    //还是那句话，定义在外面，backTrack函数就不需要传这个参数了，在下面进行初始化
    String        digits;

    //定义ch数组，按‘2’的时候对应ch数组的第0行，按‘3’的时候对应ch数组的第1行，下面依次类推
    char[][]      ch    = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' },
                                         { 'j', 'k', 'l' }, { 'm', 'n', 'o' },
                                         { 'p', 'q', 'r', 's' }, { 't', 'u', 'v' },
                                         { 'w', 'x', 'y', 'z' } };

    public List<String> letterCombinations(String digits) {
        //特殊判断
        if (digits == null || digits.length() == 0)
            return res;

        //初始化digits
        this.digits = digits;

        //开始递归
        backTrack(0);

        //返回结果
        return res;
    }

    //唯一的一个参数的含义是：digits字符的索引，从索引0开始递归
    public void backTrack(int length) {

        //终止条件，当track字符串的长度等于digits的长度，则终止递归，把字符串加到res中
        if (track.length() == digits.length()) {
            res.add(String.valueOf(track));
            return;
        }

        //从索引0开始遍历，遍历到digits的最后一个字符
        for (int i = length; i < digits.length(); ++i) {
            //注意这里的下标计算，比如第一个字符是'2'，那么对应的字符数组是{'a','b','c'},该数组在ch数组中的索引是0，即'2' - '2'
            for (int j = 0; j < ch[digits.charAt(i) - '2'].length; ++j) {

                //做选择（回溯算法基本模板三部曲，做选择--递归--撤销选择）
                track.append(ch[digits.charAt(i) - '2'][j]);

                //递归
                backTrack(i + 1);

                //撤销选择
                track.deleteCharAt(track.length() - 1);
            }
        }
    }

    //验证，代码没有错误
    public static void main(String[] args) {
        LettersCombination s = new LettersCombination();
        String str = "23";
        System.out.println(s.letterCombinations(str));
    }

}