package com.seewo.brick.app.helper.clip.page

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.app.widget.SeekValue
import com.seewo.brick.ktx.*
import com.seewo.brick.params.CornerRadius
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.params.Shadow

private class ShadowPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.ShadowPage()
        }
    }
}

fun Context.ShadowPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    ShowdowControlView()
    ShowMarkDown()
}

private fun LinearLayout.ShowdowControlView() {
    column(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(4.dp, 16.dp),
    ) {
        val cornerRadius = 4.live
        val blur = 10.live
        val offsetX = 5.live
        val offsetY = 5.live
        val color = R.color.primary.color.live
        SeekValue("圆角", 40, cornerRadius) { "$it dp" }
        SeekValue("Blur", 40, blur) { "$it dp" }
        SeekValue("OffsetX", 10, offsetX) { "$it dp" }
        SeekValue("OffsetY", 10, offsetY) { "$it dp" }
        SelectColor(color)
        layoutParams(gravity = Gravity.CENTER, margins = EdgeInsets.only(top = 8.dp)) {
            shadowBox(
                // 设置阴影参数
                shadow = Shadow(
                    offsetX = 5.dp,
                    offsetY = 5.dp,
                    blur = 10.dp,
                    color = R.color.primary.color.withAlpha(0.3f),
                ),
                // 设置整体圆角
                radius = 4.dp,
            ) {
                textView(
                    64.dp, 64.dp,
                    text = "阴影",
                    textSize = 18.dp,
                    gravity = Gravity.CENTER
                )
            }.apply {
                // 不建议动态修改阴影参数，此处仅为了demo展示
                context.inMyLifecycle {
                    cornerRadius.bindNotNull(this) {
                        backgroundRadius = it.dp.toFloat()
                    }
                    blur.bindNotNull(this) {
                        shadowRadius = it.dp.toFloat()
                    }
                    offsetX.bindNotNull(this) {
                        shadowOffsetX = it.dp.toFloat()
                    }
                    offsetY.bindNotNull(this) {
                        shadowOffsetY = it.dp.toFloat()
                    }
                    color.bindNotNull(this) {
                        shadowColor = it.withAlpha(0.3f)
                    }
                }
            }
        }
    }
}

private fun LinearLayout.ColorBlock(
    fillColor: Int,
    color: MutableLiveData<Int>
) = placeholder(
    24.dp, 24.dp,
    background = rectDrawable(
        fillColor = fillColor,
        corners = CornerRadius.all(4.dp)
    )
) {
    color.data = fillColor
}

private fun LinearLayout.SelectColor(color: MutableLiveData<Int>) {
    row(MATCH_PARENT, 24.dp) {
        textView(
            60.dp,
            text = "颜色"
        )
        expand(margins = EdgeInsets.only(start = 4.dp)) {
            row {
                ColorBlock(R.color.primary.color, color)
                layoutParams(margins = EdgeInsets.symmetric(horizontal = 4.dp)) {
                    ColorBlock(Color.RED, color)
                }
                layoutParams(margins = EdgeInsets.symmetric(horizontal = 4.dp)) {
                    ColorBlock(Color.GREEN, color)
                }
                layoutParams(margins = EdgeInsets.symmetric(horizontal = 4.dp)) {
                    ColorBlock(Color.YELLOW, color)
                }
                layoutParams(margins = EdgeInsets.symmetric(horizontal = 4.dp)) {
                    ColorBlock(Color.GRAY, color)
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

shadowBox继承自FrameLayout，提供阴影实现，为开发者提供类似前端box-shadow类似的开发体验
注意：阴影的blur和offset会产生一定的padding
特别注意：Android P以下的机型为了实现阴影，将关闭shadowBox的硬件加速(setLayerType(View.LAYER_TYPE_SOFTWARE, null))，需要关注其带来的副作用，如无法与BrickUI的View裁剪操作同时使用

```kotlin
private fun LinearLayout.ShowdowControlView() {
    column(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(4.dp, 16.dp),
    ) {
        val cornerRadius = 4.live
        val blur = 10.live
        val offsetX = 5.live
        val offsetY = 5.live
        val color = R.color.primary.color.live
        SeekValue("圆角", 40, cornerRadius) { "${'$'}it dp" }
        SeekValue("Blur", 40, blur) { "${'$'}it dp" }
        SeekValue("OffsetX", 10, offsetX) { "${'$'}it dp" }
        SeekValue("OffsetY", 10, offsetY) { "${'$'}it dp" }
        SelectColor(color)
        layoutParams(gravity = Gravity.CENTER, margins = EdgeInsets.only(top = 8.dp)) {
            shadowBox(
                // 设置阴影参数
                shadow = Shadow(
                    offsetX = 5.dp,
                    offsetY = 5.dp,
                    blur = 10.dp,
                    color = R.color.primary.withAlpha(0.3f).color,
                ),
                // 设置整体圆角
                radius = 4.dp,
            ) {
                textView(
                    64.dp, 64.dp,
                    text = "阴影",
                    textSize = 18.dp,
                    gravity = Gravity.CENTER
                )
            }.apply {
                // 不建议动态修改阴影参数，此处仅为了demo展示
                context.inMyLifecycle {
                    cornerRadius.bindNotNull(this) {
                        backgroundRadius = it.dp.toFloat()
                    }
                    blur.bindNotNull(this) {
                        shadowRadius = it.dp.toFloat()
                    }
                    offsetX.bindNotNull(this) {
                        shadowOffsetX = it.dp.toFloat()
                    }
                    offsetY.bindNotNull(this) {
                        shadowOffsetY = it.dp.toFloat()
                    }
                    color.bindNotNull(this) {
                        shadowColor = it.withAlpha(0.3f)
                    }
                }
            }
        }
    }
}

private fun LinearLayout.SeekValue(
    name: String,
    max: Int,
    value: MutableLiveData<Int>,
    valueMapper: (Int) -> String
) {
    row(MATCH_PARENT, 24.dp) {
        textView(
            60.dp,
            text = name
        )
        expand {
            liveSeekBar(
                MATCH_PARENT, 12.dp,
                thumb = ovalDrawable(
                    width = 12.dp,
                    height = 12.dp,
                    strokeWidth = 1.dp,
                    strokeColor = R.color.primary.color,
                    fillColor = Color.WHITE,
                ),
                thumbOffset = 2.dp,
                progressBackground = rectDrawable(
                    fillColor = R.color.grey_e4.color,
                    corners = CornerRadius.all(60.dp),
                ),
                progressDrawable = rectDrawable(
                    fillColor = R.color.primary.color,
                    corners = CornerRadius.all(60.dp),
                ),
                progress = value,
                max = max,
                padding = EdgeInsets.symmetric(vertical = 4.dp, horizontal = 2.dp)
            )
        }
        liveText(
            40.dp,
            text = value.map(valueMapper),
            gravity = Gravity.END,
        )
    }
}

private fun LinearLayout.ColorBlock(
    fillColor: Int,
    color: MutableLiveData<Int>
) = placeholder(
    24.dp, 24.dp,
    background = rectDrawable(
        fillColor = fillColor,
        corners = CornerRadius.all(4.dp)
    )
) {
    color.data = fillColor
}

private fun LinearLayout.SelectColor(color: MutableLiveData<Int>) {
    row(MATCH_PARENT, 24.dp) {
        textView(
            60.dp,
            text = "颜色"
        )
        expand(margins = EdgeInsets.only(start = 4.dp)) {
            row {
                ColorBlock(R.color.primary.color, color)
                layoutParams(margins = EdgeInsets.symmetric(horizontal = 4.dp)) {
                    ColorBlock(Color.RED, color)
                }
                layoutParams(margins = EdgeInsets.symmetric(horizontal = 4.dp)) {
                    ColorBlock(Color.GREEN, color)
                }
                layoutParams(margins = EdgeInsets.symmetric(horizontal = 4.dp)) {
                    ColorBlock(Color.YELLOW, color)
                }
                layoutParams(margins = EdgeInsets.symmetric(horizontal = 4.dp)) {
                    ColorBlock(Color.GRAY, color)
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
