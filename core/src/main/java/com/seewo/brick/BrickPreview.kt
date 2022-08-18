package com.seewo.brick

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.init

abstract class BrickPreview(context: Context, attributeSet: AttributeSet? = null): FrameLayout(context, attributeSet) {
    init {
        BrickUI.init(context)
        init(MATCH_PARENT, MATCH_PARENT)
        preview(context)
    }

    abstract fun preview(context: Context)
}