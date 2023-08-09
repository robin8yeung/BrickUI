package com.seewo.brick.ktx // 包名别改

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.LinearLayoutCompat
import com.seewo.brick.init.applyMargin
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets


/**
 * 构建列布局（线性布局垂直方向）
 */
fun Context.column(
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

    gravity: Int? = null,
    animateLayoutChanges: Boolean? = null,
    block: (LinearLayout.() -> Unit)? = null
) = linearLayout(
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
    LinearLayoutCompat.VERTICAL,
    gravity,
    animateLayoutChanges,
    block
)

/**
 * 构建列布局（线性布局垂直方向）
 */
fun ViewGroup.column(
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

    gravity: Int? = null,
    animateLayoutChanges: Boolean? = null,
    block: (LinearLayout.() -> Unit)? = null
) = linearLayout(
    width,
    height,
    style,
    id,
    tag,
    foreground,
    background,
    margin,
    padding,
    visibility,
    isSelected,
    onClick,
    fitsSystemWindows,
    LinearLayoutCompat.VERTICAL,
    gravity,
    animateLayoutChanges,
    block
)

/**
 * 构建行布局（线性布局水平方向）
 */
fun Context.row(
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

    gravity: Int = Gravity.CENTER_VERTICAL,
    animateLayoutChanges: Boolean? = null,
    block: (LinearLayout.() -> Unit)? = null
) = linearLayout(
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
    LinearLayoutCompat.HORIZONTAL,
    gravity,
    animateLayoutChanges,
    block
)

/**
 * 构建行布局（线性布局水平方向）
 */
fun ViewGroup.row(
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

    gravity: Int = Gravity.CENTER_VERTICAL,
    animateLayoutChanges: Boolean? = null,
    block: (LinearLayout.() -> Unit)? = null
) = linearLayout(
    width,
    height,
    style,
    id,
    tag,
    foreground,
    background,
    margin,
    padding,
    visibility,
    isSelected,
    onClick,
    fitsSystemWindows,
    LinearLayoutCompat.HORIZONTAL,
    gravity,
    animateLayoutChanges,
    block
)

private fun Context.linearLayout(
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

    @LinearLayoutCompat.OrientationMode orientation: Int,
    gravity: Int? = null,
    animateLayoutChanges: Boolean? = null,
    block: (LinearLayout.() -> Unit)? = null
) = LinearLayout(this, null, 0, style).apply {
    setup(
        width, height, id, tag, foreground, background, padding, visibility, isSelected,
        onClick = onClick, fitsSystemWindows = fitsSystemWindows
    )
    this.orientation = orientation
    gravity?.let { setGravity(it) }
    if (animateLayoutChanges == true) {
        layoutTransition = LayoutTransition()
    }
}.attach(block)

private fun ViewGroup.linearLayout(
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

    @LinearLayoutCompat.OrientationMode orientation: Int,
    gravity: Int? = null,
    animateLayoutChanges: Boolean? = null,
    block: (LinearLayout.() -> Unit)? = null
) = context.linearLayout(
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
    orientation,
    gravity,
    animateLayoutChanges,
    block
).also { addView(it) }.applyMargin(margin)

/**
 * 声明线性布局的布局属性
 */
fun LinearLayout.layoutParams(
    apply: (LayoutParams.() -> Unit)? = null,
    block: (LinearLayout.() -> View)? = null
) = attach(block)?.apply {
    apply?.let {
        layoutParams = when(layoutParams) {
            is LayoutParams -> layoutParams as LayoutParams
            is MarginLayoutParams -> LayoutParams(layoutParams as MarginLayoutParams)
            else -> LayoutParams(layoutParams)
        }.apply {
            it()
        }
    }
}

/**
 * 声明线性布局的布局属性
 */
fun LinearLayout.layoutParams(
    weight: Float? = null,
    gravity: Int? = null,
    margins: EdgeInsets? = null,
    block: (LinearLayout.() -> View)? = null
) = attach(block)?.apply {
    layoutParams = when(layoutParams) {
        is LayoutParams -> layoutParams as LayoutParams
        is MarginLayoutParams -> LayoutParams(layoutParams as MarginLayoutParams)
        else -> LayoutParams(layoutParams)
    }.apply {
        gravity?.let { this.gravity = it }
        margins?.let { setMargins(it.start, it.top, it.end, it.bottom) }
        weight?.let { this.weight = weight }
    }
}

/**
 * 声明在线性布局中伸展的部分，即设置 weight = 1f
 */
fun LinearLayout.expand(
    gravity: Int? = null,
    margins: EdgeInsets? = null,
    block: (LinearLayout.() -> View)? = null
) = layoutParams(
    weight = 1f,
    gravity = gravity,
    margins = margins,
    block = block,
)

/**
 * 声明线性布局的布局属性
 */
@Deprecated("使用 LinearLayout.inLayout")
fun <T: View> T.inLinearLayout(
    weight: Float? = null,
    gravity: Int? = null,
    margins: EdgeInsets? = null,
) = apply {
    layoutParams = when(layoutParams) {
        is LayoutParams -> layoutParams as LayoutParams
        is MarginLayoutParams -> LayoutParams(layoutParams as MarginLayoutParams)
        else -> LayoutParams(layoutParams)
    }.apply {
        gravity?.let { this.gravity = it }
        margins?.let { setMargins(it.start, it.top, it.end, it.bottom) }
        weight?.let { this.weight = weight }
    }
}
