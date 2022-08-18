package com.seewo.brick.ktx // 包名别改

import android.graphics.Outline
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider

/**
 * View 矩形/圆角矩形裁剪
 *
 * @param rect 裁剪区域
 * @param radius 裁剪圆角
 *
 * 注意：关闭硬件加速将无法生效
 */
fun ViewGroup.roundRectClip(
    rect: Rect? = null,
    radius: Int? = null,
    block: ViewGroup.() -> View
) = block().apply {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline?) {
            val finalRect = rect ?: Rect(0, 0, view.width, view.height)
            outline?.setRoundRect(finalRect, (radius ?: 0).toFloat())
        }
    }
    clipToOutline = true
}

/**
 * View 圆形/椭圆形裁剪
 *
 * @param rect 裁剪区域
 *
 * 注意：关闭硬件加速将无法生效
 */
fun ViewGroup.ovalClip(
    rect: Rect? = null,
    block: ViewGroup.() -> View
) = block().apply {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline?) {
            val finalRect = rect ?: Rect(0, 0, view.width, view.height)
            outline?.setOval(finalRect)
        }
    }
    clipToOutline = true
}