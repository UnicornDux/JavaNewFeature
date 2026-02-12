package com.edu.jdk.v09;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// JDK 9  中增强了从 JDK 7 中引入的 try .. resource 语法
public class TryResource {

    // 为了减少大量关于资源关闭的模板代码，JDK 7 中引入了 try .. resource 的写法
    // 只要类实现了对应 AutoClose 接口，就可以使用这样的语法
    static void main(String[] args) throws IOException {
        // 原始的资源释放只有在 try() 语句中创建的变量支持自动释放，
        // 9 中可以引用外部已经创建的变量
        BufferedReader reader = Files.newBufferedReader(Paths.get(""));
        try (reader){
           reader.lines().forEach(System.out::println);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
