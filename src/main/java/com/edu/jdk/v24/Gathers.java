package com.edu.jdk.v24;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

/**
 *
 * Stream API 自 Java8 引入依赖, 极大的改变了我们处理集合数据的方式,但是在一些特定的场景中,传统的 Stream 操作
 * 就显得力不从心了, Stream Gathers 正是对 Stream API 的一个重要扩展, 它解决了现有 Stream API 在某些复杂数据
 * 处理场景中的局限性, 补齐了 Stream API 的短板
 *
 */


public class Gathers {
    static void main() {

    }


    // 如果想要对一些复杂的数据聚合操作,比如滑动窗口或固定窗口分虚,可以直接用 Gathers API 处理

    List<Double> prices = Arrays.asList(100.0, 102.0, 98.0, 105.0, 110.0);

    public void handler() {

        List<Double> collect = prices.stream().gather(Gatherers.windowSliding(3)) // 创建大小为 3 的滑动窗口
                .map(window -> {
                    // window 是 List<Double> 类型,包含三个元素
                    return window.stream()
                            .mapToDouble(Double::doubleValue)
                            .average().orElse(0.0);
                }).collect(Collectors.toList());

        System.out.println("移动的凭据值: " + collect);
    }

    // Gathers 的其他 API
    // windowFix()
    // fold()
    // scan()
    // mapConcurrent()


    /**
     * > 自定义收集器(如下的例子是一个流收集器最基本的形态)
     *    - 不需要状态, 第一个参数返回 null, 表示不需要维护任何状态
     *    - 简单转换: 第二个参数接收每个元素,做简单处理后推送到下游
     *    - 无需收尾: 省略第三个参数, 因为不需要最终处理
     *
     * 虽然这个例子用 map() 也能实现,它能够维护复杂的内部状态,并根据业务逻辑灵活地向下游推送结果,让
     * 原本需要手动循环的复杂逻辑变得简洁优雅.
     * Stream Gatherers 的另一个优势是它和现有的 Stream API 完全兼容,你可以在任何 Stream 管道中
     * 的任何位置插入 Gatherer 操作,就像使用 map, filter, collect 一样自然,让复杂的操作变得强大而优雅
     *
     */
    // 例如需要给每个元素添加一个前缀
    Gatherer<String, ?, String> addPrefix = Gatherer.ofSequential(
            () -> null,  // 不需要状态, 所以初始化为 null
            (state, ele, down) -> {
                // 给每个元素添加 前缀, 并推送到下游
                down.push("prefix-" + ele);
                return true; // 继续处理
            }
            // 不需要 finisher, 省略第三个参数
    );


    // 使用流收集器
    public void use() {
        var names  = Arrays.asList("Linux", "MaxOs", "Windows", "Homony");

        List<String> collect = names.stream()
                .gather(addPrefix)
                .collect(Collectors.toList());

        System.out.println(collect);
    }
}
