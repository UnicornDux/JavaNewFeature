package com.wp.protocol

import com.wp.protocol.entry.HandleResult
import com.wp.protocol.entry.ProtocolStatus
import com.wp.protocol.entry.ReceiverData
import com.wp.protocol.entry.WrapperData
import com.wp.protocol.frame.GeneralLayer
import com.wp.protocol.wrapper.FrameWrapper

object Receiver {

    // 解析上报的指令
    fun handle(arr: ArrayList<Byte>): HandleResult<ReceiverData> {
        val version = arr[1]
        return when (version) {
           0x01.toByte() -> {
              TODO()
           }
           0x02.toByte() -> {
              TODO()
           }
           0x03.toByte() -> {
               // 版本三 协议实现
               GeneralLayer.parse(arr)
           }
           else -> {
               HandleResult(ProtocolStatus.NOT_SUPPORT_VERSION)
           }
        }
    }

    // 包装下发的指令
    fun wrapper(arr: List<Byte>, encrypt: Byte, version: Byte): HandleResult<WrapperData>  {
        val result = FrameWrapper.wrapper(arr, encrypt, version)
        return when (result.error) {
            ProtocolStatus.OK -> {
                HandleResult(WrapperData(result.data!!.data))
            }
            else -> {
                result
            }
        }
    }
}