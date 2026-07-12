package com.edu.jdk.v11;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileApi {
    static void main() throws IOException {

        // 写入文件
        String content = "这是一个测试文件\n包含多行文本\n中文支持测试";
        Path temp = Files.writeString(
            Paths.get("/tmp/filesapi.txt"),
            content,
            StandardCharsets.UTF_8
        );

        // 读取文件
        String value = Files.readString(temp, StandardCharsets.UTF_8);
        System.out.println("读取到文件内容: \n" + value);


        try (Stream<String> lines = Files.lines(Paths.get("/tmp/filesapi.txt"))) {
            lines.filter(line -> !line.isBlank())
                .map(String::strip)
                .forEach(System.out::println);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
