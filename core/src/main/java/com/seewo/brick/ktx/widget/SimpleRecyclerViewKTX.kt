package com.seewo.brick.ktx // 包名别改

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.IdRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.params.RecyclerItemData

/**
 * 构造RecyclerView，构建方法较简单，但基本无法利用RecyclerView的重用特性，滚动会造成内存抖动，但范围可控。
 *
 * @param viewHolderCreator ViewHolder默认宽为MATCH_PARENT，若不满足需求，可通过此参数自行创建（如横向RecyclerView）
 * @param data 列表数据
 * @param block 针对每一个Item的数据到创建ItemView。回调输入为数据列表，列表index
 *
 * @see ViewGroup.recyclerView 性能开销更少，但使用复杂不灵活，更建议使用simpleRecyclerView
 */
fun <T: RecyclerItemData> ViewGroup.simpleRecyclerView(
    width: Int = WRAP_CONTENT, height: Int = WRAP_CONTENT,
    @AttrRes attr: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean = false,

    overScrollMode: Int? = null,
    viewHolderCreator: (Context.() -> ViewGroup)? = null,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
        context, LinearLayoutManager.VERTICAL, false
    ),
    itemDecoration: RecyclerView.ItemDecoration? = null,
    data: List<T>? = null,
    onClick: View.OnClickListener? = null,
    block: ViewGroup.(List<T>, Int) -> Unit,
) = RecyclerView(context, null, attr).apply {
    setup(
        width, height, id, tag, background, padding, visibility,
        fitsSystemWindows = fitsSystemWindows, onClick = onClick,
    )
    this.layoutManager = layoutManager
    itemDecoration?.let { addItemDecoration(it) }
    loadData(viewHolderCreator, data ?: listOf(), block)
    overScrollMode?.let { this.overScrollMode = it }
    this@simpleRecyclerView.addView(this)
}

private fun <T: RecyclerItemData> RecyclerView.loadData(
    viewHolderCreator: (Context.() -> ViewGroup)?,
    data: List<T>,
    block: ViewGroup.(List<T>, Int) -> Unit,
) {
    if (adapter == null) {
        adapter = SimpleAdapter(viewHolderCreator, data, block)
    } else {
        (adapter as? SimpleAdapter<T>)?.update(data)
    }
}

private class SimpleAdapter<T: RecyclerItemData>(
    private val viewHolderCreator: (Context.() -> ViewGroup)?,
    private var data: List<T>,
    private val block: ViewGroup.(List<T>, Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), BrickRecyclerViewAdapter<T> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        object : RecyclerView.ViewHolder(parent.context.run {
            viewHolderCreator?.let { it() } ?: frameLayout(MATCH_PARENT)
        }) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.run {
            this as FrameLayout
            if (childCount > 0) {
                removeAllViews()
            }
            block(data, position)
        }
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    override fun update(data: List<T>) {
        val oldData = this.data
        this.data = data
        DiffUtil.calculateDiff(DiffCallback(oldData, data)).dispatchUpdatesTo(this)
    }
}