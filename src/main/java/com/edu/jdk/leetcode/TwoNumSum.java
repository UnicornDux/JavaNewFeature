package com.edu.jdk.leetcode;

import java.util.HashMap;

/*
1. 两数之和

给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。

你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。

你可以按任意顺序返回答案。

示例 1：

输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。

示例 2：

输入：nums = [3,2,4], target = 6
输出：[1,2]

示例 3：

输入：nums = [3,3], target = 6
输出：[0,1]


 */
public class TwoNumSum {
    static void main() {
          int[] nums = new int[]{2,7,11,15, 10, 11, 35, 10, 100, 22, 17};
          int target = 107;
          int[] result = towSum(nums,target);
          if (result.length > 0) {
              System.out.println(String.format("[ %d, %d ]", result[0] ,  result[1]));
          }
    }

    static int[] towSum(int[] nums, int target) {

        HashMap<Integer, Integer> result = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complete = target - nums[i];
            if (result.containsKey(complete)) {
                return new int[]{result.get(complete), i };
            }
            result.put(nums[i], i);
        }
        return new int[]{};
    }
}
