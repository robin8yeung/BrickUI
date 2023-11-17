package com.seewo.brick.indicator

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback

/**
 * CircleIndicator work with ViewPager2
 */
class CircleIndicator3(context: Context) : BaseCircleIndicator(context) {
    private var mViewpager: ViewPager2? = null

    fun setViewPager(viewPager: ViewPager2) {
        mViewpager = viewPager
        if (mViewpager?.adapter != null) {
            mLastPosition = -1
            createIndicators()
            mViewpager?.unregisterOnPageChangeCallback(mInternalPageChangeCallback)
            mViewpager?.registerOnPageChangeCallback(mInternalPageChangeCallback)
            mInternalPageChangeCallback.onPageSelected(mViewpager?.currentItem ?: 0)
        }
    }

    private fun createIndicators() {
        val count: Int = mViewpager?.adapter?.itemCount ?: 0
        createIndicators(count, mViewpager?.currentItem ?: 0)
    }

    private val mInternalPageChangeCallback: OnPageChangeCallback =
        object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val adapter = mViewpager?.adapter ?: return
                if (position == mLastPosition || adapter.itemCount <= 0) {
                    return
                }
                animatePageSelected(position)
            }
        }

    val adapterDataObserver: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            val adapter = mViewpager?.adapter ?: return
            val newCount = adapter.itemCount
            val currentCount = childCount
            mLastPosition = if (newCount == currentCount) {
                // No change
                return
            } else if (mLastPosition < newCount) {
                mViewpager!!.currentItem
            } else {
                RecyclerView.NO_POSITION
            }
            createIndicators()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            onChanged()
        }

        override fun onItemRangeChanged(
            positionStart: Int, itemCount: Int,
            payload: Any?
        ) {
            super.onItemRangeChanged(positionStart, itemCount, payload)
            onChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            onChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            onChanged()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            onChanged()
        }
    }
}