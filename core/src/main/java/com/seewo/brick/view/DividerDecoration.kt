package com.seewo.brick.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.seewo.brick.ktx.dp
import com.seewo.brick.params.EdgeInsets

class DividerDecoration(
    context: Context,
    orientation: Int? = null,
    dividerHeight: Int? = null,
    dividerColor: Int? = null,
    padding: EdgeInsets? = null,
) : ItemDecoration() {
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = dividerColor ?: Color.WHITE
        style = Paint.Style.FILL
    }
    private val mOrientation: Int = orientation ?: LinearLayoutManager.VERTICAL
    private val mDividerHeight: Int = dividerHeight ?: 1.dp
    private val mPadding: EdgeInsets = padding ?: EdgeInsets.zero
    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider) //使用系统自带的listDivider
    }

    init {
        require(!(mOrientation != LinearLayoutManager.VERTICAL && mOrientation != LinearLayoutManager.HORIZONTAL)) { "请输入正确的参数！" }
        val a = context.obtainStyledAttributes(ATTRS) //使用TypeArray加载该系统资源
        a.recycle() //缓存
    }

    //获取分割线尺寸
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mDividerHeight + mPadding.height)
        } else {
            outRect.set(0, 0, mDividerHeight + mPadding.width, 0)
        }
    }

    //绘制分割线
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    //绘制横向布局 item 分割线
    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop + mPadding.top
        val bottom = parent.measuredHeight - parent.paddingBottom - mPadding.bottom
        val childSize = parent.childCount
        for (i in 0 until childSize - 1) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + layoutParams.rightMargin + mPadding.start
            val right = left + mDividerHeight
            canvas.drawRect(
                left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                mPaint
            )
        }
    }

    //绘制纵向布局 item 分割线
    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft + mPadding.start //获取分割线的左边距，即RecyclerView的padding值
        val right = parent.measuredWidth - parent.paddingRight - mPadding.end //分割线右边距
        val childSize = parent.childCount
        //遍历所有item view，为它们的下方绘制分割线
        for (i in 0 until childSize - 1) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + layoutParams.bottomMargin + mPadding.top
            val bottom = top + mDividerHeight
            canvas.drawRect(
                left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                mPaint
            )
        }
    }

}