package com.edu.jdk.v21;

// JDK 21 中引入了未命名变量

import java.util.Map;

public class UnNamedVar {
    public static void main(String[] args) {
        var map = Map.of("one", 1, "two", 2);
        // 未使用的变量可以使用 _ 来隐去，减少变量名多带来的混淆
        map.forEach((k, _) -> {
            System.out.println(k);
        });
    }
}
