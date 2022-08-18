package com.seewo.brick.app.widget

import android.app.Activity
import android.view.ViewGroup
import android.widget.ImageView
import com.seewo.brick.app.R
import com.seewo.brick.ktx.*

fun ViewGroup.TopBar() {
    row(
        MATCH_PARENT, 44.dp,
    ) {
        imageView(
            44.dp, 44.dp,
            drawable = R.drawable.ic_back_dark.drawable,
            scaleType = ImageView.ScaleType.CENTER_INSIDE,
        ) {
            (context as? Activity)?.onBackPressed()
        }
        expand()
    }
}