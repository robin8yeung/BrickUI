package com.seewo.brick.app.component.layout.page

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.*
import com.seewo.brick.params.CornerRadius
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.view.parentId

class ConstraintPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.ConstraintPage()
        }
    }
}

fun Context.ConstraintPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    VideoItem()
    ShowMarkDown()
}

private fun LinearLayout.VideoItem() {
    // 约束布局的根布局和所有需要设置约束条件的控件均需设置id
    // 约束布局在BrickUI中基本能用，但对于一些复杂的场景，仍然存在一些问题，所以不建议优先使用
    constraintLayout(
        MATCH_PARENT,
        id = 0x4321,
        padding = EdgeInsets.all(4.dp)
    ) {
        layoutParams(
            // 视频画幅的约束条件设置
            apply = {
                startToStart = parentId
                endToEnd = parentId
                dimensionRatio = "h, 9:16"
            },
        ) {
            placeholder(
                0.dp, 64.dp,
                id = 0x1234,
                background = rectDrawable(
                    corners = CornerRadius.all(4.dp),
                    fillColor = Color.BLACK,
                ),
            )
        }
        // 视频时长的约束条件设置
        layoutParams(
            apply = {
                endToEnd = 0x1234
                bottomToBottom = 0x1234
                setMargins(4.dp, 4.dp, 4.dp, 4.dp)
            },
        ) {
            textView(
                id = 0x2345,
                background = rectDrawable(
                    corners = CornerRadius.all(16.dp),
                    fillColor = Color.GRAY.withAlpha(0.5f),
                ),
                padding = EdgeInsets.symmetric(2.dp, 6.dp),
                text = "01:34",
                textColor = Color.WHITE,
                textSize = 8.dp,
            )
        }
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

```kotlin
private fun LinearLayout.VideoItem() {
    // 约束布局的根布局和所有需要设置约束条件的控件均需设置id
    // 约束布局在BrickUI中基本能用，但对于一些复杂的场景，仍然存在一些问题，所以不建议优先使用
    constraintLayout(
        MATCH_PARENT,
        id = 0x4321,
        padding = EdgeInsets.all(4.dp)
    ) {
        layoutParams(
            // 视频画幅的约束条件设置
            apply = {
                startToStart = parentId
                endToEnd = parentId
                dimensionRatio = "w, 16:9"
            },
        ) {
            placeholder(
                0.dp, 64.dp,
                id = 0x1234,
                background = rectDrawable(
                    corners = CornerRadius.all(4.dp),
                    fillColor = Color.BLACK,
                ),
            )
        }
        // 视频时长的约束条件设置
        layoutParams(
            apply = {
                endToEnd = 0x1234
                bottomToBottom = 0x1234
                setMargins(4.dp, 4.dp, 4.dp, 4.dp)
            },
        ) {
            textView(
                id = 0x2345,
                background = rectDrawable(
                    corners = CornerRadius.all(16.dp),
                    fillColor = Color.GRAY.withAlpha(0.5f),
                ),
                padding = EdgeInsets.symmetric(2.dp, 6.dp),
                text = "01:34",
                textColor = Color.WHITE,
                textSize = 8.dp,
            )
        }
    }
}
```
                """.trimIndent()
        )
    }
}
