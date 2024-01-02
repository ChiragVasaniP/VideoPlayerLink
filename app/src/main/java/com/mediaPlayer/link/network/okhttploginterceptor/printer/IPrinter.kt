package com.mediaPlayer.link.network.okhttploginterceptor.printer

import com.mediaPlayer.link.network.okhttploginterceptor.Priority

interface IPrinter {
    fun print(priority: Priority, tag: String, msg: String)
}