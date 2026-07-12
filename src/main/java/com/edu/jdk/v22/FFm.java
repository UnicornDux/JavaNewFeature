package com.edu.jdk.v22;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Optional;


/**
 *
 * > 基于 panama 项目演化而来，已经经历了三个大版本的迭代
 * > ForeignFunctionMemory (FFM) 是 JDK 22 中引入的新特性
 *
 * ----
 * 这个功能主要设计用于以一种全新的方式与本地代码交互, 这样使得调用本地方法可以更加高效
 * 清晰，易用, 安全，(同时对于本地方法库以无感知的方式来编写，无须 jni 的胶水层代码)
 * JNI 代码需要维护头文件和构建脚本, 处理 JNIEnv 和复杂的类型转换, 一旦接口频繁变更,维护成本较高,
 *
 * > 外部函数和内存 API(FFM API) 提供了标准化,类型安全的方式来从 Java 直接调用本地代码,并在 Java
 *   侧描述函数签名和内存布局, 比起 JNI, FFM 一般不需要写 JNI 的胶水代码,调用链更简洁,可维护性更好
 *
 * > 关键概念
 *   * Linker 负责连接 Java 代码和本地代码
 *   * SymbolLookup 用来查找本地库中的函数
 *   * MethodHandle 是 Java 中已有的概念,表示本地函数的调用
 *  需要特别注意的是 Arena.ofConfined(), 这是 FFM API 中一个非常重要的内存管理概念. Arena 可以
 *  理解为一个内存管理区域, 它控制着内存的生命周期, 当你使用 try-with-resource 语句时, Arena 会在
 *  代码块结束时自动释放所有相关的内存资源,这样就避免了内存泄露的问题.
 *
 * FFM API 还有一个强大的特性是 `支持双向调用` 不仅可以从 Java 调用 C 函数,还可以把 Java 方法作为函数
 * 指针传递给 C 函数. 比如你可以用 Java 写一个比较器, 然后传给 C 标准库的函数
 *
 *
 */
public class FFm {

    static {
//          System.loadLibrary("ffm");
         String path = System.getProperty("user.dir")+"/lib/libffm.so";
//        String path = System.getProperty("user.dir")+"/lib/libsimple.so";
        System.out.println(path);
        System.load(path);
    }

    public static void main(String[] args) {

        // 原始的操作 unsafe 内存的方式，现在已经被标记为删除
        // useUnsafe();
        // callLibrary();

    }

    public static  void callLibrary() {
        // 获取本地方法的连接器
        Linker linker = Linker.nativeLinker();
        // 符号查找器
        SymbolLookup symbolLookup = SymbolLookup.loaderLookup();
        // Optional<MemorySegment> getVersion = SymbolLookup
        //     .libraryLookup(System.getProperty("user.dir") + "/src/main/cpp/libffm.so", Arena.ofAuto())
        //     .find("get_version");
        Optional<MemorySegment> getVersion = symbolLookup.find("GetLangVersion");
        if (getVersion.isPresent()) {
            System.out.println("执行 GetVersion");
            MemorySegment getClangVersion = getVersion.get();
            MethodHandle getClangVersionHandler = linker.downcallHandle(
                    // 查找到的函数地址
                    getClangVersion,
                    // 函数描述，需要传入函数的返回值类型和参数类型(参数类型为可变参数)
                    FunctionDescriptor.of(ValueLayout.JAVA_INT)
            );
            try {
                // 调用的时候可能会出现异常
                var x = (int) getClangVersionHandler.invoke();
                System.out.println(x);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    static int qsortCompare(MemorySegment ele1, MemorySegment ele2) {
        return Integer.compare(
            ele1.get(ValueLayout.JAVA_INT, 0),
            ele2.get(ValueLayout.JAVA_INT, 0)
        );
    }

    public static void demo() throws NoSuchMethodException, IllegalAccessException {
        Linker linker = Linker.nativeLinker();
        // 找到 qsort 函数
        MethodHandle qsort = linker.downcallHandle(
            linker.defaultLookup().findOrThrow("qsort"),
            FunctionDescriptor.ofVoid(
                    ValueLayout.ADDRESS,
                    ValueLayout.JAVA_LONG,
                    ValueLayout.JAVA_LONG,
                    ValueLayout.ADDRESS
            )
        );
        // 把 java 方法包装成函数指针
        MethodHandle compareHandle = MethodHandles.lookup()
                .findStatic(
                    FFm.class,
                    "qsortCompare",
                    MethodType.methodType(
                        int.class,
                        MemorySegment.class,
                        MemorySegment.class
                ));
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment compareFunc = linker.upcallStub(
                compareHandle,
                FunctionDescriptor.of(
                    ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS.withTargetLayout(ValueLayout.JAVA_INT),
                    ValueLayout.ADDRESS.withTargetLayout(ValueLayout.JAVA_INT)
                ),
                arena
            );

            // Allocate array of integers
            int[] array = {5, 2, 8, 1, 9, 3};
            MemorySegment arraySegment = arena.allocateFrom(ValueLayout.JAVA_INT, array);

            // Call qsort with the compare function pointer
            qsort.invoke(
                arraySegment,           // array pointer
                array.length,           // number of elements
                ValueLayout.JAVA_INT.byteSize(),  // size of each element
                compareFunc             // comparison function pointer
            );

            // Print sorted array
            for (int i = 0; i < array.length; i++) {
                System.out.println(arraySegment.get(
                    ValueLayout.JAVA_INT,
                    i * ValueLayout.JAVA_INT.byteSize()
                ));
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    // this will be called from c code
    public static void onEach(int element) {
        System.out.printf("%s", element);
    }


    /**
    // java 调用  C 语言
    public static void callNative() {

        Linker linker = Linker.nativeLinker();
        // 获取标准库的符号链接
        SymbolLookup stdlib = linker.defaultLookup();
        // 从标准库中找到某个函数
        MethodHandle redixsort = linker.downcallHandle(
            stdlib.find("radixsort").get(),
            FunctionDescriptor.of(null)
        );
        // 在堆上构建一个数组存储四个元素
        String[] things = { "mouse", "cat", "dog", "car" };
        // 可以受用 try resource 来管理这个 off-heap 的内存资源
        try (Arena offHeap = Arena.ofConfined()) {
           // 申请一块内存来存储这个数组的四个元素
            MemorySegment pointers = offHeap.allocate(
                    ValueLayout.ADDRESS,
                    MemorySegment.ofAddress(things.length)
            );
            // java 中堆上内容复制到申请的这块内存中
            for (int i = 0; i < things.length; i++) {
                // MemorySegment cString = offHeap.allocate(javaStrings[i])
            }
        }
    }
     */

    // 原始的 unsafe 获取内存，并操作内存的方式
    // JDK 22 之后这些内容已经被标记为删除
    public static void useUnsafe(){

        /*
        try {
            Field unSafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unSafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) unSafeField.get(null);
            long handle = unsafe.allocateMemory(16);
            // 在内存中存储值，获取内存中的值
            unsafe.putDouble(handle, 1024);
            System.out.println(unsafe.getDouble(handle));

            unsafe.freeMemory(handle);
            System.out.println(unsafe.getInt(handle));

        } catch (NoSuchFieldException | IllegalAccessException e){
            throw new RuntimeException(e);
        }
        */
    }
}

