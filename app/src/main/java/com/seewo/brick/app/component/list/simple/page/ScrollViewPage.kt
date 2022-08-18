package com.seewo.brick.app.component.list.simple.page

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.*
import com.seewo.brick.params.EdgeInsets

private class ScrollViewPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.ScrollViewPage()
        }
    }
}

fun Context.ScrollViewPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    SimpleList()
    ShowMarkDown()
}

private fun LinearLayout.SimpleList() {
    scrollView(
        MATCH_PARENT, 100.dp,
    ) {
        column(
            MATCH_PARENT
        ) {
            repeat(50) {
                if (it != 0) {
                    layoutParams(
                        margins = EdgeInsets.symmetric(horizontal = 16.dp)
                    ) {
                        divider(
                            background = R.color.primary.colorDrawable,
                        )
                    }
                }
                textView(
                    MATCH_PARENT, 32.dp,
                    text = "$it",
                    // 单数为主题色，双数为黑色
                    textColor = if (it % 2 == 0) Color.BLACK else R.color.primary.color,
                    textSize = 14.dp,
                    gravity = Gravity.CENTER,
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

通过ScrollView实现页面可滚动

```kotlin
private fun LinearLayout.simpleList() {
    scrollView(
        MATCH_PARENT, 100.dp,
    ) {
        column(
            MATCH_PARENT
        ) {
            repeat(50) {
                if (it != 0) {
                    layoutParams(
                        margins = EdgeInsets.symmetric(horizontal = 16.dp)
                    ) {
                        divider(
                            background = R.color.primary.colorDrawable,
                        )
                    }
                }
                textView(
                    MATCH_PARENT, 32.dp,
                    text = "${'$'}it",
                    // 单数为主题色，双数为黑色
                    textColor = if (it % 2 == 0) Color.BLACK else R.color.primary.color,
                    textSize = 14.dp,
                    gravity = Gravity.CENTER,
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
