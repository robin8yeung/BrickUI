package com.seewo.brick.ktx

import android.view.View
import android.view.ViewGroup
import com.seewo.brick.params.EdgeInsets

// 包名别改


fun <T : ViewGroup, R : View> T.margins(
    margins: EdgeInsets? = null,
    block: (T.() -> R)? = null
): R? = attach(block)?.apply {
    layoutParams = ViewGroup.MarginLayoutParams(layoutParams).apply {
        margins?.let { setMargins(it.start, it.top, it.end, it.bottom) }
    }
}
