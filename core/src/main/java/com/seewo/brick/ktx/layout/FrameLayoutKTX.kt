package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
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
    background: Drawable? = null,
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
    background,
    padding,
    visibility,
    isSelected,
    onClick,
    fitsSystemWindows,
    block
).also {
    addView(it)
}

/**
 * 构建帧布局
 */
fun Context.frameLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    block: (FrameLayout.() -> Unit)? = null
) = FrameLayout(this, null, 0, style).apply {
    setup(
        width, height, id, tag, background, padding, visibility, isSelected,
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
    background: Drawable? = null,
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
    background,
    padding,
    visibility,
    isSelected,
    onClick,
    fitsSystemWindows,
    block
).also {
    addView(it)
}

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
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    block: (WindowInsetsFrameLayout.() -> Unit)? = null
) = WindowInsetsFrameLayout(this, null, 0, style).apply {
    setup(
        width, height, id, tag, background, padding, visibility, isSelected,
        onClick = onClick, fitsSystemWindows = fitsSystemWindows
    )
}.attach(block)

/**
 * 声明帧布局的布局属性
 */
fun FrameLayout.layoutParams(
    gravity: Int? = null,
    margins: EdgeInsets? = null,
    block: (FrameLayout.() -> View)? = null
) = attach(block)?.apply {
    layoutParams = FrameLayout.LayoutParams(layoutParams).apply {
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
        layoutParams = FrameLayout.LayoutParams(layoutParams).apply {
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
    layoutParams = FrameLayout.LayoutParams(layoutParams).apply {
        gravity?.let { this.gravity = it }
        margins?.let { setMargins(it.start, it.top, it.end, it.bottom) }
    }
}
