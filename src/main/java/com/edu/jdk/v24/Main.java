package com.edu.jdk.v24;

import javax.sound.midi.Soundbank;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.classfile.MethodModel;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.MethodTypeDesc;

/**
 *  类文件 API
 *
 *  类文件 API 是一个专为框架工具开发者设计的强大特性,长期以来,如果你想要在运行时动态生成,分析或者修改 Java 字节码
 *  就必须依赖像 ASM, Javassist 或者 CGLIB 这样的第三方库.
 *  而操作字节码需要深入了解底层细节,学习难度很大.
 *
 *
 */
public class Main {
    static void main() {
        Main main = new Main();
        byte[] classBytes = main.generateClass();
        System.out.println("Generated class bytes length: " + classBytes.length);
    }

    // 生成一个类
    public byte[] generateClass() {
        return ClassFile.of().build(ClassDesc.of("com.edu.generate.DomClass"),
            // 添加默认构建函数
            classBuilder -> {
                classBuilder.withVersion(61, 0); // Java 17
                classBuilder.withFlags(ClassFile.ACC_PUBLIC);

                classBuilder.withMethod("<init>", MethodTypeDesc.of(ConstantDescs.CD_Void),
                    ClassFile.ACC_PUBLIC, methodBuilder -> {
                        methodBuilder.withCode(codeBuilder -> {
                            codeBuilder.aload(0)
                            .invokespecial(ConstantDescs.CD_Object, "<init>", MethodTypeDesc.of(ConstantDescs.CD_Void))
                            .return_();
                        });
                    });
                classBuilder.withMethod("sayHello", MethodTypeDesc.of(ConstantDescs.CD_String),
                    ClassFile.ACC_PUBLIC, methodBuilder -> {
                        methodBuilder.withCode(codeBuilder -> {
                            codeBuilder.ldc("hello form genetated code")
                                    .areturn();
                        });
                    });
            }
        );
    }

    // 使用这些类文件 API 分析现有的类也更加轻松
    public void analyzeClass(byte[] ClassBytes) {
        ClassModel cm = ClassFile.of().parse(ClassBytes);

        System.out.println("类名: " + cm.thisClass().asInternalName());
        System.out.println("方法列表: ");
        for(MethodModel method: cm.methods()){
            System.out.println("  -" + method.methodName().stringValue() + method.methodType().stringValue());
        }
    }
}