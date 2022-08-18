package com.seewo.brick

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat

@SuppressLint("StaticFieldLeak")
object BrickUI {
    private lateinit var context: Context

    /**
     * 通过StartUp调用，不需要自行调用
     * @see com.seewo.brick.init.BrickUIInitializer
     */
    fun init(context: Context) {
        Log.d("BrickUI", "[ Hello world ]")
        this.context = context.applicationContext
    }

    //把px转换成dp
    fun dipToPixel(dip: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dip,
            context.resources.displayMetrics
        ).toInt()
    }

    fun spToPixel(sp: Float): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, sp,
        context.resources.displayMetrics
    ).toInt()

    fun pxToDp(px: Int): Int {
        return pxToDp(context, px)
    }

    private fun pxToDp(context: Context, px: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    fun string(@StringRes id: Int) = context.getString(id)

    fun string(@StringRes id: Int, vararg params: Any) = context.getString(id, *params)

    fun color(@ColorRes color: Int, theme: Resources.Theme? = null) =
        ResourcesCompat.getColor(context.resources, color, theme)

    fun drawable(@DrawableRes drawable: Int, theme: Resources.Theme? = null): Drawable? =
        ResourcesCompat.getDrawable(context.resources, drawable, theme)

    fun dimension(@DimenRes id: Int) =
        context.resources.getDimension(id).toInt()

    val applicationContext: Context
        get() = context
}
