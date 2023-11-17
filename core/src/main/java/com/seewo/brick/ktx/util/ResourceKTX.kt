package com.seewo.brick.ktx // 包名别改

import android.animation.Animator
import android.animation.AnimatorInflater
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.AnimatorRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
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
val @receiver:DimenRes Int.drawable: Drawable?
    get() = BrickUI.drawable(this)

/**
 * 资源id转颜色类型drawable
 */
val @receiver:ColorRes Int.colorDrawable: Drawable?
    get() = runCatching { ColorDrawable(color) }.getOrNull()

/**
 * 资源id转color
 */
@get:ColorInt
val @receiver:ColorRes Int.color: Int
    get() = BrickUI.color(this)

/**
 * 资源id转String。如需携带参数，可使用 Int.getString(vararg params: Any)
 */
val @receiver:StringRes Int.string: String
    get() = BrickUI.string(this)

/**
 * 资源id转String，可携带参数
 */
fun @receiver:StringRes Int.getString(vararg params: Any): String = BrickUI.string(this, *params)

/**
 * 资源id转dimension
 */
val @receiver:DimenRes Int.dimension: Int
    get() = BrickUI.dimension(this)

val @receiver:AnimatorRes Int.animator: Animator
    get() = AnimatorInflater.loadAnimator(applicationContext, this)