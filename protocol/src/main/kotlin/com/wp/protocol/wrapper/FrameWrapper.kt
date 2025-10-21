package com.wp.protocol.wrapper

import com.wp.protocol.entry.HandleResult
import com.wp.protocol.entry.ProtocolStatus
import com.wp.protocol.entry.WrapperData
import com.wp.protocol.entry.DataFrame

object FrameWrapper {

    fun wrapper(arr: List<Byte>, encrypt: Byte, version: Byte): HandleResult<WrapperData> {
        val result = MetaWrapper.wrapper(arr, encrypt, version)
        return when (version) {
           0x02.toByte() -> {
               val frameWrapperData = arrayListOf(DataFrame.FRAME_FRONT)
               frameWrapperData.add(version)
               frameWrapperData.addAll(arr)
               frameWrapperData.add(DataFrame.FRAME_END)
               HandleResult(WrapperData(frameWrapperData))
           }
           0x03.toByte() -> {
               return when (result.error) {
                   ProtocolStatus.OK -> {
                       val frameWrapper = arrayListOf(DataFrame.FRAME_HEAD)
                       frameWrapper.addAll(result.data!!.data)
                       frameWrapper.add(DataFrame.FRAME_TAIL)
                       HandleResult(WrapperData(frameWrapper))
                   }
                   else -> {
                       result
                   }
               }
           }
           else -> {
               HandleResult(ProtocolStatus.NOT_SUPPORT_VERSION)
           }
        }
    }
}