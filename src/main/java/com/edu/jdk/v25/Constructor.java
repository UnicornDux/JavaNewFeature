package com.edu.jdk.v25;

/**
 * > 灵活的构造函数体
 *   - 这个特性解决 Java 自诞生以来就存在的一个限制, 构造函数中的 `super()` 或者 `this()` 调用必须是第一条语句
 *   - 这个限制虽然保证了对象初始化的安全性,但是也可能会影响我们的编码.
 *   - 这个特性还可以防止父类构造函数调用子类未初始化的方法
 * > 这个过程称为序言阶段,是存在一定的限制的,
 *   - 不能使用 this 引用,(除了是字段赋值)
 *   - 不能调用实例方法
 *   - 只能对未初始化的字段进行赋值
 *
 */
public class Constructor {

    int value;
    String name = "default";

    Constructor(int val, String ns) {
        if (val < 0) {
            throw new IllegalArgumentException("val not lower zero");
        }
        // 值初始化
        this.value = val;

        // 不允许,字段已经有初始化器
        // this.name = ns;

        // 不允许调用实例方法
        // this.helper();

        super();
    }


    public void helper(){}


}
