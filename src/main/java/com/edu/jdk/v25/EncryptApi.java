package com.edu.jdk.v25;

import javax.crypto.KDF;
import javax.crypto.SecretKey;
import javax.crypto.spec.HKDFParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

/**
 * 密钥派生函数 API
 *
 * 随着量子计算技术的发展, 传统的密码学算法面临威胁,后量子密码学成为必然趋势,
 * 因此 Java 也顺应时代的发展,推出了密钥派生函数(KDF) 这是一种从初始密钥材料,盐值等输入生成新密钥的加密算法
 *
 * 可以简单的理解为引入了一些新的密钥生成工具类,适用于对密码进行加强,从主密钥派生出多个子密钥的场景
 * 核心是 javax.crypto.KDF 类, 提供了主要的两个方法
 *
 */
public class EncryptApi {

    static void main() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {

        KDF hkdf = KDF.getInstance("HKDF-SHA256");

        // 准备初始密钥材料和盐值
        byte[] initialKeyMaterial = "my-secret-key".getBytes();
        byte[] salt = "random-salt".getBytes();
        byte[] info = "application-context".getBytes();

        // 创建 KDF 参数
        AlgorithmParameterSpec params = HKDFParameterSpec.ofExtract()
                .addIKM(initialKeyMaterial)   // 添加初始密钥材料
                .addSalt(salt)                // 添加盐值
                .thenExpand(info, 32);  // 扩展为 32 字节

        // 派生 AES 密钥
        SecretKey aeskey = hkdf.deriveKey("AES", params);

        // 或者直接获取字节数据
        byte[] data = hkdf.deriveData(params);

    }
}
