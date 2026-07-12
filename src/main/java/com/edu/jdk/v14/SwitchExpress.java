package com.edu.jdk.v14;

// JDK 14 中 switch 语句可以有返回值，并支持辅助给变量
public class SwitchExpress {

    public static void main(String[] args) {

        var obj = Math.random() > 0.5 ? "string" : "100";

        // 支持 switch 作为一个表达式来用, 返回值可以赋值给变量

        var value = switch (obj) {
            case "string" -> "is String Type";
            case "100" -> {
                System.out.println("string is 100");
                // 存在多行的逻辑的时候，可以使用 yield 关键字返回，与 return 返回不同
                // yield 只会结束当前代码块，而不像 return 结束整个函数
                yield "is number";
            }
            default -> null;
        };
        System.out.println(value);
    }
}
