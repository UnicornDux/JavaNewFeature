package com.wp.protocol.cat1

data class UsbResult (
    val requestCode: String,
    val data: String,
)

data class ReadTag(
    // 标签天线的序号
    val flag: Int,
    val rssi: Int?,
    // 读取到的 epc 值
    val epc: String?,
    // 读取到的 tid 值
    val tid: String?,
)

data class AutoReaderResult(
    val id: String,
    // 盘片所在的顺序
    val order: String,
    // 扫描的标签的列表
    val tags: List<ReadTag>
)

// 条带状态
data class AutoBandState(
    // 条带编号
    val order: String,
    // 条带状态
    val state: String
)
