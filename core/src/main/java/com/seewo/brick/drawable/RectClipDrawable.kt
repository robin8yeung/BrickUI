package com.seewo.brick.drawable

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF


class RectClipDrawable(private val drawable: Drawable, private val radius: Float) :
    Drawable() {
    private var mBitmap = if (drawable is BitmapDrawable) drawable.bitmap else drawable.toBitmap()
    private var mPaint = Paint().apply {
        isAntiAlias = true
        isDither = true
        shader = BitmapShader(
            mBitmap,
            Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
        )
    }
    private val mWidth: Int = mBitmap.width
    private val mHeight: Int = mBitmap.height
    private var mRectF: RectF = RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat())

    //绘制
    override fun draw(canvas: Canvas) {
        Log.e("brick", "radius = $radius rect = $mRectF")
        canvas.drawRoundRect(mRectF, radius, radius, mPaint)
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        mRectF = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
        mPaint.shader = BitmapShader(
            drawable.toBitmap(right - left, bottom - top).also {
                mBitmap = it
            },
            Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
        )
    }

    override fun setBounds(bounds: Rect) {
        super.setBounds(bounds)
        mRectF = bounds.toRectF()
        mPaint.shader = BitmapShader(
            drawable.toBitmap(bounds.width(), bounds.height()).also {
                mBitmap = it
            },
            Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
        )
    }

    //设置透明度值
    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    //设置颜色过滤器
    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    //返回不透明度
    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    //返回图片实际的宽高
    override fun getIntrinsicWidth(): Int {
        return mBitmap.width
    }

    override fun getIntrinsicHeight(): Int {
        return mBitmap.height
    }
}