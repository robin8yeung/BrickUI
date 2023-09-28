package com.seewo.brick.ktx

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.seewo.brick.params.EdgeInsets
import kotlin.math.abs

/**
 * ViewPager的LiveData封装
 *
 * @param isUserInputEnable 是否允许用户操作翻页。默认允许。
 * @param offscreenPageLimit 允许相邻几页进行离屏缓存。默认0.
 * @param viewHolder 自定义ItemView的容器。调用端需要确保传进来的是个ViewGroup【！如果使用BrickUI传入，需要用itemView包裹】
 * @param currentIndex 当前页控制
 * @param smoothScroll 当currentIndex变化时，在【相邻页】是否展示动画滑动效果。
 *
 * @see ViewGroup.viewPager
 */
fun <T> ViewGroup.liveViewPager(
    width: Int, height: Int,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    fitsSystemWindows: Boolean = false,

    isUserInputEnable: Boolean? = null,
    offscreenPageLimit: Int? = null,
    overScrollMode: Int? = null,
    viewHolder: ViewGroup? = null,
    itemDecoration: RecyclerView.ItemDecoration? = null,
    currentIndex: LiveData<Int>? = null,
    smoothScroll: Boolean = true,
    data: LiveData<List<T>>? = null,
    lifecycleOwner: LifecycleOwner? = null,
    onClick: View.OnClickListener? = null,

    onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
    onPageSelected: ((position: Int) -> Unit)? = null,
    onPageScrollStateChanged: ((state: Int) -> Unit)? = null,
    block: Context.(List<T>, Int) -> View,
) = viewPager(
    width, height, style, id, tag, foreground, background, margin, padding,
    fitsSystemWindows = fitsSystemWindows,
    isUserInputEnable = isUserInputEnable, offscreenPageLimit = offscreenPageLimit,
    overScrollMode = overScrollMode, itemDecoration = itemDecoration, viewHolder = viewHolder,
    onClick = onClick, block = block,
).apply {
    if (lifecycleOwner != null) {
        data?.bindNotNull(lifecycleOwner) {
            (adapter as? BrickRecyclerViewAdapter<T>)?.update(it)
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
            (adapter as? BrickRecyclerViewAdapter<T>)?.update(it)
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
    val block: LifecycleOwner.() -> Unit = {
        val onPageChangeCallback = BrickOnPageChangeCallback(
            currentIndex, onPageScrolled, onPageSelected, onPageScrollStateChanged)
        registerOnPageChangeCallback(onPageChangeCallback)
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    lifecycle.removeObserver(this)
                    unregisterOnPageChangeCallback(onPageChangeCallback)
                }
            }
        })
    }
    lifecycleOwner?.block() ?: context.inMyLifecycle(block)

    if (isInEditMode) {
        visibility?.value?.let { this.visibility = it }
        data?.value?.let {  (adapter as? BrickRecyclerViewAdapter<T>)?.update(it) }
        currentIndex?.value?.let { if (abs(it - currentItem) == 1) {
            setCurrentItem(it, smoothScroll)
        } else setCurrentItem(it, false) }
    }
}

/**
 * ViewPager的LiveData封装(从属于fragmentActivity.getSupportFragmentManager()，生命周期跟随Activity)
 *
 * @param parentFragment 生命周期是否从属于指定Fragment，默认从属于Activity。
 * @param isUserInputEnable 是否允许用户操作翻页。默认允许。
 * @param offscreenPageLimit 允许相邻几页进行离屏缓存。默认0.
 * @param currentIndex 当前页控制
 * @param smoothScroll 当currentIndex变化时，是否展示动画滑动效果。
 *
 * @see ViewGroup.fragmentPager
 */
fun <T> Context.liveFragmentPager(
    width: Int, height: Int,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    fitsSystemWindows: Boolean = false,
    parentFragment: Fragment? = null,

    isUserInputEnable: Boolean? = null,
    offscreenPageLimit: Int? = null,
    overScrollMode: Int? = null,
    itemDecoration: RecyclerView.ItemDecoration? = null,
    currentIndex: LiveData<Int>? = null,
    smoothScroll: Boolean = true,
    data: LiveData<List<T>>? = null,
    lifecycleOwner: LifecycleOwner? = null,
    onClick: View.OnClickListener? = null,

    onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
    onPageSelected: ((position: Int) -> Unit)? = null,
    onPageScrollStateChanged: ((state: Int) -> Unit)? = null,
    block: Context.(List<T>, Int) -> Fragment,
) = fragmentPager(
    width, height, style, id, tag, foreground, background, padding,
    fitsSystemWindows = fitsSystemWindows, parentFragment = parentFragment,
    isUserInputEnable = isUserInputEnable, offscreenPageLimit = offscreenPageLimit,
    overScrollMode = overScrollMode, itemDecoration = itemDecoration,
    onClick = onClick, block = block,
).initLiveFragmentPager(
    data, visibility, currentIndex, smoothScroll, lifecycleOwner,
    onPageScrolled, onPageSelected, onPageScrollStateChanged,
)

/**
 * ViewPager的LiveData封装(从属于fragmentActivity.getSupportFragmentManager()，生命周期跟随Activity)
 *
 * @param parentFragment 生命周期是否从属于指定Fragment，默认从属于Activity。
 * @param isUserInputEnable 是否允许用户操作翻页。默认允许。
 * @param offscreenPageLimit 允许相邻几页进行离屏缓存。默认0.
 * @param currentIndex 当前页控制
 * @param smoothScroll 当currentIndex变化时，是否展示动画滑动效果。
 *
 * @see ViewGroup.fragmentPager
 */
fun <T> ViewGroup.liveFragmentPager(
    width: Int, height: Int,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    fitsSystemWindows: Boolean = false,
    parentFragment: Fragment? = null,

    isUserInputEnable: Boolean? = null,
    offscreenPageLimit: Int? = null,
    overScrollMode: Int? = null,
    itemDecoration: RecyclerView.ItemDecoration? = null,
    currentIndex: LiveData<Int>? = null,
    smoothScroll: Boolean = true,
    data: LiveData<List<T>>? = null,
    lifecycleOwner: LifecycleOwner? = null,
    onClick: View.OnClickListener? = null,

    onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
    onPageSelected: ((position: Int) -> Unit)? = null,
    onPageScrollStateChanged: ((state: Int) -> Unit)? = null,
    block: Context.(List<T>, Int) -> Fragment,
) = fragmentPager(
    width, height, style, id, tag, foreground, background, margin, padding,
    fitsSystemWindows = fitsSystemWindows, parentFragment = parentFragment,
    isUserInputEnable = isUserInputEnable, offscreenPageLimit = offscreenPageLimit,
    overScrollMode = overScrollMode, itemDecoration = itemDecoration,
    onClick = onClick, block = block,
).initLiveFragmentPager(
    data, visibility, currentIndex, smoothScroll, lifecycleOwner,
    onPageScrolled, onPageSelected, onPageScrollStateChanged,
)

private fun <T> ViewPager2.initLiveFragmentPager(
    data: LiveData<List<T>>? = null,
    visibility: LiveData<Int>? = null,
    currentIndex: LiveData<Int>? = null,
    smoothScroll: Boolean = true,
    lifecycleOwner: LifecycleOwner? = null,
    onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
    onPageSelected: ((position: Int) -> Unit)? = null,
    onPageScrollStateChanged: ((state: Int) -> Unit)? = null,
) = apply {
    if (lifecycleOwner != null) {
        data?.bindNotNull(lifecycleOwner) {
            (adapter as? BrickRecyclerViewAdapter<T>)?.update(it)
        }
        visibility?.bindNotNull(lifecycleOwner) {
            this@apply.visibility = it
        }
        currentIndex?.bindNotNull(lifecycleOwner) {
            if (it == currentItem) return@bindNotNull
            setCurrentItem(it, smoothScroll)
        }
    } else {
        data?.bindNotNull(context) {
            (adapter as? BrickRecyclerViewAdapter<T>)?.update(it)
        }
        visibility?.bindNotNull(context) {
            this@apply.visibility = it
        }
        currentIndex?.bindNotNull(context) {
            if (it == currentItem) return@bindNotNull
            setCurrentItem(it, smoothScroll)
        }
    }
    val block: LifecycleOwner.() -> Unit = {
        val onPageChangeCallback = BrickOnPageChangeCallback(
            currentIndex, onPageScrolled, onPageSelected, onPageScrollStateChanged)
        registerOnPageChangeCallback(onPageChangeCallback)
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    lifecycle.removeObserver(this)
                    unregisterOnPageChangeCallback(onPageChangeCallback)
                }
            }
        })
    }
    lifecycleOwner?.block() ?: context.inMyLifecycle(block)
}

private class BrickOnPageChangeCallback(
    private val currentIndex: LiveData<Int>?,
    private val onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
    private val onPageSelected: ((position: Int) -> Unit)? = null,
    private val onPageScrollStateChanged: ((state: Int) -> Unit)? = null,
): OnPageChangeCallback() {
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        onPageScrolled?.invoke(position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        currentIndex?.data = position
        onPageSelected?.invoke(position)
    }

    override fun onPageScrollStateChanged(state: Int) {
        onPageScrollStateChanged?.invoke(state)
    }
}