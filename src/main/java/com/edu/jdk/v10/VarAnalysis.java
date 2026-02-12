package com.edu.jdk.v10;

// JDK 10 中提供了本地变量类型推断
public class VarAnalysis {

    // 类中的全局作用域不能使用 var
    // var x = "hello";

    static void main() {
        // 局部作用域内支持类型推导

        // 此时 x 被推导为 String 类型, 可以省略类型标注
        var x = "hello";
        // 方法的返回值也可以推断
        var y = getValue();
    }

    public static Integer getValue() {
        return 10;
    }

}
