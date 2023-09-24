package com.edu.jdk.v16;

// 数据 record 类成为了正式功能
// 将属性定义在类的声明上，
// 会自动生成数据的访问设置方法，这种类用于存储数据，简化了使用普通 java 类作为数据存储类的复杂性
// -------------------------------------------------------------------------
// 也可以重写 hashCode, equals, toString 等方法，但是 get/set 不需要写了
public record RecordData(String name, Integer age) {

}



