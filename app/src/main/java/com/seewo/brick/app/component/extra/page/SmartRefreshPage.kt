package com.seewo.brick.app.component.extra.page

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.WRAP_CONTENT
import com.seewo.brick.ktx.colorDrawable
import com.seewo.brick.ktx.column
import com.seewo.brick.ktx.dp
import com.seewo.brick.ktx.drawable
import com.seewo.brick.ktx.expand
import com.seewo.brick.ktx.imageView
import com.seewo.brick.ktx.roundRectClip
import com.seewo.brick.ktx.smartRefresh
import com.seewo.brick.ktx.textView
import com.seewo.brick.ktx.view
import com.seewo.brick.params.EdgeInsets
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private class SmartRefreshPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.SmartRefreshPage()
        }
    }
}

fun Context.SmartRefreshPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    RefreshLayout()
    ShowMarkDown()
}

private fun LinearLayout.RefreshLayout() {
    // 依赖SmartRefreshLayout，可以轻松实现下拉刷新，上拉加载更多，轻松定义Header，Footer
    smartRefresh(
        MATCH_PARENT, WRAP_CONTENT,
        refreshHeader = ClassicsHeader(context)
            .setDrawableSize(12f)
            .setDrawableMarginRight(8f)
            .setTextSizeTitle(TypedValue.COMPLEX_UNIT_DIP, 12f)
            .setAccentColor(Color.parseColor("#AAAAAA"))
            .setEnableLastTime(false),
        refreshFooter = ClassicsFooter(context)
            .setDrawableSize(12f)
            .setDrawableMarginRight(8f)
            .setTextSizeTitle(TypedValue.COMPLEX_UNIT_DIP, 12f)
            .setAccentColor(Color.parseColor("#AAAAAA")),
        onRefresh = {
            MainScope().launch {
                // 模拟刷新耗时
                delay(1000)
                it.finishRefresh(true)
            }
        },
        onLoadMore = {
            MainScope().launch {
                // 模拟加载更多耗时
                delay(1000)
                it.finishLoadMore(true)
            }
        },
        autoLoadMore = false,
        padding = EdgeInsets.symmetric(horizontal = 16.dp),
        // 颜色需要作为背景时，需要通过colorDrawable转成Drawable
        background = R.color.grey_e4.colorDrawable,
    ) {
        column(
            MATCH_PARENT, MATCH_PARENT,
            gravity = Gravity.CENTER_HORIZONTAL,
        ) {
            roundRectClip(radius = 4.dp) {
                imageView(
                    64.dp, 64.dp,
                    drawable = R.mipmap.ic_launcher.drawable,
                    margin = EdgeInsets.only(top = 16.dp, bottom = 8.dp),
                )
            }

            textView(
                text = "BrickUI",
                textSize = 24.dp,
                textStyle = Typeface.BOLD,
                padding = EdgeInsets.all(8.dp)
            )
            textView(
                text = "试试在灰色区域下拉刷新吧~",
                textSize = 18.dp,
                textColor = Color.RED,
                padding = EdgeInsets.all(8.dp),
            )
        }
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

依赖SmartRefreshLayout，可以轻松实现下拉刷新，上拉加载更多，自定义Header，Footer

```kotlin
private fun LinearLayout.RefreshLayout() {
    smartRefresh(
        MATCH_PARENT, WRAP_CONTENT,
        refreshHeader = ClassicsHeader(context)
            .setDrawableSize(12f)
            .setDrawableMarginRight(8f)
            .setTextSizeTitle(TypedValue.COMPLEX_UNIT_DIP, 12f)
            .setAccentColor(Color.parseColor("#AAAAAA"))
            .setEnableLastTime(false),
        refreshFooter = ClassicsFooter(context)
            .setDrawableSize(12f)
            .setDrawableMarginRight(8f)
            .setTextSizeTitle(TypedValue.COMPLEX_UNIT_DIP, 12f)
            .setAccentColor(Color.parseColor("#AAAAAA")),
        onRefresh = {
            MainScope().launch {
                // 模拟刷新耗时
                delay(1000)
                it.finishRefresh(true)
            }
        },
        onLoadMore = {
            MainScope().launch {
                // 模拟加载更多耗时
                delay(1000)
                it.finishLoadMore(true)
            }
        },
        padding = EdgeInsets.symmetric(horizontal = 16.dp),
        // 颜色需要作为背景时，需要通过colorDrawable转成Drawable
        background = R.color.grey_e4.colorDrawable,
    ) {
        column(
            MATCH_PARENT, MATCH_PARENT,
            gravity = Gravity.CENTER_HORIZONTAL,
        ) {
            roundRectClip(radius = 4.dp) {
                imageView(
                    64.dp, 64.dp,
                    drawable = R.mipmap.ic_launcher.drawable,
                    margin = EdgeInsets.only(top = 16.dp, bottom = 8.dp),
                )
            }

            textView(
                text = "BrickUI",
                textSize = 24.dp,
                textStyle = Typeface.BOLD,
                padding = EdgeInsets.all(8.dp)
            )
            textView(
                text = "试试在灰色区域下拉刷新吧~",
                textSize = 18.dp,
                textColor = Color.RED,
                padding = EdgeInsets.all(8.dp),
            )
        }
    }
}
```
                """.trimIndent()
        )
    }
}
