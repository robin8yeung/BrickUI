package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.seewo.brick.params.EdgeInsets

/**
 * SmartRefresh的BrickUI封装
 *
 * 示例：
 * smartRefresh(
 *     MATCH_PARENT, WRAP_CONTENT,
 *     refreshHeader = ClassicsHeader(context)
 *         .setDrawableSize(12f)
 *         .setDrawableMarginRight(8f)
 *         .setTextSizeTitle(TypedValue.COMPLEX_UNIT_DIP, 12f)
 *         .setAccentColor(Color.parseColor("#AAAAAA"))
 *         .setEnableLastTime(false),
 *     refreshFooter = ClassicsFooter(context)
 *         .setDrawableSize(12f)
 *         .setDrawableMarginRight(8f)
 *         .setTextSizeTitle(TypedValue.COMPLEX_UNIT_DIP, 12f)
 *         .setAccentColor(Color.parseColor("#AAAAAA")),
 *     onRefresh = {
 *         MainScope().launch {
 *             // 模拟刷新耗时
 *             delay(1000)
 *             it.finishRefresh(true)
 *         }
 *     },
 *     onLoadMore = {
 *         MainScope().launch {
 *             // 模拟加载更多耗时
 *             delay(1000)
 *             it.finishLoadMore(true)
 *         }
 *     },
 *     autoLoadMore = false,
 * ) {
 *     column(
 *         MATCH_PARENT, MATCH_PARENT,
 *         gravity = Gravity.CENTER_HORIZONTAL,
 *     ) {
 *         // 内容
 *     }
 * }
 */
fun Context.smartRefresh(
    width: Int = MATCH_PARENT, height: Int = MATCH_PARENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,

    refreshEnable: Boolean? = null,
    loadMoreEnable: Boolean? = null,
    noMoreData: Boolean? = null,
    autoLoadMore: Boolean = true,
    onRefresh: ((RefreshLayout) -> Unit)? = null,
    onLoadMore: ((RefreshLayout) -> Unit)? = null,
    refreshHeader: RefreshHeader? = null,
    headerHeight: Int? = null,
    refreshFooter: RefreshFooter? = null,
    footerHeight: Int? = null,
    block: (Context.() -> View)? = null
) = SmartRefreshLayout(this).apply {
    setup(
        width, height,
        id = id, tag = tag,
        foreground = foreground, background = background,
        padding = padding, visibility = visibility,
    )
    refreshHeader?.let { setRefreshHeader(it) }
    headerHeight?.let { setHeaderHeightPx(it) }
    refreshFooter?.let { setRefreshFooter(it) }
    footerHeight?.let { setFooterHeightPx(it) }
    block?.let {
        setRefreshContent(context.it())
    }
    onRefresh?.let { setOnRefreshListener(it) }
    onLoadMore?.let { setOnLoadMoreListener(it) }
    setEnableAutoLoadMore(autoLoadMore)
    refreshEnable?.let { setEnableRefresh(it) }
    loadMoreEnable?.let { setEnableLoadMore(it) }
    noMoreData?.let { setNoMoreData(it) }
}

/**
 * SmartRefresh的BrickUI封装
 *
 * 示例：
 * smartRefresh(
 *     MATCH_PARENT, WRAP_CONTENT,
 *     refreshHeader = ClassicsHeader(context)
 *         .setDrawableSize(12f)
 *         .setDrawableMarginRight(8f)
 *         .setTextSizeTitle(TypedValue.COMPLEX_UNIT_DIP, 12f)
 *         .setAccentColor(Color.parseColor("#AAAAAA"))
 *         .setEnableLastTime(false),
 *     refreshFooter = ClassicsFooter(context)
 *         .setDrawableSize(12f)
 *         .setDrawableMarginRight(8f)
 *         .setTextSizeTitle(TypedValue.COMPLEX_UNIT_DIP, 12f)
 *         .setAccentColor(Color.parseColor("#AAAAAA")),
 *     onRefresh = {
 *         MainScope().launch {
 *             // 模拟刷新耗时
 *             delay(1000)
 *             it.finishRefresh(true)
 *         }
 *     },
 *     onLoadMore = {
 *         MainScope().launch {
 *             // 模拟加载更多耗时
 *             delay(1000)
 *             it.finishLoadMore(true)
 *         }
 *     },
 *     autoLoadMore = false,
 * ) {
 *     column(
 *         MATCH_PARENT, MATCH_PARENT,
 *         gravity = Gravity.CENTER_HORIZONTAL,
 *     ) {
 *         // 内容
 *     }
 * }
 */
fun ViewGroup.smartRefresh(
    width: Int = MATCH_PARENT, height: Int = MATCH_PARENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,

    refreshEnable: Boolean? = null,
    loadMoreEnable: Boolean? = null,
    noMoreData: Boolean? = null,
    autoLoadMore: Boolean = true,
    onRefresh: ((RefreshLayout) -> Unit)? = null,
    onLoadMore: ((RefreshLayout) -> Unit)? = null,
    refreshHeader: RefreshHeader? = null,
    headerHeight: Int? = null,
    refreshFooter: RefreshFooter? = null,
    footerHeight: Int? = null,
    block: (Context.() -> View)? = null
) = SmartRefreshLayout(context).apply {
    setup(
        width, height,
        id = id, tag = tag,
        foreground = foreground, background = background,
        padding = padding, visibility = visibility,
    )
    refreshHeader?.let { setRefreshHeader(it) }
    headerHeight?.let { setHeaderHeightPx(it) }
    refreshFooter?.let { setRefreshFooter(it) }
    footerHeight?.let { setFooterHeightPx(it) }
    block?.let {
        setRefreshContent(context.it())
    }
    onRefresh?.let { setOnRefreshListener(it) }
    onLoadMore?.let { setOnLoadMoreListener(it) }
    setEnableAutoLoadMore(autoLoadMore)
    refreshEnable?.let { setEnableRefresh(it) }
    loadMoreEnable?.let { setEnableLoadMore(it) }
    noMoreData?.let { setNoMoreData(it) }
}.also { addView(it) }.applyMargin(margin)

private inline fun <T: View> T.applyMargin(
    margin: EdgeInsets?
): T = apply {
    margin?.let { (layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(
        it.start, it.top, it.end, it.bottom)
    }
}

private inline fun View.setup(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean? = null,
) {
    init(width, height)
    id?.let { this.id = it }
    tag?.let { this.tag = it }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        foreground?.let {
            this.foreground = it
        }
    }
    background?.let { this.background = it }
    padding?.let { setPadding(it.start, it.top, it.end, it.bottom) }
    visibility?.let { this.visibility = it }
    isSelected?.let { this.isSelected = it }
    isEnabled?.let { this.isEnabled = it }
    onClick?.let { setOnClickListener(it) }
    fitsSystemWindows?.let { this.fitsSystemWindows = fitsSystemWindows }
}