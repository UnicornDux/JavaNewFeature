package com.wp.protocol.entry


data class HandleResult<D>(var data: D?, var error: ProtocolStatus) {
    constructor(data: D) : this(data, ProtocolStatus.OK)
    constructor(error: ProtocolStatus) : this(null, error)
}

