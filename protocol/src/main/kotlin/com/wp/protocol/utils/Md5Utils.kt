package com.wp.protocol.utils

import java.security.MessageDigest

object Md5Utils{
     /**
     *  MD5加密后生成32位(小写字母+数字)字符串
          *    同 MD5Lower() 一样
     */
    fun  md5Upper(arr: ByteArray): String {
        val md = MessageDigest.getInstance("MD5");
        md.update(arr)
        val mdValue = md.digest();
        return String(HexUtil.encodeHex(mdValue))
    }

}