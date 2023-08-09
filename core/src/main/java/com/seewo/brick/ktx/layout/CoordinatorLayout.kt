package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior
import com.seewo.brick.init.applyMargin
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets

fun Context.coordinatorLayout(
    width: Int = MATCH_PARENT,
    height: Int = MATCH_PARENT,

    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    block: (CoordinatorLayout.() -> Unit)? = null
) = CoordinatorLayout(this).apply {
    setup(
        width, height, id, tag, foreground, background, padding, visibility, isSelected,
        onClick = onClick, fitsSystemWindows = fitsSystemWindows
    )
}.attach(block)

fun <T : ViewGroup> T.coordinatorLayout(
    width: Int = MATCH_PARENT,
    height: Int = MATCH_PARENT,

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

    block: (CoordinatorLayout.() -> Unit)? = null
) = context.coordinatorLayout(
    width,
    height,
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
).also { addView(it) }.applyMargin(margin)

fun CoordinatorLayout.layoutParams(
    behavior: CoordinatorLayout.Behavior<*>? = null,
    @IdRes anchorId: Int? = null,
    anchorGravity: Int? = null,
    apply: (CoordinatorLayout.LayoutParams.() -> Unit)? = null,
    block: (CoordinatorLayout.() -> View)? = null
) = attach(block)?.apply {
    layoutParams = when(layoutParams) {
        is CoordinatorLayout.LayoutParams -> layoutParams as CoordinatorLayout.LayoutParams
        is ViewGroup.MarginLayoutParams -> CoordinatorLayout.LayoutParams(layoutParams as ViewGroup.MarginLayoutParams)
        else -> CoordinatorLayout.LayoutParams(layoutParams)
    }.apply {
        apply?.let {
            it()
        }
        behavior?.let { this.behavior = it }
        anchorId?.let { this.anchorId = it }
        anchorGravity?.let { this.anchorGravity = it }
    }
}

fun CoordinatorLayout.scrollingView(
    apply: (CoordinatorLayout.LayoutParams.() -> Unit)? = null,
    block: (CoordinatorLayout.() -> View)? = null
) = layoutParams(
    behavior = ScrollingViewBehavior(),
    apply = apply,
    block = block,
)

fun CoordinatorLayout.appBarLayout(
    width: Int = MATCH_PARENT,
    height: Int = WRAP_CONTENT,

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

    onOffsetChanged: AppBarLayout.OnOffsetChangedListener? = null,
    block: (AppBarLayout.() -> Unit)? = null
) = AppBarLayout(context).apply {
    setup(
        width, height, id, tag, foreground, background, padding,
        visibility, isSelected,
        onClick = onClick, fitsSystemWindows = fitsSystemWindows
    )
    this.background = background
    elevation = 0f
    outlineProvider = null
    onOffsetChanged?.let { addOnOffsetChangedListener(it) }
}.also { addView(it) }.attach(block).applyMargin(margin)

fun AppBarLayout.layoutParams(
    scrollFlags: Int? = null,
    apply: (AppBarLayout.LayoutParams.() -> Unit)? = null,
    block: (AppBarLayout.() -> View)? = null
) = attach(block)?.apply {
    layoutParams = when(layoutParams) {
        is AppBarLayout.LayoutParams -> layoutParams as AppBarLayout.LayoutParams
        is ViewGroup.MarginLayoutParams -> AppBarLayout.LayoutParams(layoutParams as ViewGroup.MarginLayoutParams)
        else -> AppBarLayout.LayoutParams(layoutParams)
    }.apply {
        apply?.let {
            it()
        }
        scrollFlags?.let { this.scrollFlags = it }
    }
}
