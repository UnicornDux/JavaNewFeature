package com.edu.jdk.leetcode;

/*

字母异位词分组

给你一个字符串数组，请你将 组合在一起。可以按任意顺序返回结果列表。


示例 1:

输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]

输出: [["bat"],["nat","tan"],["ate","eat","tea"]]

解释：

    在 strs 中没有字符串可以通过重新排列来形成 "bat"。
    字符串 "nat" 和 "tan" 是字母异位词，因为它们可以重新排列以形成彼此。
    字符串 "ate" ，"eat" 和 "tea" 是字母异位词，因为它们可以重新排列以形成彼此。


 */

import java.util.*;

public class FindSameCharWord {

    static void main() {

        String[] str = new String[]{
                "eat", "tea", "tan", "ate", "nat", "bat", "hello", "olleh", "olheo", "lhole", "helol", "eollh"
        };
        System.out.println(groupAnagrams(str));
    }

    public static List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();
        Arrays.stream(strs).forEach(str -> {
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);
            String value = new String(charArray);
            map.computeIfAbsent(value, key -> new ArrayList<>()).add(str);

        });
        return  map.values().stream().toList();
    }
}
