package com.seewo.brick.ktx // 包名别改

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.IdRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seewo.brick.init.applyMargin
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.params.RecyclerItemData
import com.seewo.brick.view.DividerDecoration
import com.seewo.brick.view.GridSpaceDecoration


/**
 * 线性布局RecyclerView的分割线
 *
 * @param orientation LinearLayoutManager的布局方向的装饰器
 * @param height 分割线高度
 * @param color 分割线颜色
 * @param padding 分割线到四周的padding
 */
fun ViewGroup.dividerDecoration(
    orientation: Int? = null,
    height: Int? = null,
    color: Int? = null,
    padding: EdgeInsets? = null,
) = DividerDecoration(context, orientation, height, color, padding)

/**
 * 网格布局RecyclerView的item间设置布局间距的装饰器
 *
 * @param space item之间的间距
 */
fun gridSpaceDecoration(
    columnSpace: Int = 0,
    rowSpace: Int = 0,
) = GridSpaceDecoration(columnSpace, rowSpace)

/**
 * 构造RecyclerView，构建方法较复杂，但可以充分利用RecyclerView的重用特性，性能高
 *
 * @param data 列表数据。【建议数据实现RecyclerItemData接口，这样可以借助DiffUtil自动判断数据是否变化，减少不必要的刷新】
 * @param viewTypeBuilder 列表index与ViewType的映射关系。如果列表只存在一种ItemView可以不传
 * @param viewBuilder ViewType与View的映射关系，这里的View将作为复用模板。ItemView模板必须用recyclerItem包裹，否则会抛异常
 * @param dataBinder 绑定每一个Item的数据到ItemView模板。回调输入为数据列表，列表index和viewBuilder所创建的View模板
 *
 * @see ViewGroup.simpleRecyclerView 构造方法更简单
 */
fun <T> ViewGroup.recyclerView(
    width: Int, height: Int,
    @AttrRes attr: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean = false,

    overScrollMode: Int? = null,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
        context, LinearLayoutManager.VERTICAL, false
    ),
    itemDecoration: RecyclerView.ItemDecoration? = null,
    data: List<T>? = null,
    onClick: View.OnClickListener? = null,

    viewTypeBuilder: (Int) -> Int = { 0 },
    viewBuilder: Context.(Int) -> View,
    dataBinder: (List<T>, Int, View) -> Unit,
) = RecyclerView(context, null, attr).apply {
    setup(
        width, height, id, tag, foreground, background, padding, visibility,
        fitsSystemWindows = fitsSystemWindows, onClick = onClick,
    )
    this.layoutManager = layoutManager
    itemDecoration?.let { addItemDecoration(it) }
    loadData(data ?: listOf(), viewTypeBuilder, viewBuilder, dataBinder)
    overScrollMode?.let { this.overScrollMode = it }
    this@recyclerView.addView(this)
}.applyMargin(margin)

private fun <T> RecyclerView.loadData(
    data: List<T>,
    viewTypeBuilder: (Int) -> Int,
    viewBuilder: Context.(Int) -> View,
    dataBinder: (List<T>, Int, View) -> Unit,
) {
    if (adapter == null) {
        adapter = Adapter(data, viewTypeBuilder, viewBuilder, dataBinder)
    } else {
        (adapter as? Adapter<T>)?.update(data)
    }
}

private class Adapter<T>(
    private var data: List<T>,
    private val viewTypeBuilder: (Int) -> Int,
    private val viewBuilder: Context.(Int) -> View,
    private val dataBinder: (List<T>, Int, View) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), BrickRecyclerViewAdapter<T> {

    override fun getItemViewType(position: Int) = viewTypeBuilder(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        object : RecyclerView.ViewHolder(parent.context.run {
            viewBuilder(viewType)
        }) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        dataBinder(data, position, holder.itemView)
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    override fun update(data: List<T>) {
        val oldData = this.data
        this.data = data
        if (data.firstOrNull() is RecyclerItemData) {
            DiffUtil.calculateDiff(
                DiffCallback(
                    oldData as List<RecyclerItemData>,
                    data as List<RecyclerItemData>
                ),
            ).dispatchUpdatesTo(this)
        } else {
            notifyDataSetChanged()
        }
    }
}

interface BrickRecyclerViewAdapter<T> {
    fun update(data: List<T>)
}

class DiffCallback<T : RecyclerItemData>(
    private val oldList: List<T>,
    private val newList: List<T>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].areSameItem(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}
