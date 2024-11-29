package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.core.view.children
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Mode
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab
import com.google.android.material.tabs.TabLayout.TabGravity
import com.google.android.material.tabs.TabLayout.TabIndicatorGravity
import com.google.android.material.tabs.TabLayoutMediator
import com.seewo.brick.init.applyMargin
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets


/**
 * 构建TabLayout布局
 *
 * @param data 传入一个列表用于构造TabLayout
 * @param selectedTabIndicator 自定义选中下划线样式，仅Android 5.1以上支持自定义。建议用layerDrawable来构造，但需要Android M以上才能很好支持。如果考虑Android 5.1，建议使用selectedTabIndicatorRes
 * @param selectedTabIndicatorRes 自定义选中下划线样式，仅Android 5.1以上支持自定义。
 * @param block 为data的每一项创建一个TabView
 *
 * @see layerDrawable
 */
fun <T> ViewGroup.tabLayout(
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
    fitsSystemWindows: Boolean = false,

    data: List<T> = listOf(),
    @Mode tabMode: Int? = null,
    @TabIndicatorGravity tabIndicatorGravity: Int? = null,
    @ColorInt tabIndicatorColor: Int? = null,
    selectedTabIndicator: LayerDrawable? = null,
    @DrawableRes selectedTabIndicatorRes: Int? = null,
    @TabGravity tabGravity: Int? = null,
    fixedItemWidth: Int? = null,
    onTabSelected: ((Tab) -> Unit)? = null,
    onTabUnselected: ((Tab) -> Unit)? = null,
    onTabReselected: ((Tab) -> Unit)? = null,
    block: (Context.(index: Int, item: T) -> View)? = null
) = context.tabLayout(
    width,
    height,
    style,
    id,
    tag,
    foreground,
    background,
    padding,
    visibility,
    fitsSystemWindows,
    data,
    tabMode,
    tabIndicatorGravity,
    tabIndicatorColor,
    selectedTabIndicator,
    selectedTabIndicatorRes,
    tabGravity,
    fixedItemWidth,
    onTabSelected,
    onTabUnselected,
    onTabReselected,
    block,
).also {
    addView(it)
}.applyMargin(margin)

/**
 * 构建TabLayout布局
 *
 * @param data 传入一个列表用于构造TabLayout
 * @param selectedTabIndicator 自定义选中下划线样式，仅Android 5.1以上支持自定义。建议用layerDrawable来构造，但需要Android M以上才能很好支持。如果考虑Android 5.1，建议使用selectedTabIndicatorRes
 * @param selectedTabIndicatorRes 自定义选中下划线样式，仅Android 5.1以上支持自定义。
 * @param block 为data的每一项创建一个TabView
 *
 * @see layerDrawable
 */
fun <T> Context.tabLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,

    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean = false,

    data: List<T> = listOf(),
    @Mode tabMode: Int? = null,
    @TabIndicatorGravity tabIndicatorGravity: Int? = null,
    @ColorInt tabIndicatorColor: Int? = null,
    selectedTabIndicator: LayerDrawable? = null,
    @DrawableRes selectedTabIndicatorRes: Int? = null,
    @TabGravity tabGravity: Int? = null,
    fixedItemWidth: Int? = null,
    onTabSelected: ((Tab) -> Unit)? = null,
    onTabUnselected: ((Tab) -> Unit)? = null,
    onTabReselected: ((Tab) -> Unit)? = null,
    block: (Context.(index: Int, item: T) -> View)? = null
) = TabLayout(this, null, style).apply {
    setup(
        width, height, id, tag, foreground, background, padding, visibility,
        fitsSystemWindows = fitsSystemWindows
    )
    tabGravity?.let { setTabGravity(it) }
    tabIndicatorGravity?.let { setSelectedTabIndicatorGravity(it) }
    tabIndicatorColor?.let { setSelectedTabIndicatorColor(it) }
    if (selectedTabIndicator == null && selectedTabIndicatorRes == null) {
        setSelectedTabIndicator(null)
    }
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
        selectedTabIndicator?.let {
            isTabIndicatorFullWidth = false
            setSelectedTabIndicator(it)
        }
        selectedTabIndicatorRes?.let {
            isTabIndicatorFullWidth = false
            setSelectedTabIndicator(it)
        }
    }
    tabRippleColor = null

    tabMode?.let { this.tabMode = it }

    data.forEachIndexed { index, item ->
        block?.invoke(context, index, item)?.let { itemView ->
            newTab().apply {
                customView = itemView
            }.also { addTab(it) }
        }
    }

    addOnTabSelectedListener(object : OnTabSelectedListener {
        override fun onTabSelected(tab: Tab) {
            onTabSelected?.invoke(tab)
        }

        override fun onTabUnselected(tab: Tab) {
            onTabUnselected?.invoke(tab)
        }

        override fun onTabReselected(tab: Tab) {
            onTabReselected?.invoke(tab)
        }
    })
}.apply {
    fixedItemWidth?.let {
        children.forEach {
            (it as? ViewGroup)?.children?.forEachIndexed { _, tab ->
                tab.post {
                    tab.setPadding(0, 0, 0, 0)
                    tab.minimumWidth = 0
                    tab.layoutParams = tab.layoutParams.apply {
                        this?.width = fixedItemWidth
                    }
                }
            }
        }
    }
}

/**
 * 绑定ViewPager2和TabLayout
 *
 * 必须把tabLayoutMediator直接嵌套在要绑定的viewPager和tabLayout外（layoutParams一类除外）
 * 它会从直接嵌套的下一级子View中找ViewPager2和TabLayout，如果找不到则不生效，如果找到多个，只有第一个生效
 *
 * @param viewPagerId 需要绑定的ViewPager2控件的id，当且仅当能在block的第一层级（layoutParams一类除外）能找到ViewPager2的情况可为空
 * @param tabLayoutId 需要绑定的TabLayout控件的id，当且仅当能在block的第一层级（layoutParams一类除外）能找到TabLayout的情况可为空
 *
 * @see viewPager
 * @see tabLayout
 */
fun <T: ViewGroup> T.tabLayoutMediator(
    viewPagerId: Int? = null,
    tabLayoutId: Int? = null,
    block: (T.() -> Unit)? = null,
) {
    block?.let { it() }
    val tabLayout = (if (tabLayoutId == null) children.firstOrNull { it is TabLayout } as? TabLayout else findViewById(tabLayoutId)) ?: return
    val viewPager2 = (if (viewPagerId == null) children.firstOrNull { it is ViewPager2 } as? ViewPager2 else findViewById(viewPagerId)) ?: return
    val views = buildList {
        for (i in 0 until  tabLayout.tabCount) {
            add(tabLayout.getTabAt(i)?.customView)
        }
    }
    TabLayoutMediator(tabLayout, viewPager2
    ) { tab, position ->
        views[position]?.let {
            tab.customView = it
        }
    }.attach()
}