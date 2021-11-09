package com.wangxingxing.blockcanarydemo.blockcanary

import android.os.Build
import android.util.Log
import android.view.Choreographer
import android.view.Choreographer.FrameCallback

/**
 * author : 王星星
 * date : 2021/11/2 17:30
 * email : 1099420259@qq.com
 * description :
 */
object ChoreographerHelper {

    val TAG = "ChoreographerHelper"

    var lastFrameTimeNanos: Long = 0

    fun start() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Choreographer.getInstance().postFrameCallback(object : FrameCallback {
                // 此方法提供帧开始渲染时的时间（以纳秒为单位）。
                // 1 纳秒=0.000001 毫秒
                override fun doFrame(frameTimeNanos: Long) {
                    if (lastFrameTimeNanos == 0L) {
                        lastFrameTimeNanos = frameTimeNanos
                        Choreographer.getInstance().postFrameCallback(this)
                        return
                    }
                    val diff = (frameTimeNanos - lastFrameTimeNanos) / 1000000
                    if (diff > 16.6f) {
                        //掉帧数
                        val droppedCount = (diff / 16.6).toInt()
                        Log.d(TAG, "doFrame: 掉帧：$droppedCount")
                    }
                    lastFrameTimeNanos = frameTimeNanos
                    Choreographer.getInstance().postFrameCallback(this)
                }
            })
        }
    }

}