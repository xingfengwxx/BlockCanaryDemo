package com.wangxingxing.blockcanarydemo.view

import android.content.Context

object UIKits {
    @JvmStatic
    fun dip2Px(context: Context, dpValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}