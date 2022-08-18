package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.view.View
import androidx.lifecycle.Lifecycle
import com.seewo.brick.view.BrickFragment

/**
 * 声明式构造Fragment
 *
 * @param onStateChanged 指Fragment独有的状态变化 onHiddenChanged setUserVisibleHint，实际状态通过相关方法获取
 * @param lifecycleHandler 用于监听Fragment的生命周期
 */
fun Context.fragment(
    onStateChanged: ((BrickFragment) -> Unit)? = null,
    lifecycleHandler: (BrickFragment.(Lifecycle) -> Unit)? = null,
    block: Context.() -> View,
) = BrickFragment(onStateChanged, lifecycleHandler, block)