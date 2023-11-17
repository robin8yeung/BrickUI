package com.seewo.brick.ktx // 包名别改

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.MovementMethod
import android.util.TypedValue
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import com.seewo.brick.JavaDrawableUtils
import com.seewo.brick.init.applyMargin
import com.seewo.brick.init.setup
import com.seewo.brick.params.CompoundDrawables
import com.seewo.brick.params.EdgeInsets

/**
 * 分割线，默认为水平分割线
 */
fun Context.divider(
    width: Int = MATCH_PARENT,
    height: Int = 0.5.dp,
    @StyleRes style: Int = 0,

    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: OnClickListener? = null,
) = View(this, null, 0, style).apply {
    setup(
        width, height, id, tag, foreground, background, padding, visibility, isSelected,
        onClick = onClick
    )
}.applyMargin(margin)

/**
 * 分割线，默认为水平分割线
 */
fun ViewGroup.divider(
    width: Int = MATCH_PARENT,
    height: Int = 0.5.dp,
    @StyleRes style: Int = 0,

    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    onClick: OnClickListener? = null,
) = context.divider(
    width, height, style, id, tag, foreground, background,
    margin, padding, visibility, isSelected, onClick
).also { addView(it) }.applyMargin(margin)

/**
 * 构造ImageView
 */
fun Context.imageView(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    fitsSystemWindows: Boolean? = null,

    scaleType: ImageView.ScaleType? = null,
    drawable: Drawable? = null,
    onClick: OnClickListener? = null,
) = ImageView(this, null, 0, style).apply {
    setup(
        width,
        height,
        id,
        tag,
        foreground,
        background,
        padding,
        visibility,
        isSelected,
        isEnabled,
        onClick,
        fitsSystemWindows = fitsSystemWindows
    )
    padding?.let { setPadding(it.start, it.top, it.end, it.bottom) }
    scaleType?.let { setScaleType(it) }
    drawable?.let { setImageDrawable(it) }
}.applyMargin(margin)

/**
 * 构造ImageView
 */
fun ViewGroup.imageView(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    fitsSystemWindows: Boolean? = null,

    scaleType: ImageView.ScaleType? = null,
    drawable: Drawable? = null,
    onClick: OnClickListener? = null,
) = context.imageView(
    width,
    height,
    style,
    id,
    tag,
    foreground,
    background,
    margin,
    padding,
    visibility,
    isSelected,
    isEnabled,
    fitsSystemWindows,
    scaleType,
    drawable,
    onClick
).also { addView(it) }.applyMargin(margin)

/**
 * 构造TextView
 */
fun Context.textView(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    fitsSystemWindows: Boolean? = null,

    textStyle: Int? = null,
    textAlignment: Int? = null,
    textSize: Int? = null,
    drawablePadding: Int? = null,
    gravity: Int? = null,
    maxLines: Int? = null,
    ellipsize: TextUtils.TruncateAt? = null,
    @ColorInt textColor: Int? = null,
    textColorList: ColorStateList? = null,
    text: CharSequence? = null,
    compoundDrawables: CompoundDrawables? = null,
    movementMethod: MovementMethod? = null,
    highLightColor: Int? = null,
    onClick: OnClickListener? = null,
) = TextView(this, null, 0, style).apply {
    setup(
        width,
        height,
        id,
        tag,
        foreground,
        background,
        padding,
        visibility,
        isSelected,
        isEnabled,
        onClick,
        fitsSystemWindows
    )
    includeFontPadding = false
    textSize?.let { setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat()) }
    textAlignment?.let { this.textAlignment = textAlignment }
    drawablePadding?.let { compoundDrawablePadding = it }
    textStyle?.let { setTypeface(null, it) }
    gravity?.let { setGravity(it) }
    maxLines?.let { this.maxLines = it }
    ellipsize?.let { this.ellipsize = it }
    text?.let { this.text = it }
    movementMethod?.let {
        this.movementMethod = it
    }
    highLightColor?.let { this.highlightColor = it }
    textColor?.let { setTextColor(it) }
    textColorList?.let { setTextColor(it) }
    compoundDrawables?.let {
        setCompoundDrawablesWithIntrinsicBounds(it.start, it.top, it.end, it.bottom)
    }

}.applyMargin(margin)

/**
 * 构造TextView
 */
fun ViewGroup.textView(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    fitsSystemWindows: Boolean? = null,

    textStyle: Int? = null,
    textAlignment: Int? = null,
    textSize: Int? = null,
    drawablePadding: Int? = null,
    gravity: Int? = null,
    maxLines: Int? = null,
    ellipsize: TextUtils.TruncateAt? = null,
    @ColorInt textColor: Int? = null,
    textColorList: ColorStateList? = null,
    text: CharSequence? = null,
    compoundDrawables: CompoundDrawables? = null,
    movementMethod: MovementMethod? = null,
    highLightColor: Int? = null,
    onClick: OnClickListener? = null,
) = context.textView(
    width,
    height,
    style,
    id,
    tag,
    foreground,
    background,
    margin,
    padding,
    visibility,
    isSelected,
    isEnabled,
    fitsSystemWindows,
    textStyle,
    textAlignment,
    textSize,
    drawablePadding,
    gravity,
    maxLines,
    ellipsize,
    textColor,
    textColorList,
    text,
    compoundDrawables,
    movementMethod,
    highLightColor,
    onClick
).also { addView(it) }.applyMargin(margin)

/**
 * 构造EditText
 */
fun Context.edittext(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    fitsSystemWindows: Boolean? = null,

    textStyle: Int? = null,
    textAlignment: Int? = null,
    textSize: Int? = null,
    gravity: Int? = null,
    maxLines: Int? = null,
    maxLength: Int? = null,
    inputFilters: Array<InputFilter>? = null,

    hint: String? = null,
    @ColorInt hintTextColor: Int? = null,
    imeOptions: Int? = null,
    inputType: Int? = null,
    // Android Q以下不支持，如果要兼容，请通过Style传入
    cursorDrawable: Drawable? = null,
    textColor: Int? = null,
    textColorList: ColorStateList? = null,
    text: CharSequence? = null,
    onEditorAction: TextView.OnEditorActionListener? = null,
    beforeTextChanged: ((text: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null,
    onTextChanged: ((text: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null,
    afterTextChanged: ((text: Editable?) -> Unit)? = null,
) = EditText(this, null, 0, style).apply {
    this.isEnabled = true
    this.isFocusable = true
    this.isFocusableInTouchMode = true
    setup(
        width,
        height,
        id,
        tag,
        foreground,
        background,
        padding,
        visibility,
        isSelected,
        isEnabled,
        fitsSystemWindows = fitsSystemWindows
    )

    textSize?.let { setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat()) }
    textAlignment?.let { this.textAlignment = textAlignment }

    hintTextColor?.let { setHintTextColor(it) }
    hint?.let { this.hint = it }

    textStyle?.let { setTypeface(null, it) }
    gravity?.let { setGravity(it) }
    maxLines?.let {
        this.maxLines = it
        if (it == 1) setSingleLine()
    }
    val finalInputFilters = (inputFilters?.toMutableList() ?: mutableListOf())
    if (maxLength != null) {
        finalInputFilters.add(InputFilter.LengthFilter(maxLength))
    }
    if (finalInputFilters.isNotEmpty()) {
        filters = finalInputFilters.toTypedArray()
    }

    imeOptions?.let { this.imeOptions = it }
    onEditorAction?.let { setOnEditorActionListener(it) }
    inputType?.let { this.inputType = it }
    cursorDrawable?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.textCursorDrawable = it
        }
    }

    text?.let {
        setText(it)
        setSelection(getText().length)
    }
    textColor?.let { setTextColor(it) }
    textColorList?.let { setTextColor(it) }

    if (beforeTextChanged != null || onTextChanged != null || afterTextChanged != null) {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                beforeTextChanged?.invoke(s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged?.invoke(s, start, before, count)
            }

            override fun afterTextChanged(s: Editable?) {
                afterTextChanged?.invoke(s)
            }
        })
    }
}.applyMargin(margin)

/**
 * 构造EditText
 */
fun ViewGroup.edittext(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    fitsSystemWindows: Boolean? = null,

    textStyle: Int? = null,
    textAlignment: Int? = null,
    textSize: Int? = null,
    gravity: Int? = null,
    maxLines: Int? = null,
    maxLength: Int? = null,
    inputFilters: Array<InputFilter>? = null,

    hint: String? = null,
    @ColorInt hintTextColor: Int? = null,
    imeOptions: Int? = null,
    inputType: Int? = null,
    // Android Q以下不支持，如果要兼容，请通过Style传入
    cursorDrawable: Drawable? = null,
    textColor: Int? = null,
    textColorList: ColorStateList? = null,
    text: CharSequence? = null,
    onEditorAction: TextView.OnEditorActionListener? = null,
    beforeTextChanged: ((text: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null,
    onTextChanged: ((text: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null,
    afterTextChanged: ((text: Editable?) -> Unit)? = null,
) = context.edittext(
    width,
    height,
    style,
    id,
    tag,
    foreground,
    background,
    margin,
    padding,
    visibility,
    isSelected,
    isEnabled,
    fitsSystemWindows,
    textStyle,
    textAlignment,
    textSize,
    gravity,
    maxLines,
    maxLength,
    inputFilters,
    hint,
    hintTextColor,
    imeOptions,
    inputType,
    cursorDrawable,
    textColor,
    textColorList,
    text,
    onEditorAction,
    beforeTextChanged,
    onTextChanged,
    afterTextChanged
).also { addView(it) }.applyMargin(margin)

/**
 * Checkbox控件
 */
fun Context.checkbox(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    isChecked: Boolean = false,
    fitsSystemWindows: Boolean? = null,

    textStyle: Int? = null,
    textAlignment: Int? = null,
    textSize: Int? = null,
    drawablePadding: Int? = null,
    gravity: Int? = null,
    maxLines: Int? = null,
    ellipsize: TextUtils.TruncateAt? = null,
    @ColorInt textColor: Int? = null,
    text: CharSequence? = null,
    compoundDrawables: CompoundDrawables? = null,
    onCheckedChange: CompoundButton.OnCheckedChangeListener? = null
) = CheckBox(this, null, android.R.attr.checkboxStyle, style).apply {
    setup(
        width,
        height,
        id,
        tag,
        foreground,
        background,
        padding,
        visibility,
        isSelected,
        isEnabled,
        fitsSystemWindows = fitsSystemWindows
    )
    includeFontPadding = false
    textSize?.let { setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat()) }
    textAlignment?.let { this.textAlignment = textAlignment }
    drawablePadding?.let { compoundDrawablePadding = it }
    textStyle?.let { setTypeface(null, it) }
    gravity?.let { setGravity(it) }
    maxLines?.let { this.maxLines = it }
    ellipsize?.let { this.ellipsize = it }
    text?.let { this.text = it }
    textColor?.let { setTextColor(it) }
    compoundDrawables?.let {
        setCompoundDrawablesWithIntrinsicBounds(it.start, it.top, it.end, it.bottom)
    }
    this.isChecked = isChecked

    onCheckedChange?.let { setOnCheckedChangeListener(it) }
}.applyMargin(margin)

/**
 * Checkbox控件
 */
fun ViewGroup.checkbox(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    isChecked: Boolean = false,
    fitsSystemWindows: Boolean? = null,

    textStyle: Int? = null,
    textAlignment: Int? = null,
    textSize: Int? = null,
    drawablePadding: Int? = null,
    gravity: Int? = null,
    maxLines: Int? = null,
    ellipsize: TextUtils.TruncateAt? = null,
    @ColorInt textColor: Int? = null,
    text: CharSequence? = null,
    compoundDrawables: CompoundDrawables? = null,
    onCheckedChange: CompoundButton.OnCheckedChangeListener? = null
) = context.checkbox(
    width,
    height,
    style,
    id,
    tag,
    foreground,
    background,
    margin,
    padding,
    visibility,
    isSelected,
    isEnabled,
    isChecked,
    fitsSystemWindows,
    textStyle,
    textAlignment,
    textSize,
    drawablePadding,
    gravity,
    maxLines,
    ellipsize,
    textColor,
    text,
    compoundDrawables,
    onCheckedChange
).also { addView(it) }.applyMargin(margin)

/**
 * Switch控件
 */
fun Context.switch(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isEnabled: Boolean? = null,
    fitsSystemWindows: Boolean? = null,

    isChecked: Boolean = false,
    onCheckedChange: CompoundButton.OnCheckedChangeListener? = null
) = Switch(this, null, android.R.attr.switchStyle, style).apply {
    setup(
        width,
        height,
        id,
        tag,
        isEnabled = isEnabled,
        padding = padding,
        visibility = visibility,
        fitsSystemWindows = fitsSystemWindows
    )
    this.isChecked = isChecked
    onCheckedChange?.let { setOnCheckedChangeListener(it) }
}.applyMargin(margin)

/**
 * Switch控件
 */
@SuppressLint("UseSwitchCompatOrMaterialCode")
fun ViewGroup.switch(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isEnabled: Boolean? = null,
    fitsSystemWindows: Boolean? = null,

    isChecked: Boolean = false,
    onCheckedChange: CompoundButton.OnCheckedChangeListener? = null
) = context.switch(
    width,
    height,
    style,
    id,
    tag,
    margin,
    padding,
    visibility,
    isEnabled,
    fitsSystemWindows,
    isChecked,
    onCheckedChange,
).also { addView(it) }.applyMargin(margin)

const val PROGRESS_TYPE_SCALE = 0
const val PROGRESS_TYPE_CLIP = 1

/**
 * 横向进度条
 *
 * @param progress 进度值，默认取值0~100
 * @param max 进度条最大值，默认100
 *
 *
 *
 * 【注意】 以下属性需要minSdk至少为23，再低版本Android无法兼容.
 * 如需兼容，则请用xml来定义相关Drawable，并通过style传入
 *
 * @param progressType 进度条样式，支持缩放和裁剪两种
 * @param progressDrawable 进度条前景
 * @param progressBackground 进度条背景
 *
 * @see PROGRESS_TYPE_SCALE 缩放样式
 * @see PROGRESS_TYPE_CLIP 裁剪样式
 */
fun Context.horizontalProgressBar(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    fitsSystemWindows: Boolean? = null,

    progress: Int = 0,
    progressType: Int = PROGRESS_TYPE_SCALE,
    max: Int? = null,
    progressDrawable: Drawable? = null,
    progressBackground: Drawable? = null,
) = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal, style).apply {
    setup(
        width,
        height,
        id,
        tag,
        foreground,
        background,
        padding,
        visibility,
        isSelected,
        isEnabled,
        fitsSystemWindows = fitsSystemWindows
    )
    this.progress = progress
    max?.let { this.max = it }
    progressDrawable?.let {
        this.progressDrawable = LayerDrawable(
            arrayOf(
                progressBackground ?: rectDrawable(
                    fillColor = Color.TRANSPARENT
                ),
                JavaDrawableUtils.getDrawableWrapper(it, progressType),
            )
        ).apply {
            setId(0, android.R.id.background)
            setId(1, android.R.id.progress)
        }
    }
}.applyMargin(margin)

/**
 * 横向进度条
 *
 * @param progress 进度值，默认取值0~100
 * @param max 进度条最大值，默认100
 *
 *
 *
 * 【注意】 以下属性需要minSdk至少为23，再低版本Android无法兼容.
 * 如需兼容，则请用xml来定义相关Drawable，并通过style传入
 *
 * @param progressType 进度条样式，支持缩放和裁剪两种
 * @param progressDrawable 进度条前景
 * @param progressBackground 进度条背景
 *
 * @see PROGRESS_TYPE_SCALE 缩放样式
 * @see PROGRESS_TYPE_CLIP 裁剪样式
 */
fun ViewGroup.horizontalProgressBar(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    fitsSystemWindows: Boolean? = null,

    progress: Int = 0,
    progressType: Int = PROGRESS_TYPE_SCALE,
    max: Int? = null,
    progressDrawable: Drawable? = null,
    progressBackground: Drawable? = null,
) = context.horizontalProgressBar(
    width, height, style, id, tag, foreground, background, margin, padding, visibility, isSelected, isEnabled, fitsSystemWindows,
    progress, progressType, max, progressDrawable, progressBackground
).also { addView(it) }.applyMargin(margin)

/**
 * @param minHeight 进度条的最小高度，系统版本29以上才支持，低版本通过style传入
 * @param maxHeight 进度条的最大高度，系统版本29以上才支持，低版本通过style传入
 * @param thumb 滑块图标
 * @param thumbOffset 滑块可以超出进度条的偏移量
 * @param progressDrawable 进度条高亮部分图标
 * @param progressBackground 进度条背景部分图标
 * @param padding 不设置minHeight和maxHeight时，上下padding会影响track的高度，左右padding可以避免thumb滑到尽头时被裁剪
 */
fun Context.seekBar(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    fitsSystemWindows: Boolean? = null,

    minHeight: Int? = null,
    maxHeight: Int? = null,
    thumb: Drawable? = null,
    thumbOffset: Int = 0,
    progressDrawable: Drawable? = null,
    progressBackground: Drawable? = null,
    progress: Int = 0,
    max: Int? = null,
    onStartTrackingTouch: ((SeekBar) -> Unit)? = null,
    onStopTrackingTouch: ((SeekBar) -> Unit)? = null,
    onProgressChanged: ((seekBar: SeekBar, progress: Int, fromUser: Boolean) -> Unit)? = null,
) = SeekBar(this, null, 0, style).apply {
    setup(
        width,
        height,
        id,
        tag,
        foreground,
        background,
        padding,
        visibility,
        isSelected,
        isEnabled,
        fitsSystemWindows = fitsSystemWindows
    )
    max?.let { this.max = it }
    progressDrawable?.let {
        this.progressDrawable = LayerDrawable(
            arrayOf(
                progressBackground ?: rectDrawable(
                    fillColor = Color.TRANSPARENT
                ),
                JavaDrawableUtils.getClipDrawable(it),
            )
        ).apply {
            setId(0, android.R.id.background)
            setId(1, android.R.id.progress)
        }
    }
    thumb?.let {
        this.thumb = it
        this.thumbOffset = thumbOffset
    }
    splitTrack = false
    this.progress = progress
    if (Build.VERSION.SDK_INT >= 29) {
        maxHeight?.let { this.maxHeight = it }
        minHeight?.let { this.minHeight = it }
    }
    setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            onProgressChanged?.invoke(seekBar, progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            onStartTrackingTouch?.invoke(seekBar)
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            onStopTrackingTouch?.invoke(seekBar)
        }
    })
}.applyMargin(margin)

/**
 * @param minHeight 进度条的最小高度，系统版本29以上才支持，低版本通过style传入
 * @param maxHeight 进度条的最大高度，系统版本29以上才支持，低版本通过style传入
 * @param thumb 滑块图标
 * @param thumbOffset 滑块可以超出进度条的偏移量
 * @param progressDrawable 进度条高亮部分图标
 * @param progressBackground 进度条背景部分图标
 * @param padding 不设置minHeight和maxHeight时，上下padding会影响track的高度，左右padding可以避免thumb滑到尽头时被裁剪
 */
fun ViewGroup.seekBar(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    fitsSystemWindows: Boolean? = null,

    minHeight: Int? = null,
    maxHeight: Int? = null,
    thumb: Drawable? = null,
    thumbOffset: Int = 0,
    progressDrawable: Drawable? = null,
    progressBackground: Drawable? = null,
    progress: Int = 0,
    max: Int? = null,
    onStartTrackingTouch: ((SeekBar) -> Unit)? = null,
    onStopTrackingTouch: ((SeekBar) -> Unit)? = null,
    onProgressChanged: ((seekBar: SeekBar, progress: Int, fromUser: Boolean) -> Unit)? = null,
) = context.seekBar(
    width, height, style, id, tag, foreground, background, margin, padding, visibility, isSelected, isEnabled, fitsSystemWindows,
    minHeight, maxHeight, thumb, thumbOffset, progressDrawable, progressBackground, progress, max, onStartTrackingTouch, onStopTrackingTouch, onProgressChanged
).also { addView(it) }.applyMargin(margin)

/**
 * 构造Expend，用于在线性布局中作为PlaceHolder占满空间
 */
fun LinearLayout.expand() = expand {
    placeholder(0, 0)
}

/**
 * 构造透明占位符，也可以设置background
 */
fun Context.placeholder(
    width: Int = 0,
    height: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean? = null,
    onClick: OnClickListener? = null,
) = View(this).apply {
    setup(
        width,
        height,
        id,
        tag,
        foreground,
        background,
        padding,
        visibility,
        onClick = onClick,
        fitsSystemWindows = fitsSystemWindows
    )
}.applyMargin(margin)

/**
 * 构造透明占位符，也可以设置background
 */
fun ViewGroup.placeholder(
    width: Int = 0,
    height: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    margin: EdgeInsets? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    fitsSystemWindows: Boolean? = null,
    onClick: OnClickListener? = null,
) = context.placeholder(
    width, height, id, tag, foreground, background, margin, padding, visibility, fitsSystemWindows, onClick
).also { addView(it) }.applyMargin(margin)
