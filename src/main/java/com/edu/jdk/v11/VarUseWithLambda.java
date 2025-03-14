package com.edu.jdk.v11;

import java.util.function.BiFunction;

/**
 * Var 关键字可用于 Lambda 表达式中，不用在 Lambda 中定义类型
 */
public class VarUseWithLambda {

    public static void main(String[] args) {
        sumOfString();
    }

    // 示例
    public static String sumOfString() {
        BiFunction<String, String, String> func = (var x, var y) -> x + y;
        return func.apply("hello ", "world");
    }
}
