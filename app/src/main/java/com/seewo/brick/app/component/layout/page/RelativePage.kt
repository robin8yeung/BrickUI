package com.seewo.brick.app.component.layout.page

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.*
import com.seewo.brick.params.EdgeInsets

class RelativePage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.RelativePage()
        }
    }
}

fun Context.RelativePage() = column(
    MATCH_PARENT, MATCH_PARENT,
    animateLayoutChanges = true,
) {
    IconWithBadge()
    ShowMarkDown()
}

private fun LinearLayout.IconWithBadge() {
    relativeLayout(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(vertical = 8.dp, horizontal = 16.dp)
    ) {
        // icon在布局内居中
        layoutParams(
            rules = {
                addRule(RelativeLayout.CENTER_IN_PARENT)
            },
        ) {
            // 圆角裁剪
            roundRectClip(radius = 4.dp) {
                // icon，这里id取得比较随意，正式使用，建议把id定义在ids.xml中
                imageView(
                    48.dp, 48.dp,
                    id = 0x1234,
                    drawable = R.mipmap.ic_launcher.drawable,
                )
            }
        }
        // 红点布局与icon的右侧对齐
        layoutParams(
            rules = {
                addRule(RelativeLayout.ALIGN_END, 0x1234)
            },
            margins = EdgeInsets.only(end = (-4).dp, top = (-4).dp)
        ) {
            // 随便用个占位符作为红点
            placeholder(
                8.dp,
                8.dp,
                background = ovalDrawable(fillColor = Color.RED)
            )
        }
    }.apply {
        // 避免布局padding裁剪红点
        clipToOutline = false
        clipToPadding = false
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

```kotlin
private fun LinearLayout.IconWithBadge() {
    relativeLayout(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(vertical = 8.dp, horizontal = 16.dp)
    ) {
        // icon在布局内居中
        layoutParams(
            rules = {
                addRule(RelativeLayout.CENTER_IN_PARENT)
            },
        ) {
            // 圆角裁剪
            roundRectClip(radius = 4.dp) {
                // icon，这里id取得比较随意，正式使用，建议把id定义在ids.xml中
                imageView(
                    48.dp, 48.dp,
                    id = 0x1234,
                    drawable = R.mipmap.ic_launcher.drawable,
                )
            }
        }
        // 红点布局与icon的右侧对齐
        layoutParams(
            rules = {
                addRule(RelativeLayout.ALIGN_END, 0x1234)
            },
            margins = EdgeInsets.only(end = (-4).dp, top = (-4).dp)
        ) {
            // 随便用个占位符作为红点
            placeholder(
                8.dp,
                8.dp,
                background = ovalDrawable(fillColor = Color.RED)
            )
        }
    }.apply {
        // 避免布局padding裁剪红点
        clipToOutline = false
        clipToPadding = false
    }
}
```
                """.trimIndent()
        )
    }
}
