package com.edu.jdk.v22;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.util.Optional;

/**
 * 这个功能主要设计用于以一种全新的方式与本地代码交互, 这样使得调用本地方法可以更加高效
 * 清晰，易用, 安全，(同时对于本地方法库以无感知的方式来编写，无须 jni 的胶水层代码)
 *
 */
public class FFm {

    static {
          System.loadLibrary("ffm");
//         System.load(System.getProperty("user.dir")+"/lib/libffm.so");
    }

    public static void main(String[] args) {

        // 原始的操作 unsafe 内存的方式，现在已经被标记为删除
        // useUnsafe();
        callLibrary();

    }

    public static  void callLibrary() {
        // 获取本地方法的连接器
        Linker linker = Linker.nativeLinker();
        // 符号查找器
        SymbolLookup symbolLookup = SymbolLookup.loaderLookup();
        // Optional<MemorySegment> getVersion = SymbolLookup.libraryLookup( System.getProperty("user.dir") + "/src/main/cpp/libffm.so", Arena.ofAuto()).find("get_version");
        Optional<MemorySegment> getVersion = symbolLookup.find("get_version");
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
                getClangVersionHandler.invoke();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    // this will be called from c code
    public static void onEach(int element) {
        System.out.printf("%s", element);
    }


    // java 调用  C 语言
    /*
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
    /*
    public static void useUnsafe(){

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
    }
    */
}

