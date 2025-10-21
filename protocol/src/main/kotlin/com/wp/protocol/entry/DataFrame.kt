package com.wp.protocol.entry

class DataFrame {

    // 帧定义
    companion object {
        // 新版
        const val FRAME_HEAD = 0xBB.toByte()
        const val FRAME_TAIL = 0x7E.toByte()

        // 旧版
        const val FRAME_FRONT = 0xAB.toByte()
        const val FRAME_END = 0xFE.toByte()
    }

}