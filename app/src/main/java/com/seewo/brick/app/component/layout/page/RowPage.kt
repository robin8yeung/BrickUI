package com.seewo.brick.app.component.layout.page

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.app.widget.buttonBackGround
import com.seewo.brick.ktx.*
import com.seewo.brick.params.CornerRadius
import com.seewo.brick.params.EdgeInsets

class RowPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.RowPage()
        }
    }
}

fun Context.RowPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    InputBar()
    ShowMarkdown()
}

private fun LinearLayout.InputBar() {
    // LiveData控制文本框输入的文字
    val text: MutableLiveData<CharSequence> = "".live
    // 行布局
    row(
        MATCH_PARENT, 54.dp,
        padding = EdgeInsets.symmetric(horizontal = 16.dp)
    ) {
        // 地球图标
        imageView(
            28.dp, 28.dp,
            drawable = R.drawable.ic_expand_web.drawable,
        )
        // 拉伸
        expand(
            margins = EdgeInsets.symmetric(horizontal = 8.dp)
        ) {
            // 文本框，因为用到LiveData，所以是liveEdit控件
            liveEdit(
                text = text,
                hint = "请输入网址",
                textSize = 14.dp,
                padding = EdgeInsets.symmetric(4.dp, 12.dp),
                // 类似于传统的selector xml
                background = stateListDrawable(
                    mapOf(
                        intArrayOf(-android.R.attr.state_focused) to rectDrawable(
                            corners = CornerRadius.all(4.dp),
                            strokeWidth = 1.dp,
                            strokeColor = R.color.grey_e4.color,
                        ),
                        intArrayOf(android.R.attr.state_focused) to rectDrawable(
                            corners = CornerRadius.all(4.dp),
                            strokeWidth = 1.dp,
                            strokeColor = R.color.primary.color,
                        ),
                    )
                ),
                maxLines = 1,
                // 设置键盘事件和回调
                imeOptions = EditorInfo.IME_ACTION_GO,
                onEditorAction = { v, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        context.showConfirmDialog(text.value)
                        true
                    } else false
                }
            )
        }
        // 跳转按钮，由于状态与文本框中的文本有关，所以用liveText
        liveText(
            text = "跳转".static,
            textColor = R.color.white.COLOR,
            textSize = 14.dp,
            isEnabled = text.map { it.isNotBlank() },
            // 类似于传统的selector xml
            background = buttonBackGround,
            padding = EdgeInsets.symmetric(
                vertical = 4.dp, horizontal = 8.dp,
            )
        ) {
            context.showConfirmDialog(text.value)
        }
    }
}

private fun Context.showConfirmDialog(title: CharSequence?) {
    AlertDialog.Builder(this)
        .setTitle("跳转到 $title")
        .setPositiveButton("确定") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}


private fun LinearLayout.ShowMarkdown() {
    expand {
        Markdown(
            """
## 代码展示

```kotlin
private fun LinearLayout.InputBar() {
    // LiveData控制文本框输入的文字
    val text: MutableLiveData<CharSequence> = "".live
    // 行布局
    row(
        MATCH_PARENT, 54.dp,
        padding = EdgeInsets.symmetric(horizontal = 16.dp)
    ) {
        // 地球图标
        imageView(
            28.dp, 28.dp,
            drawable = R.drawable.ic_expand_web.drawable,
        )
        // 拉伸
        expand(
            margins = EdgeInsets.symmetric(horizontal = 8.dp)
        ) {
            // 文本框，因为用到LiveData，所以是liveEdit控件
            liveEdit(
                text = text,
                hint = "请输入网址",
                textSize = 14.dp,
                padding = EdgeInsets.symmetric(4.dp, 12.dp),
                // 类似于传统的selector xml
                background = stateListDrawable(
                    mapOf(
                        intArrayOf(-android.R.attr.state_focused) to rectDrawable(
                            corners = CornerRadius.all(4.dp),
                            strokeWidth = 1.dp,
                            strokeColor = R.color.grey_e4.color,
                        ),
                        intArrayOf(android.R.attr.state_focused) to rectDrawable(
                            corners = CornerRadius.all(4.dp),
                            strokeWidth = 1.dp,
                            strokeColor = R.color.primary.color,
                        ),
                    )
                ),
                maxLines = 1,
                // 设置键盘事件和回调
                imeOptions = EditorInfo.IME_ACTION_GO,
                onEditorAction = { v, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        context.showConfirmDialog(text.value)
                        true
                    } else false
                }
            )
        }
        // 跳转按钮，由于状态与文本框中的文本有关，所以用liveText
        liveText(
            text = "跳转".static,
            textColor = R.color.white.COLOR,
            textSize = 14.dp,
            isEnabled = text.map { it.isNotBlank() },
            // 类似于传统的selector xml
            background = stateListDrawable(
                mapOf(
                    intArrayOf(-android.R.attr.state_enabled) to rectDrawable(
                        fillColor = R.color.primary.color.withAlpha(0.4f),
                        corners = CornerRadius.all(4.dp),
                    ),
                    intArrayOf(android.R.attr.state_pressed) to rectDrawable(
                        fillColor = R.color.primary.color.withAlpha(0.6f),
                        corners = CornerRadius.all(4.dp),
                    ),
                    intArrayOf(
                        -android.R.attr.state_pressed,
                        android.R.attr.state_enabled
                    ) to rectDrawable(
                        fillColor = R.color.primary.color,
                        corners = CornerRadius.all(4.dp),
                    ),
                )
            ),
            padding = EdgeInsets.symmetric(
                vertical = 4.dp, horizontal = 8.dp,
            )
        ) {
            context.showConfirmDialog(text.value)
        }
    }
}

private fun Context.showConfirmDialog(title: CharSequence?) {
    AlertDialog.Builder(this)
        .setTitle("跳转到 ${'$'}title")
        .setPositiveButton("确定") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}
```
                """.trimIndent()
        )
    }
}

