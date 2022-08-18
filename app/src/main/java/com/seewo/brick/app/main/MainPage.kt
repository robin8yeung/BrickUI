package com.seewo.brick.app.main

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.component.ComponentPage
import com.seewo.brick.app.extra.ExtraPage
import com.seewo.brick.app.helper.HelperPage
import com.seewo.brick.ktx.*
import com.seewo.brick.params.CompoundDrawables
import com.seewo.brick.params.EdgeInsets

class MainPage(context: Context, attributeSet: AttributeSet?): BrickPreview(context, attributeSet) {
    override fun preview(context: Context) {
        view {
            context.mainPage()
        }
    }
}

fun Context.mainPage() = column(
    MATCH_PARENT, MATCH_PARENT,
    fitsSystemWindows = true
) {
    val currentIndex = 0.live
    layoutParams(
        weight = 1f
    ) {
        liveViewPager(
            MATCH_PARENT, MATCH_PARENT,
            data = listOf(1, 2, 3).static,
            currentIndex = currentIndex,
            smoothScroll = false,
            isUserInputEnable = false,
        ) { _, index ->
            when (index) {
                0 -> ComponentPage()
                1 -> HelperPage()
                else -> ExtraPage()
            }
        }
    }
    divider(height = 1.dp, background = R.color.grey_e4.colorDrawable)
    row(
        MATCH_PARENT, WRAP_CONTENT,
        padding = EdgeInsets.symmetric(horizontal = 44.dp),
        gravity = Gravity.CENTER,
    ) {
        val list = listOf(
            Pair(R.drawable.selector_icon_tabbar_component, "控件"),
            Pair(R.drawable.selector_icon_tabbar_util, "工具"),
            Pair(R.drawable.selector_icon_tabbar_expand, "扩展"),
        )
        for (index in list.indices) {
            val item = list[index]
            layoutParams(
                weight = 1f, gravity = Gravity.CENTER
            ) {
                liveText(0, WRAP_CONTENT,
                    text = item.second.static,
                    isSelected = currentIndex.map { it == index },
                    style = R.style.TabStyle,
                    compoundDrawables = CompoundDrawables(
                        top = item.first.drawable
                    ).static,
                    textStyle = Typeface.BOLD,
                    onClick = {
                        currentIndex.value = index
                    }
                )
            }
        }
    }
}