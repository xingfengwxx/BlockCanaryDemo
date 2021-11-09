package com.wangxingxing.blockcanarydemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.wangxingxing.blockcanarydemo.databinding.ActivityMainBinding

/**
 * author : 王星星
 * date : 2021/11/1 10:53
 * email : 1099420259@qq.com
 * description : 
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        init()
    }

    private fun init() {
        mBinding.apply {
            btnShow.setOnClickListener {
                //模拟不断创建对象，造成频繁GC
                loading.visibility = View.VISIBLE
            }

            btnHide.setOnClickListener {
                loading.visibility = View.GONE
            }

            btnBlock.setOnClickListener {
                Thread.sleep(1000)
            }
        }
    }
}