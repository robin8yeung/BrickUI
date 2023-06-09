package com.seewo.brick.app.component.coordinator

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.TitleStyle
import com.seewo.brick.ktx.ToolbarNavigation
import com.seewo.brick.ktx.WRAP_CONTENT
import com.seewo.brick.ktx.appBarLayout
import com.seewo.brick.ktx.collapsingToolbarLayout
import com.seewo.brick.ktx.color
import com.seewo.brick.ktx.colorDrawable
import com.seewo.brick.ktx.column
import com.seewo.brick.ktx.coordinatorLayout
import com.seewo.brick.ktx.dp
import com.seewo.brick.ktx.drawable
import com.seewo.brick.ktx.expand
import com.seewo.brick.ktx.imageView
import com.seewo.brick.ktx.layoutParams
import com.seewo.brick.ktx.nestedScrollView
import com.seewo.brick.ktx.scrollingView
import com.seewo.brick.ktx.setSystemBarTransparent
import com.seewo.brick.ktx.string
import com.seewo.brick.ktx.textView
import com.seewo.brick.ktx.toolbar
import com.seewo.brick.params.CollapseMode
import com.seewo.brick.params.EdgeInsets

class CoordinatorLayoutActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSystemBarTransparent(false)
        setContentView(column(
            MATCH_PARENT, MATCH_PARENT,
            background = R.color.purple_200.colorDrawable,
        ) {
            CoordinatorPage()
            ShowMarkDown()
        })
    }

    private fun LinearLayout.CoordinatorPage() =
        coordinatorLayout(MATCH_PARENT, 350.dp) {
            appBarLayout(
                MATCH_PARENT, WRAP_CONTENT,
                fitsSystemWindows = true,
                onOffsetChanged = { _, verticalOffset ->
                    Log.d("brick", "verticalOffset: $verticalOffset")
                },
            ) {
                layoutParams(
                    scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP,
                ) {
                    collapsingToolbarLayout(
                        height = 180.dp,
                        contentScrim = R.color.purple_200.colorDrawable,
                        statusBarScrim = R.color.purple_200.colorDrawable,
                        scrimsShownAnim = false,
                        collapsedTitleStyle = TitleStyle(textColor = R.color.white.color),
                        fitsSystemWindows = true,
                    ) {
                        layoutParams(
                            collapseMode = CollapseMode.COLLAPSE_MODE_PARALLAX,
                            parallaxMultiplier = 0.5f
                        ) {
                            imageView(
                                MATCH_PARENT,
                                drawable = R.mipmap.ic_launcher.drawable,
                                scaleType = ImageView.ScaleType.CENTER_CROP,
                                fitsSystemWindows = true,
                            )
                        }
                        layoutParams(
                            collapseMode = CollapseMode.COLLAPSE_MODE_PIN,
                        ) {
                            toolbar(
                                MATCH_PARENT, 56.dp,
                                title = "BrickUI",
                                subTitle = "基于View体系的声明式UI",
                                navigation = ToolbarNavigation(R.drawable.ic_back_white.drawable) {
                                  finish()
                                },
                            )
                        }
                    }
                }
                textView(
                    MATCH_PARENT,
                    text = "吸顶展示Tab之类",
                    padding = EdgeInsets.symmetric(8.dp, 16.dp),
                    textSize = 18.dp,
                    textColor = R.color.white.color,
                    background = R.color.teal_200.colorDrawable,
                )
            }
            scrollingView {
                nestedScrollView {
                    textView(
                        padding = EdgeInsets.symmetric(8.dp, 16.dp),
                        text = R.string.large_text.string,
                        textColor = R.color.white.color,
                        textSize = 12.dp,
                    )
                }
            }
        }
}


private fun LinearLayout.ShowMarkDown() = expand(margins = EdgeInsets.only(top = 16.dp)) {
    Markdown(
        """
## 代码展示

通过 CollapsingToolbarLayout 来实现图片的时差折叠和Toolbar的折叠效果

```kotlin
private fun LinearLayout.CoordinatorPage() =
        coordinatorLayout(MATCH_PARENT, 350.dp) {
            appBarLayout(
                MATCH_PARENT, WRAP_CONTENT,
                fitsSystemWindows = true,
                onOffsetChanged = { _, verticalOffset ->
                    Log.d("brick", "verticalOffset: ${'$'}verticalOffset")
                },
            ) {
                layoutParams(
                    scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP,
                ) {
                    collapsingToolbarLayout(
                        height = 180.dp,
                        title = "BrickUI",
                        contentScrim = R.color.purple_200.colorDrawable,
                        statusBarScrim = R.color.purple_200.colorDrawable,
                        scrimsShownAnim = false,
                        collapsedTitleStyle = TitleStyle(textColor = R.color.white.color),
                        fitsSystemWindows = true,
                    ) {
                        layoutParams(
                            collapseMode = CollapseMode.COLLAPSE_MODE_PARALLAX,
                            parallaxMultiplier = 0.5f
                        ) {
                            imageView(
                                MATCH_PARENT,
                                drawable = R.mipmap.ic_launcher.drawable,
                                scaleType = ImageView.ScaleType.CENTER_CROP,
                                fitsSystemWindows = true,
                            )
                        }
                        layoutParams(
                            collapseMode = CollapseMode.COLLAPSE_MODE_PIN,
                        ) {
                            toolbar(MATCH_PARENT, 44.dp)
                        }
                    }
                }
                textView(
                    MATCH_PARENT,
                    text = "吸顶展示Tab之类",
                    padding = EdgeInsets.symmetric(8.dp, 16.dp),
                    textSize = 18.dp,
                    textColor = R.color.white.color,
                    background = R.color.teal_200.colorDrawable,
                )
            }
            scrollingView {
                nestedScrollView {
                    textView(
                        padding = EdgeInsets.symmetric(8.dp, 16.dp),
                        text = R.string.large_text.string,
                        textColor = R.color.white.color,
                        textSize = 12.dp,
                    )
                }
            }
        }
```
                """.trimIndent()
    )
}
