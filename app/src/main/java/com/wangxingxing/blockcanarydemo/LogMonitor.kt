package com.wangxingxing.blockcanarydemo

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Printer
import java.lang.ref.WeakReference
import kotlin.math.log

/**
 * author : 王星星
 * date : 2021/11/1 20:23
 * email : 1099420259@qq.com
 * description :
 */
class LogMonitor : Printer {

    val TAG = "block-canary"

    // 打印开关，开始执行dispatchMessage方法时，mPrintingStarted 设置为true，该方法执行完再设置为false
    // 下一次dispatchMessage再执行时，再进行同样的操作，循环往复
    private var mPrintingStarted = false

    // 记录开始执行dispatchMessage的时间戳
    private var mStartTimestamp: Long = 0

    // 卡顿阈值
    private val mBlockThresholdMillis: Long = 30

    // 采样频率，
    // 虽然dispatchMessage执行的频率很高，由于使用了handler.postDelayed方法，但是获取主线程堆栈信息的频率，被降下来了
    val mSampleInterval: Long = 10

    private var mStackSampler: StackSampler = StackSampler(mSampleInterval)
    private var mLogHandler: Handler

    init {
        val handlerThread = HandlerThread("block-canary-io")
        handlerThread.start()
        mLogHandler = Handler(handlerThread.looper)
    }

    override fun println(x: String?) {
        //从if到else会执行 dispatchMessage，如果执行耗时超过阈值，输出卡顿信息
        if (!mPrintingStarted) {
            //记录开始时间
            mStartTimestamp = System.currentTimeMillis()
            mPrintingStarted = true
            // 开始采集
            mStackSampler.startDump()
        } else {
            val endTime = System.currentTimeMillis()
            mPrintingStarted = false
            // 出现卡顿
            if (isBlock(endTime)) {
                Log.e(TAG, "出现卡顿" )
                notifyBlockEvent(endTime)
            }
            mStackSampler.stopDump()
        }
    }

    /**
     * 是否卡顿
     * 当dispatchMessage方法执行时长超过阈值，则为卡顿
     *
     * @param endTime
     * @return
     */
    private fun isBlock(endTime: Long): Boolean {
        return endTime - mStartTimestamp > mBlockThresholdMillis
    }

    /**
     * 在主线程卡顿时，次方法用于获取UI 主线程的堆栈信息，并打印
     *
     * @param endTime
     */
    private fun notifyBlockEvent(endTime: Long) {
        mLogHandler.post(LogTask(this, endTime))
    }

    class LogTask(
        logMonitor: LogMonitor,
        private val endTime: Long
    ) : Runnable {

        private var mLogMonitor: WeakReference<LogMonitor> = WeakReference(logMonitor)

        override fun run() {
            mLogMonitor.get()?.also {
                val stacks = it.mStackSampler.getStacks(it.mStartTimestamp, endTime)
                for (stack in stacks) {
                    Log.e(it.TAG, stack)
                }
            }
        }

    }
}