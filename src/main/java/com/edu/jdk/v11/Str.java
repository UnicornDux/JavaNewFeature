package com.edu.jdk.v11;

public class Str {
    public static void main() {
        var x = "hello";
        var y = "   ";
        var z = " h ";
        var k = """
                foo
                bar
                music
                """;

        // 更好的字符串判空
        System.out.println(x.isBlank());

        // 比 trim() 更强, strip 这种可以处理 Unicode 空白字符
        System.out.println(y.strip());

        System.out.println(z.stripLeading());
        System.out.println(z.stripTrailing());

        // 重复字符串
        System.out.println("=".repeat(50));

        // 多行文本处理
        k.lines()
            .map(line -> "处理: " + line)
            .forEach(System.out::println);
        System.out.println(k.lines().count());



    }
}
