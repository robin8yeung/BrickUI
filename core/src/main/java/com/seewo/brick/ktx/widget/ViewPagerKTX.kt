package com.seewo.brick.ktx // 包名别改

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets

/**
 * ViewPager的LiveData封装
 *
 * @param isUserInputEnable 是否允许用户操作翻页。默认允许。
 * @param offscreenPageLimit 允许相邻几页进行离屏缓存。默认0.
 * @param viewHolder 自定义ItemView的容器。调用端需要确保传进来的是个ViewGroup【！如果使用BrickUI传入，需要用itemView包裹】
 */
fun <T> ViewGroup.viewPager(
    width: Int, height: Int,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean = false,

    isUserInputEnable: Boolean? = null,
    offscreenPageLimit: Int? = null,
    overScrollMode: Int? = null,
    viewHolder: ViewGroup? = null,
    itemDecoration: RecyclerView.ItemDecoration? = null,
    data: List<T>? = null,
    onClick: View.OnClickListener? = null,

    onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
    onPageSelected: ((position: Int) -> Unit)? = null,
    onPageScrollStateChanged: ((state: Int) -> Unit)? = null,
    block: Context.(List<T>, Int) -> View,
) = ViewPager2(context, null, 0, style).apply {
    setup(
        width, height, id, tag, background, padding, visibility,
        fitsSystemWindows = fitsSystemWindows, onClick = onClick,
    )
    itemDecoration?.let { addItemDecoration(it) }
    loadData(viewHolder, data ?: listOf(), block)
    overScrollMode?.let {
        this.overScrollMode = it
        this.runCatching {
            (getChildAt(0) as? RecyclerView)?.overScrollMode = it
        }
    }
    offscreenPageLimit?.let { this.offscreenPageLimit = it }
    isUserInputEnable?.let { this.isUserInputEnabled = it }
    registerOnPageChangeCallback(object : OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            onPageScrolled?.invoke(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            onPageSelected?.invoke(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged?.invoke(state)
        }
    })
}.also { addView(it) }

private fun <T> ViewPager2.loadData(
    viewHolderView: ViewGroup?,
    data: List<T>,
    block: Context.(List<T>, Int) -> View,
) {
    if (adapter == null) {
        adapter = ViewPagerAdapter(viewHolderView, data, block)
    } else {
        (adapter as? ViewPagerAdapter<T>)?.update(data)
    }
}

private class ViewPagerAdapter<T>(
    private val viewHolderView: ViewGroup?,
    private var data: List<T>,
    private val block: Context.(List<T>, Int) -> View,
): RecyclerView.Adapter<RecyclerView.ViewHolder>(), BrickRecyclerViewAdapter<T> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        object : RecyclerView.ViewHolder(viewHolderView ?: FrameLayout(parent.context).apply {
            setup(MATCH_PARENT, MATCH_PARENT)
        }) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.run {
            this as ViewGroup
            if (childCount > 0) {
                removeAllViews()
            }
            addView(context.block(data, position))
        }
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    override fun update(data: List<T>) {
        this.data = data
        notifyDataSetChanged()
    }
}
