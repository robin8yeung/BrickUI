package com.seewo.brick.ktx

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.seewo.brick.pager.LoopPagerAdapterWrapper
import com.seewo.brick.params.EdgeInsets
import kotlin.math.abs
import kotlin.time.Duration

/**
 * loopPager的LiveData封装
 *
 * @param offscreenPageLimit 允许相邻几页进行离屏缓存。默认0.
 * @param currentIndex 当前页控制
 * @param smoothScroll 当currentIndex变化时，在【相邻页】是否展示动画滑动效果。
 * @param duration 轮播自动翻页间隔。null则不自动翻页
 * @param scrollDuration viewPager滚动动画时长，单位毫秒
 *
 * @see ViewGroup.loopPager
 */
fun <T> ViewGroup.liveLoopPager(
    width: Int = MATCH_PARENT, height: Int = WRAP_CONTENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    fitsSystemWindows: Boolean = false,

    offscreenPageLimit: Int? = null,
    overScrollMode: Int? = null,

    currentIndex: LiveData<Int>? = null,
    smoothScroll: Boolean = true,
    data: LiveData<List<T>>? = null,
    lifecycleOwner: LifecycleOwner? = null,
    onClick: View.OnClickListener? = null,

    duration: LiveData<Duration>? = null,

    scrollDuration: Int? = null,
    onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
    onPageSelected: ((position: Int) -> Unit)? = null,
    onPageScrollStateChanged: ((state: Int) -> Unit)? = null,
    block: Context.(List<T>, Int) -> View,
) = loopPager(
    width,
    height,
    id,
    tag,
    foreground,
    background,
    margin,
    padding,
    data = data?.data,
    fitsSystemWindows = fitsSystemWindows,
    offscreenPageLimit = offscreenPageLimit,
    overScrollMode = overScrollMode,
    scrollDuration = scrollDuration,
    onClick = onClick,
    onPageScrolled = onPageScrolled,
    onPageSelected = onPageSelected,
    onPageScrollStateChanged = onPageScrollStateChanged,
    block = block,
).apply {
    (lifecycleOwner ?: context as? LifecycleOwner)?.run {
        data?.bindNotNull(this) {
            update(it)
        }
        visibility?.bindNotNull(this) {
            this@apply.visibility = it
        }
        currentIndex?.bindNotNull(this) {
            if (it == currentItem) return@bindNotNull
            if (abs(it - currentItem) == 1) {
                setCurrentItem(it, smoothScroll)
            } else setCurrentItem(it, false)
        }
        duration?.bind(this) {
            setDuration(it)
        }
    }

    if (isInEditMode) {
        visibility?.value?.let { this.visibility = it }
        data?.value?.let {
            ((adapter as? LoopPagerAdapterWrapper)?.realAdapter as? BrickRecyclerViewAdapter<T>)?.update(
                it
            )
        }
        currentIndex?.value?.let {
            if (abs(it - currentItem) == 1) {
                setCurrentItem(it, smoothScroll)
            } else setCurrentItem(it, false)
        }
    }
}

/**
 * loopPager的LiveData封装(从属于fragmentActivity.getSupportFragmentManager()，生命周期跟随Activity)
 *
 * @param offscreenPageLimit 允许相邻几页进行离屏缓存。默认0.
 * @param currentIndex 当前页控制
 * @param smoothScroll 当currentIndex变化时，是否展示动画滑动效果。
 * @param duration 轮播自动翻页间隔。null则不自动翻页
 * @param scrollDuration viewPager滚动动画时长，单位毫秒
 *
 * @see ViewGroup.fragmentPager
 */
fun <T> Context.liveLoopPager(
    width: Int = MATCH_PARENT, height: Int = WRAP_CONTENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    fitsSystemWindows: Boolean = false,

    offscreenPageLimit: Int? = null,
    overScrollMode: Int? = null,

    currentIndex: LiveData<Int>? = null,
    smoothScroll: Boolean = true,
    data: LiveData<List<T>>? = null,
    lifecycleOwner: LifecycleOwner? = null,
    onClick: View.OnClickListener? = null,

    duration: Duration? = null,
    scrollDuration: Int? = null,
    onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
    onPageSelected: ((position: Int) -> Unit)? = null,
    onPageScrollStateChanged: ((state: Int) -> Unit)? = null,
    block: Context.(List<T>, Int) -> View,
) = loopPager(
    width,
    height,
    id,
    tag,
    foreground,
    background,
    margin,
    padding,
    fitsSystemWindows = fitsSystemWindows,
    offscreenPageLimit = offscreenPageLimit,
    overScrollMode = overScrollMode,
    duration = duration,
    scrollDuration = scrollDuration,
    onClick = onClick,
    onPageScrolled = onPageScrolled,
    onPageSelected = onPageSelected,
    onPageScrollStateChanged = onPageScrollStateChanged,
    block = block,
).apply {
    if (lifecycleOwner != null) {
        data?.bindNotNull(lifecycleOwner) {
            ((adapter as? LoopPagerAdapterWrapper)?.realAdapter as? BrickRecyclerViewAdapter<T>)?.update(
                it
            )
        }
        visibility?.bindNotNull(lifecycleOwner) {
            this@apply.visibility = it
        }
        currentIndex?.bindNotNull(lifecycleOwner) {
            if (it == currentItem) return@bindNotNull
            if (abs(it - currentItem) == 1) {
                setCurrentItem(it, smoothScroll)
            } else setCurrentItem(it, false)
        }
    } else {
        data?.bindNotNull(context) {
            ((adapter as? LoopPagerAdapterWrapper)?.realAdapter as? BrickRecyclerViewAdapter<T>)?.update(
                it
            )
        }
        visibility?.bindNotNull(context) {
            this@apply.visibility = it
        }
        currentIndex?.bindNotNull(context) {
            if (it == currentItem) return@bindNotNull
            if (abs(it - currentItem) == 1) {
                setCurrentItem(it, smoothScroll)
            } else setCurrentItem(it, false)
        }
    }

    if (isInEditMode) {
        visibility?.value?.let { this.visibility = it }
        data?.value?.let {
            ((adapter as? LoopPagerAdapterWrapper)?.realAdapter as? BrickRecyclerViewAdapter<T>)?.update(
                it
            )
        }
        currentIndex?.value?.let {
            if (abs(it - currentItem) == 1) {
                setCurrentItem(it, smoothScroll)
            } else setCurrentItem(it, false)
        }
    }
}
