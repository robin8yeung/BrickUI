package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import com.seewo.brick.init.applyMargin
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.view.WindowInsetsFrameLayout


/**
 * 构建帧布局
 */
fun ViewGroup.frameLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    block: (FrameLayout.() -> Unit)? = null
) = context.frameLayout(
    width,
    height,
    style,
    id,
    tag,
    foreground,
    background,
    padding,
    visibility,
    isSelected,
    onClick,
    fitsSystemWindows,
    block
).also {
    addView(it)
}.applyMargin(margin)

/**
 * 构建帧布局
 */
fun Context.frameLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    block: (FrameLayout.() -> Unit)? = null
) = FrameLayout(this, null, 0, style).apply {
    setup(
        width, height, id, tag, foreground, background,
        padding, visibility, isSelected,
        onClick = onClick, fitsSystemWindows = fitsSystemWindows
    )
}.attach(block)

/**
 * 构建可分发WindowInsets的帧布局
 *
 * 避免在1个Activity中使用多个Fragment时导致fitsSystemWindows设置无效
 */
fun ViewGroup.windowInsetsFrameLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    block: (WindowInsetsFrameLayout.() -> Unit)? = null
) = context.windowInsetsFrameLayout(
    width,
    height,
    style,
    id,
    tag,
    foreground,
    background,
    padding,
    visibility,
    isSelected,
    onClick,
    fitsSystemWindows,
    block
).also {
    addView(it)
}.applyMargin(margin)

/**
 * 构建可分发WindowInsets的帧布局
 *
 * 避免在1个Activity中使用多个Fragment时导致fitsSystemWindows设置无效
 */
fun Context.windowInsetsFrameLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    block: (WindowInsetsFrameLayout.() -> Unit)? = null
) = WindowInsetsFrameLayout(this, null, 0, style).apply {
    setup(
        width, height, id, tag, foreground, background,
        padding, visibility, isSelected,
        onClick = onClick, fitsSystemWindows = fitsSystemWindows
    )
}.attach(block)

/**
 * 声明帧布局居中的布局属性
 */
fun FrameLayout.center(
    margins: EdgeInsets? = null,
    block: (FrameLayout.() -> View)? = null
) = attach(block)?.apply {
    layoutParams = when(layoutParams) {
        is FrameLayout.LayoutParams -> layoutParams as FrameLayout.LayoutParams
        is ViewGroup.MarginLayoutParams -> FrameLayout.LayoutParams(layoutParams as ViewGroup.MarginLayoutParams)
        else -> FrameLayout.LayoutParams(layoutParams)
    }.apply {
        this.gravity = Gravity.CENTER
        margins?.let { setMargins(it.start, it.top, it.end, it.bottom) }
    }
}

/**
 * 声明帧布局的布局属性
 */
fun FrameLayout.layoutParams(
    gravity: Int? = null,
    margins: EdgeInsets? = null,
    block: (FrameLayout.() -> View)? = null
) = attach(block)?.apply {
    layoutParams = when(layoutParams) {
        is FrameLayout.LayoutParams -> layoutParams as FrameLayout.LayoutParams
        is ViewGroup.MarginLayoutParams -> FrameLayout.LayoutParams(layoutParams as ViewGroup.MarginLayoutParams)
        else -> FrameLayout.LayoutParams(layoutParams)
    }.apply {
        gravity?.let { this.gravity = it }
        margins?.let { setMargins(it.start, it.top, it.end, it.bottom) }
    }
}

/**
 * 声明帧布局的布局属性
 */
fun FrameLayout.layoutParams(
    apply: (FrameLayout.LayoutParams.() -> Unit)? = null,
    block: (FrameLayout.() -> View)? = null
) = attach(block)?.apply {
    apply?.let {
        layoutParams = when(layoutParams) {
            is FrameLayout.LayoutParams -> layoutParams as FrameLayout.LayoutParams
            is ViewGroup.MarginLayoutParams -> FrameLayout.LayoutParams(layoutParams as ViewGroup.MarginLayoutParams)
            else -> FrameLayout.LayoutParams(layoutParams)
        }.apply {
            it()
        }
    }
}

/**
 * 声明帧布局的布局属性
 */
@Deprecated("使用 FrameLayout.layoutParams")
fun <T: View> T.inFrameLayout(
    gravity: Int? = null,
    margins: EdgeInsets? = null
) = apply {
    layoutParams = when(layoutParams) {
        is FrameLayout.LayoutParams -> layoutParams as FrameLayout.LayoutParams
        is ViewGroup.MarginLayoutParams -> FrameLayout.LayoutParams(layoutParams as ViewGroup.MarginLayoutParams)
        else -> FrameLayout.LayoutParams(layoutParams)
    }.apply {
        gravity?.let { this.gravity = it }
        margins?.let { setMargins(it.start, it.top, it.end, it.bottom) }
    }
}
