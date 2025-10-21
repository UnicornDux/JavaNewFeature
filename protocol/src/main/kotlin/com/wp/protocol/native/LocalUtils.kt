package com.wp.protocol.native

import java.io.File


object LocalUtils {

    init {
        // System.loadLibrary("com/wp/protocol")
        var libName: String? = null
        val os = System.getProperty("os.name").lowercase();
        val arch = System.getProperty("os.arch").lowercase();
        if (os.contains("win") && (arch == "x86_64" || arch == "amd64")) {
          libName = "libprotocol.dll"
        }else if (os.contains("linux") && (arch == "x86_64" || arch == "amd64")) {
          libName = "libprotocol.so"
        }else if (os.contains("mac")) {
          if (arch == "x86_64" || arch == "amd64") {
            libName = "libprotocol.dylib"
          }else if (arch == "aarch64") {
            libName = "libprotocol.dylib"
          }
        }
        if (libName == null) {
          System.err.println("os: $os not support")
          throw RuntimeException("os: $os not support")
        }
        System.load(System.getProperty("user.dir") + File.separator + "libs" + File.separator + libName);
    }

    external fun crc(byteArray: ByteArray): Short
}