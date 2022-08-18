package com.seewo.brick.app.widget

import android.graphics.drawable.Drawable
import com.seewo.brick.app.R
import com.seewo.brick.ktx.*
import com.seewo.brick.params.CornerRadius

val buttonBackGround: Drawable
    get() = stateListDrawable(
        mapOf(
            intArrayOf(-android.R.attr.state_enabled) to rectDrawable(
                fillColor = R.color.primary.color.withAlpha(0.4f),
                corners = CornerRadius.all(4.dp),
            ),
            intArrayOf(android.R.attr.state_pressed) to rectDrawable(
                fillColor = R.color.primary.color.withAlpha(0.6f),
                corners = CornerRadius.all(4.dp),
            ),
            intArrayOf(
                -android.R.attr.state_pressed,
                android.R.attr.state_enabled
            ) to rectDrawable(
                fillColor = R.color.primary.color,
                corners = CornerRadius.all(4.dp),
            ),
        )
    )