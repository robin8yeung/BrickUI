package com.seewo.brick.ktx

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.seewo.brick.params.EdgeInsets

/**
 * SmartRefresh的BrickUI封装
 */
fun ViewGroup.liveSmartRefresh(
    width: Int = MATCH_PARENT, height: Int = MATCH_PARENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,

    refreshEnable: LiveData<Boolean>? = null,
    loadMoreEnable: LiveData<Boolean>? = null,
    noMoreData: LiveData<Boolean>? = null,
    autoLoadMore: Boolean = true,
    onRefresh: ((RefreshLayout) -> Unit)? = null,
    onLoadMore: ((RefreshLayout) -> Unit)? = null,
    refreshHeader: RefreshHeader? = null,
    headerHeight: Int? = null,
    refreshFooter: RefreshFooter? = null,
    footerHeight: Int? = null,
    block: (Context.() -> View)? = null
) = smartRefresh(
    width, height, id, tag, background, padding,
    autoLoadMore = autoLoadMore,
    onRefresh = onRefresh, onLoadMore = onLoadMore,
    refreshHeader = refreshHeader, headerHeight = headerHeight,
    refreshFooter = refreshFooter, footerHeight = footerHeight,
    block = block
).apply {
    context.inMyLifecycle {
        refreshEnable?.bindNotNull(this) {
            setEnableRefresh(it)
        }
        loadMoreEnable?.bindNotNull(this) {
            setEnableLoadMore(it)
        }
        noMoreData?.bindNotNull(this) {
            setNoMoreData(it)
        }
        visibility?.bindNotNull(this) {
            this@apply.visibility = it
        }
    }
}