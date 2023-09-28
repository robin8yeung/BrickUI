package com.seewo.brick.ktx

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.seewo.brick.params.EdgeInsets

/**
 * SmartRefresh的BrickUI封装
 *
 * 注意：使用时必须保证项目中引入了以下依赖:
 * implementation 'com.github.robin8yeung.BrickUI:brick-ui-smart-refresh:{VERSION}'
 */
fun ViewGroup.liveSmartRefresh(
    width: Int = MATCH_PARENT, height: Int = MATCH_PARENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    lifecycleOwner: LifecycleOwner? = null,

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
    width, height, id, tag, foreground, background, margin, padding,
    autoLoadMore = autoLoadMore,
    onRefresh = onRefresh, onLoadMore = onLoadMore,
    refreshHeader = refreshHeader, headerHeight = headerHeight,
    refreshFooter = refreshFooter, footerHeight = footerHeight,
    block = block
).apply {
    if (lifecycleOwner != null) {
        refreshEnable?.bindNotNull(lifecycleOwner) {
            setEnableRefresh(it)
        }
        loadMoreEnable?.bindNotNull(lifecycleOwner) {
            setEnableLoadMore(it)
        }
        noMoreData?.bindNotNull(lifecycleOwner) {
            setNoMoreData(it)
        }
        visibility?.bindNotNull(lifecycleOwner) {
            this@apply.visibility = it
        }
    } else {
        refreshEnable?.bindNotNull(context) {
            setEnableRefresh(it)
        }
        loadMoreEnable?.bindNotNull(context) {
            setEnableLoadMore(it)
        }
        noMoreData?.bindNotNull(context) {
            setNoMoreData(it)
        }
        visibility?.bindNotNull(context) {
            this@apply.visibility = it
        }
    }
}