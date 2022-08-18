package com.seewo.brick.app.main

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.ktx.*
import com.seewo.brick.params.EdgeInsets

class PreviewMainItem(context: Context, attrs: AttributeSet? = null): BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        MainItemWidget(
            "Hello Brick",
            R.mipmap.ic_launcher.drawable,
        )
    }
}

class MainItemBean(
    val text: String,
    val drawable: Drawable? = null,
    val onClick: OnClickListener? = null,
)

fun ViewGroup.MainItemWidget(
    text: String,
    drawable: Drawable? = null,
    onClick: OnClickListener? = null,
) = column(
    MATCH_PARENT, 120.dp,
    padding = EdgeInsets.symmetric(horizontal = 8.dp, vertical = 10.dp),
    gravity = Gravity.CENTER,
    background = stateListDrawable(
        mapOf(
            intArrayOf(-android.R.attr.state_pressed) to rectDrawable(fillColor = Color.WHITE),
            intArrayOf(android.R.attr.state_pressed) to rectDrawable(fillColor = Color.parseColor("#E4E4E4")),
        )
    ),
    onClick = onClick,
) {
    imageView(
        48.dp, 48.dp,
        drawable = drawable,
    )
    layoutParams(margins = EdgeInsets.all(8.dp)) {
        textView(
            text = text,
            maxLines = 1,
            ellipsize = TextUtils.TruncateAt.END,
        )
    }
}