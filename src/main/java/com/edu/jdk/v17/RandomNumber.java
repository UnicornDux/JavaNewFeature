package com.edu.jdk.v17;

import java.util.Random;
import java.util.random.RandomGenerator;

/**
 * 引入了更多的伪随机数的生成器，支持更高效和更多的算法选择
 */
public class RandomNumber {
    static void main() {

        // 传统方式的随机数
        Random random = new Random();
        int old = random.nextInt();

        // 新的随机数生成器
        RandomGenerator randomGenerator = RandomGenerator.of("L32X64MixRandom");
        int newValue = randomGenerator.nextInt(100);
        System.out.println(newValue);

    }
}
