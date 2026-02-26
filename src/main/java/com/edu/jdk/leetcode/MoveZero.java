package com.edu.jdk.leetcode;

/*
283. 移动零

给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。

请注意 ，必须在不复制数组的情况下原地对数组进行操作。

示例 1:

输入: nums = [0,1,0,3,12]
输出: [1,3,12,0,0]

示例 2:

输入: nums = [0]
输出: [0]

 */


import java.util.Arrays;

public class MoveZero {
    static void main() {
        int[] nums = new int[]{0,1,0,3,12, 19, 8, 10, 0, 1, 10, 11, 12, 10, 0, 2, 0};
        moveZero(nums);
        Arrays.stream(nums).forEach(item -> System.out.printf("%d ", item));
    }
    public static void moveZero(int[] nums) {

        int zeros = 0;
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zeros++;
            }else {
                nums[j] = nums[i];
                j++;
            }
        }
        for (int i = 0; i < zeros; i++) {
            nums[nums.length - 1 - i] = 0;
        }
    }
}
