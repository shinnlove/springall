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

    /** 存放所有字符串下可能组合的结果列表 */
    List<String>  res   = new ArrayList<>();

    //定义track，用来装满足条件的单个字符串
    StringBuilder track = new StringBuilder();

    /** 最初的字符串变量，定义在外面，backTrack函数就不需要传这个参数了，在下面进行初始化 */
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

    /**
     * 唯一的一个参数的含义是：digits字符的索引。
     * 
     * 当从主入口开始调用的时候，从索引0开始递归。
     * 
     * 回溯法：
     *  a) 终止条件
     *  b) 循环每一次
     *      i) 做选择、并保存结果!!!
     *      ii) 递归下一次开始条件
     *      iii) 撤销选择也就是将结果集回退到做选择之前，也就是还原结果!!!
     * 
     * @param length        当前回溯递归到的第几层（也就是第几个字符）
     */
    public void backTrack(int length) {

        //终止条件，当track字符串的长度等于digits的长度，则终止递归，把字符串加到res中
        if (track.length() == digits.length()) {
            res.add(String.valueOf(track));
            return;
        }

        /**
         * 第一层循环：
         *  从当前索引开始遍历，遍历到digits初始字符串的最后一个位置。
         *      如果是第一次调用，则length其实是0；
         *      如果是递归调用，则每调用一次，length++。
         * 
         * 第二层循环：
         *  a)  因为已经定义好了ch数组，当2字符当成数字-2的时候就是0，因此会索引到二维字符数组的 {'a', 'b', 'c' }
         *      第二层循环从索引到的一维字符数组开始遍历，使用下标j
         *  b) 每一次第二层循环，只做选择一个字符，因此当递归调用回溯法的时候，也只需要撤销掉一个元素即可
         */
        for (int i = length; i < digits.length(); ++i) {

            /**
             * Java字符串遍历特性：
             *  a) String使用chatAt来遍历
             *  b) 在char字符下，ASCII码数字相减，会出现英文字母
             */
            for (int j = 0; j < ch[digits.charAt(i) - '2'].length; ++j) {

                //做选择（回溯算法基本模板三部曲，做选择--递归--撤销选择）
                track.append(ch[digits.charAt(i) - '2'][j]);

                //递归
                backTrack(i + 1);

                //撤销选择
                // 注意这里StringBuilder可以删除字符的，仅删除最后一个字符即可，因为之前做选择的时候也只用了一个字符
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