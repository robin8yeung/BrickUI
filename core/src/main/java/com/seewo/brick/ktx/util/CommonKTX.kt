package com.seewo.brick.ktx // 包名别改

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.FloatRange
import androidx.annotation.LayoutRes
import androidx.lifecycle.AndroidViewModel
import com.seewo.brick.BrickUI


const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT

/**
 * 通过代码构造View时，可以通过init迅速传入宽高
 */
fun View.init(width: Int = WRAP_CONTENT, height: Int = WRAP_CONTENT) {
    layoutParams = ViewGroup.LayoutParams(width, height)
}

/**
 * 普通View嵌入声明
 * 包裹普通View，嵌入到BrickUI中
 *
 * 如果View已经有父布局了，不要使用本函数嵌入，否则会抛以下异常
 * java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
 */
inline fun <T: ViewGroup> T.view(
    block: (T).() -> View
): View = block().also { addView(it) }

/**
 * 获取Application的Context
 */
val applicationContext: Context
    get() = BrickUI.applicationContext

/**
 * inflate Layout资源
 */
fun Context.inflate(
    @LayoutRes resource: Int, 
    root: ViewGroup?,
    attachToRoot: Boolean = root != null
) = LayoutInflater.from(this).inflate(resource, root, attachToRoot)

/**
 * 获取Activity的ViewModel
 *
 * 注意：preview中使用这个方法会抛异常，需要用到IDE预览的，可以使用Context.activityViewModelOrNull()
 */
inline fun <reified VM: AndroidViewModel> Context.activityViewModel(): VM =
    (this as ComponentActivity).viewModels<VM>().value

/**
 * 获取Activity的ViewModel
 */
inline fun <reified VM: AndroidViewModel> Context.activityViewModelOrNull(): VM? =
    (this as? ComponentActivity)?.viewModels<VM>()?.value

/**
 * 对一个ColorInt设置不透明度
 * @param alpha 不透明度，取值0到1f
 */
fun Int.withAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float): Int = this and 0xFFFFFF or ((alpha * 255.0f).toInt() shl 24)