package com.wp.protocol.cat1

import com.wp.protocol.entry.HandleResult
import com.wp.protocol.entry.ReceiverData
import com.wp.protocol.entry.WrapperData


interface ProtocolHandle {

    // 处理数据的函数
    fun handler(data: ReceiverData, handler: UsbDataHandler)

    // 数据发送
    fun sendData(seq: Short, data: ByteArray): HandleResult<WrapperData>;
}
