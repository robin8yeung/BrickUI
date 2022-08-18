package com.seewo.brick.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout

class WindowInsetsFrameLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(
    context!!, attrs, defStyleAttr, defStyleRes
) {
    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        val childCount = childCount
        for (index in 0 until childCount) {
            getChildAt(index).dispatchApplyWindowInsets(insets)
        }
        return insets
    }

    override fun dispatchApplyWindowInsets(insets: WindowInsets): WindowInsets {
        //重写分发方法，不判断是否消费
        for (index in 0 until childCount) {
            try {
                getChildAt(index).dispatchApplyWindowInsets(insets)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        return insets
    }

    init {
        setOnHierarchyChangeListener(object : OnHierarchyChangeListener {
            override fun onChildViewAdded(parent: View, child: View) {
                requestApplyInsets()
            }

            override fun onChildViewRemoved(parent: View, child: View) {}
        })
    }
}