package com.seewo.brick.app.helper.spannable.page

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.text.*
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.*
import com.seewo.brick.params.EdgeInsets

private class SpannablePage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.SpannablePage()
        }
    }
}

fun Context.SpannablePage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    SpannableDemo()
    ShowMarkDown()
}

private fun LinearLayout.SpannableDemo() {
    column(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(4.dp, 16.dp),
    ) {
        textView(
            style = R.style.text_body_small,
            text = buildSpannedString {
                // 插入图片
                append(R.drawable.ic_well_chosen)
                append(" ")
                // 加粗
                bold {
                    // 文字颜色
                    color(Color.RED) {
                        append("注意: ")
                    }
                }
                // 文字大小
                size(14.dp) {
                    // 删除线
                    strikeThrough {
                        color(Color.LTGRAY) {
                            append("忽略这句")
                        }
                    }
                }
                append("请点击")
                // 可点击链接，必须设置movementMethod才能使其生效
                appendClickable("这里") {
                    context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://www.baidu.com")
                    })
                }
                append("跳转到")
                // 背景颜色
                backgroundColor(Color.RED) {
                    // 斜体
                    italic {
                        // 下划线
                        underline {
                            color(Color.WHITE) {
                                append("百度")
                            }
                        }
                    }
                }
                // 高斯模糊
                blur(4.dp.toFloat()) {
                    append("马赛克")
                }
                // 引用段落
                quote {
                    append("\n 这里是引用别人的")
                }
            },
            textSize = 18.dp,
            movementMethod = LinkMovementMethod.getInstance(),
            // 点击时的高光颜色
            highLightColor = Color.TRANSPARENT,
        )
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

支持常用的富文本构造

```kotlin
private fun LinearLayout.SpannableDemo() {
    column(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(4.dp, 16.dp),
    ) {
        textView(
            style = R.style.text_body_small,
            text = buildSpannedString {
                // 插入图片
                append(R.drawable.ic_well_chosen)
                append(" ")
                // 加粗
                bold {
                    // 文字颜色
                    color(Color.RED) {
                        append("注意: ")
                    }
                }
                // 文字大小
                size(14.dp) {
                    // 删除线
                    strikeThrough {
                        color(Color.LTGRAY) {
                            append("忽略这句")
                        }
                    }
                }
                append("请点击")
                // 可点击链接，必须设置movementMethod才能使其生效
                appendClickable("这里") {
                    context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://www.baidu.com")
                    })
                }
                append("跳转到")
                // 背景颜色
                backgroundColor(Color.RED) {
                    // 斜体
                    italic {
                        // 下划线
                        underline {
                            color(Color.WHITE) {
                                append("百度")
                            }
                        }
                    }
                }
                // 高斯模糊
                blur(4.dp.toFloat()) {
                    append("马赛克")
                }
                // 引用段落
                quote {
                    append("\n 这里是引用别人的")
                }
            },
            textSize = 18.dp,
            movementMethod = LinkMovementMethod.getInstance(),
            // 点击时的高光颜色
            highLightColor = Color.TRANSPARENT,
        )
    }
}
```
                """.trimIndent()
        )
    }
}
