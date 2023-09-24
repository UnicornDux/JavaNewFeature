package com.edu.jdk.v12;

// JDK 12 中 switch 语句可以有返回值，并支持辅助给变量
public class SwitchExpress {

    public static void main(String[] args) {

        var obj = Math.random() > 0.5 ? "string" : "100";

        // 支持 switch 作为一个表达式来用, 返回值可以赋值给变量

        var value = switch (obj) {
            case "string" -> "is String Type";
            case "100" -> "is number";
            default -> null;
        };
        System.out.println(value);
    }
}
