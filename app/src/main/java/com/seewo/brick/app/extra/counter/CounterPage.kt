package com.seewo.brick.app.extra.counter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.app.widget.TopBar
import com.seewo.brick.ktx.*
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.params.Shadow


class CounterPage(context: Context, attrs: AttributeSet?) : BrickPreview(context, attrs) {
    override fun preview(context: Context) {
        view {
            context.CounterPage()
        }
    }
}

fun Context.CounterPage() = column(
    MATCH_PARENT, MATCH_PARENT,
    fitsSystemWindows = true,
) {
    // 通过上下文获取ViewModel，这样可以保证在任何嵌套下均能获取到Activity的ViewModel
    val viewModel: CounterPageViewModel? = context.activityViewModelOrNull()

    TopBar()
    divider(background = ColorDrawable(Color.GRAY))
    row(
        MATCH_PARENT, 160.dp,
        gravity = Gravity.CENTER,
        fitsSystemWindows = true,
    ) {
        button("-") {
            viewModel?.minus()
        }
        liveText(
            84.dp,
            style = R.style.BigNumber,
            text = viewModel?.count?.map { it.toString() } ?: "0".static, // 为了在预览模式能看到0的效果
            textColor = viewModel?.count?.map { if (it % 2 == 0) Color.RED else Color.BLACK },
            padding = EdgeInsets.all(12.dp),
        )
        button("+") {
            viewModel?.plus()
        }
    }
    ShowMarkDown()
}

private fun ViewGroup.button(
    text: String,
    onClick: View.OnClickListener
) = shadowBox(
    radius = 14.dp, shadow = Shadow(blur = 8.dp),
    onClick = onClick
) {
    textView(
        width = 100.dp, height = 100.dp,
        text = text,
        textSize = 28.dp,
        textStyle = Typeface.BOLD,
        gravity = Gravity.CENTER
    )
}


private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 最佳实践

### 架构

与Flutter的默认demo类似，这里给到一个简单的计数器demo，用于阐释一个带有具体业务的例子。
BrickUI作为基于View体系的一套框架，对标的实际更多是DataBinding，所以最佳实践仍然是采用MVVM架构。

### 数据绑定

相比DataBind，我们不需要再在xml里面写逻辑，而且brick-ui-live提供了比DataBinding更纯粹而有效的双向绑定，可以参考其中liveEdit、liveCheckBox、liveSeekBar等的实现

### 代码展示
篇幅有限，这里就不展示代码了，请到demo源码去看吧
                """.trimIndent()
        )
    }
}
