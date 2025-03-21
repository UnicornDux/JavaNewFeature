package com.edu.jdk.v10;

/**
 * 用于描述 JDK 10 中引入的新的功能变化
 * ----------------------------------------------------------------
 * > 代码编写差异的内容在代码中查看
 * > 非代码编写的后台特性
 *   - 应用类数据共享，为改善启动和占用空间，在现有的类数据共享("CDS") 功能上再次扩展，
 *     允许应用类放置在共享存档中
 *   - 局部线程管控， 允许停止单个线程，而不是只能启用和停止所有线程
 *   - G1 垃圾回收器中引入了 并行的 Full GC
 */
public class Main {
}
