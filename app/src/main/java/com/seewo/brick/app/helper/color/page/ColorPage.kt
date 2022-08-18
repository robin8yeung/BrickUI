package com.seewo.brick.app.helper.color.page

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.app.widget.SeekValue
import com.seewo.brick.ktx.*
import com.seewo.brick.params.CornerRadius
import com.seewo.brick.params.EdgeInsets

private class ColorPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.ColorPage()
        }
    }
}

fun Context.ColorPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    ColorControlView()
    ShowMarkDown()
}

private fun LinearLayout.ColorControlView() {
    val alpha = 50.live
    column(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(4.dp, 16.dp),
    ) {
        SeekValue("alpha", 100, alpha) { "${it / 100f}" }
        row(
            padding = EdgeInsets.symmetric(4.dp),
        ) {
            textView(
                text = "颜色: R.color.primary",
                padding = EdgeInsets.only(end = 8.dp)
            )
            // 提供了color函数，等效于Resource.getColor函数，快速从资源获取到颜色值
            // 另外还提供了COLOR函数，等效于Resource.getColor().static，快速构建颜色值的静态LiveData

            // 提供了withAlpha函数快速修改ColorInt的不透明度，需要注意的是这个alpha是绝对值，不是相对值
            liveImage(
                72.dp, 72.dp,
                drawable = alpha.map { rectDrawable(
                    fillColor = R.color.primary.color.withAlpha(it / 100f),
                    corners = CornerRadius.all(8.dp)
                ) },
                background = R.mipmap.ic_launcher.drawable?.clipRect(8.dp)
            )
        }
        row(
            padding = EdgeInsets.symmetric(4.dp),
        ) {
            textView(
                text = "颜色点击效果：",
                padding = EdgeInsets.only(end = 8.dp)
            )
            textView(
                text = "点我试试",
                textSize = 24.dp,
                gravity = Gravity.CENTER,
                padding = EdgeInsets.symmetric(horizontal = 4.dp, vertical = 2.dp),
                // 通过stateListColor设置不同状态下的颜色
                textColorList = stateListColor(mapOf(
                    intArrayOf(-android.R.attr.state_pressed) to R.color.primary.color,
                    intArrayOf(android.R.attr.state_pressed) to Color.RED,
                )),
                background = rectDrawable(
                    strokeWidth = 1.dp,
                    strokeColor = R.color.primary.color,
                    corners = CornerRadius.all(4.dp)
                )
            ) {
                // onClick here
            }
        }
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

```kotlin
private fun LinearLayout.ColorControlView() {
    val alpha = 50.live
    column(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(4.dp, 16.dp),
    ) {
        SeekValue("alpha", 100, alpha) { "${'$'}{it / 100f}" }
        row(
            padding = EdgeInsets.symmetric(4.dp),
        ) {
            textView(
                text = "颜色: R.color.primary",
                padding = EdgeInsets.only(end = 8.dp)
            )
            // 提供了color函数，等效于Resource.getColor函数，快速从资源获取到颜色值
            // 另外还提供了COLOR函数，等效于Resource.getColor().static，快速构建颜色值的静态LiveData

            // 提供了withAlpha函数快速修改ColorInt的不透明度，需要注意的是这个alpha是绝对值，不是相对值
            liveImage(
                72.dp, 72.dp,
                drawable = alpha.map { rectDrawable(
                    fillColor = R.color.primary.color.withAlpha(it / 100f),
                    corners = CornerRadius.all(8.dp)
                ) },
                background = R.mipmap.ic_launcher.drawable?.clipRect(8.dp)
            )
        }
        row(
            padding = EdgeInsets.symmetric(4.dp),
        ) {
            textView(
                text = "颜色点击效果：",
                padding = EdgeInsets.only(end = 8.dp)
            )
            textView(
                text = "点我试试",
                textSize = 24.dp,
                gravity = Gravity.CENTER,
                padding = EdgeInsets.symmetric(horizontal = 4.dp, vertical = 2.dp),
                // 通过stateListColor设置不同状态下的颜色
                textColorList = stateListColor(mapOf(
                    intArrayOf(-android.R.attr.state_pressed) to R.color.primary.color,
                    intArrayOf(android.R.attr.state_pressed) to Color.RED,
                )),
                background = rectDrawable(
                    strokeWidth = 1.dp,
                    strokeColor = R.color.primary.color,
                    corners = CornerRadius.all(4.dp)
                )
            ) {
                // onClick here
            }
        }
    }
}
```
                """.trimIndent()
        )
    }
}
