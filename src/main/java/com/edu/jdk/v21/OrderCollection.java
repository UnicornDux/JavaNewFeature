package com.edu.jdk.v21;

import java.util.ArrayList;
import java.util.List;

/**
 * 有序集合
 * Java 21 的有序集合为提供了更加直观的方式来操作集合的头尾元素
 *
 */
public class OrderCollection {
    
    static void main() {
        
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // 反序
        List<String> reversed = list.reversed();

        // 获取首位
        String first = list.getFirst();
        String last = list.getLast();

        // 移除首位
        String s = list.removeFirst();
        String e = list.removeLast();

    }
}
