package com.seewo.brick.params

class CornerRadius private constructor(
    val leftTop: Float,
    val rightTop: Float,
    val rightBottom: Float,
    val leftBottom: Float,
) {
    val toArray: FloatArray
        get() = floatArrayOf(
            leftTop, leftTop, rightTop, rightTop,
            rightBottom, rightBottom, leftBottom, leftBottom,
        )

    companion object {
        @JvmStatic
        fun only(
            leftTop: Int = 0,
            rightTop: Int = 0,
            rightBottom: Int = 0,
            leftBottom: Int = 0,
        ): CornerRadius = CornerRadius(
            leftTop.toFloat(), rightTop.toFloat(), rightBottom.toFloat(), leftBottom.toFloat())

        @JvmStatic
        fun zero() = only()

        @JvmStatic
        fun all(radius: Int) = only(radius, radius, radius, radius)
    }
}