package com.edu.jdk.v21;


import java.util.List;

/**
 *
 * 对 switch 进行增强，支持对变量类型的模式匹配，快速处理不同的类型对应的代码逻辑, 避免冗余的 if else 匹配转换
 *
 * @param name
 * @param age
 */

// record 类 在进行模式匹配的时候可以自动结构为变量赋值

public record RecordPerson(String name, Integer age) {

    public static void main(String[] args) {

        Object obj = Math.random() > 0.5 ? new RecordPerson("alex", 10) : "None";

        // 在 使用 instanceof 的时候解构
        if (obj instanceof RecordPerson(String name, Integer age)) {
            System.out.printf("%s, %s%n", name, age);
        }

        switch (obj) {
            // 使用 switch 的时候解构
            case RecordPerson(String name, Integer age) -> System.out.printf("%s, %s%n", name, age);
            // 可以匹配不同的类型同时进行变量赋值 (同时可以使用 when 附加分支条件)
            case String x when args.length > 2 -> System.out.println(x);
            // case List<?> n -> n.forEach(System.out::println);
            case null, default -> {
                // do nothing,
            }
        }
    }
}


