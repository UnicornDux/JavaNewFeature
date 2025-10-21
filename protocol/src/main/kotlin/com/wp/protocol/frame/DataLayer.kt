package com.wp.protocol.frame

import com.wp.protocol.entry.HandleResult
import com.wp.protocol.entry.ProtocolStatus
import com.wp.protocol.entry.ReceiverData
import com.wp.protocol.utils.ByteUtils
import com.wp.protocol.utils.HexUtil

object DataLayer {

    val Tag = this.javaClass.simpleName

    fun parse(array: ArrayList<Byte>): HandleResult<ReceiverData> {
        val device = array[0]
//        val validCode = array.subList(0, -2)
//        // 数据校验
//        if (!validate(array, ByteUtils.bytesToInt(validCode[0], validCode[1]))) {
//            return HandleResult(ProtocolStatus.VALID_FAILURE)
//        }
        return when (device) {
            0x01.toByte() ->  {
                handle(array, device)
            }
            0x03.toByte() -> {
                handle(array, device)
            }
            0x04.toByte() -> {
                handle(array, device)
            }
            0x05.toByte() -> {
                handle(array, device)
            }
            else -> {
                HandleResult(ProtocolStatus.NOT_SUPPORT_DEVICE)
            }
        }
    }

    private fun validate(array: ArrayList<Byte>, validateCode: Int): Boolean {
        return true;
    }

    private fun handle(arrayList: ArrayList<Byte>, deviceType: Byte):HandleResult<ReceiverData> {
        val seq = ByteUtils.bytesToInt(arrayList[2], arrayList[3]).toShort()
        val len = ByteUtils.byte4ToIntBigEndian(arrayList.subList(4, 8).toByteArray())
        val data = arrayList.subList(8, 8 + len)
        return HandleResult(
            ReceiverData(
                version = 0x03.toByte(),
                deviceType = deviceType,
                dataType = arrayList[1],
                sequence = seq,
                length = len,
                value = HexUtil.encodeHexStr(data)
            )
        )
    }
}