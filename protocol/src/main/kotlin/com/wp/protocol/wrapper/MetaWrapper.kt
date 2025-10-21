package com.wp.protocol.wrapper

import com.wp.protocol.entry.HandleResult
import com.wp.protocol.entry.ProtocolStatus
import com.wp.protocol.entry.WrapperData
import com.wp.protocol.native.LocalUtils
import com.wp.protocol.utils.ByteUtils

object MetaWrapper {

    fun wrapper(arr: List<Byte>, encrypt: Byte, version: Byte): HandleResult<WrapperData> {
        val result = EncryptWrapper.handle(arr, encrypt)
        return when (result.error) {
            ProtocolStatus.OK -> {
                val data = result.data!!.data
                val metaWrapper = arrayListOf(version)
                val length = ByteUtils.intToByteArray((data.size + 9))
                metaWrapper.addAll(length.asList())
                metaWrapper.addAll(result.data!!.data)
                val checkValue:Short = makeCheckValue(data.subList(1,data.size))
                metaWrapper.addAll(ByteUtils.shortToByteArray( checkValue).asList())
                return HandleResult(WrapperData(metaWrapper))
            }
            else -> {
                result
            }
        }
    }

    private fun makeCheckValue(metaWrapper: MutableList<Byte>): Short {
        return LocalUtils.crc(metaWrapper.toByteArray())
    }
}