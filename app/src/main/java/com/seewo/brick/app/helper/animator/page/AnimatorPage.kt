package com.seewo.brick.app.helper.animator.page

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.column
import com.seewo.brick.ktx.dp
import com.seewo.brick.ktx.drawable
import com.seewo.brick.ktx.expand
import com.seewo.brick.ktx.imageView
import com.seewo.brick.ktx.ovalClip
import com.seewo.brick.ktx.row
import com.seewo.brick.ktx.runAnimator
import com.seewo.brick.ktx.textView
import com.seewo.brick.ktx.view
import com.seewo.brick.params.EdgeInsets

private class AnimatorPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.AnimatorPage()
        }
    }
}

fun Context.AnimatorPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    AnimatorDemo()
    ShowMarkDown()
}

private fun LinearLayout.AnimatorDemo() {
    column(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(4.dp, 16.dp),
    ) {
        row(
            MATCH_PARENT,
            padding = EdgeInsets.symmetric(4.dp),
        ) {
            ovalClip {
                // 在声明式UI中，通过runAnimator快速构造ValueAnimator，并应用在view上
                imageView(
                    64.dp, 64.dp,
                    background = R.mipmap.ic_launcher.drawable
                )
            }.runAnimator(
                values = floatArrayOf(0f, 360f),
                duration = 1000,
                repeatCount = ValueAnimator.INFINITE,
            ) {
                rotation = it
            }

            textView(
                gravity = Gravity.CENTER,
                padding = EdgeInsets.all(8.dp)
            ).apply {
                runAnimator(
                    values = intArrayOf(0, 100),
                    duration = 3000,
                    repeatCount = ValueAnimator.INFINITE,
                    repeatMode = ValueAnimator.REVERSE,
                ) {
                    text = "文字不透明度:$it"
                    alpha = it / 100f
                }
                runAnimator(
                    values = intArrayOf(0, 100),
                    duration = 5000,
                    repeatCount = ValueAnimator.INFINITE,
                    repeatMode = ValueAnimator.REVERSE,
                ) {
                    translationX = it.dp.toFloat()
                }
            }
        }
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

通过扩展函数runAnimator可以帮助开发者快速构造ValueAnimator，并应用在声明式UI中.
目前支持同时启动多个动画，也可以分步执行多个动画（需通过设置startDelay的方式）

```kotlin
private fun LinearLayout.AnimatorDemo() {
    column(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(4.dp, 16.dp),
    ) {
        row(
            MATCH_PARENT,
            padding = EdgeInsets.symmetric(4.dp),
        ) {
            ovalClip {
                // 在声明式UI中，通过runAnimator快速构造ValueAnimator，并应用在view上
                imageView(
                    64.dp, 64.dp,
                    background = R.mipmap.ic_launcher.drawable
                )
            }.runAnimator(
                values = floatArrayOf(0f, 360f),
                duration = 1000,
                repeatCount = ValueAnimator.INFINITE,
            ) {
                rotation = it
            }

            textView(
                gravity = Gravity.CENTER,
                padding = EdgeInsets.all(8.dp)
            ).apply {
                runAnimator(
                    values = intArrayOf(0, 100),
                    duration = 3000,
                    repeatCount = ValueAnimator.INFINITE,
                    repeatMode = ValueAnimator.REVERSE,
                ) {
                    text = "文字不透明度: ${'$'}it"
                    alpha = it / 100f
                }
                runAnimator(
                    values = intArrayOf(0, 100),
                    duration = 5000,
                    repeatCount = ValueAnimator.INFINITE,
                    repeatMode = ValueAnimator.REVERSE,
                ) {
                    translationX = it.dp.toFloat()
                }
            }
        }
    }
}
```
                """.trimIndent()
        )
    }
}
