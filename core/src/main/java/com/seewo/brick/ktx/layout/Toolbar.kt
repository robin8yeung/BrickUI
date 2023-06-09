package com.seewo.brick.ktx // 包名别改

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets

/**
 * 注意：用于 [CollapsingToolbarLayout] 的 [Toolbar] 有较多限制，部分属性无法生效
 */
fun <T: ViewGroup> T.toolbar(
    width: Int = MATCH_PARENT,
    height: Int = WRAP_CONTENT,
    @IdRes id: Int? = null,
    navigation: ToolbarNavigation? = null,
    logo: Drawable? = null,
    title: String? = null,
    titleStyle: ToolbarTitleStyle? = null,
    titleMargins: EdgeInsets? = null,
    subTitle: String? = null,
    subTitleStyle: ToolbarTitleStyle? = null,
    @MenuRes menu: Int? = null,
    onMenuItemClick: OnMenuItemClickListener? = null,
    block: (Toolbar.() -> Unit)? = null,
) = Toolbar(context).apply {
    setup(width, height, id = id)
    logo?.let { this.logo = it }
    title?.let { this.title = it }
    titleStyle?.run {
        textAppearance?.let { setTitleTextAppearance(context, it) }
        textColor?.let { setTitleTextColor(it) }
        textColors?.let { setTitleTextColor(it) }
    }
    titleMargins?.let { setTitleMargin(it.start, it.top, it.end, it.bottom) }
    subTitle?.let { this.subtitle = it }
    subTitleStyle?.run {
        textAppearance?.let { setSubtitleTextAppearance(context, it) }
        textColor?.let { setSubtitleTextColor(it) }
        textColors?.let { setSubtitleTextColor(it) }
    }
    navigation?.run {
        icon?.let { navigationIcon = it }
        onClick?.let { setNavigationOnClickListener(it) }
    }
    menu?.let {
        inflateMenu(it)
        onMenuItemClick?.let { listener -> setOnMenuItemClickListener(listener) }
    }
}.also { addView(it) }.attach(block)

class ToolbarTitleStyle(
    @StyleRes val textAppearance: Int? = null,
    @ColorInt val textColor: Int? = null,
    val textColors: ColorStateList? = null,
)

class ToolbarNavigation(
    val icon: Drawable? = null,
    val onClick: View.OnClickListener? = null,
)
