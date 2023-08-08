package com.seewo.brick.ktx // 包名别改

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.IdRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets

/**
 * 构造RecyclerView，构建方法较简单，但基本无法利用RecyclerView的重用特性，滚动会造成内存都懂。谨慎使用
 *
 * @param viewHolderCreator ViewHolder默认宽为MATCH_PARENT，若不满足需求，可通过此参数自行创建（如横向RecyclerView）
 * @param data 列表数据
 * @param block 针对每一个Item的数据到创建ItemView。回调输入为数据列表，列表index
 *
 * @see ViewGroup.recyclerView 性能开销更少
 */
fun <T> Context.simpleStatelessRecyclerView(
    width: Int, height: Int,
    @AttrRes attr: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean = false,

    overScrollMode: Int? = null,
    viewHolderCreator: (Context.() -> ViewGroup)? = null,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
        this, LinearLayoutManager.VERTICAL, false
    ),
    itemDecoration: RecyclerView.ItemDecoration? = null,
    data: List<T>? = null,
    onClick: OnClickListener? = null,
    block: ViewGroup.(List<T>, Int) -> Unit,
) = RecyclerView(this, null, attr).apply {
    setup(
        width, height, id, tag, foreground, background, padding, visibility,
        fitsSystemWindows = fitsSystemWindows, onClick = onClick,
    )
    this.layoutManager = layoutManager
    itemDecoration?.let { addItemDecoration(it) }
    loadDataStateless(viewHolderCreator, data ?: listOf(), block)
    overScrollMode?.let { this.overScrollMode = it }
}

/**
 * 构造RecyclerView，构建方法较简单，但基本无法利用RecyclerView的重用特性，滚动会造成内存都懂。谨慎使用
 *
 * @param viewHolderCreator ViewHolder默认宽为MATCH_PARENT，不满足需求，可通过此参数自行创建（如横向RecyclerView）
 * @param data 列表数据
 * @param block 针对每一个Item的数据到创建ItemView。回调输入为数据列表，列表index
 *
 * @see ViewGroup.recyclerView 性能开销更少
 */
fun <T> ViewGroup.simpleStatelessRecyclerView(
    width: Int, height: Int,
    @AttrRes attr: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
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
    onClick: OnClickListener? = null,
    block: ViewGroup.(List<T>, Int) -> Unit,
) = context.simpleStatelessRecyclerView(
    width,
    height,
    attr,
    id,
    tag,
    foreground,
    background,
    padding,
    visibility,
    fitsSystemWindows,
    overScrollMode,
    viewHolderCreator,
    layoutManager,
    itemDecoration,
    data,
    onClick,
    block
).also { addView(it) }

private fun <T> RecyclerView.loadDataStateless(
    viewHolderCreator: (Context.() -> ViewGroup)?,
    data: List<T>,
    block: ViewGroup.(List<T>, Int) -> Unit,
) {
    if (adapter == null) {
        adapter = SimpleStateLessAdapter(viewHolderCreator, data, block)
    } else {
        (adapter as? SimpleStateLessAdapter<T>)?.update(data)
    }
}

private class SimpleStateLessAdapter<T>(
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
        this.data = data
        notifyDataSetChanged()
    }
}