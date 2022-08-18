package com.seewo.brick.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

/**
 * @param onStateChanged 指Fragment独有的状态变化 onHiddenChanged setUserVisibleHint
 * @param lifecycleHandler 用于监听Fragment的生命周期
 */
class BrickFragment(
    private val onStateChanged: ((BrickFragment) -> Unit)? = null,
    private val lifecycleHandler: (BrickFragment.(Lifecycle) -> Unit)? = null,
    private val block: Context.() -> View,
): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = requireContext().block().apply {
        lifecycleHandler?.let {
            it(lifecycle)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        onStateChanged?.invoke(this)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        onStateChanged?.invoke(this)
    }

    val isConfirmedVisible: Boolean
        get() = isResumed && isVisible && userVisibleHint
}