package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import com.seewo.brick.init.setup
import com.seewo.brick.params.EdgeInsets


/**
 * 构建网格布局（实验）
 */
fun ViewGroup.gridLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,

    orientation: Int = GridLayout.HORIZONTAL,
    rowCount: Int = 2,
    columnCount: Int? = null,
    block: (GridLayout.() -> Unit)? = null
) = context.gridLayout(
    width,
    height,
    style,
    id,
    tag,
    foreground,
    background,
    padding,
    visibility,
    isSelected,
    orientation,
    rowCount,
    columnCount,
    block
).also {
    addView(it)
}

/**
 * 构建网格布局（实验）
 */
fun Context.gridLayout(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,

    orientation: Int = GridLayout.HORIZONTAL,
    rowCount: Int = 2,
    columnCount: Int? = null,
    block: (GridLayout.() -> Unit)? = null
) = GridLayout(this, null, 0, style).apply {
    setup(width, height, id, tag, foreground, background, padding, visibility, isSelected)
    this.orientation = orientation
    this.rowCount = rowCount
    columnCount?.let { this.columnCount = it }
}.attach(block)

/**
 * 声明网格布局的布局属性
 */
fun GridLayout.layoutParams(
    column: Int = GridLayout.UNDEFINED,
    columnSpan: Int = 1,
    columnWeight: Float = 0f,
    row: Int = GridLayout.UNDEFINED,
    rowSpan: Int = 1,
    rowWeight: Float = 0f,
    gravity: Int = Gravity.NO_GRAVITY,
    block: (GridLayout.() -> View)? = null
) = attach(block)?.apply {
    layoutParams = GridLayout.LayoutParams(layoutParams).apply {
        val columnAlignment = getAlignment(gravity, true)
        val rowAlignment = getAlignment(gravity, false)
        columnSpec = if (columnAlignment == null) GridLayout.spec(column, columnSpan, columnWeight)
        else GridLayout.spec(column, columnSpan, columnAlignment, columnWeight)
        rowSpec = if (rowAlignment == null) GridLayout.spec(row, rowSpan, rowWeight)
        else GridLayout.spec(row, rowSpan, rowAlignment, rowWeight)
    }
}

/**
 * 声明网格布局的布局属性
 */
fun GridLayout.layoutParams(
    apply: (GridLayout.LayoutParams.() -> Unit)? = null,
    block: (GridLayout.() -> View)? = null
) = attach(block)?.apply {
    apply?.let {
        layoutParams = GridLayout.LayoutParams(layoutParams).apply {
            it()
        }
    }
}

/**
 * 声明网格布局的布局属性
 */
@Deprecated("GridLayout.layoutParams")
fun <T: View> T.inGridLayout(
    column: Int = GridLayout.UNDEFINED,
    columnSpan: Int = 1,
    columnWeight: Float = 0f,
    row: Int = GridLayout.UNDEFINED,
    rowSpan: Int = 1,
    rowWeight: Float = 0f,
    gravity: Int = Gravity.NO_GRAVITY,
) = apply {
    layoutParams = GridLayout.LayoutParams(layoutParams).apply {
        val columnAlignment = getAlignment(gravity, true)
        val rowAlignment = getAlignment(gravity, false)
        columnSpec = if (columnAlignment == null) GridLayout.spec(column, columnSpan, columnWeight)
        else GridLayout.spec(column, columnSpan, columnAlignment, columnWeight)
        rowSpec = if (rowAlignment == null) GridLayout.spec(row, rowSpan, rowWeight)
        else GridLayout.spec(row, rowSpan, rowAlignment, rowWeight)
    }
}

private fun getAlignment(gravity: Int, horizontal: Boolean): GridLayout.Alignment? {
    val mask = if (horizontal) Gravity.HORIZONTAL_GRAVITY_MASK else Gravity.VERTICAL_GRAVITY_MASK
    val shift = if (horizontal) Gravity.AXIS_X_SHIFT else Gravity.AXIS_Y_SHIFT
    return when (gravity and mask shr shift) {
        Gravity.AXIS_SPECIFIED or Gravity.AXIS_PULL_BEFORE -> if (horizontal) GridLayout.LEFT else GridLayout.TOP
        Gravity.AXIS_SPECIFIED or Gravity.AXIS_PULL_AFTER -> if (horizontal) GridLayout.RIGHT else GridLayout.BOTTOM
        Gravity.AXIS_SPECIFIED or Gravity.AXIS_PULL_BEFORE or Gravity.AXIS_PULL_AFTER -> GridLayout.FILL
        Gravity.AXIS_SPECIFIED -> GridLayout.CENTER
        Gravity.AXIS_SPECIFIED or Gravity.AXIS_PULL_BEFORE or Gravity.RELATIVE_LAYOUT_DIRECTION -> GridLayout.START
        Gravity.AXIS_SPECIFIED or Gravity.AXIS_PULL_AFTER or Gravity.RELATIVE_LAYOUT_DIRECTION -> GridLayout.END
        else -> null
    }
}
