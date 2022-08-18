package com.seewo.brick.app.component.list.simple.page

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.*
import com.seewo.brick.params.EdgeInsets

private class HorizontalListPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.HorizontalListPage()
        }
    }
}

fun Context.HorizontalListPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    SimpleList()
    ShowMarkDown()
}

private fun LinearLayout.SimpleList() {
    simpleStatelessRecyclerView(
        MATCH_PARENT, WRAP_CONTENT,
        // 列表数据
        data = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
        // 设置分割线
        itemDecoration = dividerDecoration(
            color = R.color.primary.color,
            orientation = LinearLayoutManager.HORIZONTAL,
        ),
        // 设置横向LayoutManager
        layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        },
        // 注意：由于默认ViewHolder不适合布局横向列表，这里需要自定义一个宽高结尾WRAP_CONTENT的帧布局
        viewHolderCreator = {
            frameLayout()
        },
        padding = EdgeInsets.symmetric(vertical = 8.dp, horizontal = 16.dp)
    ) { data, index ->
        // 构造每一项的控件，可以根据不同的item构建不同的控件
        val item = data[index]
        textView(
            48.dp, 32.dp,
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
        MATCH_PARENT, WRAP_CONTENT,
        // 列表数据
        data = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
        // 设置分割线
        itemDecoration = dividerDecoration(
            color = R.color.primary.color,
            orientation = LinearLayoutManager.HORIZONTAL,
        ),
        // 设置横向LayoutManager
        layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        },
        // 注意：由于默认ViewHolder不适合布局横向列表，这里需要自定义一个宽高结尾WRAP_CONTENT的帧布局
        viewHolderCreator = {
            frameLayout()
        },
        padding = EdgeInsets.symmetric(vertical = 8.dp, horizontal = 16.dp)
    ) { data, index ->
        // 构造每一项的控件，可以根据不同的item构建不同的控件
        val item = data[index]
        textView(
            48.dp, 32.dp,
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
