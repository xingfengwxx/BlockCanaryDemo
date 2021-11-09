package com.wangxingxing.blockcanarydemo

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.concurrent.atomic.AtomicBoolean

/**
 * author : 王星星
 * date : 2021/11/1 20:25
 * email : 1099420259@qq.com
 * description : 用于对 UI 主线程堆栈信息进行定期采集（采样）
 */
class StackSampler(
    // 多长时间进行一次采样，也就是每隔多长时间获取一次dispatchMessage的执行时间
    val mSampleInterval: Long
) {
    private var mHandler: Handler
    private var mRunnable: Runnable

    // 保存 UI 主线程执行的堆栈信息
    private val mStackMap: LinkedHashMap<Long, String> = LinkedHashMap()

    val SEPARATOR = "\r\n"
    val TIME_FORMATTER = SimpleDateFormat("MM-dd HH:mm:ss.SSS")

    // 设置是否继续采样的开关，当它的值为false时，code A处将退出执行
    protected val mShouldSample = AtomicBoolean(false)

    init {
        // 创建一个子线程，子线程维护一个 Looper 轮询器，Looper 维护着一个消息队列
        // UI 主线程
        val handlerThread = HandlerThread("block-canary-sampler")
        handlerThread.start()
        mHandler = Handler(handlerThread.looper)
        mRunnable = StackTraceTask(this)
    }

    /**
     * 开始采样，获取 UI 主线程执行的堆栈信息
     */
    fun startDump() {
        if (mShouldSample.get())
            return

        mShouldSample.set(true)
        // 先移除历史消息，再重新开始
        mHandler.removeCallbacks(mRunnable)
        mHandler.postDelayed(mRunnable, mSampleInterval)
    }

    /**
     * 停止采样
     */
    fun stopDump() {
        if (!mShouldSample.get())
            return
        mShouldSample.set(false)
        mHandler.removeCallbacks(mRunnable)
    }

    /**
     * 获取指定时间范围内的堆栈信息
     *
     * @param startTime
     * @param endTime
     * @return
     */
    fun getStacks(startTime: Long, endTime: Long): List<String> {
        val list = ArrayList<String>()
        synchronized(mStackMap) {
            mStackMap.mapKeys {
                if (it.key in (startTime + 1) until endTime) {
                    list.add(TIME_FORMATTER.format(it.key) + SEPARATOR + SEPARATOR + it.value)
                }
            }
        }
        return list
    }

    class StackTraceTask(stackSampler: StackSampler) : Runnable {

        // 堆栈信息的最大条数
        private val mMaxCount = 100

        private var mStackSampler: WeakReference<StackSampler> = WeakReference(stackSampler)

        override fun run() {

            mStackSampler.get()?.also {
                // 在子线程中获取 UI 主线程的堆栈信息
                val sb = StringBuilder()
                val stackTrace = Looper.getMainLooper().thread.stackTrace
                for (stackTraceElement in stackTrace) {
                    sb.appendLine(stackTraceElement.toString())
                }
                synchronized(it.mStackMap) {
                    //最多保存100条堆栈信息
                    if (it.mStackMap.size == mMaxCount) {
                        it.mStackMap.remove(it.mStackMap.keys.iterator().next())
                    }
                    it.mStackMap.put(System.currentTimeMillis(), sb.toString())
                }

                // 循环获取 UI 主线程的堆栈信息
                // -------code A--------
                if (it.mShouldSample.get()) {
                    it.mHandler.postDelayed(this, it.mSampleInterval)
                }
            }

        }

    }

}