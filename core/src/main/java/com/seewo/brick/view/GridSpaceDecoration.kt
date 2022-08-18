package com.seewo.brick.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class GridSpaceDecoration(
    private val columnSpace: Int,
    private val rowSpace: Int,
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager

        if (layoutManager is GridLayoutManager) {
            val horizontalItemSpace =
                columnSpace * (layoutManager.spanCount - 1) / layoutManager.spanCount
            val rowCount = (layoutManager.itemCount - 1) / layoutManager.spanCount + 1
            val verticalItemSpace = rowSpace * (rowCount - 1) / rowCount
            val position = parent.getChildAdapterPosition(view)
            when (position % layoutManager.spanCount) {
                0 -> {
                    outRect.right = horizontalItemSpace
                }
                layoutManager.spanCount - 1 -> {
                    outRect.left = horizontalItemSpace
                }
                else -> {
                    outRect.left = horizontalItemSpace / 2
                    outRect.right = horizontalItemSpace / 2
                }
            }
            when (position / layoutManager.spanCount) {
                0 -> {
                    outRect.bottom = verticalItemSpace
                }
                (layoutManager.itemCount - 1) / layoutManager.spanCount -> {
                    outRect.top = verticalItemSpace
                }
                else -> {
                    outRect.bottom = verticalItemSpace / 2
                    outRect.top = verticalItemSpace / 2
                }
            }
        }
    }
}