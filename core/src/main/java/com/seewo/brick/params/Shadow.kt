package com.seewo.brick.params

import android.graphics.Color
import androidx.annotation.ColorInt
import com.seewo.brick.ktx.dp

/**
 * 阴影参数设置
 *
 * @param color 阴影颜色
 * @param blur 阴影模糊半径
 * @param offsetX 阴影横向偏移
 * @param offsetY 阴影纵向偏移
 */
class Shadow(
    @ColorInt val color: Int = Color.parseColor("#5F333333"),
    val blur: Int = 3.dp,
    val offsetX: Int = 0,
    val offsetY: Int = 0,
)