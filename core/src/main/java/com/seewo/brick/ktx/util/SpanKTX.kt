package com.seewo.brick.ktx // 包名别改


import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.*
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.core.text.inSpans
import com.seewo.brick.span.CenterAlignImageSpan

private const val IMAGE_SPAN_TEXT = "<img/>"
private const val SPACE_SPAN_TEXT = "<space/>"

inline fun SpannableStringBuilder.size(
    size: Float,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = size(size.toInt(), builderAction)

inline fun SpannableStringBuilder.size(
    size: Int,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(AbsoluteSizeSpan(size), builderAction)

inline fun SpannableStringBuilder.blur(
    radius: Float,
    style: BlurMaskFilter.Blur = BlurMaskFilter.Blur.NORMAL,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = maskFilter(BlurMaskFilter(radius, style), builderAction)

inline fun SpannableStringBuilder.maskFilter(
    filter: MaskFilter,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(MaskFilterSpan(filter), builderAction)

inline fun SpannableStringBuilder.fontFamily(
    family: String?,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(TypefaceSpan(family), builderAction)

inline fun SpannableStringBuilder.typeface(
    typeface: Typeface,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(TypefaceSpanCompat(typeface), builderAction)

inline fun SpannableStringBuilder.url(
    url: String,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(URLSpan(url), builderAction)

inline fun SpannableStringBuilder.alignCenter(
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = alignment(Layout.Alignment.ALIGN_CENTER, builderAction)

inline fun SpannableStringBuilder.alignOpposite(
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = alignment(Layout.Alignment.ALIGN_OPPOSITE, builderAction)

inline fun SpannableStringBuilder.alignment(
    alignment: Layout.Alignment,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(AlignmentSpan.Standard(alignment), builderAction)

inline fun SpannableStringBuilder.leadingMargin(
    first: Float,
    rest: Float = first,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = leadingMargin(first.toInt(), rest.toInt(), builderAction)

inline fun SpannableStringBuilder.leadingMargin(
    first: Int,
    rest: Int = first,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(LeadingMarginSpan.Standard(first, rest), builderAction)

inline fun SpannableStringBuilder.bullet(
    gapWidth: Float,
    @ColorInt color: Int? = null,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = bullet(gapWidth.toInt(), color, builderAction)

inline fun SpannableStringBuilder.bullet(
    gapWidth: Int = BulletSpan.STANDARD_GAP_WIDTH,
    @ColorInt color: Int? = null,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder =
    inSpans(if (color == null) BulletSpan(gapWidth) else BulletSpan(gapWidth, color), builderAction)

inline fun SpannableStringBuilder.quote(
    @ColorInt color: Int? = null,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder =
    inSpans(if (color == null) QuoteSpan() else QuoteSpan(color), builderAction)

fun SpannableStringBuilder.append(
    drawable: Drawable,
    width: Int = drawable.intrinsicWidth,
    height: Int = drawable.intrinsicHeight
): SpannableStringBuilder {
    drawable.setBounds(0, 0, width, height)
    return inSpans(CenterAlignImageSpan(drawable)) { append(IMAGE_SPAN_TEXT) }
}

fun SpannableStringBuilder.append(
    @DrawableRes resourceId: Int
): SpannableStringBuilder = inSpans(CenterAlignImageSpan(applicationContext, resourceId)) { append(IMAGE_SPAN_TEXT) }

fun SpannableStringBuilder.append(
    bitmap: Bitmap,
): SpannableStringBuilder = inSpans(CenterAlignImageSpan(applicationContext, bitmap)) { append(IMAGE_SPAN_TEXT) }

/**
 * 使用时，TextView需设置movementMethod为LinkMovementMethod.getInstance()
 */
fun SpannableStringBuilder.appendClickable(
    text: CharSequence?,
    @ColorInt color: Int? = null,
    isUnderlineText: Boolean = true,
    onClick: (View) -> Unit
): SpannableStringBuilder = inSpans(ClickableSpan(color, isUnderlineText, onClick)) { append(text) }

/**
 * 使用时，TextView需设置movementMethod为LinkMovementMethod.getInstance()
 */
fun SpannableStringBuilder.appendClickable(
    drawable: Drawable,
    width: Int = drawable.intrinsicWidth,
    height: Int = drawable.intrinsicHeight,
    onClick: (View) -> Unit
): SpannableStringBuilder = inSpans(ClickableSpan(onClick = onClick)) { append(drawable, width, height) }

/**
 * 使用时，TextView需设置movementMethod为LinkMovementMethod.getInstance()
 */
fun SpannableStringBuilder.appendClickable(
    @DrawableRes resourceId: Int,
    onClick: (View) -> Unit
): SpannableStringBuilder = inSpans(ClickableSpan(onClick = onClick)) { append(resourceId) }

/**
 * 使用时，TextView需设置movementMethod为LinkMovementMethod.getInstance()
 */
fun SpannableStringBuilder.appendClickable(
    bitmap: Bitmap,
    onClick: (View) -> Unit
): SpannableStringBuilder = inSpans(ClickableSpan(onClick = onClick)) { append(bitmap) }

fun SpannableStringBuilder.appendSpace(
    @FloatRange(from = 0.0) size: Float,
    @ColorInt color: Int = Color.TRANSPARENT
): SpannableStringBuilder = appendSpace(size.toInt(), color)

fun SpannableStringBuilder.appendSpace(
    @IntRange(from = 0) size: Int,
    @ColorInt color: Int = Color.TRANSPARENT
): SpannableStringBuilder = inSpans(SpaceSpan(size, color)) { append(SPACE_SPAN_TEXT) }

fun ClickableSpan(
    @ColorInt color: Int? = null,
    isUnderlineText: Boolean = true,
    onClick: (View) -> Unit,
): ClickableSpan = object : ClickableSpan() {
    override fun onClick(widget: View) = onClick(widget)

    override fun updateDrawState(ds: TextPaint) {
        ds.color = color ?: ds.linkColor
        ds.isUnderlineText = isUnderlineText
    }
}

class SpaceSpan constructor(private val width: Int, color: Int = Color.TRANSPARENT) : ReplacementSpan() {
    private val paint = Paint().apply {
        this.color = color
        style = Paint.Style.FILL
    }

    override fun getSize(
        paint: Paint, text: CharSequence?,
        @IntRange(from = 0) start: Int,
        @IntRange(from = 0) end: Int,
        fm: Paint.FontMetricsInt?
    ): Int = width

    override fun draw(
        canvas: Canvas, text: CharSequence?,
        @IntRange(from = 0) start: Int,
        @IntRange(from = 0) end: Int,
        x: Float, top: Int, y: Int, bottom: Int, paint: Paint
    ) = canvas.drawRect(x, top.toFloat(), x + width, bottom.toFloat(), this.paint)
}

class TypefaceSpanCompat(private val newType: Typeface) : TypefaceSpan(null) {
    override fun updateDrawState(ds: TextPaint) {
        ds.applyTypeFace(newType)
    }

    override fun updateMeasureState(paint: TextPaint) {
        paint.applyTypeFace(newType)
    }

    private fun TextPaint.applyTypeFace(tf: Typeface) {
        val oldStyle: Int
        val old = typeface
        oldStyle = old?.style ?: 0
        val fake = oldStyle and tf.style.inv()
        if (fake and Typeface.BOLD != 0) {
            isFakeBoldText = true
        }
        if (fake and Typeface.ITALIC != 0) {
            textSkewX = -0.25f
        }
        typeface = tf
    }
}