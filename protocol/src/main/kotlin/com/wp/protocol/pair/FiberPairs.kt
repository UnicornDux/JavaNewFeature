package com.wp.bluetooth.devices.v3

import com.google.gson.Gson
import com.wp.bluetooth.entity.InitValue
import com.wp.bluetooth.entity.PairValue
import com.wp.protocol.Receiver
import com.wp.protocol.cat1.ProtocolHandle
import com.wp.protocol.cat1.UsbDataHandler
import com.wp.protocol.cat1.UsbResult
import com.wp.protocol.entry.HandleResult
import com.wp.protocol.entry.ReceiverData
import com.wp.protocol.entry.WrapperData
import com.wp.protocol.utils.ByteUtils
import com.wp.protocol.utils.HexUtil


enum class FiberPairs(val code: Byte): ProtocolHandle {

    // "获取设备信息"
    DEVICE_INFO(0xBB.toByte()) {
        override fun handler(data: ReceiverData, handler: UsbDataHandler) {
            println("${this.name} - ${data.value}")
        }
    },

     // "系统更新"
    UPDATE(0xB6.toByte()) {
        override fun handler(data: ReceiverData, handler: UsbDataHandler) {
            println("${this.name} - ${data.value}")
        }
    },

    //  "开机上报"
    START_UP(0xC0.toByte()){
        override fun handler(data: ReceiverData, handler: UsbDataHandler ) {
            val num = data.value.substring(0, 2)
            val deviceState = data.value.substring(2, 4)
            val deviceCode = String(HexUtil.decodeHex(data.value.substring(4, 14)))
            val electricity = data.value.substring(14, 16).toInt(16)
            val lightIntensity = data.value.substring(16, 24).toInt(16)
            val result = UsbResult(
                this.name,
                gson.toJson(InitValue(deviceCode, electricity, lightIntensity))
            )
            handler.response(result)
        }
    },

    // "触发数据"
    DATA_UPLOAD(0x92.toByte()){
        override fun handler(data: ReceiverData, handler: UsbDataHandler ) {
            val num = data.value.substring(0, 2)
            val deviceState = data.value.substring(2, 4)
            val deviceCode = String(HexUtil.decodeHex(data.value.substring(4, 14)))
            val electricity = data.value.substring(14, 16).toInt(16)
            val lightIntensity = data.value.substring(16, 24).toInt(16)
            val signal = data.value.substring(28, 32)
            val result = UsbResult(
                this.name,
                gson.toJson(PairValue(deviceCode, electricity, lightIntensity, signal))
            )
            handler.response(result)
        }
    },
    // "不支持的指令类型"
    UNKNOWN(0xFF.toByte()) {
        override fun handler(data: ReceiverData, handler: UsbDataHandler) {
            println("${this.name} - ${data.value}")
        }
    };

    // 数据发送
    override fun sendData(seq: Short, data: ByteArray): HandleResult<WrapperData> {
        val arr = arrayListOf(DEVICE_TYPE)
        // 值类型
        arr.add(this.code.toByte())
        // 数据序列号
        arr.addAll(ByteUtils.shortToByteArray(seq).toList())
        // 值长度
        arr.addAll(ByteUtils.intToByteArray(data.size).toList())
        // 值
        arr.addAll(data.toList())
        val result = Receiver.wrapper(
            arr.toList(),
            encrypt = ENCRYPT,
            version = VERSION
        )
        return result
//        when(result.error){
//            ProtocolStatus.OK -> {
//                BleManager.sendInstruct(
//                    mac,
//                    result.data?.data?.toByteArray(),
//                    null
//                )
//            }
//            else -> {
//                Log.e("数据包装协议失败: Error", result.error.name)
//            }
//        }
    }

    companion object {
        const val ENCRYPT = 0x00.toByte()

        // 当前视频版本的蓝牙扫描器定位为版本 0x03 版本
        const val DEVICE_TYPE = 0x04.toByte()

        private const val VERSION = 0x03.toByte()

        private val gson = Gson()


        private val ELE_VALUE_MAPPING = HashMap<Byte, FiberPairs>();
        init {
            values().forEach {
                ELE_VALUE_MAPPING[it.code] = it
            }
        }
        fun matchValue(code: Byte): FiberPairs {
            return ELE_VALUE_MAPPING[code] ?: UNKNOWN
        }
    }
}