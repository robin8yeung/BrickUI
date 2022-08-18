package com.seewo.brick.drawable

import android.graphics.*
import android.graphics.Shader.TileMode
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import kotlin.math.min


class OvalClipDrawable : Drawable {
    private val mBitmap: Bitmap
    private val mPaint : Paint = Paint()
    private val mRadius : Float
    private var mRectF : RectF

    constructor(bitmap: Bitmap) {
        mBitmap = bitmap
        init()
        mRadius = min(mBitmap.width, mBitmap.height) / 2f
        mRectF = RectF(0f, 0f, mBitmap.width.toFloat(), mBitmap.height.toFloat())
    }

    constructor(drawable: Drawable) {
        mBitmap = drawable.toBitmap()
        init()
        mRadius = min(mBitmap.width, mBitmap.height) / 2f
        mRectF = RectF(0f, 0f, mBitmap.width.toFloat(), mBitmap.height.toFloat())
    }

    private fun init() {
        //初始化画笔
        mPaint.apply {
            isAntiAlias = true
            isDither = true
            shader = BitmapShader(mBitmap, TileMode.CLAMP, TileMode.CLAMP)
        }
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        mRectF = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(mBitmap.width / 2f, mBitmap.height / 2f, mRadius, mPaint)
    }

    override fun getIntrinsicWidth(): Int {
        return (mRadius * 2).toInt()
    }

    override fun getIntrinsicHeight(): Int {
        return (mRadius * 2).toInt()
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        mPaint.colorFilter = cf
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}