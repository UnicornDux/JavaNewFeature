package com.edu.jdk.v21;


import java.text.MessageFormat;

// 字符串模板功能
// JDK 21 中为预览功能，因此在使用的时候，添加如下的参数才能运行
// --enable-preview --source 21

public class StringTemplate {

    public static void main(String[] args) {
        stringFormat();
        stringTemplate();

    }

    // 回顾以往的字符串格式化的写法
    public static void stringFormat() {
        String name = "alex";
        Integer age = 29;
        String v1 = "name: " + name + ", with age: " + age;
        String v2 = String.format("name: %s, with age: %d", name, age);
        MessageFormat message = new MessageFormat("name: {0}, with age: {1,number}");
        String v3 = message.format(new Object[]{name, age});
        System.out.println(v1);
        System.out.println(v2);
        System.out.println(v3);
    }

    public static void stringTemplate(){
        String name = "alex";
        Integer age = 29;
        String v = STR."name: \{name}, with age: \{age}";
        System.out.println(v);
    }
}
