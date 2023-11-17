package com.seewo.brick.indicator

import android.animation.Animator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.animation.Interpolator
import android.widget.LinearLayout
import com.seewo.brick.ktx.animator
import com.seewo.brick.ktx.dp
import com.seewo.brick.ktx.ovalDrawable
import com.seewo.brick.pager.R
import kotlin.math.abs

abstract class BaseCircleIndicator(context: Context) : LinearLayout(context) {
    private var mIndicatorPadding = DEFAULT_INDICATOR_WIDTH.dp
    private var mIndicatorWidth = DEFAULT_INDICATOR_WIDTH.dp
    private var mIndicatorHeight = DEFAULT_INDICATOR_WIDTH.dp
    private var mIndicatorSelectedDrawable: Drawable = ovalDrawable(fillColor = Color.WHITE)
    private var mIndicatorUnselectedDrawable: Drawable = ovalDrawable(fillColor = Color.WHITE)
    private var mAnimatorIn: Animator? = R.animator.scale_with_alpha.animator
    private var mAnimatorOut: Animator? = mAnimatorIn?.clone()?.reversed()
    private var mImmediateAnimatorIn: Animator? = mAnimatorIn?.clone()?.immediate()
    private var mImmediateAnimatorOut: Animator? = mAnimatorIn?.clone()?.reversed()?.immediate()
    protected var mLastPosition = -1
    private var mIndicatorCreatedListener: IndicatorCreatedListener? = null

    init {
        initialize()
        if (isInEditMode) {
            createIndicators(3, 1)
        }
    }

    private fun Animator.reversed() = apply {
        interpolator = ReverseInterpolator()
    }

    private fun Animator.immediate() = apply {
        duration = 0
    }

    private fun initialize() {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    fun setIndicatorPadding(padding: Int) {
        mIndicatorPadding = padding
    }

    @JvmOverloads
    fun setIndicatorDrawable(
        selected: Drawable?,
        unselected: Drawable? = selected
    ) {
        mIndicatorSelectedDrawable = selected ?: mIndicatorSelectedDrawable
        mIndicatorUnselectedDrawable = unselected ?: mIndicatorSelectedDrawable
        changeIndicatorBackground()
    }

    @JvmOverloads
    fun setIndicatorAnimator(
        animatorIn: Animator?,
        animatorOut: Animator? = null
    ) {
        mAnimatorIn = animatorIn
        mAnimatorOut = animatorOut ?: animatorIn?.clone()?.reversed()
        mImmediateAnimatorIn = mAnimatorIn?.clone()?.immediate()
        mImmediateAnimatorOut = animatorOut?.clone()?.immediate()
            ?: animatorIn?.clone()?.reversed()?.immediate()
    }

    interface IndicatorCreatedListener {
        /**
         * IndicatorCreatedListener
         *
         * @param view internal indicator view
         * @param position position
         */
        fun onIndicatorCreated(view: View?, position: Int)
    }

    fun setIndicatorCreatedListener(
        indicatorCreatedListener: IndicatorCreatedListener?
    ) {
        mIndicatorCreatedListener = indicatorCreatedListener
    }

    private fun Animator.start(target: Any) {
        setTarget(target)
        start()
    }

    private fun Animator.stop() {
        if (isRunning) {
            end()
            cancel()
        }
    }

    fun createIndicators(count: Int, currentPosition: Int) {
        mImmediateAnimatorIn?.stop()
        mImmediateAnimatorOut?.stop()

        // Diff View
        val childViewCount = childCount
        if (count < childViewCount) {
            removeViews(count, childViewCount - count)
        } else if (count > childViewCount) {
            val addCount = count - childViewCount
            val orientation = orientation
            for (i in 0 until addCount) {
                addIndicator(orientation)
            }
        }

        // Bind Style
        var indicator: View
        for (i in 0 until count) {
            indicator = getChildAt(i)
            if (currentPosition == i) {
                indicator.setBackgroundDrawable(mIndicatorSelectedDrawable)
                mImmediateAnimatorIn?.start(indicator)
                mImmediateAnimatorIn?.end()
            } else {
                indicator.setBackgroundDrawable(mIndicatorUnselectedDrawable)
                mImmediateAnimatorOut?.start(indicator)
                mImmediateAnimatorOut?.end()
            }
            mIndicatorCreatedListener?.onIndicatorCreated(indicator, i)
        }
        mLastPosition = currentPosition
    }

    private fun addIndicator(orientation: Int) {
        val indicator = View(context)
        val params = generateDefaultLayoutParams()
        params.width = mIndicatorWidth
        params.height = mIndicatorHeight
        if (orientation == HORIZONTAL) {
            params.leftMargin = mIndicatorPadding
            params.rightMargin = mIndicatorPadding
        } else {
            params.topMargin = mIndicatorPadding
            params.bottomMargin = mIndicatorPadding
        }
        addView(indicator, params)
    }

    fun animatePageSelected(position: Int) {
        if (mLastPosition == position) {
            return
        }
        mAnimatorOut?.stop()
        mAnimatorIn?.stop()
        val currentIndicator = getChildAt(mLastPosition)
        if (mLastPosition >= 0 && currentIndicator != null) {
            currentIndicator.setBackgroundDrawable(mIndicatorUnselectedDrawable)
            mAnimatorOut?.setTarget(currentIndicator)
            mAnimatorOut?.start()
        }
        val selectedIndicator = getChildAt(position)
        if (selectedIndicator != null) {
            selectedIndicator.setBackgroundDrawable(mIndicatorSelectedDrawable)
            mAnimatorIn?.start(selectedIndicator)
        }
        mLastPosition = position
    }

    private fun changeIndicatorBackground() {
        val count = childCount
        if (count <= 0) {
            return
        }
        var currentIndicator: View
        for (i in 0 until count) {
            currentIndicator = getChildAt(i)
            if (i == mLastPosition) {
                currentIndicator.setBackgroundDrawable(mIndicatorSelectedDrawable)
            } else {
                currentIndicator.setBackgroundDrawable(mIndicatorUnselectedDrawable)
            }
        }
    }

    protected class ReverseInterpolator : Interpolator {
        override fun getInterpolation(value: Float): Float {
            return abs(1.0f - value)
        }
    }

    companion object {
        private const val DEFAULT_INDICATOR_WIDTH = 5
    }
}