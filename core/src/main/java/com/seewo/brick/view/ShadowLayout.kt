package com.seewo.brick.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.seewo.brick.ktx.dp


class ShadowLayout : FrameLayout {

    var customBackgroundColor: Int = Color.WHITE
        set(value) {
            field = value
            postInvalidate()
        }

    var backgroundRadius: Float = 0f
        set(value) {
            field = value
            postInvalidate()
        }

    var shadowOffsetX = 0f
        set(value) {
            field = value
            updatePaint()
            updateRect()
            requestLayout()
        }

    var shadowOffsetY = 0f
        set(value) {
            field = value
            updatePaint()
            updateRect()
            requestLayout()
        }
    var shadowColor = Color.BLACK
        set(value) {
            field = value
            updatePaint()
            postInvalidate()
        }

    var shadowRadius = 2.dp.toFloat()
        set(value) {
            field = value
            updateRect()
            updatePaint()
            requestLayout()
        }

    private val backgroundRect = RectF()
    private val backgroundPaint = Paint()
    private val initPaddingRect = Rect()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        setWillNotDraw(false)
        updatePaint()
        initPaddingRect.set(paddingStart, paddingTop, paddingEnd, paddingBottom)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        updateRect()
        super.onLayout(changed, left, top, right, bottom)
    }

    private fun updatePaint() {
        backgroundPaint.apply {
            color = customBackgroundColor
            setShadowLayer(shadowRadius, shadowOffsetX, shadowOffsetY, shadowColor)
        }
    }

    private fun updateRect() {
        setPadding(
            (initPaddingRect.left + shadowRadius - shadowOffsetX).toInt(),
            (initPaddingRect.top + shadowRadius - shadowOffsetY).toInt(),
            (shadowRadius + initPaddingRect.right + shadowOffsetX).toInt(),
            (shadowRadius + initPaddingRect.bottom + shadowOffsetY).toInt(),
        )
        if (width == 0 || height == 0) return
        backgroundRect.set(
            paddingStart.toFloat(),
            paddingTop.toFloat(),
            (width - paddingEnd).toFloat(),
            (height - paddingBottom).toFloat()
        )
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRoundRect(backgroundRect, backgroundRadius, backgroundRadius, backgroundPaint)
        super.onDraw(canvas)
    }
}