package com.seewo.brick.ktx

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.params.Shadow
import com.seewo.brick.view.ShadowLayout


/**
 * 外阴影容器
 * 支持通过代码来实现外阴影
 *
 * 注意：Android P以下的机型将关闭硬件加速
 */
fun Context.liveShadowBox(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    onClick: View.OnClickListener? = null,

    @ColorInt color: Int = Color.WHITE,
    radius: Int = 0,
    shadow: Shadow = Shadow(),

    block: (ViewGroup.() -> Unit)? = null
) = shadowBox(
    width, height, id, tag, foreground, background, padding,
    onClick = onClick,
    color = color, radius = radius, shadow = shadow, block = block
).apply {
    visibility?.bind(context) {
        it ?: return@bind
        this@apply.visibility = it
    }
    isSelected?.bind(context) {
        it ?: return@bind
        this@apply.isSelected = it
    }
}

/**
 * 外阴影容器
 * 支持通过代码来实现外阴影
 *
 * 注意：Android P以下的机型将关闭硬件加速
 */
fun ViewGroup.liveShadowBox(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    onClick: View.OnClickListener? = null,

    @ColorInt color: Int = Color.WHITE,
    radius: Int = 0,
    shadow: Shadow = Shadow(),

    block: (ShadowLayout.() -> Unit)? = null
) = shadowBox(
    width, height, id, tag, foreground, background, margin, padding,
    onClick = onClick,
    color = color, radius = radius, shadow = shadow, block = block
).apply {
    visibility?.bind(context) {
        it ?: return@bind
        this@apply.visibility = it
    }
    isSelected?.bind(context) {
        it ?: return@bind
        this@apply.isSelected = it
    }
}
