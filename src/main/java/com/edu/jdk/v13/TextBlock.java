package com.edu.jdk.v13;

public class TextBlock {
    public static void main(String[] args) {
        text();
    }

    // 引入了文本块的功能
    // 编写一些需要格式化，带有特殊字符的文本的时候就需要各种转义了，可以直接在 """写入内容"""
    public static void text() {
        String json = """
                {
                    "name":"alex",
                    "age": 18
                }
                """;
        System.out.println(json);
    }
}
