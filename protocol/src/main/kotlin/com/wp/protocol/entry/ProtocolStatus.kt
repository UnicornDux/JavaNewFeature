package com.wp.protocol.entry

enum class ProtocolStatus(private var message: String?){
    ERROR_LENGTH("数据帧长度异常"),
    CHECK_ERROR("校验错误"),
    NOT_SUPPORT_VERSION("不支持的版本"),
    NOT_SUPPORT_DEVICE("不支持的设备"),
    NOT_SUPPORT_TYPE("不支持的指令类型"),
    VALID_FAILURE("数据校验失败"),
    OK(null);
}
