package com.edu.jdk.v09;


import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

// JDK 9.0 版本在集合里面添加的变化
// 添加的这一组新的构建方法出来的都是不不可变集合类型

public class CollectionNew {

    public static void main(String[] args) {
        newListOf();
        newSetOf();
        newMapOf();
    }

    // 新的创建 List 的方法
    public static void newListOf() {
        List<String> hello = List.of("hello", "world", "java", "collection");
        log("list: %v", hello);
    }

    // 新的创建 Set 的方法
    public static void newSetOf() {
        // 由于是不可变集合，所以元素不能重复
        Set<String> set = Set.of("h", "o", "e", "l", "d");
        log("Set: %v", set);
    }

    // 新的创建 Map 的方法
    public static void newMapOf(){
        // 最多可以支持 10 个键值对
        Map<String, ? extends Serializable> map
                = Map.of("name", "alex", "age", 18);
        log("map: %v", map);

        Map<String, String> smap = Map.ofEntries(Map.entry("name", "alex"), Map.entry("kitty", "terminal"));
        log("map entry: %s",smap);
    }

    private static void log(String format, Object... args) {
       System.out.println(String.format(format, args));
    }
}
