package com.edu.jdk.v25;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

/**
 * 结构化并发(preview)
 *
 * > 结构化并发解决什么问题,线程泄露和资源管理问题, 例如在一个具有一定因果关系的多并发的程序中,当其中某些失败的时候,
 *   在此作用关联域内的其他并发任务如何感知和响应的问题.(继续执行或者同时取消), 异常传播问题,
 *   - 自动清理: 任一子任务失败,其他子任务自动取消(执行策略可选)
 *   - 异常传播: 主线程被中断,子任务也自动取消
 *   - 资源管理: 可以配合 try-with-resources 保证资源的释放
 */

public class StructureScope {

    static void main() {
        scope();
    }

    public static List<?> scope() {
        // 默认策略,(所有都要成功,有失败,所有都取消)
        try (var scope = StructuredTaskScope.open()){

            // 竞速策略: 任一个成功
            // try (var scope = StructuredTaskScope.open(Joiner.anySuccessfulResultOrThrow())){

            // 收集所有的结果,忽略失败
            // try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.awaitAll())){

            var userTask = scope.fork(() -> findUser());
            var stockTask = scope.fork(() -> findStock());

            // 等待所有的子任务完成或者失败
            scope.join();
            return Arrays.asList(userTask.get(), stockTask.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 走出作用域,所有的子任务都自动清理,不会泄露
    }

    public static void findUser() {}
    public static void findStock() {}
}
