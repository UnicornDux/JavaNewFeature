package com.wp.protocol.cat1

import com.google.gson.Gson
import com.wp.protocol.Receiver
import com.wp.protocol.entry.HandleResult
import com.wp.protocol.entry.ReceiverData
import com.wp.protocol.entry.WrapperData
import com.wp.protocol.utils.ByteUtils
import com.wp.protocol.utils.HexUtil
import java.nio.charset.StandardCharsets
import java.util.HashMap
import kotlin.collections.forEach
import kotlin.collections.set
import kotlin.collections.toList
import kotlin.collections.toString
import kotlin.text.isEmpty
import kotlin.text.isNotEmpty
import kotlin.text.substring
import kotlin.text.toByte
import kotlin.text.toInt

enum class AutoReaderHandle(val code: Byte  ) : ProtocolHandle {

    RESET_CONTROL(0x01.toByte()) {
        override fun handler( data: ReceiverData, handler: UsbDataHandler) {
            val result = UsbResult(this.name, data.value)
            handler.response(result)
        }
    },
    RESET_SLAVER(0x02.toByte()){
        override fun handler( data: ReceiverData, handler: UsbDataHandler) {
            val result = UsbResult(this.name, data.value.toInt(16).toString())
            handler.response(result)
        }
    },

    GET_CONTROL_ARGS(0x03.toByte()){
        override fun handler( data: ReceiverData, handler: UsbDataHandler) {
            val result = UsbResult(this.name, data.value)
            handler.response(result)
        }
    },
    GET_SLAVER_ARGS(0x04.toByte()) {
        override fun handler( data: ReceiverData, handler: UsbDataHandler) {
            val result = UsbResult(this.name, data.value)
            handler.response(result)
        }
    },
    SET_CONTROL_ARGS(0x05.toByte()){
        override fun handler( data: ReceiverData, handler: UsbDataHandler) {
            val result = UsbResult(this.name, data.value)
            handler.response(result)
        }
    },
    SET_SLAVER_ARGS(0x06.toByte()){
        override fun handler( data: ReceiverData, handler: UsbDataHandler) {
            val result = UsbResult(this.name, data.value)
            handler.response(result)
        }
    },
    AUTO_VERSION(0x0A.toByte()){
        override fun handler( data: ReceiverData, handler: UsbDataHandler) {
            val value = data.value
            // val deviceType = data.deviceType
            val version = value.substring(0,2).toInt(16)
            val deviceType = value.substring(2,4).toByte(16)
            val sn = HexUtil.decodeHex(value.substring(4)).toString(StandardCharsets.UTF_8)
            val result = UsbResult(this.name,
                // gson.toJson(AutoDeviceInfo(sn, deviceType.toInt()))
                ""
            )
            handler.response(result)
        }
    },
    READ_DATA(0x07.toByte()){
        override fun handler( data: ReceiverData, handler: UsbDataHandler) {
            val value = data.value
            val order = value.substring(0, 2).toInt(16).toString()
            val id = value.substring(2, 18)
            val type = value.substring(18, 20)
            val tags: List<ReadTag> = when (type) {
                "02" -> {
                    decodeTid(value.substring(20))
                }
                "03" -> {
                    decodeEpc(value.substring(20))
                }
                "04" -> {
                    decodeAll(value.substring(20))
                }
                else ->  {
                    ArrayList()
                }
            }
            var code = this.name
            val readResult = AutoReaderResult("0001000000${id}", order, tags)
            val result = UsbResult(code, gson.toJson(readResult))
            handler.response(result)
        }
    },

    ERROR_UPLOAD(0x09.toByte()){
        override fun handler(data: ReceiverData, handler: UsbDataHandler) {
            val result = UsbResult(this.name, data.value)
            handler.response(result)
        }
    },
    SET_TIMER(0x0B.toByte()) {
        override fun handler(data: ReceiverData, handler: UsbDataHandler) {
            val result = UsbResult(this.name, data.value)
            handler.response(result)
        }
    },
    DEVICE_CONN_STATUS(0x0D.toByte()){
        override fun handler( data: ReceiverData, handler: UsbDataHandler) {
            val address = data.value.substring(0, 2).toInt(16).toString()
            val state = data.value.substring(2, 4)
            val value = gson.toJson(AutoBandState(address, state))
            val result = UsbResult(this.name, value)
            handler.response(result)
        }
    },
    UNKNOWN(0xFF.toByte()) {
        override fun handler( data: ReceiverData, handler: UsbDataHandler) {
            val result = UsbResult(this.name, data.value)
            handler.response(result)
        }
    };

    override fun sendData(seq: Short,  bytes: ByteArray): HandleResult<WrapperData> {
        val arr = arrayListOf(DEVICE_TYPE)
        // 值类型
        arr.add(this.code.toByte())
        // 数据序列号
        arr.addAll(ByteUtils.shortToByteArray(seq).toList())
        // 值长度
        arr.addAll(ByteUtils.intToByteArray(bytes.size).toList())
        // 值
        arr.addAll(bytes.toList())
        return Receiver.wrapper(
            arr.toList(),
            encrypt = ENCRYPT,
            version = VERSION,
        )
//            .apply {
//            when(error) {
//                ProtocolStatus.OK -> {
//                    UsbHidClient.send(data!!.data.toByteArray())
//                }
//                else -> {
//                    Log.e("数据包装协议失败: Error, ", error.name)
//                }
//            }
//        }
    }

    companion object {

        const val ENCRYPT = 0x00.toByte()

        const val VERSION = 0x03.toByte()
        const val DEVICE_TYPE = 0x05.toByte()

        private val gson = Gson()

        private val COM_VALUE_MAPPING = HashMap<Byte, AutoReaderHandle>()

        init {
            values().forEach {
                COM_VALUE_MAPPING[it.code] = it
            }
        }

        fun matchValue(code: Byte): ProtocolHandle {
            return COM_VALUE_MAPPING[code] ?: UNKNOWN
        }


        private fun decodeTid(dataStr: String): List<ReadTag> {
            val result =  ArrayList<ReadTag>()
            if (dataStr.isEmpty()) return result
            var data = dataStr
            while(data.isNotEmpty()) {
                val flag = data.substring(0, 2).toInt(16)
                val rssi = data.substring(2,4).toInt(16)
                val len = data.substring(4,6).toInt(16)
                val value = data.substring(6, (6 + (len * 2)))
                data = data.substring(6 + (len * 2))
                result.add(ReadTag( flag, rssi, null, value))
            }
            return result
        }

        private fun decodeEpc(dataStr: String): List<ReadTag> {
            val result =  ArrayList<ReadTag>()
            if (dataStr.isEmpty()) return result
            var data = dataStr
            while(data.isNotEmpty()) {
                val flag = data.substring(0, 2).toInt(16)
                val len = data.substring(2,4).toInt(16)
                val value = data.substring(4, (4 + (len * 2)))
                data = data.substring(4 + (len * 2))
                result.add(ReadTag(flag, null,value, null))
            }
            return result
        }

        private fun decodeAll( dataStr: String): List<ReadTag> {
            val result =  ArrayList<ReadTag>()
            if (dataStr.isEmpty()) return result
            var data = dataStr
            while(data.isNotEmpty()) {
                val flag = data.substring(0, 2).toInt(16)
                val lenEpc = data.substring(2,4).toInt(16)
                val valueEpc = data.substring(4, (4 + (lenEpc * 2)))
                val lenTid = data.substring(4 + lenEpc * 2, 6 + lenEpc * 2).toInt(16)
                val valueTid = data.substring(6 + lenEpc * 2, 6 + (lenEpc + lenTid) * 2)
                data = data.substring(6 + (lenEpc + lenTid) * 2)
                result.add(ReadTag(flag, null,valueEpc, valueTid))
            }
            return result
        }
    }
}