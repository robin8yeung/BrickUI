package com.seewo.brick.ktx

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import com.google.android.material.tabs.TabLayout
import com.seewo.brick.params.EdgeInsets


/**
 * 构建列布局（线性布局垂直方向）
 */
fun Context.liveColumn(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    gravity: Int? = null,
    block: (LinearLayout.() -> Unit)? = null
) = column (
    width, height, style, id, tag, background, padding,
    onClick = onClick,
    fitsSystemWindows = fitsSystemWindows, gravity = gravity, block = block
).apply {
    context.inMyLifecycle {
        visibility?.bind(this) {
            it ?: return@bind
            this@apply.visibility = it
        }
        isSelected?.bind(this) {
            it ?: return@bind
            this@apply.isSelected = it
        }
    }
}

/**
 * 构建列布局（线性布局垂直方向）
 */
fun ViewGroup.liveColumn(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    gravity: Int? = null,
    block: (LinearLayout.() -> Unit)? = null
) = column (
    width, height, style, id, tag, background, padding,
    onClick = onClick,
    fitsSystemWindows = fitsSystemWindows, gravity = gravity, block = block
).apply {
    context.inMyLifecycle {
        visibility?.bind(this) {
            it ?: return@bind
            this@apply.visibility = it
        }
        isSelected?.bind(this) {
            it ?: return@bind
            this@apply.isSelected = it
        }
    }
}

/**
 * 构建行布局（线性布局水平方向）
 */
fun Context.liveRow(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    gravity: Int = Gravity.CENTER_VERTICAL,
    block: (LinearLayout.() -> Unit)? = null
) = row(
    width, height, style, id, tag, background, padding,
    onClick = onClick, fitsSystemWindows = fitsSystemWindows, gravity = gravity,
    block = block
).apply {
    context.inMyLifecycle {
        visibility?.bind(this) {
            it ?: return@bind
            this@apply.visibility = it
        }
        isSelected?.bind(this) {
            it ?: return@bind
            this@apply.isSelected = it
        }
    }
}

/**
 * 构建行布局（线性布局水平方向）
 */
fun ViewGroup.liveRow(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    gravity: Int = Gravity.CENTER_VERTICAL,
    block: (LinearLayout.() -> Unit)? = null
) = row(
    width, height, style, id, tag, background, padding,
    onClick = onClick, fitsSystemWindows = fitsSystemWindows, gravity = gravity,
    block = block
).apply {
    context.inMyLifecycle {
        visibility?.bind(this) {
            it ?: return@bind
            this@apply.visibility = it
        }
        isSelected?.bind(this) {
            it ?: return@bind
            this@apply.isSelected = it
        }
    }
}

/**
 * 构建约束布局
 *
 * @param id 需要配置id，用于声明与父布局的关系
 */
fun Context.liveConstraintLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,
    block: (ConstraintLayout.() -> Unit)? = null
) = constraintLayout(
    width,
    height,
    style,
    id,
    tag,
    background,
    padding,
    onClick = onClick,
    fitsSystemWindows = fitsSystemWindows,
    block = block
).apply {
    context.inMyLifecycle {
        visibility?.bind(this) {
            it ?: return@bind
            this@apply.visibility = it
        }
        isSelected?.bind(this) {
            it ?: return@bind
            this@apply.isSelected = it
        }
    }
}

/**
 * 构建约束布局
 *
 * @param id 需要配置id，用于声明与父布局的关系
 */
fun ViewGroup.liveConstraintLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,
    block: (ConstraintLayout.() -> Unit)? = null
) = constraintLayout(
    width,
    height,
    style,
    id,
    tag,
    background,
    padding,
    onClick = onClick,
    fitsSystemWindows = fitsSystemWindows,
    block = block
).apply {
    context.inMyLifecycle {
        visibility?.bind(this) {
            it ?: return@bind
            this@apply.visibility = it
        }
        isSelected?.bind(this) {
            it ?: return@bind
            this@apply.isSelected = it
        }
    }
}

/**
 * 构建相对布局
 */
fun Context.liveRelativeLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    block: (RelativeLayout.() -> Unit)? = null
) = relativeLayout(
    width, height, style, id, tag, background, padding,
    onClick = onClick, fitsSystemWindows = fitsSystemWindows, block = block
).apply {
    context.inMyLifecycle {
        visibility?.bind(this) {
            it ?: return@bind
            this@apply.visibility = it
        }
        isSelected?.bind(this) {
            it ?: return@bind
            this@apply.isSelected = it
        }
    }
}

/**
 * 构建相对布局
 */
fun ViewGroup.liveRelativeLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    block: (RelativeLayout.() -> Unit)? = null
) = relativeLayout(
    width, height, style, id, tag, background, padding,
    onClick = onClick, fitsSystemWindows = fitsSystemWindows, block = block
).apply {
    context.inMyLifecycle {
        visibility?.bind(this) {
            it ?: return@bind
            this@apply.visibility = it
        }
        isSelected?.bind(this) {
            it ?: return@bind
            this@apply.isSelected = it
        }
    }
}

/**
 * 构建帧布局
 */
fun Context.liveFrameLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    block: (FrameLayout.() -> Unit)? = null
) = frameLayout(
    width, height, style, id, tag, background, padding,
    onClick = onClick, fitsSystemWindows = fitsSystemWindows,
    block = block
).apply {
    context.inMyLifecycle {
        visibility?.bind(this) {
            it ?: return@bind
            this@apply.visibility = it
        }
        isSelected?.bind(this) {
            it ?: return@bind
            this@apply.isSelected = it
        }
    }
}

/**
 * 构建帧布局
 */
fun ViewGroup.liveFrameLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean = false,

    block: (FrameLayout.() -> Unit)? = null
) = frameLayout(
    width, height, style, id, tag, background, padding,
    onClick = onClick, fitsSystemWindows = fitsSystemWindows,
    block = block
).apply {
    context.inMyLifecycle {
        visibility?.bind(this) {
            it ?: return@bind
            this@apply.visibility = it
        }
        isSelected?.bind(this) {
            it ?: return@bind
            this@apply.isSelected = it
        }
    }
}

/**
 * 构建网格布局（实验）
 */
fun ViewGroup.liveGridLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    orientation: Int = GridLayout.VERTICAL,
    rowCount: Int = 2,
    columnCount: Int? = null,
    block: (GridLayout.() -> Unit)? = null
) = gridLayout(
    width, height, style, id, tag, background, padding,
    orientation = orientation, rowCount = rowCount, columnCount = columnCount,
    block = block
).apply {
    context.inMyLifecycle {
        visibility?.bind(this) {
            it ?: return@bind
            this@apply.visibility = it
        }
        isSelected?.bind(this) {
            it ?: return@bind
            this@apply.isSelected = it
        }
    }
}

/**
 * 构建滚动
 */
fun ViewGroup.liveScrollView(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    fitsSystemWindows: Boolean = false,
    block: (ScrollView.() -> Unit)? = null
) = scrollView(
    width, height, style, id, tag, background, padding,
    fitsSystemWindows = fitsSystemWindows,
    block = block
).apply {
    context.inMyLifecycle {
        visibility?.bind(this) {
            it ?: return@bind
            this@apply.visibility = it
        }
        isSelected?.bind(this) {
            it ?: return@bind
            this@apply.isSelected = it
        }
    }
}

/**
 * 构建TabLayout
 *
 * @param data 传入一个列表用于构造TabLayout
 * @param selectedTabIndicator 自定义选中下划线样式，仅Android 5.1以上支持自定义。建议用layerDrawable来构造，但需要Android M以上才能很好支持。如果考虑Android 5.1，建议使用selectedTabIndicatorRes
 * @param selectedTabIndicatorRes 自定义选中下划线样式，仅Android 5.1以上支持自定义。
 * @param block 为data的每一项创建一个TabView
 *
 * @see layerDrawable
 */
fun <T> ViewGroup.liveTabLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,

    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    fitsSystemWindows: Boolean = false,

    data: List<T> = listOf(),
    currentIndex: LiveData<Int> = 0.static,
    @TabLayout.Mode tabMode: Int? = null,
    @TabLayout.TabIndicatorGravity tabIndicatorGravity: Int? = null,
    @ColorInt tabIndicatorColor: Int? = null,
    selectedTabIndicator: LayerDrawable? = null,
    @DrawableRes selectedTabIndicatorRes: Int? = null,
    @TabLayout.TabGravity tabGravity: Int? = null,
    onTabSelected: ((TabLayout.Tab) -> Unit)? = null,
    onTabUnselected: ((TabLayout.Tab) -> Unit)? = null,
    onTabReleased: ((TabLayout.Tab) -> Unit)? = null,
    block: (Context.(index: Int, item: T) -> View)? = null
) = tabLayout(
    width, height, style, id, tag, background, padding,
    fitsSystemWindows = fitsSystemWindows,
    data = data,
    tabMode = tabMode,
    tabIndicatorGravity = tabIndicatorGravity,
    tabIndicatorColor = tabIndicatorColor,
    selectedTabIndicator = selectedTabIndicator,
    selectedTabIndicatorRes = selectedTabIndicatorRes,
    tabGravity = tabGravity,
    onTabSelected = {
        onTabSelected?.invoke(it)
        currentIndex.data = it.position
    },
    onTabUnselected = onTabUnselected,
    onTabReleased = onTabReleased,
    block = block,
).apply {
    context.inMyLifecycle {
        visibility?.bindNotNull(this) {
            this@apply.visibility = it
        }
        currentIndex.bindNotNull(this) {
            if (this@apply.selectedTabPosition != it) {
                this@apply.selectTab(this@apply.getTabAt(it))
            }
        }
    }
}
