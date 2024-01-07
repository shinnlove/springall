/**
 * Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * Leetcode 80. 删除有序数组中的重复项II
 * 
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/description/
 * 
 * @author Tony Zhao
 * @version $Id: RemoveDuplicates.java, v 0.1 2024-01-07 4:03 PM Tony Zhao Exp $$
 */
public class RemoveDuplicates {

    public int removeDuplicatesHasQuestionDIYAnswer(int[] nums) {

        int len = nums.length;

        if (len <= 2) {
            return len;
        }

        int remain = len;
        int repeated = 0;
        int i = 0;
        for (int j = i + 1; j < len; j++) {

            if (nums[i] == nums[j]) {
                repeated++;

                if (repeated < 2) {
                    continue;
                }

                // 大于2需要前移
                int k = j + 1;
                for (; k < len; k++) {
                    if (nums[i] != nums[k]) {
                        break;
                    }
                    // 停留的位置就是和i当前数字不一样的位置，计算diff
                }

                // 算出需要移动多少个元素
                int squashNum = k - i - 2;

                // 需要移动
                if (squashNum > 0) {
                    remain -= squashNum;
                }

                for (int l = 0; l < squashNum; l++) {
                    for (; k < len; k++) {
                        nums[k - 1] = nums[k];
                    }
                }

                // 重置重复标记
                repeated = 0;

            } else {
                i = j;
            }
        }

        return remain;
    }

    /**
     * 这个方法非常的巧妙，读写指针和数字精准定位法。
     * 建议多看多想多学习多应用，以后可以在别的地方发挥。
     * 
     * @param nums 
     * @return
     */
    public int removeDuplicates(int[] nums) {
        // 只有2个元素或以下都是直接返回数组长度(0或1或2)
        int n = nums.length;
        if (n <= 2) {
            return n;
        }

        // 保留前两个元素（因为这种默认保留方法基于最多重复2个元素，哪怕前两个元素一样也没有关系）
        // slow指针为数字写入位置指针、fast指针为读取并处理数字位置指针
        int slow = 2, fast = 2;

        // fast指针一直检查到末尾
        while (fast < n) {

            // slow指针的位置，就是当前元素要写入的位置
            // slow指针是否要写入，决定于nums[slow-2]这个数是否和fast指针相同
            // 一开始slow和fast是相同的
            // 如果已经第3次出现相同的数字，则slow指针-2的数一定和当前slow指针数一样
            // 不会进入slow指针写入和移动的逻辑，而直接移动fast指针
            // 在移动fast指针的过程中，已经是在忽略相同的元素了
            // 当fast指针找到第一个不再重复的元素时，一定和slow指针-2指向的数字不同
            // 此时fast位置的数，应该紧凑的写入到slow指针的位置
            // 也就是nums[slow] = nums[fast]
            // 当slow写完最新的数字，slow就会往前移动，fast指针也会跟着移动到下一个数字
            // 无论重复发生几次，slow一直都会因为相同元素拉慢步调
            // 最后fast抵达数组右侧的时候，slow指针的位置就在紧凑好的数组的末尾
            // 这样做相当于不需要每次发现重复的数字，就哗啦啦一阵移动紧凑后面的数字，哪怕后面也有重复
            // 而是直接识别出了最先不重复2次的数字应该排列的位置
            // 移动次数是最少的
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                ++slow;
            }

            ++fast;
        }

        return slow;
    }

    public static void main(String[] args) {

        RemoveDuplicates rd = new RemoveDuplicates();

        int[] nums1 = new int[] { 1, 1, 1, 2, 2, 3 };
        int remain1 = rd.removeDuplicatesHasQuestionDIYAnswer(nums1);

        System.out.println("After remove, array's length is = " + remain1 + ".");
        for (int i = 0; i < nums1.length; i++) {
            System.out.print(nums1[i] + ", ");
        }

        int[] nums2 = new int[] { 0, 0, 1, 1, 1, 1, 2, 3, 3 };
        int remain2 = rd.removeDuplicatesHasQuestionDIYAnswer(nums2);

        System.out.println("After remove, array's length is = " + remain2 + ".");
        for (int i = 0; i < nums2.length; i++) {
            System.out.print(nums2[i] + ", ");
        }
    }

}