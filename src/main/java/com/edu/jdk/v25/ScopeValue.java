package com.edu.jdk.v25;

import java.nio.file.attribute.UserDefinedFileAttributeView;

import static java.lang.ScopedValue.where;

/**
 * > 引入虚拟线程后, 一种新的方式,可以与固定的代码块作用域进行绑定的值,
 * > 它是不可修改的,
 *
 */
public class ScopeValue {

    static final  ScopedValue<String> USER_ID = ScopedValue.newInstance();
    static final  ScopedValue<String> TRACE_ID = ScopedValue.newInstance();

    public void processRequest(String userId){
        // 自动清理, 无须手动清理
        where(USER_ID, userId).run(this::doWork);

        // run 方法
        where(USER_ID, userId).run(() -> {
            Thread.ofVirtual().start(() -> {
                // 虚拟线程共享, 父子线程自动继承共享
                String uid = USER_ID.get();
                //
            });
        });

        // 支持返回值
        where(USER_ID, userId).call(() -> {
           return "handler" + USER_ID.get();
        });

        // 作用域嵌套
        where(USER_ID, userId).run( () ->  {
            System.out.println("userId: " + USER_ID.get());
            // 获取到就近的原则
            where(USER_ID, "peek").run(() -> {
                System.out.println(USER_ID.get());
            });
        });

        // 邦定多个作用域值
        where(USER_ID, userId)
            .where(TRACE_ID, "start")
            .run(() -> {
                System.out.println("user: " + USER_ID.get() + ", trace_id: " + TRACE_ID.get());
            });
    }

    public void doWork() {
        String userId = USER_ID.get();  // 读取
        System.out.println("处理用户 :" + userId);
    }

    static void main() {

    }
}
