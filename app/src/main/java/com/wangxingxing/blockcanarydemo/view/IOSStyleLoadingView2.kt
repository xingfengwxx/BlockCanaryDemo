package com.wangxingxing.blockcanarydemo.view

import com.wangxingxing.blockcanarydemo.view.UIKits.dip2Px
import kotlin.jvm.JvmOverloads
import com.wangxingxing.blockcanarydemo.view.UIKits
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class IOSStyleLoadingView2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(
    context, attrs, defStyleAttr
) {
    private lateinit var valueAnimator: ValueAnimator

    private val radius: Double
    private val insideRadius: Double
    private var northwestXStart = 0f
    private var northwestYStart = 0f
    private var northXStart = 0f
    private var northYStart = 0f
    private var notheastXStart = 0f
    private var notheastYStart = 0f
    private var eastXStart = 0f
    private var eastYStart = 0f
    private var southeastXStart = 0f
    private var southeastYStart = 0f
    private var southXStart = 0f
    private var southYStart = 0f
    private var southwestXStart = 0f
    private var southwestYStart = 0f
    private var westXStart = 0f
    private var westYStart = 0f
    private var northwestXEnd = 0f
    private var northwestYEnd = 0f
    private var northXEnd = 0f
    private var northYEnd = 0f
    private var notheastXEnd = 0f
    private var notheastYEnd = 0f
    private var eastXEnd = 0f
    private var eastYEnd = 0f
    private var southeastXEnd = 0f
    private var southeastYEnd = 0f
    private var southXEnd = 0f
    private var southYEnd = 0f
    private var southwestXEnd = 0f
    private var southwestYEnd = 0f
    private var westXEnd = 0f
    private var westYEnd = 0f
    private var currentColor = 7
    var color = arrayOf(
        "#a5a5a5",
        "#b7b7b7",
        "#c0c0c0",
        "#c9c9c9",
        "#d2d2d2",
        "#dbdbdb",
        "#e4e4e4",
        "#e4e4e4"
    )
    var colors = IntArray(color.size)
    var paint = Paint()
    var path = Path()


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        path.reset()
        paint.isAntiAlias = true
        paint.strokeWidth = dip2Px(context!!, 2).toFloat()
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        path.reset()
        paint.color =
            colors[currentColor++ % colors.size] //Color.parseColor方法会调用String的substring()方法，substring()方法会产生新的String对象
        path.moveTo(northwestXStart, northwestYStart)
        path.lineTo(northwestXEnd, northwestYEnd)
        canvas.drawPath(path, paint)
        path.reset()
        paint.color = colors[currentColor++ % colors.size]
        path.moveTo(northXStart, northYStart)
        path.lineTo(northXEnd, northYEnd)
        canvas.drawPath(path, paint)
        path.reset()
        paint.color = colors[currentColor++ % colors.size]
        path.moveTo(notheastXStart, notheastYStart)
        path.lineTo(notheastXEnd, notheastYEnd)
        canvas.drawPath(path, paint)
        path.reset()
        paint.color = colors[currentColor++ % colors.size]
        path.moveTo(eastXStart, eastYStart)
        path.lineTo(eastXEnd, eastYEnd)
        canvas.drawPath(path, paint)
        path.reset()
        paint.color = colors[currentColor++ % colors.size]
        path.moveTo(southeastXStart, southeastYStart)
        path.lineTo(southeastXEnd, southeastYEnd)
        canvas.drawPath(path, paint)
        path.reset()
        paint.color = colors[currentColor++ % colors.size]
        path.moveTo(southXStart, southYStart)
        path.lineTo(southXEnd, southYEnd)
        canvas.drawPath(path, paint)
        path.reset()
        paint.color = colors[currentColor++ % colors.size]
        path.moveTo(southwestXStart, southwestYStart)
        path.lineTo(southwestXEnd, southwestYEnd)
        canvas.drawPath(path, paint)
        path.reset()
        paint.color = colors[currentColor++ % colors.size]
        path.moveTo(westXStart, westYStart)
        path.lineTo(westXEnd, westYEnd)
        canvas.drawPath(path, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val centerX = (measuredWidth / 2).toDouble()
        val centerY = (measuredHeight / 2).toDouble()
        val leg = radius * Math.cos(Math.PI / 4)
        val insideLeg = insideRadius * Math.cos(Math.PI / 4)
        northwestXStart = (centerX - leg).toFloat()
        northwestYStart = (centerY - leg).toFloat()
        northXStart = centerX.toFloat()
        northYStart = (centerY - radius).toFloat()
        notheastXStart = (centerX + leg).toFloat()
        notheastYStart = (centerY - leg).toFloat()
        eastXStart = (centerX + radius).toFloat()
        eastYStart = centerY.toFloat()
        southeastXStart = (centerX + leg).toFloat()
        southeastYStart = (centerY + leg).toFloat()
        southXStart = centerX.toFloat()
        southYStart = (centerY + radius).toFloat()
        southwestXStart = (centerX - leg).toFloat()
        southwestYStart = (centerY + leg).toFloat()
        westXStart = (centerX - radius).toFloat()
        westYStart = centerY.toFloat()
        northwestXEnd = (centerX - insideLeg).toFloat()
        northwestYEnd = (centerY - insideLeg).toFloat()
        northXEnd = centerX.toFloat()
        northYEnd = (centerY - insideRadius).toFloat()
        notheastXEnd = (centerX + insideLeg).toFloat()
        notheastYEnd = (centerY - insideLeg).toFloat()
        eastXEnd = (centerX + insideRadius).toFloat()
        eastYEnd = centerY.toFloat()
        southeastXEnd = (centerX + insideLeg).toFloat()
        southeastYEnd = (centerY + insideLeg).toFloat()
        southXEnd = centerX.toFloat()
        southYEnd = (centerY + insideRadius).toFloat()
        southwestXEnd = (centerX - insideLeg).toFloat()
        southwestYEnd = (centerY + insideLeg).toFloat()
        westXEnd = (centerX - insideRadius).toFloat()
        westYEnd = centerY.toFloat()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        valueAnimator.apply {
            removeAllUpdateListeners()
            cancel()
        }
    }


    fun startAnimation() {
        valueAnimator = ValueAnimator.ofInt(7, 0)
        valueAnimator.apply {
            duration = 400
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()

            addUpdateListener(AnimatorUpdateListener { animation ->
                if (animation.animatedValue as Int != currentColor) {
                    invalidate()
                    currentColor = animation.animatedValue as Int
                }
            })

            start()
        }

    }

    init {
        radius = dip2Px(context!!, 9).toDouble()
        insideRadius = dip2Px(context, 5).toDouble()
        // 提前在构造方法里调用Color.parseColor解析好数据，而不是在onDraw方法里频繁调用
        for (i in color.indices) {
            colors[i] = Color.parseColor(color[i])
        }
    }
}