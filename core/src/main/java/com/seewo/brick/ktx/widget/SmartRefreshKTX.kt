package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets

/**
 * SmartRefresh的BrickUI封装
 */
fun ViewGroup.smartRefresh(
    width: Int = MATCH_PARENT, height: Int = MATCH_PARENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
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
) = SmartRefreshLayout(context).apply {
    setup(
        width, height,
        id = id, tag = tag, background = background, padding = padding, visibility = visibility,
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
}.also { addView(it) }