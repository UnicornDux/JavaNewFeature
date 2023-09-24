package com.edu.jdk.v16;

// JDK 16 中使用 instanceof 完成数据类型的转化，简化语法
public class InstanceOfModel {

    public static void main(String[] args) {

        Object obj = Math.random() > 0.5 ? "kitty" : 25;

        // 原来的写法
        if (obj instanceof String) {
            String cat = (String)obj;
            System.out.println(cat);
        }

        if (obj instanceof String cat) {
            System.out.println(cat);
        }
    }
}
