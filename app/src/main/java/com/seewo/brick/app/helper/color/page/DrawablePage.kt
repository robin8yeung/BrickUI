package com.seewo.brick.app.helper.color.page

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.google.android.flexbox.AlignItems
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.*
import com.seewo.brick.params.CornerRadius
import com.seewo.brick.params.EdgeInsets

private class DrawablePage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.DrawablePage()
        }
    }
}

fun Context.DrawablePage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    DrawableDemo()
    ShowMarkDown()
}

private fun LinearLayout.DrawableDemo() {
    flexboxLayout(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(4.dp, 16.dp),
        alignItems = AlignItems.STRETCH,
    ) {
        margins(
            margins = EdgeInsets.all(4.dp)
        ) {
            // colorDrawable扩展属性帮助开发者快速实现颜色资源转Drawable
            // 此外COLOR_DRAWABLE扩展属性帮助开发者快速实现颜色资源转Drawable后转为静态LiveData
            imageView(
                64.dp, 64.dp,
                drawable = R.color.primary.colorDrawable,
            )
        }
        margins(
            margins = EdgeInsets.all(4.dp)
        ) {
            // drawable扩展属性帮助开发者快速实现drawable资源拿到Drawable
            imageView(
                64.dp, 64.dp,
                drawable = R.mipmap.ic_launcher.drawable
            )
        }
        margins(
            margins = EdgeInsets.all(4.dp)
        ) {
            // 对drawable进行圆形裁剪
            imageView(
                64.dp, 64.dp,
                drawable = R.mipmap.ic_launcher.drawable?.clipOval
            )
        }
        margins(
            margins = EdgeInsets.all(4.dp)
        ) {
            // 对drawable进行圆角矩形裁剪
            imageView(
                64.dp, 64.dp,
                drawable = R.mipmap.ic_launcher.drawable?.clipRect(8.dp)
            )
        }
        margins(
            margins = EdgeInsets.all(4.dp)
        ) {
            imageView(
                64.dp, 64.dp,
                // ovalDrawable函数帮助开发者快速构建圆形Drawable
                drawable = ovalDrawable(
                    fillColor = Color.RED,
                    strokeColor = R.color.primary.color,
                    strokeWidth = 1.dp,
                )
            )
        }
        margins(
            margins = EdgeInsets.all(4.dp)
        ) {
            imageView(
                64.dp, 64.dp,
                // rectDrawable函数帮助开发者快速构建可设置圆角的矩形Drawable
                drawable = rectDrawable(
                    fillColor = Color.GREEN,
                    strokeColor = R.color.primary.color,
                    strokeWidth = 3.dp,
                    corners = CornerRadius.only(leftTop = 8.dp, leftBottom = 8.dp)
                )
            )
        }
        margins(
            margins = EdgeInsets.all(4.dp)
        ) {
            imageView(
                64.dp, 64.dp,
                // shapeDrawable函数帮助开发者快速构建可设置形状、渐变的Drawable
                drawable = shapeDrawable(
                    width = 4.dp,
                    strokeColor = R.color.primary.color,
                    strokeWidth = 1.dp,
                    corners = CornerRadius.all(4.dp),
                    colors = intArrayOf(Color.RED, Color.WHITE),
                    orientation = GradientDrawable.Orientation.LEFT_RIGHT,
                    shape = GradientDrawable.RECTANGLE,
                )
            )
        }
        margins(
            margins = EdgeInsets.all(4.dp)
        ) {
            textView(
                text = "点我试试",
                gravity = Gravity.CENTER,
                padding = EdgeInsets.symmetric(horizontal = 8.dp),
                // stateListDrawable函数帮助开发者快速构建跟随View状态改变的Drawable
                background = stateListDrawable(mapOf(
                    intArrayOf(android.R.attr.state_pressed) to rectDrawable(
                        fillColor = Color.RED,
                        corners = CornerRadius.all(8.dp)
                    ),
                    intArrayOf(-android.R.attr.state_pressed) to rectDrawable(
                        fillColor = Color.GREEN,
                        corners =CornerRadius.all(8.dp),
                    )
                ))
            ) {
                // onClick here
            }
        }
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

BrickUI提供了一些帮助开发者快速构建drawable的函数或扩展属性

```kotlin
private fun LinearLayout.DrawableDemo() {
    flexboxLayout(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(4.dp, 16.dp),
    ) {
        layoutParams(
            margins = EdgeInsets.all(4.dp)
        ) {
            // colorDrawable扩展属性帮助开发者快速实现颜色资源转Drawable
            // 此外COLOR_DRAWABLE扩展属性帮助开发者快速实现颜色资源转Drawable后转为静态LiveData
            imageView(
                64.dp, 64.dp,
                drawable = R.color.primary.colorDrawable,
            )
        }

        layoutParams(
            margins = EdgeInsets.all(4.dp)
        ) {
            // drawable扩展属性帮助开发者快速实现drawable资源拿到Drawable
            imageView(
                64.dp, 64.dp,
                drawable = R.mipmap.ic_launcher.drawable
            )
        }
        margins(
            margins = EdgeInsets.all(4.dp)
        ) {
            // 对drawable进行圆形裁剪
            imageView(
                64.dp, 64.dp,
                drawable = R.mipmap.ic_launcher.drawable?.ovalClip
            )
        }
        margins(
            margins = EdgeInsets.all(4.dp)
        ) {
            // 对drawable进行圆角矩形裁剪
            imageView(
                64.dp, 64.dp,
                drawable = R.mipmap.ic_launcher.drawable?.rectClip(8.dp)
            )
        }
        layoutParams(
            margins = EdgeInsets.all(4.dp)
        ) {
            imageView(
                64.dp, 64.dp,
                // ovalDrawable函数帮助开发者快速构建圆形Drawable
                drawable = ovalDrawable(
                    fillColor = Color.RED,
                    strokeColor = R.color.primary.color,
                    strokeWidth = 1.dp,
                )
            )
        }
        layoutParams(
            margins = EdgeInsets.all(4.dp)
        ) {
            imageView(
                64.dp, 64.dp,
                // rectDrawable函数帮助开发者快速构建可设置圆角的矩形Drawable
                drawable = rectDrawable(
                    fillColor = Color.GREEN,
                    strokeColor = R.color.primary.color,
                    strokeWidth = 3.dp,
                    corners = CornerRadius.only(leftTop = 8.dp, leftBottom = 8.dp)
                )
            )
        }
        layoutParams(
            margins = EdgeInsets.all(4.dp)
        ) {
            imageView(
                64.dp, 64.dp,
                // shapeDrawable函数帮助开发者快速构建可设置形状、渐变的Drawable
                drawable = shapeDrawable(
                    width = 4.dp,
                    strokeColor = R.color.primary.color,
                    strokeWidth = 1.dp,
                    corners = CornerRadius.all(4.dp),
                    colors = intArrayOf(Color.RED, Color.WHITE),
                    orientation = GradientDrawable.Orientation.LEFT_RIGHT,
                    shape = GradientDrawable.RECTANGLE,
                )
            )
        }
        layoutParams(
            margins = EdgeInsets.all(4.dp)
        ) {
            textView(
                text = "点我试试",
                gravity = Gravity.CENTER,
                padding = EdgeInsets.symmetric(horizontal = 8.dp),
                // stateListDrawable函数帮助开发者快速构建跟随View状态改变的Drawable
                background = stateListDrawable(mapOf(
                    intArrayOf(android.R.attr.state_pressed) to rectDrawable(
                        fillColor = Color.RED,
                        corners = CornerRadius.all(4.dp)
                    ),
                    intArrayOf(-android.R.attr.state_pressed) to rectDrawable(
                        fillColor = Color.GREEN,
                        corners =CornerRadius.all(4.dp),
                    )
                ))
            ) {
                // onClick here
            }
        }
    }
}
```
                """.trimIndent()
        )
    }
}
