package com.seewo.brick.app.component.pager.page


import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.column
import com.seewo.brick.ktx.dp
import com.seewo.brick.ktx.drawable
import com.seewo.brick.ktx.expand
import com.seewo.brick.ktx.frameLayout
import com.seewo.brick.ktx.imageView
import com.seewo.brick.ktx.indicator
import com.seewo.brick.ktx.layoutParams
import com.seewo.brick.ktx.loopPager
import com.seewo.brick.ktx.view
import kotlin.time.Duration.Companion.seconds

private class LoopViewPagerPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.LoopViewPagerPage()
        }
    }
}

fun Context.LoopViewPagerPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    LoopViewPager()
    ShowMarkDown()
}

private fun LinearLayout.LoopViewPager() {
    frameLayout(
        MATCH_PARENT, 200.dp,
    ) {
        val viewPager = loopPager(
            MATCH_PARENT, MATCH_PARENT,
            data = listOf(
                R.drawable.page1,
                R.drawable.page2,
                R.drawable.page3,
            ),
            duration = 1.seconds,
            scrollDuration = 300,
            onPageSelected = {
                Log.i("BrickUI", "onPageSelected: $it")
            }
        ) { data, position ->
            imageView(
                MATCH_PARENT, MATCH_PARENT,
                scaleType = ImageView.ScaleType.CENTER_CROP,
                drawable = data[position].drawable,
            )
        }
        layoutParams(gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM) {
            indicator(
                MATCH_PARENT, 48.dp,
                viewPager,
            )
        }
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

轮播图以1秒间隔翻页

```kotlin
private fun LinearLayout.LoopViewPager() {
    frameLayout(
        MATCH_PARENT, 200.dp,
    ) {
        val viewPager = loopPager(
            MATCH_PARENT, MATCH_PARENT,
            data = listOf(
                R.drawable.page1,
                R.drawable.page2,
                R.drawable.page3,
            ),
            duration = 1.seconds,
            onPageSelected = {
                Log.i("BrickUI", "onPageSelected: ${'$'}it")
            }
        ) { data, position ->
            imageView(
                MATCH_PARENT, MATCH_PARENT,
                scaleType = ImageView.ScaleType.CENTER_CROP,
                drawable = data[position].drawable,
            )
        }
        layoutParams(gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM) {
            indicator(
                MATCH_PARENT, 48.dp,
                viewPager,
            )
        }
    }
}
```
                """.trimIndent()
        )
    }
}
