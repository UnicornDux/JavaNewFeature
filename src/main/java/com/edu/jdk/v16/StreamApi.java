package com.edu.jdk.v16;

import java.util.List;

public class StreamApi {
    static void main() {

        List<String> names = List.of("hello", "world", "java");

        // flatmap 写法
        List<Character> chars = names.stream()
                .flatMap(word -> word.chars().mapToObj(c -> (char) c))
                .toList();

        // multimap 写法
        // 将一个元素映射为多个元素,但是某些场景比 flatmap 更加高效,
        // 当需要一个元素生成多个元素时, flatmap 需要先创建一个中间 Stream, 而 mapMulti() 可以通过传入的
        // Comsumer 直接推送多个元素,避免中间集合或 Stream 的创建开销.
        names.stream().mapMulti((word, consumer) -> {
            for (char c : word.toCharArray()) {
                consumer.accept(c);
            }
        });
    }
}
