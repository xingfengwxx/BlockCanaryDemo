package com.wangxingxing.blockcanarydemo.view

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

class IOSStyleLoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(
    context, attrs, defStyleAttr
) {
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
    var color = arrayOf<String?>(
        "#a5a5a5",
        "#b7b7b7",
        "#c0c0c0",
        "#c9c9c9",
        "#d2d2d2",
        "#dbdbdb",
        "#e4e4e4",
        "#e4e4e4"
    )


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.strokeWidth = UIKits.dip2Px(context, 2).toFloat()
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        val p0 = Path()
        paint.color =
            Color.parseColor(color[0]) //Color.parseColor方法会调用String的substring()方法，substring()方法会产生新的String对象
        p0.moveTo(northwestXStart, northwestYStart)
        p0.lineTo(northwestXEnd, northwestYEnd)
        canvas.drawPath(p0, paint)
        val p1 = Path()
        paint.color = Color.parseColor(color[1])
        p1.moveTo(northXStart, northYStart)
        p1.lineTo(northXEnd, northYEnd)
        canvas.drawPath(p1, paint)
        val p2 = Path()
        paint.color = Color.parseColor(color[2])
        p2.moveTo(notheastXStart, notheastYStart)
        p2.lineTo(notheastXEnd, notheastYEnd)
        canvas.drawPath(p2, paint)
        val p3 = Path()
        paint.color = Color.parseColor(color[3])
        p3.moveTo(eastXStart, eastYStart)
        p3.lineTo(eastXEnd, eastYEnd)
        canvas.drawPath(p3, paint)
        val p4 = Path()
        paint.color = Color.parseColor(color[4])
        p4.moveTo(southeastXStart, southeastYStart)
        p4.lineTo(southeastXEnd, southeastYEnd)
        canvas.drawPath(p4, paint)
        val p5 = Path()
        paint.color = Color.parseColor(color[5])
        p5.moveTo(southXStart, southYStart)
        p5.lineTo(southXEnd, southYEnd)
        canvas.drawPath(p5, paint)
        val p6 = Path()
        paint.color = Color.parseColor(color[6])
        p6.moveTo(southwestXStart, southwestYStart)
        p6.lineTo(southwestXEnd, southwestYEnd)
        canvas.drawPath(p6, paint)
        val p7 = Path()
        paint.color = Color.parseColor(color[7])
        p7.moveTo(westXStart, westYStart)
        p7.lineTo(westXEnd, westYEnd)
        canvas.drawPath(p7, paint)
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

    fun startAnimation() {
        val valueAnimator = ValueAnimator.ofInt(7, 0)
        valueAnimator.setDuration(400)
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE)
        valueAnimator.setInterpolator(LinearInterpolator())
        valueAnimator.addUpdateListener(AnimatorUpdateListener { animation ->
            if (animation.animatedValue as Int != currentColor) {
                val b = arrayOfNulls<String>(color.size) //移动后的数组
                var c = 0
                val size = color.size - 1
                while (c < size) {
                    b[c + 1] = color[c]
                    c++
                }
                b[0] = color[color.size - 1]
                color = b
                invalidate()
                currentColor = animation.animatedValue as Int
            }
        })
        valueAnimator.start()
    }

    init {
        radius = UIKits.dip2Px(context, 9).toDouble()
        insideRadius = UIKits.dip2Px(context, 5).toDouble()
    }
}