package com.seewo.brick.app.widget

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.ktx.*
import com.seewo.brick.params.EdgeInsets


class MultiTabViewPager(context: Context, attributeSet: AttributeSet?): BrickPreview(context, attributeSet) {
    override fun preview(context: Context) {
        view {
            context.multiTabViewPager(
                data = listOf(
                    "行布局",
                    "列布局",
                ),
                viewPagerBuilder = { _, index ->
                    when (index) {
                        0 -> frameLayout(
                            MATCH_PARENT, MATCH_PARENT,
                            background = R.color.grey_e4.colorDrawable,
                        )
                        else -> frameLayout(
                            MATCH_PARENT, MATCH_PARENT,
                            background = R.color.purple_700.colorDrawable,
                        )
                    }
                }
            )
        }
    }
}

fun Context.multiTabViewPager(
    data: List<String>,
    viewPagerBuilder: Context.(data: List<String>, index: Int) -> View
) = column(
    MATCH_PARENT, MATCH_PARENT,
    fitsSystemWindows = true
) {
    val tabIndex = 0.live
    row(MATCH_PARENT) {
        imageView(
            44.dp, 44.dp,
            drawable = R.drawable.ic_back_dark.drawable,
            scaleType = ImageView.ScaleType.CENTER_INSIDE,
        ) {
            (context as? Activity)?.onBackPressed()
        }
        expand {
            simpleStatelessRecyclerView(
                MATCH_PARENT, WRAP_CONTENT,
                padding = EdgeInsets.only(end = 16.dp, start = 4.dp),
                viewHolderCreator = {
                    frameLayout()
                },
                data = data,
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            ) { data, index ->
                val item = data[index]
                column(
                    onClick = {
                        tabIndex.value = index
                    }
                ) {
                    liveText(
                        text = item.static,
                        isSelected = tabIndex.map { it == index },
                        style = R.style.TabStyle,
                        textStyle = Typeface.BOLD,
                        padding = EdgeInsets.symmetric(8.dp, 12.dp),
                    ) {
                        tabIndex.value = index
                    }
                    layoutParams(margins = EdgeInsets.symmetric(horizontal = 4.dp)) {
                        liveDivider(
                            height = 2.dp,
                            isSelected = tabIndex.map { it == index },
                            background = stateListDrawable(
                                mapOf(
                                    intArrayOf(-android.R.attr.state_selected) to rectDrawable(fillColor = android.R.color.transparent.color),
                                    intArrayOf(android.R.attr.state_selected) to rectDrawable(fillColor = R.color.primary.color),
                                )
                            ),
                        )
                    }
                }
            }.apply {
                isHorizontalFadingEdgeEnabled = true
                setFadingEdgeLength(56.dp)
            }
        }
    }
    layoutParams(
        weight = 1f
    ) {
        liveViewPager(
            MATCH_PARENT, MATCH_PARENT,
            data = data.static,
            smoothScroll = false,
            currentIndex = tabIndex,
            isUserInputEnable = false,
        ) { data, index ->
            viewPagerBuilder(data, index)
        }
    }
}