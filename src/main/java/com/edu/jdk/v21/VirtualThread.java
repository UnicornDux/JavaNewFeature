package com.edu.jdk.v21;

import java.util.concurrent.*;

public class VirtualThread {
    public static void main(String[] args) {
//        usevirthreadwithname();
//        usevirthreadwithfactory();
//        usevirthreadstartdirect();
//        usevirthreadwiththreadpool();
        useVirThreadWithStructureScope();
    }

    /**
     * 1. 开启虚拟线程的方式一
     *   Thread.ofVirtual()
     */
    public static void useVirThreadWithName() {

        // 对虚拟线程进行设置
        Thread.Builder.OfVirtual ofVirtual = Thread
                .ofVirtual()
                .name("virtual-one")
                .inheritInheritableThreadLocals(true);

        // 设置任务并不立即启动
        // Thread vt = ofVirtual.unstarted(new Task());
        // vt.start(); // 启动任务

        // 设置任务并立即启动
        ofVirtual.start(new Task("withName"));
    }
    /**
     * 2. 开启虚拟线程的方式二
     *   Thread.ofVirtual() 构建工厂
     */
    public static void useVirThreadWithFactory() {
        ThreadFactory factory = Thread.ofVirtual().name("virtual-two").factory();
        Thread thread = factory.newThread(new Task("withFactory"));
        thread.start();
    }

    /**
     * 3. 开启虚拟线程的方式三
     *   Thread.startVirtualThread(Runnable)
     */
    public static void useVirThreadStartDirect() {
        Thread thread = Thread.startVirtualThread(new Task("startDirect"));
    }

    /**
     * 4. 开启虚拟线程的方式四
     *   Executors.newVirtualThreadPerTaskExecutors()
     */
    public static void useVirThreadWithThreadPool() {
        // 因为返回了一个 AutoClose 对象，可以自动关闭资源
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()){
            for (int i = 0; i < 10; i++) {
                executorService.submit(new Task("threadPool"));
            }
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 5. 开启虚拟线程的方式五
     *  StructureTaskScope#fork (结构化并发类)
     */
    public static void useVirThreadWithStructureScope() {
        // 返回一个 AutoClose 对象，可以自动关闭资源
        try (
                // 结构化并发类的精髓
                // ------------------------------------
                // > ShutdownOnFail : 在多任务并发的情况下，有一个失败则终止所有任务
                // > ShutdownOnSuccess : 多任务并发，有一个成功，则其他的都不再执行
                StructuredTaskScope scope = StructuredTaskScope.open()
        ) {
           scope.fork(() -> {
               System.out.printf("structureScope\t%s%n", Thread.currentThread());
               return "success";
           });

           scope.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

/**
 * 构建的测试虚拟线程的任务
 */
class Task implements Runnable {
    private final String name;
    public Task(String name){
       this.name = name;
    }
    @Override
    public void run() {
        System.out.printf("%s\t%s%n", name, Thread.currentThread());
    }
}
