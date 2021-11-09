package com.wangxingxing.blockcanarydemo

import android.app.Application
import com.wangxingxing.blockcanarydemo.blockcanary.ChoreographerHelper

/**
 * author : 王星星
 * date : 2021/11/2 15:37
 * email : 1099420259@qq.com
 * description :
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        BlockCanary.install()

//        ChoreographerHelper.start()
    }
}