package com.wp.protocol.entry

data class ReceiverData(
    val version: Byte,
    val deviceType: Byte,
    val dataType: Byte,
    val sequence: Short,
    val length: Int,
    val value: String,
);
