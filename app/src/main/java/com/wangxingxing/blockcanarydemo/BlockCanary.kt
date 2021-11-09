package com.wangxingxing.blockcanarydemo

import android.os.Looper

/**
 * author : 王星星
 * date : 2021/11/1 20:22
 * email : 1099420259@qq.com
 * description :
 */
object BlockCanary {
    fun install() {
        // 给MainLooper指定自己的日志输出
        val logMonitor = LogMonitor()
        Looper.getMainLooper().setMessageLogging(logMonitor)
    }
}