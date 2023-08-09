package com.seewo.brick.init

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.annotation.IdRes
import com.seewo.brick.ktx.WRAP_CONTENT
import com.seewo.brick.ktx.init
import com.seewo.brick.params.EdgeInsets

internal fun View.setup(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    @IdRes id: Int? = null,
    tag: Any? = null,
    foreground: Drawable? = null,
    background: Drawable? = null,
    padding: EdgeInsets? = null,
    visibility: Int? = null,
    isSelected: Boolean? = null,
    isEnabled: Boolean? = null,
    onClick: View.OnClickListener? = null,
    fitsSystemWindows: Boolean? = null,
) {
    init(width, height)
    id?.let { this.id = it }
    tag?.let { this.tag = it }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        foreground?.let {
            this.foreground = it
        }
    }
    background?.let { this.background = it }
    padding?.let { setPadding(it.start, it.top, it.end, it.bottom) }
    visibility?.let { this.visibility = it }
    isSelected?.let { this.isSelected = it }
    isEnabled?.let { this.isEnabled = it }
    onClick?.let { setOnClickListener(it) }
    fitsSystemWindows?.let { this.fitsSystemWindows = fitsSystemWindows }
}

internal fun <T : View> T.applyMargin(
    margin: EdgeInsets? = null,
): T = apply {
    margin?.let {
        (layoutParams as? MarginLayoutParams)?.setMargins(
            it.start, it.top, it.end, it.bottom
        )
    }
}
