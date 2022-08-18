package com.seewo.brick.app.helper.clip.page

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.*
import com.seewo.brick.params.EdgeInsets

private class ClipPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.ClipPage()
        }
    }
}

fun Context.ClipPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    ClipViews()
    ShowMarkDown()
}

private fun LinearLayout.ClipViews() {
    column(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(4.dp, 16.dp),
    ) {
        row(
            MATCH_PARENT,
            padding = EdgeInsets.symmetric(4.dp),
        ) {
            // 圆角矩形裁剪图片
            roundRectClip(
                radius = 8.dp,
            ) {
                imageView(
                    64.dp, 64.dp,
                    drawable = R.mipmap.ic_launcher.drawable,
                )
            }
            placeholder(
                width = 4.dp
            )
            // 圆形裁剪图片
            ovalClip {
                imageView(
                    64.dp, 64.dp,
                    drawable = R.mipmap.ic_launcher.drawable,
                )
            }
        }
        // 圆角矩形裁剪ViewGroup
        roundRectClip(
            radius = 8.dp,
        ) {
            row {
                imageView(
                    64.dp, 64.dp,
                    drawable = R.mipmap.ic_launcher.drawable,
                )
                textView(
                    WRAP_CONTENT, 64.dp,
                    background = R.color.grey_e4.colorDrawable,
                    text = "BrickUI",
                    textSize = 18.dp,
                    gravity = Gravity.START or Gravity.CENTER_VERTICAL,
                    padding = EdgeInsets.symmetric(horizontal = 8.dp),
                )
            }
        }
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

允许开发者快速对View进行圆形、圆角矩形裁剪
特别注意：必须打开硬件加速(setLayerType(View.LAYER_TYPE_HARD, null)，普通View默认是打开的)才能生效

```kotlin
private fun LinearLayout.ClipViews() {
    column(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(4.dp, 16.dp),
    ) {
        row(
            MATCH_PARENT,
            padding = EdgeInsets.symmetric(4.dp),
        ) {
            // 圆角矩形裁剪图片
            roundRectClip(
                radius = 8.dp,
            ) {
                imageView(
                    64.dp, 64.dp,
                    drawable = R.mipmap.ic_launcher.drawable,
                )
            }
            placeholder(
                width = 4.dp
            )
            // 圆形裁剪图片
            ovalClip {
                imageView(
                    64.dp, 64.dp,
                    drawable = R.mipmap.ic_launcher.drawable,
                )
            }
        }
        // 圆角矩形裁剪ViewGroup
        roundRectClip(
            radius = 8.dp,
        ) {
            row {
                imageView(
                    64.dp, 64.dp,
                    drawable = R.mipmap.ic_launcher.drawable,
                )
                textView(
                    WRAP_CONTENT, 64.dp,
                    background = R.color.grey_e4.colorDrawable,
                    text = "BrickUI",
                    textSize = 18.dp,
                    gravity = Gravity.START or Gravity.CENTER_VERTICAL,
                    padding = EdgeInsets.symmetric(horizontal = 8.dp),
                )
            }
        }
    }
}
```
                """.trimIndent()
        )
    }
}
