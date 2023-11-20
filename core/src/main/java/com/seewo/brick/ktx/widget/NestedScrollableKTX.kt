package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import com.seewo.brick.init.applyMargin
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.view.NestedScrollableHost

/**
 * 解决ViewPager2等的嵌套导致的滚动冲突
 */
fun Context.nestedScrollableChild(
    width: Int, height: Int,
    @IdRes id: Int? = null,
    tag: Any? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean = false,
    block: NestedScrollableHost.() -> View,
) = NestedScrollableHost(this).apply {
    setup(
        width, height, id, tag, padding = padding, visibility = visibility,
        fitsSystemWindows = fitsSystemWindows,
    )
    this.block()
}

/**
 * 解决ViewPager2等的嵌套导致的滚动冲突
 */
fun ViewGroup.nestedScrollableChild(
    width: Int, height: Int,
    @IdRes id: Int? = null,
    tag: Any? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean = false,
    block: NestedScrollableHost.() -> View,
) = context.nestedScrollableChild(
    width, height, id, tag, padding, visibility, fitsSystemWindows, block
).also { addView(it) }.applyMargin(margin)
