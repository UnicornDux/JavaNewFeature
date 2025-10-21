package com.wp.protocol.wrapper

import com.wp.protocol.entry.HandleResult
import com.wp.protocol.entry.WrapperData

object EncryptWrapper {

    fun handle(arr: List<Byte>, encrypt: Byte): HandleResult<WrapperData> {
       val x = encrypt.takeLowestOneBit()
       val encryptWrapper = arrayListOf(encrypt)
       return when (x) {
           0x01.toByte() -> {
               val encryptData = doEncrypt(arr, encrypt)
               encryptWrapper.addAll(encryptData)
               HandleResult(WrapperData(encryptWrapper))
           }
           0x00.toByte() -> {
               encryptWrapper.addAll(arr)
               HandleResult(WrapperData(encryptWrapper))
           }
           else -> {
               TODO()
           }
       }
    }

    private fun doEncrypt(arr:List<Byte>, encrypt: Byte): List<Byte> {
        return arr;
    }

}