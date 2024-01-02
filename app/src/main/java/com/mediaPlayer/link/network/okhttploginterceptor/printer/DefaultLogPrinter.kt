package com.mediaPlayer.link.network.okhttploginterceptor.printer

import android.util.Log
import com.mediaPlayer.link.network.okhttploginterceptor.Priority

class DefaultLogPrinter : IPrinter {
    override fun print(priority: Priority, tag: String, msg: String) {
        Log.println(priority.toInt(), tag, msg)
    }
}