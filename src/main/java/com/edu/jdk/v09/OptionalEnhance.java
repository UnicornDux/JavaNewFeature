package com.edu.jdk.v09;

import java.util.Optional;
import java.util.stream.Stream;

// JDK 9.0 中对 Optional 的增强
public class OptionalEnhance {

    public static void main(String[] args) {

        useDefault();
        System.out.println(useThrow());
    }


    // 如果没有值 使用 orElseThrow 抛出异常
    public static String useThrow(){
        return Stream.of("h", "a", "c")
                .findAny()
                .orElseThrow();
    }

    // 如果存在值与不存在值的默认处理方式
    public static void useDefault() {
        Stream.of("h", "a", "v")
            .findAny()
            .ifPresentOrElse(
                    System.out::println,
                    () -> System.out.println("empty")
            );
    }

    // java 8.0 中的用法
    public static void useElse() {
        Optional<String> opt = Stream.of("h", "a", "v")
                .findAny();
        if (opt.isPresent()) {
            System.out.println(opt.get());
        }else {
            System.out.println("empty");
        };
    }
}
