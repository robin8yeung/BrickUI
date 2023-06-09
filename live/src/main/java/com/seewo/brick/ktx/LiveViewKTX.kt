package com.seewo.brick.ktx

import android.animation.Animator
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.MovementMethod
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seewo.brick.params.CompoundDrawables
import com.seewo.brick.params.EdgeInsets


/**
 * 构造ImageView
 */
fun ViewGroup.liveImage(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    fitsSystemWindows: Boolean? = null,

    scaleType: ImageView.ScaleType? = null,
    drawable: LiveData<Drawable>? = null,
    onClick: View.OnClickListener? = null,
) = imageView(
    width, height, style, id, tag, background, padding,
    onClick = onClick, scaleType = scaleType, fitsSystemWindows = fitsSystemWindows,
).apply {
    context.inMyLifecycle {
        drawable?.bind(this) {
            setImageDrawable(it)
        }
        visibility?.bindNotNull(this) {
            this@apply.visibility = it
        }
        isSelected?.bindNotNull(this) {
            this@apply.isSelected = it
        }
    }
    if (isInEditMode) {
        visibility?.value?.let { this.visibility = it }
        isSelected?.value?.let { this.isSelected = it }
        drawable?.value?.let { setImageDrawable(it) }
    }
}

/**
 * 构造TextView
 */
fun ViewGroup.liveText(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    isEnabled: LiveData<Boolean>? = null,
    fitsSystemWindows: Boolean? = null,

    textStyle: Int? = null,
    textAlignment: Int? = null,
    textSize: Int? = null,
    drawablePadding: Int? = null,
    gravity: Int? = null,
    maxLines: Int? = null,
    ellipsize: TextUtils.TruncateAt? = null,
    textColor: LiveData<Int>? = null,
    textColorList: ColorStateList? = null,
    text: LiveData<CharSequence>? = null,
    compoundDrawables: LiveData<CompoundDrawables>? = null,
    movementMethod: MovementMethod? = null,
    highLightColor: Int? = null,
    onClick: View.OnClickListener? = null,
) = textView(
    width,
    height,
    style,
    id,
    tag,
    background,
    padding,
    onClick = onClick,
    textStyle = textStyle,
    textAlignment = textAlignment,
    textSize = textSize,
    drawablePadding = drawablePadding,
    gravity = gravity,
    maxLines = maxLines,
    ellipsize = ellipsize,
    textColorList = textColorList,
    movementMethod = movementMethod,
    highLightColor = highLightColor,
    fitsSystemWindows = fitsSystemWindows
).apply {
    context.inMyLifecycle {
        visibility?.bindNotNull(this) {
            this@apply.visibility = it
        }
        isSelected?.bindNotNull(this) {
            this@apply.isSelected = it
        }
        isEnabled?.bindNotNull(this) {
            this@apply.isEnabled = it
        }
        text?.bind(this) {
            this@apply.text = it
        }
        textColor?.bindNotNull(this) {
            setTextColor(it)
        }
        compoundDrawables?.bindNotNull(this) {
            setCompoundDrawablesWithIntrinsicBounds(it.start, it.top, it.end, it.bottom)
        }
    }
    if (isInEditMode) {
        visibility?.value?.let { this.visibility = it }
        isSelected?.value?.let { this.isSelected = it }
        isEnabled?.value?.let { this.isEnabled = it }
        text?.value?.let { this.text = it }
        textColor?.value?.let { setTextColor(it) }
        compoundDrawables?.value?.let {
            setCompoundDrawablesWithIntrinsicBounds(
                it.start,
                it.top,
                it.end,
                it.bottom
            )
        }
    }
}

/**
 * 构造EditText
 *
 * @param text 文本框的内容，既是输入，也是输出。双向绑定。
 * @param afterTextChanged 仅用于作为事件回调，不必作为文本框内容输出。
 */
fun ViewGroup.liveEdit(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
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
    textColor: LiveData<Int>? = null,
    textColorList: ColorStateList? = null,
    text: LiveData<CharSequence>? = null,
    onEditorAction: TextView.OnEditorActionListener? = null,
    beforeTextChanged: ((text: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null,
    onTextChanged: ((text: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null,
    afterTextChanged: ((text: Editable?) -> Unit)? = null,
) = edittext(
    width,
    height,
    style, id, tag, background, padding,
    textStyle = textStyle, textAlignment = textAlignment, textSize = textSize,
    gravity = gravity, maxLines = maxLines, hint = hint, hintTextColor = hintTextColor,
    imeOptions = imeOptions, inputType = inputType, cursorDrawable = cursorDrawable,
    onEditorAction = onEditorAction, textColorList = textColorList, maxLength = maxLength,
    inputFilters = inputFilters, fitsSystemWindows = fitsSystemWindows,
).apply {
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged?.invoke(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged?.invoke(s, start, before, count)
        }

        override fun afterTextChanged(s: Editable) {
            if (text is MutableLiveData<CharSequence>) {
                text.value = s.toString()
            }
            afterTextChanged?.invoke(s)
        }
    }
    addTextChangedListener(textWatcher)
    context.inMyLifecycle {
        visibility?.bindNotNull(this) {
            this@apply.visibility = it
        }
        isSelected?.bindNotNull(this) {
            this@apply.isSelected = it
        }
        text?.bind(this) {
            val newText = it ?: ""
            if (newText.toString() == this@apply.text.toString()) return@bind
            removeTextChangedListener(textWatcher)
            this@apply.setText(it)
            setSelection(getText().length)
            addTextChangedListener(textWatcher)
        }
        textColor?.bindNotNull(this) {
            setTextColor(it)
        }
    }
    if (isInEditMode) {
        visibility?.value?.let { this.visibility = it }
        isSelected?.value?.let { this.isSelected = it }
        text?.value?.let { setText(it) }
        textColor?.value?.let { setTextColor(it) }
    }
}

/**
 * Checkbox控件
 *
 * @param isChecked Checkbox是否选中，既作为输入，也作为输出。双向绑定。
 * @param onCheckedChange 仅用于回调事件，不必作为选中状态输出
 */
fun ViewGroup.liveCheckbox(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    isChecked: LiveData<Boolean>? = null,
    fitsSystemWindows: Boolean? = null,

    textStyle: Int? = null,
    textAlignment: Int? = null,
    textSize: Int? = null,
    drawablePadding: Int? = null,
    gravity: Int? = null,
    maxLines: Int? = null,
    ellipsize: TextUtils.TruncateAt? = null,
    textColor: LiveData<Int>? = null,
    text: LiveData<CharSequence>? = null,
    compoundDrawables: LiveData<CompoundDrawables>? = null,
    onCheckedChange: CompoundButton.OnCheckedChangeListener? = null
) = checkbox(
    width,
    height,
    style, id, tag, background, padding,
    textStyle = textStyle, textAlignment = textAlignment,
    textSize = textSize, drawablePadding = drawablePadding, gravity = gravity,
    maxLines = maxLines, ellipsize = ellipsize, isChecked = isChecked?.value == true,
    fitsSystemWindows = fitsSystemWindows,
).apply {
    val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener { v, checked ->
        if (isChecked != null && isChecked.data != checked) {
            isChecked.data = checked
        }
        onCheckedChange?.onCheckedChanged(v, checked)
    }
    setOnCheckedChangeListener(onCheckedChangeListener)
    context.inMyLifecycle {
        isChecked?.bindNotNull(this) {
            setOnCheckedChangeListener(null)
            this@apply.isChecked = it
            setOnCheckedChangeListener(onCheckedChangeListener)
        }
        visibility?.bindNotNull(this) {
            this@apply.visibility = it
        }
        isSelected?.bindNotNull(this) {
            this@apply.isSelected = it
        }
        text?.bind(this) {
            this@apply.text = it
        }
        textColor?.bindNotNull(this) {
            setTextColor(it)
        }
        compoundDrawables?.bindNotNull(this) {
            setCompoundDrawablesWithIntrinsicBounds(it.start, it.top, it.end, it.bottom)
        }
    }
    if (isInEditMode) {
        isChecked?.value?.let { this.isChecked = it }
        visibility?.value?.let { this.visibility = it }
        isSelected?.value?.let { this.isSelected = it }
        text?.value?.let { this.text = it }
        textColor?.value?.let { setTextColor(it) }
        compoundDrawables?.value?.let {
            setCompoundDrawablesWithIntrinsicBounds(
                it.start,
                it.top,
                it.end,
                it.bottom
            )
        }
    }
}

/**
 * Switch控件
 *
 * @param isChecked Switch是否选中，既作为输入，也作为输出。双向绑定。
 * @param onCheckedChange 仅用于回调事件，不必作为选中状态输出
 */
fun ViewGroup.liveSwitch(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isChecked: LiveData<Boolean>? = null,
    fitsSystemWindows: Boolean? = null,
    onCheckedChange: CompoundButton.OnCheckedChangeListener? = null
) = switch(width, height, style, id, tag, padding, fitsSystemWindows = fitsSystemWindows).apply {
    val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener { v, checked ->
        if (isChecked is MutableLiveData<Boolean>) {
            isChecked.value = checked
        }
        onCheckedChange?.onCheckedChanged(v, checked)
    }
    setOnCheckedChangeListener(onCheckedChange)
    context.inMyLifecycle {
        visibility?.bindNotNull(this) {
            this@apply.visibility = it
        }
        isChecked?.bindNotNull(this) {
            setOnCheckedChangeListener(null)
            this@apply.isChecked = it
            setOnCheckedChangeListener(onCheckedChangeListener)
        }
    }
    if (isInEditMode) {
        isChecked?.value?.let { this.isChecked = it }
        visibility?.value?.let { this.visibility = it }
    }
}

/**
 * 横向进度条
 *
 * @param progress 进度值，默认取值0~100
 * @param progressType 进度条绘制方式，可选择缩放或裁剪
 * @param max 进度条最大值，默认100
 * @param progressDrawable 进度条前景
 * @param progressBackground 进度条背景
 * @param onProgressChanged ui上实际的进度回调，由于可能存在动画，所以这里回调的progress和传入的progress可能不相等
 *
 * @see PROGRESS_TYPE_CLIP 裁剪方式
 * @see PROGRESS_TYPE_SCALE 缩放方式
 */
fun ViewGroup.liveHorizontalProgressBar(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    isEnabled: LiveData<Boolean>? = null,
    fitsSystemWindows: Boolean? = null,

    progress: LiveData<Int>? = null,
    progressType: Int = PROGRESS_TYPE_SCALE,
    max: Int? = null,
    progressDrawable: Drawable? = null,
    progressBackground: Drawable? = null,
    animateDuration: Long? = 0L,
    onProgressChanged: ((progress: Int) -> Unit)? = null,
) = horizontalProgressBar(
    width,
    height,
    style,
    id,
    tag,
    background,
    padding,
    max = max,
    progressType = progressType,
    progressDrawable = progressDrawable,
    progressBackground = progressBackground,
    fitsSystemWindows = fitsSystemWindows,
).apply {
    var animator: Animator? = null
    context.inMyLifecycle {
        visibility?.bindNotNull(this) {
            this@apply.visibility = it
        }
        if (animateDuration != null) {
            // 避免max只有100时，离散不够密集导致动画卡顿
            this@apply.max = this@apply.max * 1000
            progress?.bindNotNull(this) {
                if (this@apply.progress != it * 1000) {
                    animator?.cancel()
                    var currentUiProgress = this@apply.progress / 1000
                    animator = runAnimator(
                        values = intArrayOf(this@apply.progress, it * 1000),
                        duration = animateDuration
                    ) { value ->
                        val intValue = value / 1000
                        if (currentUiProgress != intValue) {
                            onProgressChanged?.invoke(intValue)
                        }
                        this@apply.progress = value
                        currentUiProgress = intValue
                    }
                }
            }
        } else {
            progress?.bindNotNull(this) {
                this@apply.progress = it
            }
        }


        isSelected?.bindNotNull(this) {
            this@apply.isSelected = it
        }
        isEnabled?.bindNotNull(this) {
            this@apply.isEnabled = it
        }
    }
    if (isInEditMode) {
        progress?.value?.let { this.progress = it }
        visibility?.value?.let { this.visibility = it }
        isSelected?.value?.let { this.isSelected = it }
        isEnabled?.value?.let { this.isEnabled = it }
    }
}

/**
 * @param progress seekBar进度。既作为输入，也作为输出。双向绑定。
 * @param minHeight 进度条的最小高度，系统版本29以上才支持，低版本通过style传入
 * @param maxHeight 进度条的最大高度，系统版本29以上才支持，低版本通过style传入
 * @param thumb 滑块图标
 * @param thumbOffset 滑块可以超出进度条的偏移量
 * @param progressDrawable 进度条高亮部分图标
 * @param progressBackground 进度条背景部分图标
 * @param padding 不设置minHeight和maxHeight时，height和上下padding相减得到track的高度，左右padding可以避免thumb滑到尽头时被裁剪
 * @param onProgressChanged 仅用于回调事件，不必作为seekBar进度输出
 */
fun ViewGroup.liveSeekBar(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @StyleRes style: Int = 0,
    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    isEnabled: LiveData<Boolean>? = null,
    fitsSystemWindows: Boolean? = null,

    minHeight: Int? = null,
    maxHeight: Int? = null,
    thumb: Drawable? = null,
    thumbOffset: Int = 0,
    progressDrawable: Drawable? = null,
    progressBackground: Drawable? = null,
    progress: LiveData<Int>? = null,
    max: Int? = null,
    onStartTrackingTouch: ((SeekBar) -> Unit)? = null,
    onStopTrackingTouch: ((SeekBar) -> Unit)? = null,
    onProgressChanged: ((seekBar: SeekBar, progress: Int, fromUser: Boolean) -> Unit)? = null,
) = seekBar(
    width,
    height,
    style,
    id,
    tag,
    background,
    padding,
    max = max,
    minHeight = minHeight,
    maxHeight = maxHeight,
    thumb = thumb,
    thumbOffset = thumbOffset,
    progressDrawable = progressDrawable,
    progressBackground = progressBackground,
    onStartTrackingTouch = onStartTrackingTouch,
    onStopTrackingTouch = onStopTrackingTouch,
    onProgressChanged = { seekBar, currentProgress, fromUser ->
        progress?.let {
            if (fromUser && it.value != currentProgress) {
                it.data = currentProgress
            }
        }
        onProgressChanged?.invoke(seekBar, currentProgress, fromUser)
    },
    fitsSystemWindows = fitsSystemWindows,
).apply {
    context.inMyLifecycle {
        visibility?.bindNotNull(this) {
            this@apply.visibility = it
        }
        progress?.bindNotNull(this) {
            this@apply.progress = it
        }
        isSelected?.bindNotNull(this) {
            this@apply.isSelected = it
        }
        isEnabled?.bindNotNull(this) {
            this@apply.isEnabled = it
        }
    }
    if (isInEditMode) {
        progress?.value?.let { this.progress = it }
        visibility?.value?.let { this.visibility = it }
        isSelected?.value?.let { this.isSelected = it }
        isEnabled?.value?.let { this.isEnabled = it }
    }
}

/**
 * 构造ImageView
 */
fun ViewGroup.liveDivider(
    width: Int = MATCH_PARENT,
    height: Int = 0.5.dp,
    @StyleRes style: Int = 0,

    @IdRes id: Int? = null,
    tag: Any? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: LiveData<Int>? = null,
    isSelected: LiveData<Boolean>? = null,
    onClick: View.OnClickListener? = null,
) = divider(
    width, height, style, id, tag, background, padding, onClick = onClick,
).apply {
    context.inMyLifecycle {
        visibility?.bindNotNull(this) {
            this@apply.visibility = it
        }
        isSelected?.bindNotNull(this) {
            this@apply.isSelected = it
        }
    }
    if (isInEditMode) {
        visibility?.value?.let { this.visibility = it }
        isSelected?.value?.let { this.isSelected = it }
    }
}
