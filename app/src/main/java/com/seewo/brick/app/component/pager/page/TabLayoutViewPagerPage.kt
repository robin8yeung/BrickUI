package com.seewo.brick.app.component.pager.page

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.color
import com.seewo.brick.ktx.colorDrawable
import com.seewo.brick.ktx.column
import com.seewo.brick.ktx.dp
import com.seewo.brick.ktx.expand
import com.seewo.brick.ktx.frameLayout
import com.seewo.brick.ktx.layerDrawable
import com.seewo.brick.ktx.layoutParams
import com.seewo.brick.ktx.rectDrawable
import com.seewo.brick.ktx.stateListColor
import com.seewo.brick.ktx.tabLayout
import com.seewo.brick.ktx.tabLayoutMediator
import com.seewo.brick.ktx.textView
import com.seewo.brick.ktx.view
import com.seewo.brick.ktx.viewPager
import com.seewo.brick.params.CornerRadius

private class TabLayoutViewPagerPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.TabLayoutViewPagerPage()
        }
    }
}

fun Context.TabLayoutViewPagerPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    ViewPager()
    ShowMarkDown()
}

private fun LinearLayout.ViewPager() {
    val items = listOf(
        "第一页", "第二页"
    )
    column(MATCH_PARENT, 140.dp) {
        // 即TabLayoutMediator，用于绑定TabLayout的选项和ViewPager
        tabLayoutMediator {
            // 构造方式与列表类似
            tabLayout(
                MATCH_PARENT, 40.dp,
                data = items,
                // 选中指示器样式（sdk 22以上才有效）
                selectedTabIndicator = layerDrawable(
                    arrayOf(
                        rectDrawable(
                            fillColor = R.color.primary.color,
                            corners = CornerRadius.all(1.5.dp),
                        )
                    ),
                    layerWidth = 30.dp,
                    layerHeight = 2.dp,
                    layerGravity = Gravity.CENTER_HORIZONTAL,
                ),
                tabIndicatorColor = R.color.primary.color,
                tabMode = TabLayout.MODE_SCROLLABLE,
                onTabSelected = {
                    // 此处不要使用setTypeface来加粗，会导致Indicator动画异常
                    (it.customView as? TextView)?.paint?.isFakeBoldText = true
                },
                onTabUnselected = {
                    (it.customView as? TextView)?.paint?.isFakeBoldText = false
                }
            ) { _, item ->
                textView(
                    text = item,
                    textSize = 18.dp,
                    textColorList = stateListColor(
                        mapOf(
                            intArrayOf(-android.R.attr.state_selected) to Color.DKGRAY,
                            intArrayOf(android.R.attr.state_selected) to R.color.primary.color,
                        )
                    )
                )
            }
            expand {
                // 构造方式与列表类似
                viewPager(
                    MATCH_PARENT, 0,
                    data = items,
                    overScrollMode = View.OVER_SCROLL_NEVER,
                ) { data, index ->
                    frameLayout(
                        MATCH_PARENT, MATCH_PARENT,
                        background = when (index) {
                            0 -> R.color.purple_200.colorDrawable
                            1 -> R.color.purple_500.colorDrawable
                            else -> R.color.purple_700.colorDrawable
                        },
                    ) {
                        layoutParams(
                            gravity = Gravity.CENTER
                        ) {
                            textView(
                                text = data[index],
                                textColor = Color.WHITE,
                                textSize = 22.dp,
                            )
                        }
                    }

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

每个分页通过View实现，并且绑定顶部TabLayout的选项

```kotlin
private fun LinearLayout.ViewPager() {
    val items = listOf(
        "第一页", "第二页"
    )
    column(MATCH_PARENT, 140.dp) {
        // 即TabLayoutMediator，用于绑定TabLayout的选项和ViewPager
        tabLayoutMediator {
            // 构造方式与列表类似
            tabLayout(
                MATCH_PARENT, 40.dp,
                data = items,
                // 选中指示器样式（sdk 22以上才有效）
                selectedTabIndicator = layerDrawable(
                    arrayOf(
                        rectDrawable(
                            fillColor = R.color.primary.color,
                            corners = CornerRadius.all(1.5.dp),
                        )
                    ),
                    layerWidth = 30.dp,
                    layerHeight = 2.dp,
                    layerGravity = Gravity.CENTER_HORIZONTAL,
                ),
                tabIndicatorColor = R.color.primary.color,
                tabMode = TabLayout.MODE_SCROLLABLE,
                onTabSelected = {
                    // 此处不要使用setTypeface来加粗，会导致Indicator动画异常
                    (it.customView as? TextView)?.paint?.isFakeBoldText = true
                },
                onTabUnselected = {
                    (it.customView as? TextView)?.paint?.isFakeBoldText = false
                }
            ) { _, item ->
                textView(
                    text = item,
                    textSize = 18.dp,
                    textColorList = stateListColor(
                        mapOf(
                            intArrayOf(-android.R.attr.state_selected) to Color.DKGRAY,
                            intArrayOf(android.R.attr.state_selected) to R.color.primary.color,
                        )
                    )
                )
            }
            expand {
                // 构造方式与列表类似
                viewPager(
                    MATCH_PARENT, 0,
                    data = items,
                    overScrollMode = View.OVER_SCROLL_NEVER,
                ) { data, index ->
                    frameLayout(
                        MATCH_PARENT, MATCH_PARENT,
                        background = when (index) {
                            0 -> R.color.purple_200.colorDrawable
                            1 -> R.color.purple_500.colorDrawable
                            else -> R.color.purple_700.colorDrawable
                        },
                    ) {
                        layoutParams(
                            gravity = Gravity.CENTER
                        ) {
                            textView(
                                text = data[index],
                                textColor = Color.WHITE,
                                textSize = 22.dp,
                            )
                        }
                    }

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
