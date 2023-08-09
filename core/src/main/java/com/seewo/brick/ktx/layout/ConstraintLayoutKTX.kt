package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import com.seewo.brick.init.applyMargin
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.view.ConstraintHelper


/**
 * 构建约束布局
 *
 * @param id 需要配置id，用于声明与父布局的关系
 */
fun ViewGroup.constraintLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,
    block: (ConstraintLayout.() -> Unit)? = null
) = context.constraintLayout(
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
 * 构建约束布局
 *
 * @param id 需要配置id，用于声明与父布局的关系
 */
fun Context.constraintLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    block: (ConstraintLayout.() -> Unit)? = null
) = ConstraintLayout(this, null, 0, style).apply {
    setup(
        width, height, id, tag, foreground, background, padding, visibility, isSelected,
        onClick = onClick, fitsSystemWindows = fitsSystemWindows
    )
}.attach(block)

/**
 * 声明约束布局的布局属性
 *
 * 示例：
 * layoutParams(
 *     apply = {
 *         startToStart = parentId
 *         endToEnd = parentId
 *         dimensionRatio = "h, 9:16"
 *     },
 * ) {
 *    // childView
 * }
 *
 * @param apply 布局规则，通过ConstraintLayout.LayoutParams设置
 */
fun <T: View> ConstraintLayout.layoutParams(
    apply: (LayoutParams.() -> Unit)? = null,
    block: (ConstraintLayout.() -> T)? = null
) = attach(block)?.apply {
    apply?.let {
        layoutParams = when(layoutParams) {
            is LayoutParams -> layoutParams as LayoutParams
            is ViewGroup.MarginLayoutParams -> LayoutParams(layoutParams as ViewGroup.MarginLayoutParams)
            else -> LayoutParams(layoutParams)
        }.apply {
            it()
        }
    }
}

/**
 * 声明约束布局的布局属性
 *
 * @param rules 布局规则，通过Editor工具类传入
 *
 * 注意：声明view之间的约束关系需要用到各自的id，所以view和parent均需定义id
 */
@Deprecated("使用 ConstraintLayout.layoutParams")
fun <T: View> T.inConstraintLayout(
    rules: (T.(ConstraintHelper.Editor) -> Unit)? = null,
) = apply {
    rules?.let {
        ConstraintHelper(parent as ConstraintLayout).edit()
            .also { editor ->
                it(editor)
            }.commit()
    }
}
