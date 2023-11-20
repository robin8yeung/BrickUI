/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.seewo.brick.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import kotlin.math.absoluteValue
import kotlin.math.sign

/**
 * Layout to wrap a scrollable component inside a ViewPager2. Provided as a solution to the problem
 * where pages of ViewPager2 have nested scrollable elements that scroll in the same direction as
 * ViewPager2. The scrollable element needs to be the immediate and only child of this host layout.
 *
 * This solution has limitations when using multiple levels of nested scrollable elements
 * (e.g. a horizontal RecyclerView in a vertical RecyclerView in a horizontal ViewPager2).
 */
class NestedScrollableHost : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var touchSlop = 0
    private var initialX = 0f
    private var initialY = 0f
    var debug = true
    private val parentViewPager: ViewPager2?
        get() {
            var v: View? = parent as? View
            while (v != null && v !is ViewPager2) {
                v = v.parent as? View
            }
            return v as? ViewPager2
        }

    private val child: View? get() = if (childCount > 0) getChildAt(0) else null

    init {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    private fun canChildScroll(orientation: Int, delta: Float): Boolean {
        val direction = -delta.sign.toInt()
        log("orientation: $orientation, direction: $direction")
        return when (orientation) {
            0 -> child?.canScrollHorizontally(direction) ?: false
            1 -> child?.canScrollVertically(direction) ?: false
            else -> throw IllegalArgumentException()
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        try {
            handleInterceptTouchEvent(e)
        } catch (t: Throwable) {
            log(t.toString())
        }
        return super.onInterceptTouchEvent(e)
    }

    private var currentMotionEvent: MotionEvent? = null
    private fun handleInterceptTouchEvent(e: MotionEvent) {
        currentMotionEvent = e
        log("handleInterceptTouchEvent: ${e.action}")

        val orientation = parentViewPager?.orientation ?: return
        log("check can child scroll: ${e.action}")

        // Early return if child can't scroll in same direction as parent
        if (!canChildScroll(orientation, -1f) && !canChildScroll(orientation, 1f)) {
            log("cant scroll")
            return
        }

        if (e.action == MotionEvent.ACTION_DOWN) {
            initialX = e.x
            initialY = e.y
            parent.requestDisallowInterceptTouchEvent(true)
        } else if (e.action == MotionEvent.ACTION_MOVE) {
            val dx = e.x - initialX
            val dy = e.y - initialY
            val isVpHorizontal = orientation == ORIENTATION_HORIZONTAL

            val scaledDx = dx.absoluteValue
            val scaledDy = dy.absoluteValue

            log("touchSlop: $touchSlop, Dx: $scaledDx, Dy: $scaledDy")
            if (isVpHorizontal == (scaledDy > scaledDx)) {
                // Gesture is perpendicular, allow all parents to intercept
                log("isVpHorizontal == (scaledDy > scaledDx), false")
                parent.requestDisallowInterceptTouchEvent(false)
            } else {
                // Gesture is parallel, query child if movement in that direction is possible
                if (canChildScroll(orientation, if (isVpHorizontal) dx else dy)) {
                    // Child can scroll, disallow all parents to intercept
                    log("canChildScroll(orientation, if (isVpHorizontal) dx else dy), true")
                    parent.requestDisallowInterceptTouchEvent(true)
                } else {
                    // Child cannot scroll, allow all parents to intercept
                    log("canChildScroll(orientation, if (isVpHorizontal) dx else dy), false")
                    //If the opposite MOVE direction is continued, the child view can still be scrolled,
                    // but it cannot be handled. The parent needs to be redispatch.
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
        }
    }

    private fun log(msg: String) {
        if (debug) Log.i(TAG, msg)
    }

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        //double check. When child allow its parent intercept touch event,
        // but we want disallow our parent intercept touch event.
        log("requestDisallowInterceptTouchEvent -> $disallowIntercept")
        if (interceptDisallowRequest(disallowIntercept)) {
            log("requestDisallowInterceptTouchEvent -> $currentMotionEvent")
            return
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
    }

    private fun interceptDisallowRequest(disallowIntercept: Boolean): Boolean {
        if (disallowIntercept) return false
        val orientation = parentViewPager?.orientation ?: return false
        val isVpHorizontal = orientation == ORIENTATION_HORIZONTAL
        currentMotionEvent?.let {
            val dx = it.x - initialX
            val dy = it.y - initialY
            if (it.action == MotionEvent.ACTION_MOVE && canChildScroll(
                    orientation,
                    if (isVpHorizontal) dx else dy
                )
            ) {
                return true
            }
        }
        return false
    }

    companion object {
        private const val TAG = "NestedScrollableHost"
    }
}