package com.seewo.brick.ktx // 包名别改

import android.animation.Animator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Size
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.seewo.brick.indicator.CircleIndicator
import com.seewo.brick.indicator.CircleIndicator3
import com.seewo.brick.pager.R
import com.seewo.brick.params.EdgeInsets

/**
 * 封装CircleIndicator：https://github.com/ongakuer/CircleIndicator
 * 为适配BrickUI，引入到本项目中并做一定修改
 *
 * @param viewPager 绑定的ViewPager
 * @param indicatorPadding 指示器间距
 * @param animatorIn 指示器进入动画
 * @param animatorOut 指示器退出动画
 * @param selected 选中状态指示器样式
 * @param unselected 未选中状态指示器样式
 * @param orientation 指示器方向
 * @param gravity 指示器位置
 */
fun Context.indicator(
    width: Int, height: Int,
    viewPager: ViewPager,
    indicatorSize: Size? = null,
    indicatorPadding: Int? = null,
    animatorIn: Animator? = R.animator.scale_with_alpha.animator,
    animatorOut: Animator? = null,
    selected: Drawable? = null,
    unselected: Drawable? = null,
    orientation: Int? = null,
    gravity: Int? = null,
    padding: EdgeInsets? = null,

    @IdRes id: Int? = null,
    tag: Any? = null,
    visibility: Int? = null,
    margin: EdgeInsets? = null,
) = CircleIndicator(this).apply {
    setup(
        width, height,
        id = id,
        tag = tag,
        visibility = visibility,
        padding = padding,
    )
    indicatorSize?.let { setIndicatorSize(it) }
    indicatorPadding?.let { setIndicatorPadding(it) }
    setIndicatorAnimator(animatorIn, animatorOut)
    setIndicatorDrawable(selected, unselected)
    orientation?.let { setOrientation(it) }
    gravity?.let { setGravity(it) }
    setViewPager(viewPager)
}.applyMargin(margin)

/**
 * 封装CircleIndicator：https://github.com/ongakuer/CircleIndicator
 * 为适配BrickUI，引入到本项目中并做一定修改
 *
 * @param viewPager 绑定的ViewPager
 * @param indicatorPadding 指示器间距
 * @param animatorIn 指示器进入动画
 * @param animatorOut 指示器退出动画
 * @param selected 选中状态指示器样式
 * @param unselected 未选中状态指示器样式
 * @param orientation 指示器方向
 * @param gravity 指示器位置
 */
fun ViewGroup.indicator(
    width: Int = MATCH_PARENT, height: Int = WRAP_CONTENT,
    viewPager: ViewPager,
    indicatorSize: Size? = null,
    indicatorPadding: Int? = null,
    animatorIn: Animator? = R.animator.scale_with_alpha.animator,
    animatorOut: Animator? = null,
    selected: Drawable? = null,
    unselected: Drawable? = null,
    orientation: Int? = null,
    gravity: Int? = null,
    padding: EdgeInsets? = null,

    @IdRes id: Int? = null,
    tag: Any? = null,
    visibility: Int? = null,
    margin: EdgeInsets? = null,
) = context.indicator(
    width, height, viewPager, indicatorSize, indicatorPadding, animatorIn, animatorOut, selected, unselected,
    orientation, gravity, padding,
    id, tag, visibility, margin,
).also { addView(it) }.applyMargin(margin)

/**
 * 封装CircleIndicator：https://github.com/ongakuer/CircleIndicator
 * 为适配BrickUI，引入到本项目中并做一定修改
 *
 * @param viewPager 绑定的ViewPager
 * @param indicatorPadding 指示器间距
 * @param animatorIn 指示器进入动画
 * @param animatorOut 指示器退出动画
 * @param selected 选中状态指示器样式
 * @param unselected 未选中状态指示器样式
 * @param orientation 指示器方向
 * @param gravity 指示器位置
 */
fun Context.indicator(
    width: Int, height: Int,
    viewPager: ViewPager2,
    indicatorSize: Size? = null,
    indicatorPadding: Int? = null,
    animatorIn: Animator? = R.animator.scale_with_alpha.animator,
    animatorOut: Animator? = null,
    selected: Drawable? = null,
    unselected: Drawable? = null,
    orientation: Int? = null,
    gravity: Int? = null,
    padding: EdgeInsets? = null,

    @IdRes id: Int? = null,
    tag: Any? = null,
    visibility: Int? = null,
    margin: EdgeInsets? = null,
) = CircleIndicator3(this).apply {
    setup(
        width, height,
        id = id,
        tag = tag,
        visibility = visibility,
        padding = padding,
    )
    indicatorSize?.let { setIndicatorSize(it) }
    indicatorPadding?.let { setIndicatorPadding(it) }
    setIndicatorAnimator(animatorIn, animatorOut)
    setIndicatorDrawable(selected, unselected)
    orientation?.let { setOrientation(it) }
    gravity?.let { setGravity(it) }
    setViewPager(viewPager)
}.applyMargin(margin)

/**
 * 封装CircleIndicator：https://github.com/ongakuer/CircleIndicator
 * 为适配BrickUI，引入到本项目中并做一定修改
 *
 * @param viewPager 绑定的ViewPager
 * @param indicatorPadding 指示器间距
 * @param animatorIn 指示器进入动画
 * @param animatorOut 指示器退出动画
 * @param selected 选中状态指示器样式
 * @param unselected 未选中状态指示器样式
 * @param orientation 指示器方向
 * @param gravity 指示器位置
 */
fun ViewGroup.indicator(
    width: Int, height: Int,
    viewPager: ViewPager2,
    indicatorSize: Size? = null,
    indicatorPadding: Int? = null,
    animatorIn: Animator? = R.animator.scale_with_alpha.animator,
    animatorOut: Animator? = null,
    selected: Drawable? = null,
    unselected: Drawable? = null,
    orientation: Int? = null,
    gravity: Int? = null,
    padding: EdgeInsets? = null,

    @IdRes id: Int? = null,
    tag: Any? = null,
    visibility: Int? = null,
    margin: EdgeInsets? = null,
) = context.indicator(
    width, height, viewPager, indicatorSize, indicatorPadding, animatorIn, animatorOut, selected, unselected,
    orientation, gravity, padding,
    id, tag, visibility, margin,
).also { addView(it) }.applyMargin(margin)