package com.edu.jdk.v09;

// JDK 9.0 中 接口的变化,
public interface InterfacePrivateMethod {

    // 接口中支持定义私有方法, 但是必须要有实现，
    // 因为定义一个私有的没有实现的方法是没有意义的
    private int plus(int a, int b) {
        return a + b;
    }
}
