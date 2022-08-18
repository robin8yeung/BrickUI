package com.seewo.brick.app.widget

import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import com.seewo.brick.app.R
import com.seewo.brick.ktx.*
import com.seewo.brick.params.CornerRadius
import com.seewo.brick.params.EdgeInsets

fun LinearLayout.SeekValue(
    name: String,
    max: Int,
    value: MutableLiveData<Int>,
    valueMapper: (Int) -> String
) {
    row(MATCH_PARENT, 24.dp) {
        textView(
            60.dp,
            text = name
        )
        expand {
            liveSeekBar(
                MATCH_PARENT, 12.dp,
                thumb = ovalDrawable(
                    width = 12.dp,
                    height = 12.dp,
                    strokeWidth = 1.dp,
                    strokeColor = R.color.primary.color,
                    fillColor = Color.WHITE,
                ),
                thumbOffset = 2.dp,
                progressBackground = rectDrawable(
                    fillColor = R.color.grey_e4.color,
                    corners = CornerRadius.all(60.dp),
                ),
                progressDrawable = rectDrawable(
                    fillColor = R.color.primary.color,
                    corners = CornerRadius.all(60.dp),
                ),
                progress = value,
                max = max,
                padding = EdgeInsets.symmetric(vertical = 4.dp, horizontal = 2.dp)
            )
        }
        liveText(
            40.dp,
            text = value.map(valueMapper),
            gravity = Gravity.END,
        )
    }
}