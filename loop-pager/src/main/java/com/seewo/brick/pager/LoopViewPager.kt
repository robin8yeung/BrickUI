/*
 * Copyright (C) 2013 Leszek Mzyk
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
package com.seewo.brick.pager

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration

class LoopViewPager : ViewPager {
    private var mAdapter: LoopPagerAdapterWrapper? = null
    private var mBoundaryCaching = DEFAULT_BOUNDARY_CASHING
    private var mBoundaryLooping = DEFAULT_BOUNDARY_LOOPING
    private var mOnPageChangeListeners: MutableList<OnPageChangeListener>? = null
    private var mDuration: Duration? = null
    private var mDurationJob: Job? = null
    private var mFirstAttach = true

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        super.removeOnPageChangeListener(onPageChangeListener)
        super.addOnPageChangeListener(onPageChangeListener)
        if (context is LifecycleOwner) {
            context.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) {
                    if (!isAttachedToWindow) return
                    mDuration?.let {
                        setDuration(it)
                    }
                }

                override fun onStop(owner: LifecycleOwner) {
                    mDurationJob?.cancel()
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    owner.lifecycle.removeObserver(this)
                }
            })
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        fixBugResetFirstLayout()
        mDuration?.let {
            setDuration(it)
        }
    }

    private fun LoopViewPager.fixBugResetFirstLayout() {
        if (!mFirstAttach) {
            runCatching {
                // 避免在RecyclerView或父级ViewPager中，二次attach后，首次smoothScroll时丢失动画
                ViewPager::class.java.getDeclaredField("mFirstLayout").run {
                    isAccessible = true
                    setBoolean(this@LoopViewPager, false)
                }
            }
        } else {
            mFirstAttach = false
        }
    }

    override fun onDetachedFromWindow() {
        if ((context as? Activity)?.isDestroyed != false) {
            super.onDetachedFromWindow()
        } else {
            fixBugViewPagerAnimationInterrupted()
        }
        mDurationJob?.cancel()
    }

    @SuppressLint("SoonBlockedPrivateApi")
    private fun LoopViewPager.fixBugViewPagerAnimationInterrupted() {
        // 当ViewPager在RecyclerView中，当detach时，ViewPager的动画会被中断
        runCatching {
            ViewGroup::class.java.getDeclaredMethod("clearCachedLayoutMode").run {
                isAccessible = true
                invoke(this@LoopViewPager)
            }
        }

    }

    /**
     * If set to true, the boundary views (i.e. first and last) will never be
     * destroyed This may help to prevent "blinking" of some views
     */
    fun setBoundaryCaching(flag: Boolean) {
        mBoundaryCaching = flag
        mAdapter?.setBoundaryCaching(flag)
    }

    fun setBoundaryLooping(flag: Boolean) {
        mBoundaryLooping = flag
        mAdapter?.setBoundaryLooping(flag)
    }

    override fun setAdapter(adapter: PagerAdapter?) {
        adapter ?: return
        mAdapter = LoopPagerAdapterWrapper(adapter).apply {
            setBoundaryCaching(mBoundaryCaching)
            setBoundaryLooping(mBoundaryLooping)
        }
        super.setAdapter(mAdapter)
        setCurrentItem(0, false)
    }

    override fun getAdapter(): PagerAdapter? {
        return mAdapter?.realAdapter
    }

    fun setDuration(duration: Duration?) {
        mDuration = duration
        mDurationJob?.cancel()
        duration ?: return
        mDurationJob = MainScope().launch {
            repeat(Int.MAX_VALUE) {
                delay(duration)
                currentItem += 1
            }
        }
    }

    override fun getCurrentItem(): Int {
        return mAdapter?.toRealPosition(super.getCurrentItem()) ?: 0
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        val realItem = mAdapter?.toInnerPosition(item) ?: 0
        super.setCurrentItem(realItem, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        if (currentItem != item) {
            setCurrentItem(item, true)
        }
    }

    override fun addOnPageChangeListener(listener: OnPageChangeListener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = ArrayList()
        }
        mOnPageChangeListeners?.add(listener)
    }

    override fun removeOnPageChangeListener(listener: OnPageChangeListener) {
        mOnPageChangeListeners?.remove(listener)
    }

    override fun clearOnPageChangeListeners() {
        mOnPageChangeListeners?.clear()
    }

    private val onPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        private var mPreviousOffset = -1f
        private var mPreviousPosition = -1f
        override fun onPageSelected(position: Int) {
            val realPosition = mAdapter?.toRealPosition(position) ?: return
            if (mPreviousPosition != realPosition.toFloat()) {
                mPreviousPosition = realPosition.toFloat()
                mOnPageChangeListeners?.forEach {
                    it.onPageSelected(realPosition)
                }
            }
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            val adapter = mAdapter ?: return
            val realPosition = adapter.toRealPosition(position)
            if (positionOffset == 0f
                && mPreviousOffset == 0f
                && (position == 0
                        || position == adapter.count - 1)
            ) {
                setCurrentItem(realPosition, false)
            }
            mPreviousOffset = positionOffset
            mOnPageChangeListeners?.forEach {
                if (realPosition != adapter.realCount - 1) {
                    it.onPageScrolled(
                        realPosition, positionOffset,
                        positionOffsetPixels
                    )
                } else {
                    if (positionOffset > .5) {
                        it.onPageScrolled(0, 0f, 0)
                    } else {
                        it.onPageScrolled(realPosition, 0f, 0)
                    }
                }
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            val adapter = mAdapter ?: return
            val position = super@LoopViewPager.getCurrentItem()
            val realPosition = adapter.toRealPosition(position)
            if (state == SCROLL_STATE_IDLE && (position == 0
                        || position == adapter.count - 1)
            ) {
                setCurrentItem(realPosition, false)
            }
            if (state == SCROLL_STATE_IDLE) {
                setDuration(mDuration)
            } else {
                mDurationJob?.cancel()
            }
            mOnPageChangeListeners?.forEach {
                it.onPageScrollStateChanged(state)
            }
        }
    }

    companion object {
        private const val DEFAULT_BOUNDARY_CASHING = true
        private const val DEFAULT_BOUNDARY_LOOPING = true

        /**
         * helper function which may be used when implementing FragmentPagerAdapter
         *
         * @return (position - 1)%count
         */
        fun toRealPosition(position: Int, count: Int): Int {
            var result = position
            result -= 1
            if (result < 0) {
                result += count
            } else {
                result %= count
            }
            return result
        }
    }
}