package com.seewo.brick.view

import android.content.Context
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager

class FixDurationScroller(context: Context, private val duration: Int): Scroller(context, AccelerateDecelerateInterpolator()) {
    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, duration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, this.duration)
    }
}

fun ViewPager.trySetDuration(duration: Int) {
    try {
        val scrollerField = ViewPager::class.java.getDeclaredField("mScroller")
        scrollerField.isAccessible = true
        val scroller = FixDurationScroller(context, duration)
        scrollerField.set(this, scroller)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}