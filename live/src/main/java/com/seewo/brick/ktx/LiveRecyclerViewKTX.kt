package com.seewo.brick.ktx

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.IdRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.params.RecyclerItemData


/**
 * 构造RecyclerView，构建方法较复杂，但可以充分利用RecyclerView的重用特性，性能高
 *
 * @param data 列表数据
 * @param viewTypeBuilder 列表index与ViewType的映射关系。如果列表只存在一种ItemView可以不传
 * @param viewBuilder ViewType与View的映射关系，这里的View将作为复用模板。ItemView模板必须用recyclerItem包裹，否则会抛异常
 * @param dataBinder 绑定每一个Item的数据到ItemView模板。回调输入为数据列表，列表index和viewBuilder所创建的View模板
 *
 * @see ViewGroup.simpleRecyclerView 构造方法更简单
 */
fun <T: RecyclerItemData> ViewGroup.liveRecyclerView(
    width: Int, height: Int,
    @AttrRes attr: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    fitsSystemWindows: Boolean = false,
    lifecycleOwner: LifecycleOwner? = null,

    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
        context, LinearLayoutManager.VERTICAL, false
    ),
    itemDecoration: RecyclerView.ItemDecoration? = null,
    data: LiveData<List<T>>? = null,
    onClick: View.OnClickListener? = null,

    viewTypeBuilder: (Int) -> Int = { 0 },
    viewBuilder: Context.(Int) -> View,
    dataBinder: (List<T>, Int, View) -> Unit,
) = recyclerView(
    width, height, attr, id, tag, foreground, background, margin, padding,
    fitsSystemWindows = fitsSystemWindows, layoutManager = layoutManager,
    itemDecoration = itemDecoration,
    viewTypeBuilder = viewTypeBuilder, viewBuilder = viewBuilder,
    dataBinder = dataBinder, onClick = onClick,
).apply {
    if (lifecycleOwner != null) {
        visibility?.bindNotNull(lifecycleOwner) {
            this@apply.visibility = it
        }
        data?.bindNotNull(lifecycleOwner) {
            (adapter as? BrickRecyclerViewAdapter<T>)?.update(it)
        }
    } else {
        visibility?.bindNotNull(context) {
            this@apply.visibility = it
        }
        data?.bindNotNull(context) {
            (adapter as? BrickRecyclerViewAdapter<T>)?.update(it)
        }
    }
}

/**
 * 构造RecyclerView，构建方法较简单，但基本无法利用RecyclerView的重用特性，滚动会造成内存都懂。谨慎使用
 *
 * @param viewHolderCreator ViewHolder默认宽为MATCH_PARENT，若不满足需求，可通过此参数自行创建（如横向RecyclerView）
 * @param data 列表数据【建议数据实现RecyclerItemData接口，这样可以借助DiffUtil自动判断数据是否变化，减少不必要的刷新】
 * @param block 针对每一个Item的数据到创建ItemView。回调输入为数据列表，列表index。不需要包裹recyclerItem。
 *
 * @see ViewGroup.recyclerView 性能开销更少
 */
fun <T> ViewGroup.liveSimpleRecyclerView(
    width: Int, height: Int,
    @AttrRes attr: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    fitsSystemWindows: Boolean = false,
    lifecycleOwner: LifecycleOwner? = null,

    overScrollMode: Int? = null,
    viewHolderCreator: (Context.() -> ViewGroup)? = null,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
        context, LinearLayoutManager.VERTICAL, false),
    itemDecoration: RecyclerView.ItemDecoration? = null,
    data: LiveData<List<T>>? = null,
    onClick: View.OnClickListener? = null,
    block: ViewGroup.(List<T>, Int) -> Unit,
) = simpleRecyclerView(
    width, height, attr, id, tag, foreground, background, margin, padding,
    fitsSystemWindows = fitsSystemWindows, layoutManager = layoutManager,
    itemDecoration = itemDecoration, block = block, onClick = onClick,
    overScrollMode = overScrollMode, viewHolderCreator = viewHolderCreator,
).apply {
    if (lifecycleOwner != null) {
        data?.bindNotNull(lifecycleOwner) {
            (adapter as? BrickRecyclerViewAdapter<T>)?.update(it)
        }
        visibility?.bindNotNull(lifecycleOwner) {
            this@apply.visibility = it
        }
    } else {
        data?.bindNotNull(context) {
            (adapter as? BrickRecyclerViewAdapter<T>)?.update(it)
        }
        visibility?.bindNotNull(context) {
            this@apply.visibility = it
        }
    }
}
