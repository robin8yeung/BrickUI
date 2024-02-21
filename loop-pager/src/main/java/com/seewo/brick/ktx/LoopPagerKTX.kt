package com.seewo.brick.ktx // 包名别改

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.seewo.brick.pager.LoopPagerAdapterWrapper
import com.seewo.brick.pager.LoopViewPager
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.view.trySetDuration
import kotlin.time.Duration


/**
 * 封装LoopViewPager：https://github.com/ongakuer/CircleIndicator
 * 为适配BrickUI，引入到本项目中并做一定修改
 *
 * @param duration 轮播自动翻页间隔。null则不自动翻页
 * @param scrollDuration viewPager滚动动画时长，单位毫秒
 * @param offscreenPageLimit 允许相邻几页进行离屏缓存。默认0.
 */
fun <T> Context.loopPager(
    width: Int = MATCH_PARENT, height: Int = WRAP_CONTENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean = false,
    offscreenPageLimit: Int? = null,
    overScrollMode: Int? = null,
    data: List<T>? = null,
    onClick: View.OnClickListener? = null,

    duration: Duration? = null,
    scrollDuration: Int? = null,
    onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
    onPageSelected: ((position: Int) -> Unit)? = null,
    onPageScrollStateChanged: ((state: Int) -> Unit)? = null,
    block: Context.(List<T>, Int) -> View,
) = LoopViewPager(this).apply {
    setup(
        width, height, id, tag, foreground, background, padding, visibility,
        fitsSystemWindows = fitsSystemWindows, onClick = onClick,
    )
    loadData(data ?: emptyList(), block)
    overScrollMode?.let {
        this.overScrollMode = it
        this.runCatching {
            (getChildAt(0) as? RecyclerView)?.overScrollMode = it
        }
    }
    offscreenPageLimit?.let { this.offscreenPageLimit = it }
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int,
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
    setDuration(duration)
    scrollDuration?.let { trySetDuration(it) }
}.applyMargin(margin)

/**
 * 封装LoopViewPager：https://github.com/ongakuer/CircleIndicator
 * 为适配BrickUI，引入到本项目中并做一定修改
 *
 * @param duration 轮播自动翻页间隔。null则不自动翻页
 * @param scrollDuration viewPager滚动动画时长，单位毫秒
 * @param offscreenPageLimit 允许相邻几页进行离屏缓存。默认0.
 */
fun <T> ViewGroup.loopPager(
    width: Int = MATCH_PARENT, height: Int = WRAP_CONTENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean = false,
    offscreenPageLimit: Int? = null,
    overScrollMode: Int? = null,
    data: List<T>? = null,
    onClick: View.OnClickListener? = null,

    duration: Duration? = null,
    scrollDuration: Int? = null,
    onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
    onPageSelected: ((position: Int) -> Unit)? = null,
    onPageScrollStateChanged: ((state: Int) -> Unit)? = null,
    block: Context.(List<T>, Int) -> View,
) = context.loopPager(
    width, height, id, tag, foreground, background, margin, padding, visibility,
    fitsSystemWindows, offscreenPageLimit, overScrollMode, data, onClick,
    duration, scrollDuration, onPageScrolled, onPageSelected, onPageScrollStateChanged, block,
).also { addView(it) }.applyMargin(margin)

private fun <T> LoopViewPager.loadData(
    data: List<T>,
    block: Context.(List<T>, Int) -> View,
) {
    if (adapter == null) {
        adapter = LoopPagerAdapterWrapper(SimpleViewPagerAdapter(data, block))
    } else {
        update(data)
    }
}

fun <T> LoopViewPager.update(data: List<T>) {
    ((adapter as? LoopPagerAdapterWrapper)?.realAdapter as? BrickRecyclerViewAdapter<T>)?.update(data)
}

private class SimpleViewPagerAdapter<T>(
    private var data: List<T>,
    private val block: Context.(List<T>, Int) -> View,
) : PagerAdapter(), BrickRecyclerViewAdapter<T> {

    init {
        if (data.isEmpty()) throw IllegalArgumentException("Data must not be empty")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun update(data: List<T>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getCount(): Int = data.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return container.context.block(data, position).also {
            container.addView(it)
        }
    }
}
