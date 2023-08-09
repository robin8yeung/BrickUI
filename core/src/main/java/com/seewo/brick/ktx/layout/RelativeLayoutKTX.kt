package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import com.seewo.brick.init.applyMargin
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets


/**
 * 构建相对布局
 */
fun ViewGroup.relativeLayout(
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

    block: (RelativeLayout.() -> Unit)? = null
) = context.relativeLayout(
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
 * 构建相对布局
 */
fun Context.relativeLayout(
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

    block: (RelativeLayout.() -> Unit)? = null
) = RelativeLayout(this, null, 0, style).apply {
    setup(
        width, height, id, tag, foreground, background, padding, visibility, isSelected,
        onClick = onClick, fitsSystemWindows = fitsSystemWindows
    )
}.attach(block)

/**
 * 声明相对布局的布局属性
 */
fun RelativeLayout.layoutParams(
    margins: EdgeInsets? = null,
    rules: (RelativeLayout.LayoutParams.() -> Unit)? = null,
    block: (RelativeLayout.() -> View)? = null
) = attach(block)?.apply {
    layoutParams = when(layoutParams) {
        is RelativeLayout.LayoutParams -> layoutParams as RelativeLayout.LayoutParams
        is ViewGroup.MarginLayoutParams -> RelativeLayout.LayoutParams(layoutParams as ViewGroup.MarginLayoutParams)
        else -> RelativeLayout.LayoutParams(layoutParams)
    }.apply {
        margins?.let { setMargins(it.start, it.top, it.end, it.bottom) }
        rules?.invoke(this)
    }
}

/**
 * 声明相对布局的布局属性
 */
@Deprecated("使用 RelativeLayout.layoutParams")
fun <T: View> T.inRelativeLayout(
    margins: EdgeInsets? = null,
    rules: (RelativeLayout.LayoutParams.() -> Unit)? = null,
) = apply {
    layoutParams = when(layoutParams) {
        is RelativeLayout.LayoutParams -> layoutParams as RelativeLayout.LayoutParams
        is ViewGroup.MarginLayoutParams -> RelativeLayout.LayoutParams(layoutParams as ViewGroup.MarginLayoutParams)
        else -> RelativeLayout.LayoutParams(layoutParams)
    }.apply {
        margins?.let { setMargins(it.start, it.top, it.end, it.bottom) }
        rules?.invoke(this)
    }
}
