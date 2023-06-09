package com.seewo.brick.app.component.coordinator

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.appBarLayout
import com.seewo.brick.ktx.color
import com.seewo.brick.ktx.column
import com.seewo.brick.ktx.coordinatorLayout
import com.seewo.brick.ktx.dp
import com.seewo.brick.ktx.expand
import com.seewo.brick.ktx.layerDrawable
import com.seewo.brick.ktx.layoutParams
import com.seewo.brick.ktx.nestedScrollView
import com.seewo.brick.ktx.rectDrawable
import com.seewo.brick.ktx.row
import com.seewo.brick.ktx.scrollingView
import com.seewo.brick.ktx.setStatusBarTransparent
import com.seewo.brick.ktx.string
import com.seewo.brick.ktx.tabLayout
import com.seewo.brick.ktx.tabLayoutMediator
import com.seewo.brick.ktx.textView
import com.seewo.brick.ktx.viewPager
import com.seewo.brick.params.CornerRadius
import com.seewo.brick.params.EdgeInsets

class CoordinatorLayoutActivity1 : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setStatusBarTransparent(true)
            setContentView(column(
                MATCH_PARENT, MATCH_PARENT, fitsSystemWindows = true
            ) {
                val tabs = listOf("推荐", "最新")
                CoordinatorPage(tabs)
                ShowMarkDown()
            })
        }

    private fun LinearLayout.CoordinatorPage(tabs: List<String>) =
        coordinatorLayout(MATCH_PARENT, 350.dp) {
            // 绑定Tab和ViewPager
            tabLayoutMediator(
                tabLayoutId = 0x123,
                viewPagerId = 0x456,
            ) {
                appBarLayout(
                    onOffsetChanged =  { _, verticalOffset ->
                        Log.d("brick", "verticalOffset: $verticalOffset")
                    }
                ) {
                    layoutParams(
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP,
                    ) {
                        column(
                            MATCH_PARENT,
                            gravity = Gravity.CENTER,
                        ) {
                            row(
                                MATCH_PARENT,
                                padding = EdgeInsets.symmetric(horizontal = 16.dp, vertical = 8.dp),
                            ) {
                                textView(
                                    text = "下方Tab将会吸顶",
                                    textSize = 18.dp,
                                    textStyle = Typeface.BOLD,
                                    textColor = R.color.primary.color,
                                )
                            }
                        }
                    }
                    layoutParams(
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
                    ) {
                        tabLayout(
                            MATCH_PARENT, 40.dp,
                            id = 0x123,
                            data = tabs,
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
                                (it.customView as? TextView)?.run {
                                    paint?.isFakeBoldText = true
                                    invalidate()
                                }
                            },
                            onTabUnselected = {
                                (it.customView as? TextView)?.run {
                                    paint?.isFakeBoldText = false
                                    invalidate()
                                }
                            }
                        ) { _, item ->
                            textView(
                                text = item,
                                textSize = 18.dp,
                            )
                        }
                    }
                }
                scrollingView {
                    viewPager(
                        MATCH_PARENT, MATCH_PARENT,
                        id = 0x456,
                        data = tabs,
                        overScrollMode = ViewPager2.OVER_SCROLL_NEVER
                    ) { _, _ ->
                        nestedScrollView {
                            textView(
                                padding = EdgeInsets.symmetric(4.dp, 16.dp),
                                text = R.string.large_text.string,
                            )
                        }
                    }
                }
            }

        }
}

private fun LinearLayout.ShowMarkDown() = expand {
    Markdown(
        """
## 代码展示

通过 CollapsingToolbarLayout 来实现Tab吸顶效果

```kotlin
private fun LinearLayout.CoordinatorPage(tabs: List<String>) =
        coordinatorLayout(MATCH_PARENT, 350.dp) {
            // 绑定Tab和ViewPager
            tabLayoutMediator(
                tabLayoutId = 0x123,
                viewPagerId = 0x456,
            ) {
                appBarLayout {
                    layoutParams(
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP,
                    ) {
                        column(
                            MATCH_PARENT,
                            gravity = Gravity.CENTER,
                        ) {
                            row(
                                MATCH_PARENT,
                                padding = EdgeInsets.symmetric(horizontal = 16.dp, vertical = 8.dp),
                            ) {
                                textView(
                                    text = "下方Tab将会吸顶",
                                    textSize = 18.dp,
                                    textStyle = Typeface.BOLD,
                                    textColor = R.color.primary.color,
                                )
                            }
                        }
                    }
                    layoutParams(
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
                    ) {
                        tabLayout(
                            MATCH_PARENT, 40.dp,
                            id = 0x123,
                            data = tabs,
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
                                (it.customView as? TextView)?.run {
                                    paint?.isFakeBoldText = true
                                    invalidate()
                                }
                            },
                            onTabUnselected = {
                                (it.customView as? TextView)?.run {
                                    paint?.isFakeBoldText = false
                                    invalidate()
                                }
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
                    }
                }.apply {
                    addOnOffsetChangedListener { _, verticalOffset ->
                        Log.e("brick", "verticalOffset: ${'$'}verticalOffset")
                    }
                }
                scrollingView {
                    viewPager(
                        MATCH_PARENT, MATCH_PARENT,
                        id = 0x456,
                        data = tabs,
                        overScrollMode = ViewPager2.OVER_SCROLL_NEVER
                    ) { _, _ ->
                        nestedScrollView {
                            textView(
                                padding = EdgeInsets.symmetric(4.dp, 16.dp),
                                text = R.string.large_text.string,
                            )
                        }
                    }
                }
            }

        }
```
                """.trimIndent()
    )
}
