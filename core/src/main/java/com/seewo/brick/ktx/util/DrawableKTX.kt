package com.seewo.brick.ktx // 包名别改

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import androidx.annotation.ColorInt
import com.seewo.brick.cache.DrawableCache
import com.seewo.brick.drawable.OvalClipDrawable
import com.seewo.brick.drawable.RectClipDrawable
import com.seewo.brick.params.CornerRadius

/**
 * 通用形状Drawable创建
 *
 * @param gradientType 渐变样式
 * @param orientation 渐变方向
 * @param gradientRadius 仅对gradientType为 GradientDrawable.RADIAL_GRADIENT 有效
 * @param gradientCenter 渐变中心点
 * @param shape 形状
 * @param corners 圆角（仅形状为 GradientDrawable.RECTANGLE 生效）
 * @param colors 颜色集 当传入多个颜色，则呈现为渐变
 * @param cacheKey 用于缓存Drawable的key，避免重复构建，调用方需要保证其唯一性
 *
 * @see GradientDrawable
 */
fun shapeDrawable(
    width: Int? = null, height: Int? = null,
    shape: Int = GradientDrawable.RECTANGLE,
    gradientType: Int = GradientDrawable.LINEAR_GRADIENT,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,
    gradientRadius: Float = 0f,
    gradientCenter: PointF? = null,
    corners: CornerRadius = CornerRadius.zero(),
    colors: IntArray = intArrayOf(),
    @ColorInt strokeColor: Int? = null,
    strokeWidth: Int? = null,
    alpha: Int? = null,
    cacheKey: String? = null,
) = cacheKey.mayGetFormCache() ?: GradientDrawable().apply {
    this.shape = shape
    this.gradientType = gradientType
    this.orientation = orientation
    this.gradientRadius = gradientRadius
    gradientCenter?.let {
        setGradientCenter(it.x, it.y)
    }
    cornerRadii = corners.toArray
    setStroke(strokeWidth ?: 0, strokeColor ?: Color.TRANSPARENT)
    if (colors.size >= 2) {
        this.colors = colors
    } else if (colors.size == 1) {
        setColor(colors.first())
    }
    if (width != null && height != null) {
        setSize(width, height)
    }
    alpha?.let { this.alpha = alpha }
}.mayPutToCache(cacheKey)

/**
 * 构建圆形/椭圆形Drawable
 *
 * @param cacheKey 用于缓存Drawable的key，避免重复构建，调用方需要保证其唯一性
 */
fun ovalDrawable(
    width: Int? = null, height: Int? = null,
    @ColorInt fillColor: Int = Color.TRANSPARENT,
    @ColorInt strokeColor: Int? = null,
    strokeWidth: Int? = null,
    alpha: Int? = null,
    cacheKey: String? = null,
) = shapeDrawable(
    width, height, GradientDrawable.OVAL,
    colors = intArrayOf(fillColor),
    strokeColor = strokeColor,
    strokeWidth = strokeWidth,
    alpha = alpha,
    cacheKey = cacheKey
)

/**
 * 构建矩形/圆角矩形Drawable
 *
 * @param cacheKey 用于缓存Drawable的key，避免重复构建，调用方需要保证其唯一性
 */
fun rectDrawable(
    width: Int? = null, height: Int? = null,
    corners: CornerRadius = CornerRadius.zero(),
    @ColorInt fillColor: Int = Color.TRANSPARENT,
    @ColorInt strokeColor: Int? = null,
    strokeWidth: Int? = null,
    alpha: Int? = null,
    cacheKey: String? = null,
) = shapeDrawable(
    width, height, GradientDrawable.RECTANGLE,
    corners = corners,
    colors = intArrayOf(fillColor),
    strokeColor = strokeColor,
    strokeWidth = strokeWidth,
    alpha = alpha,
    cacheKey = cacheKey
)

/**
 * Drawable裁剪为圆形
 */
val Drawable.clipOval: Drawable
    get() = OvalClipDrawable(this)

/**
 * Drawable裁剪为圆形
 *
 * @param cacheKey 用于缓存Drawable的key，避免重复构建，调用方需要保证其唯一性
 */
fun Drawable.clipOval(
    cacheKey: String? = null,
): Drawable = cacheKey.mayGetFormCache() ?: OvalClipDrawable(this).mayPutToCache(cacheKey)

/**
 * Drawable裁剪为圆角矩形
 *
 * @param cacheKey 用于缓存Drawable的key，避免重复构建，调用方需要保证其唯一性
 */
fun Drawable.clipRect(
    radius: Int,
    cacheKey: String? = null,
) = cacheKey.mayGetFormCache()
    ?: RectClipDrawable(this, radius.toFloat()).mayPutToCache(cacheKey)

/**
 * 构建LayerDrawable
 *
 * @param cacheKey 用于缓存Drawable的key，避免重复构建，调用方需要保证其唯一性
 *
 * 示例：
 * layerDrawable(
 *     arrayOf(
 *         rectDrawable(
 *             fillColor = R.color.primary.color,
 *             corners = CornerRadius.all(1.5.dp),
 *         )
 *     ),
 *     layerWidth = 30.dp,
 *     layerHeight = 2.dp,
 *     layerGravity = Gravity.CENTER_HORIZONTAL,
 * )
 */
fun layerDrawable(
    layers: Array<Drawable>,
    layerWidth: Int? = null,
    layerHeight: Int? = null,
    layerGravity: Int? = null,
    cacheKey: String? = null,
): LayerDrawable = cacheKey.mayGetFormCache() ?: LayerDrawable(layers).apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        layerWidth?.let { setLayerWidth(0, it) }
        layerHeight?.let { setLayerHeight(0, it) }
        layerGravity?.let { setLayerGravity(0, it) }
    }
}.mayPutToCache(cacheKey)

/**
 * 等效于selector
 *
 * @param cacheKey 用于缓存Drawable的key，避免重复构建，调用方需要保证其唯一性
 *
 * 示例：
 * stateListDrawable(mapOf(
 *     intArrayOf(android.R.attr.state_pressed) to rectDrawable(
 *         fillColor = Color.RED,
 *         corners = CornerRadius.all(8.dp)
 *     ),
 *     intArrayOf(-android.R.attr.state_pressed) to rectDrawable(
 *         fillColor = Color.GREEN,
 *         corners =CornerRadius.all(8.dp),
 *     )
 * )
 *
 * @see android.R.attr.state_checked
 * @see android.R.attr.state_selected
 * @see android.R.attr.state_pressed
 * @see android.R.attr.state_enabled 设置disabled状态请使用 -android.R.attr.state_enabled
 *
 * @param states 多个状态与Drawable的对应关系
 *
 */
fun stateListDrawable(
    states: Map<IntArray, Drawable>,
    cacheKey: String? = null,
): Drawable = cacheKey.mayGetFormCache() ?: StateListDrawable().apply {
    states.forEach { (k, v) ->
        addState(k, v)
    }
}.mayPutToCache(cacheKey)

/**
 * 等效于selector
 *
 * 示例：
 * stateListColor(mapOf(
 *     intArrayOf(-android.R.attr.state_pressed) to R.color.primary.color,
 *     intArrayOf(android.R.attr.state_pressed) to Color.RED,
 * ))
 *
 * @see android.R.attr.state_checked
 * @see android.R.attr.state_selected
 * @see android.R.attr.state_pressed
 * @see android.R.attr.state_enabled 设置disabled状态请使用 -android.R.attr.state_enabled
 *
 * @param states 多个状态与ColorInt的对应关系
 */
fun stateListColor(
    states: Map<IntArray, Int>
): ColorStateList {
    return ColorStateList(states.keys.toTypedArray(), states.values.toIntArray())
}

private inline fun <reified T: Drawable> String?.mayGetFormCache(): T? = this?.let {
    DrawableCache.get(it)
}

private inline fun <T: Drawable> T.mayPutToCache(key: String?): T = apply {
    DrawableCache.put(key ?: return@apply, this)
}
