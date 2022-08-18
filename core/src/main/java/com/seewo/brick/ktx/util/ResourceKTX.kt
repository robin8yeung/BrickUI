package com.seewo.brick.ktx // 包名别改

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.seewo.brick.BrickUI


/**
 * dp转px
 */
val Double.dp: Int
    get() = this.toFloat().dp

/**
 * dp转px
 */
val Float.dp: Int
    get() = BrickUI.dipToPixel(this)

/**
 * dp转px
 */
val Int.dp: Int
    get() = this.toFloat().dp

/**
 * sp转px
 */
val Double.sp: Int
    get() = this.toFloat().sp

/**
 * sp转px
 */
val Float.sp: Int
    get() = BrickUI.spToPixel(this)

/**
 * sp转px
 */
val Int.sp: Int
    get() = this.toFloat().sp

/**
 * 资源id转drawable
 */
val Int.drawable: Drawable?
    get() = BrickUI.drawable(this)

/**
 * 资源id转颜色类型drawable
 */
val Int.colorDrawable: Drawable?
    get() = runCatching { ColorDrawable(color) }.getOrNull()

/**
 * 资源id转color
 */
val Int.color: Int
    get() = BrickUI.color(this)

/**
 * 资源id转String。如需携带参数，可使用 Int.getString(vararg params: Any)
 */
val Int.string: String
    get() = BrickUI.string(this)

/**
 * 资源id转String，可携带参数
 */
fun Int.getString(vararg params: Any): String = BrickUI.string(this, *params)

/**
 * 资源id转dimension
 */
val Int.dimension: Int
    get() = BrickUI.dimension(this)