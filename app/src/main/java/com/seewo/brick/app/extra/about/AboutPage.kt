package com.seewo.brick.app.extra.about

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.text.buildSpannedString
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.TopBar
import com.seewo.brick.ktx.*
import com.seewo.brick.params.EdgeInsets

class AboutPage(context: Context, attrs: AttributeSet): BrickPreview(context, attrs) {
    override fun preview(context: Context) {
        view {
            context.AboutPage()
        }
    }
}

fun Context.AboutPage() = column(
    MATCH_PARENT, MATCH_PARENT,
    gravity = Gravity.CENTER_HORIZONTAL,
    fitsSystemWindows = true,
) {
    TopBar()
    LOGO()
    Title()
    TextBody()
    Links()
}

private fun ViewGroup.Links() {
    row(
        padding = EdgeInsets.only(bottom = 32.dp)
    ) {
        textView(
            text = buildSpannedString {
                appendClickable(
                    "BrickUI@github",
                    isUnderlineText = false,
                ) {
                    context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://github.com/robin8yeung/BrickUI")
                    })
                }
            },
            textSize = 14.dp,
            padding = EdgeInsets.only(end = 8.dp),
            movementMethod = LinkMovementMethod(),
            highLightColor = Color.TRANSPARENT,
        )
        divider(
            1.dp, 16.dp,
            background = R.color.grey_e4.colorDrawable,
        )
        textView(
            text = buildSpannedString {
                appendClickable(
                    "BrickUI@gitlab(内部)",
                    isUnderlineText = false,
                ) {
                    context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://gitlab.gz.cvte.cn/seewocbb/BrickUI")
                    })
                }
            },
            textSize = 14.dp,
            padding = EdgeInsets.only(start = 8.dp),
            movementMethod = LinkMovementMethod(),
            highLightColor = Color.TRANSPARENT,
        )
    }
}

private fun LinearLayout.Title() {
    textView(
        text = "BrickUI",
        textSize = 24.dp,
        textColor = R.color.primary.color,
        padding = EdgeInsets.only(bottom = 48.dp),
        textStyle = Typeface.BOLD,
    )
}

private fun LinearLayout.TextBody() {
    expand {
        textView(
            MATCH_PARENT, MATCH_PARENT,
            text = """
受Jetpack Compose启发，通过组合和声明的方式去搭建UI，就像用砖头垒出来的一样自然。

BrickUI是一套Kotlin实现的，基于原生View体系的声明式UI框架。
与原生View体系无缝对接，从此代码徒手撸layout，忘记你的xml吧~
            """.trimIndent(),
            textSize = 16.dp,
            padding = EdgeInsets.only(bottom = 16.dp, start = 32.dp, end = 32.dp),
            textColor = Color.GRAY,
        )
    }
}

private fun LinearLayout.LOGO() {
    layoutParams(margins = EdgeInsets.only(top = 32.dp, bottom = 16.dp)) {
        roundRectClip(
            radius = 8.dp
        ) {
            imageView(
                64.dp, 64.dp,
                drawable = R.mipmap.ic_launcher.drawable,
            )
        }
    }
}
