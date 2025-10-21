package com.wp.protocol.frame

import com.wp.protocol.entry.HandleResult
import com.wp.protocol.entry.ProtocolStatus
import com.wp.protocol.entry.ReceiverData
import com.wp.protocol.native.LocalUtils
import com.wp.protocol.utils.ByteUtils

/**
 * 数据帧的外部通用层
 */
object GeneralLayer {

    fun parse(array: ArrayList<Byte>): HandleResult<ReceiverData> {
        val encrypt = array[6]
        val frameLength = ByteUtils.byte4ToIntBigEndian(array.subList(2, 6).toByteArray())
        if (frameLength != array.size){
            return HandleResult(ProtocolStatus.ERROR_LENGTH)
        }
        // 数据区域
        val data = array.subList(7, frameLength - 3)
        if (!checkCode(data, array.subList(frameLength - 3, frameLength - 1))){
           return HandleResult(ProtocolStatus.CHECK_ERROR)
        }

        val isEncrypt = encrypt.takeLowestOneBit()
        // 密钥位置
        when(isEncrypt){
            // 未加密
            0x00.toByte() -> {
                return DataLayer.parse(ArrayList(data));
            }
            // 加密
            else -> {
                // 解析加密算法，进行解密
                TODO("Not yet implemented")
            }
        }
    }

    // 数据帧校验
    private fun checkCode(data: MutableList<Byte>, subList: MutableList<Byte>): Boolean {
//        Log.i("CRC", "${HexUtil.encodeHexStr(subList).toShort(16)}")
//        Log.i("CRC", HexUtil.encodeHexStr(data))
//        Log.i("CRC", "${LocalUtils.crc(data.toByteArray()).toUShort()}")
        return ByteUtils.bytesToShortBigEndian(subList.toByteArray()) == LocalUtils.crc(data.toByteArray())
    }

    // 解密
    private fun decode(data: ArrayList<Byte>, encrypt: Byte): ArrayList<Byte> {
        TODO("Not yet implemented")
    }
}