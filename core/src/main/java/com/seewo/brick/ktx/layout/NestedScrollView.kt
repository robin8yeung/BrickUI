package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.widget.NestedScrollView
import com.seewo.brick.init.applyMargin
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets

fun <T: ViewGroup> T.nestedScrollView(
    width: Int = MATCH_PARENT,
    height: Int = MATCH_PARENT,

    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean = false,

    block: (NestedScrollView.() -> Unit)? = null
) = context.nestedScrollView(
    width, height, id, tag, foreground, background, padding,
    visibility, fitsSystemWindows, block
).also { addView(it) }.applyMargin(margin)

fun Context.nestedScrollView(
    width: Int = MATCH_PARENT,
    height: Int = MATCH_PARENT,

    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean = false,

    block: (NestedScrollView.() -> Unit)? = null
) = NestedScrollView(this).apply {
    setup(
        width, height, id, tag, foreground, background, padding, visibility,
        fitsSystemWindows = fitsSystemWindows
    )
}.attach(block)
