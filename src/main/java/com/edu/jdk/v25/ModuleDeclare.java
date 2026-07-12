package com.edu.jdk.v25;

/**
 *  > 模块导入声明
 *  模块导入声明(Module Import Declarations) 虽然是首次亮相, 但它的设计理念可以追溯到 Java9 的模块系统
 *  模块系统允许我们将代码组织成模块,每个模块都有明确的依赖关系和导出接口,让大型应用的架构变得更加清晰和可维护.
 *
 *  > 由于现代的 jdk 已经完成了模块化的设计与开发, 标准库可以直接使用这种方式, 但是由于 java 庞大的生态是基于老的
 *    classpath 模式的构建, 第三方库大部分没有适配,还可能会引入包不够明确, 命名冲突等问题
 */

import module java.base;

public class ModuleDeclare {

    static void main() {

        // 不用再一个个包的导入到项目中, 只需一行导入
        List<String> list = new ArrayList<>();

    }
}
