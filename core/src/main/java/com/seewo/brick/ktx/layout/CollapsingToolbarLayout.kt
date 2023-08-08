package com.seewo.brick.ktx // 包名别改

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.IntRange
import androidx.annotation.StyleRes
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.seewo.brick.init.setup
import com.seewo.brick.params.CollapseMode
import com.seewo.brick.params.EdgeInsets

@SuppressLint("RestrictedApi")
fun AppBarLayout.collapsingToolbarLayout(
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

    @IdRes toolbarId: Int? = null,
    title: String? = null,
    titleEnable: Boolean? = null,
    @CollapsingToolbarLayout.TitleCollapseMode titleCollapseMode: Int? = null,
    scrimsShown: Boolean? = null,
    scrimsShownAnim: Boolean? = null,
    contentScrim: Drawable? = null,
    statusBarScrim: Drawable? = null,
    collapsedTitleStyle: TitleStyle? = null,
    expandedTitleStyle: TitleStyle? = null,
    expandedTitleMargin: EdgeInsets? = null,
    maxLines: Int? = null,
    @IntRange(from = 0) scrimAnimationDuration: Long? = null,

    block: (CollapsingToolbarLayout.() -> Unit)? = null
) = CollapsingToolbarLayout(context).apply {
    setup(
        width, height, id, tag, foreground, background, padding, visibility, isSelected,
        onClick = onClick, fitsSystemWindows = fitsSystemWindows
    )
    toolbarId?.let {
        this::class.java.getDeclaredField("toolbarId")
            .apply { isAccessible = true }
            .set(this, it)
    }
    title?.let { this.title = it }
    titleEnable?.let { this.isTitleEnabled = it }
    titleCollapseMode?.let { this.titleCollapseMode = it }
    scrimsShown?.let { setScrimsShown(it, scrimsShownAnim ?: true) }
        ?: scrimsShownAnim?.let { setScrimsShown(true, it) }
    contentScrim?.let { setContentScrim(it) }
    statusBarScrim?.let { setStatusBarScrim(it) }
    collapsedTitleStyle?.run {
        textAppearance?.let { setCollapsedTitleTextAppearance(it) }
        textColor?.let { setCollapsedTitleTextColor(it) }
        textColors?.let { setCollapsedTitleTextColor(it) }
        gravity?.let { collapsedTitleGravity = it }
        typeface?.let { setCollapsedTitleTypeface(it) }
    }
    expandedTitleStyle?.run {
        textAppearance?.let { setExpandedTitleTextAppearance(it) }
        textColor?.let { setExpandedTitleColor(it) }
        textColors?.let { setExpandedTitleTextColor(it) }
        gravity?.let { expandedTitleGravity = it }
        typeface?.let { setExpandedTitleTypeface(it) }
    }
    expandedTitleMargin?.let { setExpandedTitleMargin(it.start, it.top, it.end, it.bottom) }
    maxLines?.let { this.maxLines = it }
    scrimAnimationDuration?.let { setScrimAnimationDuration(it) }
}.also { addView(it) }.attach(block)

class TitleStyle(
    @StyleRes val textAppearance: Int? = null,
    @ColorInt val textColor: Int? = null,
    val textColors: ColorStateList? = null,
    val gravity: Int? = null,
    val typeface: Typeface? = null,
)

fun CollapsingToolbarLayout.layoutParams(
    @CollapseMode collapseMode: Int? = null,
    parallaxMultiplier: Float? = null,
    apply: (CollapsingToolbarLayout.LayoutParams.() -> Unit)? = null,
    block: (CollapsingToolbarLayout.() -> View)? = null
) = attach(block)?.apply {
    layoutParams = CollapsingToolbarLayout.LayoutParams(layoutParams).apply {
        apply?.let {
            it()
        }
        collapseMode?.let { this.collapseMode = it }
        parallaxMultiplier?.let { this.parallaxMultiplier = it }
        this.parallaxMultiplier
    }
}
