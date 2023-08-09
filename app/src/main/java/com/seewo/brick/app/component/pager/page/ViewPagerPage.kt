package com.seewo.brick.app.component.pager.page

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.center
import com.seewo.brick.ktx.color
import com.seewo.brick.ktx.colorDrawable
import com.seewo.brick.ktx.column
import com.seewo.brick.ktx.data
import com.seewo.brick.ktx.divider
import com.seewo.brick.ktx.dp
import com.seewo.brick.ktx.expand
import com.seewo.brick.ktx.frameLayout
import com.seewo.brick.ktx.layoutParams
import com.seewo.brick.ktx.live
import com.seewo.brick.ktx.liveText
import com.seewo.brick.ktx.liveViewPager
import com.seewo.brick.ktx.map
import com.seewo.brick.ktx.row
import com.seewo.brick.ktx.stateListColor
import com.seewo.brick.ktx.static
import com.seewo.brick.ktx.textView
import com.seewo.brick.ktx.view

private class ViewPagerPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.ViewPagerPage()
        }
    }
}

fun Context.ViewPagerPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    ViewPager()
    ShowMarkDown()
}

private fun LinearLayout.ViewPager() {
    val currentIndex = 0.live
    column(MATCH_PARENT, 140.dp) {
        expand {
            // 构造方式与列表类似
            liveViewPager(
                MATCH_PARENT, 0,
                data = listOf(1, 2, 3).static,
                // 页码双向绑定
                currentIndex = currentIndex,
            ) { data, index ->
                frameLayout(
                    MATCH_PARENT, MATCH_PARENT,
                    background = when (index) {
                        0 -> R.color.purple_200.colorDrawable
                        1 -> R.color.purple_500.colorDrawable
                        else -> R.color.purple_700.colorDrawable
                    },
                ) {
                    center {
                        textView(
                            text = data[index].toString(),
                            textColor = Color.WHITE,
                            textSize = 22.dp,
                        )
                    }
                }

            }
        }
    }
    // 底部切页按钮
    row(
        MATCH_PARENT, 40.dp
    ) {
        repeat(3) { selectedIndex ->
            layoutParams(
                weight = 1f, gravity = Gravity.CENTER
            ) {
                liveText(
                    0, MATCH_PARENT,
                    textAlignment = View.TEXT_ALIGNMENT_CENTER,
                    text = "第${selectedIndex + 1}页".static,
                    textColorList = stateListColor(mapOf(
                        intArrayOf(android.R.attr.state_selected) to R.color.primary.color,
                        intArrayOf(-android.R.attr.state_selected) to Color.GRAY,
                    )),
                    isSelected = currentIndex.map { selectedIndex == it },
                    gravity = Gravity.CENTER,
                ) {
                    // 点击切页
                    currentIndex.data = selectedIndex
                }
            }
        }
    }
    divider(background = R.color.black.colorDrawable)
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

每个分页通过View实现

```kotlin
private fun LinearLayout.ViewPager() {
    val currentIndex = 0.live
    column(MATCH_PARENT, 140.dp) {
        layoutParams(weight = 1f) {
            // 构造方式与列表类似
            liveViewPager(
                MATCH_PARENT, 0,
                data = listOf(1, 2, 3).static,
                // 页码双向绑定
                currentIndex = currentIndex,
            ) { data, index ->
                frameLayout(
                    MATCH_PARENT, MATCH_PARENT,
                    background = when (index) {
                        0 -> R.color.purple_200.colorDrawable
                        1 -> R.color.purple_500.colorDrawable
                        else -> R.color.purple_700.colorDrawable
                    },
                ) {
                    center {
                        textView(
                            text = data[index].toString(),
                            textColor = Color.WHITE,
                            textSize = 22.dp,
                        )
                    }
                }

            }
        }
    }
    // 底部切页按钮
    row(
        MATCH_PARENT, 40.dp
    ) {
        repeat(3) { selectedIndex ->
            layoutParams(
                weight = 1f, gravity = Gravity.CENTER
            ) {
                liveText(
                    0, MATCH_PARENT,
                    textAlignment = View.TEXT_ALIGNMENT_CENTER,
                    text = "第${'$'}{selectedIndex + 1}页".static,
                    textColorList = stateListColor(mapOf(
                        intArrayOf(android.R.attr.state_selected) to R.color.primary.color,
                        intArrayOf(-android.R.attr.state_selected) to Color.GRAY,
                    )),
                    isSelected = currentIndex.map { selectedIndex == it },
                    gravity = Gravity.CENTER,
                ) {
                    // 点击切页
                    currentIndex.data = selectedIndex
                }
            }
        }
    }
    divider(background = R.color.black.colorDrawable)
}
```
                """.trimIndent()
        )
    }
}
