package com.edu.jdk.leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**

 128. 最长连续序列

 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。

 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。

 示例 1：

 输入：nums = [ 100,4,200,1,3,2]
 输出：4
 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。

 示例 2：

 输入：nums = [0,3,7,2,5,8,4,6,0,1]
 输出：9

 示例 3：

 输入：nums = [1,0,1,2]
 输出：3

 */

public class LongestSequence {

    static void main() {
        int[] nums = new int[]{0,3,7,2,5,8,4,6,0,1, 9, 10, 11, 23, 100, 102};
        System.out.println(findLongerSequence(nums));
    }


    public static int findLongerSequence(int[] nums) {
        HashSet<Integer> containers = new HashSet<>();
        containers.addAll(Arrays.stream(nums).boxed().toList());

        int longest = 0;

        for (Integer i : containers) {
            if (!containers.contains(i - 1)) {
                int current = i;
                int length = 1;

                while (containers.contains(current + 1)) {
                    current += 1;
                    length += 1;
                }
                longest = Math.max(longest, length);
            }
        }
        return longest;
    }
}
