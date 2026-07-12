package com.edu.jdk.v09;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 用于展示 JDK 9 中引入的新的功能变化
 * --------------------------------------------------------------------
 * > 代码编写性的变化可参照代码
 * > 非代码编写性的内部改变
 *    - G1 垃圾回收器成为了默认的垃圾回收器
 */
public class Main {

    static void main() {
        LocalDate date = LocalDate.now(); // 当前日期
        LocalTime time  = LocalTime.now(); // 当前时间
        LocalDateTime dateTime = LocalDateTime.now(); // 当前的日期和时间

        date = LocalDate.of(2025, 2, 10); // 指定日期
        time = LocalTime.of(23, 59, 59); // 指定时间
        dateTime = LocalDateTime.of(date, time);  // 指定日期和时间
        dateTime = LocalDateTime.of(2025, 12, 12, 12, 12, 12);

        // 时间解析
        LocalDate parseDate = LocalDate.parse("2025-02-12");
        LocalTime parseTime = LocalTime.parse("23:59:59");
        LocalDateTime parseDateTime = LocalDateTime.parse("2025-02-12T23:59:59");

        // 自定义格式解析
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateString = parseDateTime.format(formatter);
        LocalDateTime parse = LocalDateTime.parse(dateString, formatter);
    }

}
