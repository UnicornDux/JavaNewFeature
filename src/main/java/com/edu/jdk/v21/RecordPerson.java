package com.edu.jdk.v21;

// record 类 在进行模式匹配的时候可以自动结构为变量赋值

public record RecordPerson(String name, Integer age) {

    public static void main(String[] args) {

        Object obj = Math.random() > 0.5 ? new RecordPerson("alex", 10) : "None";

        // 在 使用 instanceof 的时候解构
        if (obj instanceof RecordPerson(String name, Integer age)) {
            System.out.printf("%s, %s%n", name, age);
        }

        // 使用 switch 的时候解构
        switch (obj) {
            case RecordPerson(String name, Integer age) -> System.out.printf("%s, %s%n", name, age);
            case null, default -> {
                // do nothing
            }
        }
    }
}


