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

private class VerticalListPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.VerticalListPage()
        }
    }
}

fun Context.VerticalListPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    SimpleList()
    ShowMarkDown()
}

private fun LinearLayout.SimpleList() {
    simpleStatelessRecyclerView(
        MATCH_PARENT, 100.dp,
        // 列表数据
        data = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
        // 设置分割线
        itemDecoration = dividerDecoration(
            color = R.color.primary.color,
            padding = EdgeInsets.only(bottom = 8.dp, start = 16.dp, end = 16.dp)
        ),
    ) { data, index ->
        // 构造每一项的控件，可以根据不同的item构建不同的控件
        val item = data[index]
        textView(
            MATCH_PARENT, 32.dp,
            text = "$item",
            // 单数为主题色，双数为黑色
            textColor = if (item % 2 == 0) Color.BLACK else R.color.primary.color,
            textSize = 14.dp,
            gravity = Gravity.CENTER,
        )
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

无状态简单列表，静态设置的简单列表，data为固定列表，不可修改

```kotlin
private fun LinearLayout.simpleList() {
    simpleStatelessRecyclerView(
        MATCH_PARENT, 100.dp,
        // 列表数据
        data = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
        // 设置分割线
        itemDecoration = dividerDecoration(
            color = R.color.primary.color,
            padding = EdgeInsets.only(bottom = 8.dp, start = 16.dp, end = 16.dp)
        ),
    ) { data, index ->
        // 构造每一项的控件，可以根据不同的item构建不同的控件
        val item = data[index]
        textView(
            MATCH_PARENT, 32.dp,
            text = "${'$'}item",
            // 单数为主题色，双数为黑色
            textColor = if (item % 2 == 0) Color.BLACK else R.color.primary.color,
            textSize = 14.dp,
            gravity = Gravity.CENTER,
        )
    }
}
```
                """.trimIndent()
        )
    }
}
