package com.seewo.brick.view

import android.view.View
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager

val View.parentId: Int
    get() = (parent as? View)?.id ?: 0

@Deprecated("使用ConstraintLayout.layoutParams()")
class ConstraintHelper(private val constraintLayout: ConstraintLayout) {
    private var begin: Editor? = null
    private val applyConstraintSet = ConstraintSet()
    private val resetConstraintSet = ConstraintSet()

    /**
     * 开始修改
     */
    fun edit(): Editor {
        synchronized(Editor::class.java) {
            if (begin == null) {
                begin = Editor()
            }
        }
        applyConstraintSet.clone(constraintLayout)
        return begin!!
    }

    /**
     * 带动画的修改
     */
    fun editWithAnim(): Editor? {
        TransitionManager.beginDelayedTransition(constraintLayout)
        return edit()
    }

    /**
     * 重置
     */
    fun reset() {
        resetConstraintSet.applyTo(constraintLayout)
    }

    /**
     * 带动画的重置
     */
    fun resetWidthAnim() {
        TransitionManager.beginDelayedTransition(constraintLayout)
        resetConstraintSet.applyTo(constraintLayout)
    }

    inner class Editor {
        /**
         * 清除关系<br></br>
         * 注意：这里不仅仅会清除关系，还会清除对应控件的宽高为 w:0,h:0
         */
        fun clear(@IdRes vararg viewIds: Int): Editor {
            for (viewId in viewIds) {
                applyConstraintSet.clear(viewId)
            }
            return this
        }

        /**
         * 清除某个控件的，某个关系
         */
        fun clear(viewId: Int, anchor: Int): Editor {
            applyConstraintSet.clear(viewId, anchor)
            return this
        }

        /**
         * 为某个控件设置 margin
         * @param viewId 某个控件ID
         * @param left marginLeft
         * @param top   marginTop
         * @param right marginRight
         * @param bottom marginBottom
         */
        fun setMargin(@IdRes viewId: Int, left: Int, top: Int, right: Int, bottom: Int): Editor {
            setMarginStart(viewId, left)
            setMarginTop(viewId, top)
            setMarginEnd(viewId, right)
            setMarginBottom(viewId, bottom)
            return this
        }

        /**
         * 为某个控件设置 marginLeft
         * @param viewId 某个控件ID
         * @param left marginLeft
         */
        fun setMarginStart(@IdRes viewId: Int, left: Int): Editor {
            applyConstraintSet.setMargin(viewId, ConstraintSet.START, left)
            return this
        }

        /**
         * 为某个控件设置 marginRight
         * @param viewId 某个控件ID
         * @param right marginRight
         */
        fun setMarginEnd(@IdRes viewId: Int, right: Int): Editor {
            applyConstraintSet.setMargin(viewId, ConstraintSet.END, right)
            return this
        }

        /**
         * 为某个控件设置 marginTop
         * @param viewId 某个控件ID
         * @param top marginTop
         */
        fun setMarginTop(@IdRes viewId: Int, top: Int): Editor {
            applyConstraintSet.setMargin(viewId, ConstraintSet.TOP, top)
            return this
        }

        /**
         * 为某个控件设置marginBottom
         * @param viewId 某个控件ID
         * @param bottom marginBottom
         */
        fun setMarginBottom(@IdRes viewId: Int, bottom: Int): Editor {
            applyConstraintSet.setMargin(viewId, ConstraintSet.BOTTOM, bottom)
            return this
        }

        /**
         * 为某个控件设置关联关系 left_to_left_of
         */
        fun startToStartOf(@IdRes startId: Int, @IdRes endId: Int): Editor {
            applyConstraintSet.connect(startId, ConstraintSet.START, endId, ConstraintSet.START)
            return this
        }

        /**
         * 为某个控件设置关联关系 left_to_right_of
         */
        fun startToEndOf(@IdRes startId: Int, @IdRes endId: Int): Editor {
            applyConstraintSet.connect(startId, ConstraintSet.START, endId, ConstraintSet.END)
            return this
        }

        /**
         * 为某个控件设置关联关系 top_to_top_of
         */
        fun topToTopOf(@IdRes startId: Int, @IdRes endId: Int): Editor {
            applyConstraintSet.connect(startId, ConstraintSet.TOP, endId, ConstraintSet.TOP)
            return this
        }

        /**
         * 为某个控件设置关联关系 top_to_bottom_of
         */
        fun topToBottomOf(@IdRes startId: Int, @IdRes endId: Int): Editor {
            applyConstraintSet.connect(startId, ConstraintSet.TOP, endId, ConstraintSet.BOTTOM)
            return this
        }

        /**
         * 为某个控件设置关联关系 right_to_left_of
         */
        fun endToStartOf(@IdRes startId: Int, @IdRes endId: Int): Editor {
            applyConstraintSet.connect(startId, ConstraintSet.END, endId, ConstraintSet.START)
            return this
        }

        /**
         * 为某个控件设置关联关系 right_to_right_of
         */
        fun endToEndOf(@IdRes startId: Int, @IdRes endId: Int): Editor {
            applyConstraintSet.connect(startId, ConstraintSet.END, endId, ConstraintSet.END)
            return this
        }

        /**
         * 为某个控件设置关联关系 bottom_to_bottom_of
         */
        fun bottomToBottomOf(@IdRes startId: Int, @IdRes endId: Int): Editor {
            applyConstraintSet.connect(startId, ConstraintSet.BOTTOM, endId, ConstraintSet.BOTTOM)
            return this
        }

        /**
         * 为某个控件设置关联关系 bottom_to_top_of
         */
        fun bottomToTopOf(@IdRes startId: Int, @IdRes endId: Int): Editor {
            applyConstraintSet.connect(startId, ConstraintSet.BOTTOM, endId, ConstraintSet.TOP)
            return this
        }

        /**
         * 为某个控件设置宽度
         */
        fun setWidth(@IdRes viewId: Int, width: Int): Editor {
            applyConstraintSet.constrainWidth(viewId, width)
            return this
        }

        /**
         * 为某个控件设置宽度比例
         */
        fun setPercentWidth(@IdRes viewId: Int, percent: Float): Editor {
            applyConstraintSet.constrainPercentWidth(viewId, percent)
            return this
        }

        /**
         * 为某个控件设置最大宽度
         */
        fun setMaxWidth(@IdRes viewId: Int, width: Int): Editor {
            applyConstraintSet.constrainMaxWidth(viewId, width)
            return this
        }

        /**
         * 为某个控件设置最小宽度
         */
        fun setMinWidth(@IdRes viewId: Int, width: Int): Editor {
            applyConstraintSet.constrainMinWidth(viewId, width)
            return this
        }

        /**
         * 某个控件设置高度
         */
        fun setHeight(@IdRes viewId: Int, height: Int): Editor {
            applyConstraintSet.constrainHeight(viewId, height)
            return this
        }

        /**
         * 为某个控件设置宽度比例
         */
        fun setPercentHeight(@IdRes viewId: Int, percent: Float): Editor {
            applyConstraintSet.constrainPercentHeight(viewId, percent)
            return this
        }

        /**
         * 为某个控件设置最大高度
         */
        fun setMaxHeight(@IdRes viewId: Int, height: Int): Editor {
            applyConstraintSet.constrainMaxHeight(viewId, height)
            return this
        }

        /**
         * 为某个控件设置最小高度
         */
        fun setMinHeight(@IdRes viewId: Int, height: Int): Editor {
            applyConstraintSet.constrainMinHeight(viewId, height)
            return this
        }

        fun setRatio(@IdRes viewId: Int, ratioString: String?): Editor {
            applyConstraintSet.setDimensionRatio(viewId, ratioString)
            return this
        }

        /**
         * 提交应用生效
         */
        fun commit() {
            applyConstraintSet.applyTo(constraintLayout)
        }
    }
}