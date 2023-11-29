package com.seewo.brick.indicator

import android.content.Context
import android.database.DataSetObserver
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener

/**
 * CircleIndicator work with ViewPager
 */
class CircleIndicator(context: Context) : BaseCircleIndicator(context) {
    private var mViewpager: ViewPager? = null

    fun setViewPager(viewPager: ViewPager) {
        viewPager.also {
            mViewpager = it
        }.apply {
            mLastPosition = -1
            createIndicators()
            removeOnPageChangeListener(mInternalPageChangeListener)
            addOnPageChangeListener(mInternalPageChangeListener)
            mInternalPageChangeListener.onPageSelected(currentItem)
            mViewpager?.adapter?.registerDataSetObserver(dataSetObserver)
        }
    }

    private fun createIndicators() {
        createIndicators(
            mViewpager?.adapter?.count ?: 0,
            mViewpager?.currentItem ?: 0,
        )
    }

    private val mInternalPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrolled(
            position: Int, positionOffset: Float,
            positionOffsetPixels: Int
        ) = Unit

        override fun onPageSelected(position: Int) {
            if ((mViewpager?.adapter?.count ?: -1) <= 0) return
            animatePageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    val dataSetObserver: DataSetObserver = object : DataSetObserver() {
        override fun onChanged() {
            super.onChanged()
            val adapter = mViewpager?.adapter ?: return
            val newCount = adapter.count
            val currentCount = childCount
            mLastPosition = if (newCount == currentCount) {
                // No change
                return
            } else if (mLastPosition < newCount) {
                mViewpager!!.currentItem
            } else {
                -1
            }
            createIndicators()
        }
    }

    @Deprecated("User ViewPager addOnPageChangeListener")
    fun setOnPageChangeListener(
        onPageChangeListener: OnPageChangeListener?
    ) {
        if (mViewpager == null) {
            throw NullPointerException("can not find Viewpager , setViewPager first")
        }
        if (onPageChangeListener == null) {
            mViewpager!!.clearOnPageChangeListeners()
        } else {
            mViewpager!!.removeOnPageChangeListener(onPageChangeListener)
            mViewpager!!.addOnPageChangeListener(onPageChangeListener)
        }
    }
}