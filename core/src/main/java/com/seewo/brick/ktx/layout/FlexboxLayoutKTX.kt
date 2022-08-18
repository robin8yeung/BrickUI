package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import com.google.android.flexbox.*
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.view.LimitLinesFlexboxLayout


/**
 * 构建流式布局
 *
 * @param flexDirection 主轴布局方向
 * @see FlexDirection
 * @param flexWrap 换行方式
 * @see FlexWrap
 * @param justifyContent 主轴对齐方式
 * @see JustifyContent
 * @param alignItems 交叉轴对齐方式
 * @see AlignItems
 * @param maxLine 最大行数，超出最大行数则不显示，默认不限制最大行数
 */
fun ViewGroup.flexboxLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    @FlexDirection flexDirection: Int = FlexDirection.ROW,
    @FlexWrap flexWrap: Int = FlexWrap.WRAP,
    @JustifyContent justifyContent: Int = JustifyContent.FLEX_START,
    @AlignItems alignItems: Int = AlignItems.STRETCH,
    maxLine: Int? = null,
    block: (LimitLinesFlexboxLayout.() -> Unit)? = null
) = context.flexboxLayout(
    width,
    height,
    id,
    tag,
    background,
    padding,
    visibility,
    isSelected,
    onClick,
    fitsSystemWindows,
    flexDirection,
    flexWrap,
    justifyContent,
    alignItems,
    maxLine,
    block,
).also {
    addView(it)
}

/**
 * 构建流式布局
 *
 * @param flexDirection 主轴布局方向
 * @see FlexDirection
 * @param flexWrap 换行方式
 * @see FlexWrap
 * @param justifyContent 主轴对齐方式
 * @see JustifyContent
 * @param alignItems 交叉轴对齐方式
 * @see AlignItems
 * @param maxLine 最大行数，超出最大行数则不显示，默认不限制最大行数
 */
fun Context.flexboxLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    @FlexDirection flexDirection: Int = FlexDirection.ROW,
    @FlexWrap flexWrap: Int = FlexWrap.WRAP,
    @JustifyContent justifyContent: Int = JustifyContent.FLEX_START,
    @AlignItems alignItems: Int = AlignItems.STRETCH,
    maxLine: Int? = null,
    block: (LimitLinesFlexboxLayout.() -> Unit)? = null
) = LimitLinesFlexboxLayout(this).apply {
    setup(
        width, height, id, tag, background, padding, visibility, isSelected,
        onClick = onClick, fitsSystemWindows = fitsSystemWindows
    )
    maxLine?.let {
        this.maxLine = it
    }
    this.flexDirection = flexDirection
    this.flexWrap = flexWrap
    this.justifyContent = justifyContent
    this.alignItems = alignItems
}.attach(block)

/**
 * 简单设置flexbox布局的布局属性
 */
fun LimitLinesFlexboxLayout.margins(
    margins: EdgeInsets? = null,
    block: (LimitLinesFlexboxLayout.() -> View)? = null
) = attach(block)?.apply {
    layoutParams = FlexboxLayout.LayoutParams(layoutParams).apply {
        margins?.let { setMargins(margins.start, margins.top, margins.end, margins.bottom) }
    }
}

/**
 * 设置flexbox布局的布局属性
 */
fun LimitLinesFlexboxLayout.layoutParams(
    apply: (FlexboxLayout.LayoutParams.() -> Unit)? = null,
    block: (LimitLinesFlexboxLayout.() -> View)? = null
) = attach(block)?.apply {
    apply?.let {
        layoutParams = FlexboxLayout.LayoutParams(layoutParams).apply {
            it()
        }
    }
}

/**
 * 声明flexbox布局的布局属性
 */
@Deprecated("使用 FlexboxLayout.layoutParams")
fun <T: View> T.inFlexboxLayout(
    margins: EdgeInsets? = null,
) = apply {
    layoutParams = FlexboxLayout.LayoutParams(layoutParams).apply {
        margins?.let { setMargins(margins.start, margins.top, margins.end, margins.bottom) }
    }
}
