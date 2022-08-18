package com.seewo.brick.ktx // 包名别改

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets

/**
 * 基于Fragment构造ViewPager
 *
 * @param isUserInputEnable 是否允许用户操作翻页。默认允许。
 * @param offscreenPageLimit 允许相邻几页进行离屏缓存。默认0.
 */
fun <T> Context.fragmentPager(
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
    itemDecoration: RecyclerView.ItemDecoration? = null,
    data: List<T>? = null,
    onClick: View.OnClickListener? = null,

    onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
    onPageSelected: ((position: Int) -> Unit)? = null,
    onPageScrollStateChanged: ((state: Int) -> Unit)? = null,
    block: Context.(List<T>, Int) -> Fragment,
) = ViewPager2(this, null, 0, style).apply {
    setup(
        width, height, id, tag, background, padding, visibility,
        fitsSystemWindows = fitsSystemWindows, onClick = onClick,
    )
    itemDecoration?.let { addItemDecoration(it) }
    loadData(context as FragmentActivity, data ?: listOf(), block)
    overScrollMode?.let { this.overScrollMode = it }
    offscreenPageLimit?.let { this.offscreenPageLimit = it }
    isUserInputEnable?.let { this.isUserInputEnabled = it }
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
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
}

/**
 * 基于Fragment构造ViewPager
 *
 * @param isUserInputEnable 是否允许用户操作翻页。默认允许。
 * @param offscreenPageLimit 允许相邻几页进行离屏缓存。默认0.
 */
fun <T> ViewGroup.fragmentPager(
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
    itemDecoration: RecyclerView.ItemDecoration? = null,
    data: List<T>? = null,
    onClick: View.OnClickListener? = null,

    onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
    onPageSelected: ((position: Int) -> Unit)? = null,
    onPageScrollStateChanged: ((state: Int) -> Unit)? = null,
    block: Context.(List<T>, Int) -> Fragment,
) = ViewPager2(context, null, 0, style).apply {
    setup(
        width, height, id, tag, background, padding, visibility,
        fitsSystemWindows = fitsSystemWindows, onClick = onClick,
    )
    itemDecoration?.let { addItemDecoration(it) }
    loadData(context as FragmentActivity, data ?: listOf(), block)
    overScrollMode?.let { this.overScrollMode = it }
    offscreenPageLimit?.let { this.offscreenPageLimit = it }
    isUserInputEnable?.let { this.isUserInputEnabled = it }
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
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
    activity: FragmentActivity,
    data: List<T>,
    block: Context.(List<T>, Int) -> Fragment,
) {
    if (adapter == null) {
        adapter = FragmentPagerAdapter(activity, data, block)
    } else {
        (adapter as? FragmentPagerAdapter<T>)?.update(data)
    }
}

private class FragmentPagerAdapter<T>(
    private val activity: FragmentActivity,
    private var data: List<T>,
    private val block: Context.(List<T>, Int) -> Fragment,
): FragmentStateAdapter(activity), BrickRecyclerViewAdapter<T> {

    override fun getItemCount(): Int = data.size

    override fun createFragment(position: Int): Fragment = activity.block(data, position)

    @SuppressLint("NotifyDataSetChanged")
    override fun update(data: List<T>) {
        this.data = data
        notifyDataSetChanged()
    }
}
