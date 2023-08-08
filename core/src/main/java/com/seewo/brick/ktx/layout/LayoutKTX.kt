package com.seewo.brick.ktx  // 包名别改

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ScrollView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import com.google.android.flexbox.*
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.params.Shadow
import com.seewo.brick.view.ShadowLayout


/**
 * 构建滚动
 */
fun ViewGroup.scrollView(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    fitsSystemWindows: Boolean = false,
    overScrollMode: Int? = null,
    block: (ScrollView.() -> Unit)? = null
) = context.scrollView(
    width,
    height,
    style,
    id,
    tag,
    background,
    padding,
    visibility,
    isSelected,
    fitsSystemWindows,
    overScrollMode,
    block,
).also { addView(it) }

/**
 * 构建滚动
 */
fun Context.scrollView(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    fitsSystemWindows: Boolean = false,
    overScrollMode: Int? = null,
    block: (ScrollView.() -> Unit)? = null
) = ScrollView(this, null, 0, style).apply {
    setup(
        width, height, id, tag, background, padding, visibility, isSelected,
        fitsSystemWindows = fitsSystemWindows
    )
    overScrollMode?.let { this.overScrollMode = it }
}.attach(block)

/**
 * 外阴影容器
 * 支持通过代码来实现外阴影
 *
 * @param color 容器填充颜色
 * @param radius 容器圆角
 * @param shadow 阴影设置
 * @see Shadow
 *
 * 注意：Android P以下的机型将关闭硬件加速
 */
fun Context.shadowBox(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: View.OnClickListener? = null,

    @ColorInt color: Int = Color.WHITE,
    radius: Int = 0,
    shadow: Shadow = Shadow(),

    block: (ViewGroup.() -> Unit)? = null
) = ShadowLayout(this).apply {
    setup(width, height, id, tag, background, padding, visibility, isSelected,
        onClick = onClick)

    backgroundRadius = radius.toFloat()
    customBackgroundColor = color
    shadowOffsetX = shadow.offsetX.toFloat()
    shadowOffsetY = shadow.offsetY.toFloat()
    shadowColor = shadow.color
    shadowRadius = shadow.blur.toFloat()
}.attach(block)

/**
 * 外阴影容器
 * 支持通过代码来实现外阴影
 *
 * @param color 容器填充颜色
 * @param radius 容器圆角
 * @param shadow 阴影设置
 * @see Shadow
 *
 * 注意：Android P以下的机型将关闭硬件加速
 */
fun ViewGroup.shadowBox(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: View.OnClickListener? = null,

    @ColorInt color: Int = Color.WHITE,
    radius: Int = 0,
    shadow: Shadow = Shadow(),

    block: (ShadowLayout.() -> Unit)? = null
) = ShadowLayout(context).apply {
    setup(width, height, id, tag, background, padding, visibility, isSelected,
        onClick = onClick)

    backgroundRadius = radius.toFloat()
    customBackgroundColor = color
    shadowOffsetX = shadow.offsetX.toFloat()
    shadowOffsetY = shadow.offsetY.toFloat()
    shadowColor = shadow.color
    shadowRadius = shadow.blur.toFloat()
}.also { addView(it) }.attach(block)

internal fun <T : ViewGroup> T.attach(block: (T.() -> Unit)?): T = apply {
    block?.let { it() }
}

internal fun <T : ViewGroup, R : View> T.attach(block: (T.() -> R)?): R? = run {
    block?.let { it() }
}

fun <T : ViewGroup, R : View> T.margins(
    margins: EdgeInsets? = null,
    block: (T.() -> R)? = null
) = attach(block)?.apply {
    layoutParams = MarginLayoutParams(layoutParams).apply {
        margins?.let { setMargins(it.start, it.top, it.end, it.bottom) }
    }
}
