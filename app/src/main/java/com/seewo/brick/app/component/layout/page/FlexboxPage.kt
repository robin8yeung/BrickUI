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
import com.seewo.brick.view.LimitLinesFlexboxLayout

class FlexboxPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.FlexboxPage()
        }
    }
}

fun Context.FlexboxPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    Chips()
    ShowMarkDown()
}

private fun LinearLayout.Chips() {
    // flexboxLayout 用于线性展示控件，并在控件不够时可以换行
    // 需要引入FlexboxLayout的依赖 com.google.android.flexbox:flexbox
    flexboxLayout(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(8.dp, 16.dp)
    ) {
        repeat(10) {
            Chip(it)
        }
    }
}

private fun LimitLinesFlexboxLayout.Chip(it: Int) {
    margins(
        margins = EdgeInsets.all(4.dp)
    ) {
        textView(
            background = rectDrawable(
                corners = CornerRadius.all(4.dp),
                fillColor = Color.GRAY.withAlpha(0.7f),
            ),
            padding = EdgeInsets.symmetric(2.dp, 6.dp),
            text = "TAG $it",
            textColor = Color.WHITE,
            textSize = 12.dp,
        )
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

需要引入FlexboxLayout的依赖 com.google.android.flexbox:flexbox

```kotlin
private fun LinearLayout.Chips() {
    // flexboxLayout 用于线性展示控件，并在控件不够时可以换行
    flexboxLayout(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(8.dp, 16.dp)
    ) {
        repeat(10) {
            Chip(it)
        }
    }
}

private fun LimitLinesFlexboxLayout.Chip(it: Int) {
    layoutParams(
        margins = EdgeInsets.all(4.dp)
    ) {
        textView(
            background = rectDrawable(
                corners = CornerRadius.all(4.dp),
                fillColor = Color.GRAY.withAlpha(0.7f),
            ),
            padding = EdgeInsets.symmetric(2.dp, 6.dp),
            text = "TAG $${'$'}it",
            textColor = Color.WHITE,
            textSize = 12.dp,
        )
    }
}
```
                """.trimIndent()
        )
    }
}
