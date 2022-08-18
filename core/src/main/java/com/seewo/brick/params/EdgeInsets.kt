package com.seewo.brick.params

import kotlin.math.abs

class EdgeInsets private constructor(
    val start: Int,
    val top: Int,
    val end: Int,
    val bottom: Int,
) {

    val height = abs(bottom - top)
    val width = abs(end - start)

    companion object {
        @JvmStatic
        fun all(value: Int) = EdgeInsets(value, value, value, value)

        @JvmStatic
        val zero: EdgeInsets
            get() = EdgeInsets(0, 0, 0, 0)

        @JvmStatic
        fun only(
            start: Int = 0,
            top: Int = 0,
            end: Int = 0,
            bottom: Int = 0,
        ) = EdgeInsets(start, top, end, bottom)

        @JvmStatic
        fun symmetric(
            vertical: Int = 0,
            horizontal: Int = 0,
        ) = EdgeInsets(horizontal, vertical, horizontal, vertical)
    }
}