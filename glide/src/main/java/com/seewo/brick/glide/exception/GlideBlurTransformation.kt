package com.seewo.brick.glide.exception

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest
import kotlin.math.roundToInt

private const val BITMAP_SCALE = 0.5f

class GlideBlurTransformation(private val context: Context, private val radius: Float) :
    CenterCrop() {
    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight)
        return bitmap.blur(context, radius)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {}
}

/**
 * 模糊图片
 */
private fun Bitmap.blur(context: Context?, blurRadius: Float): Bitmap {
    // 计算图片缩小后的长宽
    val width = (width * BITMAP_SCALE).roundToInt()
    val height = (height * BITMAP_SCALE).roundToInt()

    // 将缩小后的图片做为预渲染的图片
    val inputBitmap = Bitmap.createScaledBitmap(this, width, height, false)
    // 创建一张渲染后的输出图片
    val outputBitmap = Bitmap.createBitmap(inputBitmap)

    // 创建RenderScript内核对象
    val rs = RenderScript.create(context)
    // 创建一个模糊效果的RenderScript的工具对象
    val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

    // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
    // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
    val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
    val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)

    // 设置渲染的模糊程度, 25f是最大模糊度
    blurScript.setRadius(blurRadius)
    // 设置blurScript对象的输入内存
    blurScript.setInput(tmpIn)
    // 将输出数据保存到输出内存中
    blurScript.forEach(tmpOut)

    // 将数据填充到Allocation中
    tmpOut.copyTo(outputBitmap)
    return outputBitmap
}