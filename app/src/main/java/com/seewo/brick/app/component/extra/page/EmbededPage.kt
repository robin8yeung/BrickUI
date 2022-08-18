package com.seewo.brick.app.component.extra.page

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.column
import com.seewo.brick.ktx.expand
import com.seewo.brick.ktx.view

private class EmbededPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.EmbededPage()
        }
    }
}

fun Context.EmbededPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    ShowMarkDown()
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

虽然BrickUI封装了一些常用的控件，和一些好用的第三方控件，但不可能对所有控件都进行封装，而且这样做意义不大。
由于BrickUI本身就是基于View体系实现的，把第三方控件嵌入到BrickUI中并非难事。
当前你所看到的Markdown文档就是用第三方控件来展示的，实现如下：

```kotlin
private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            ""${'"'}
...
这里是 Markdown 文档
...
                ""${'"'}.trimIndent()
        )
    }
}

fun ViewGroup.Markdown(
    text: String
): View {
    if (isInEditMode) return placeholder() // 避免预览模式报错
    return view {
        // 第三方控件：MarkdownWebView
        MarkdownWebView(context).apply {
            // 初始化宽高
            init(MATCH_PARENT, MATCH_PARENT)
            setText(text)
        }
    }
}
```
                """.trimIndent()
        )
    }
}
