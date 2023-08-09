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
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.colorDrawable
import com.seewo.brick.ktx.column
import com.seewo.brick.ktx.divider
import com.seewo.brick.ktx.dp
import com.seewo.brick.ktx.drawable
import com.seewo.brick.ktx.expand
import com.seewo.brick.ktx.live
import com.seewo.brick.ktx.liveTabLayout
import com.seewo.brick.ktx.liveViewPager
import com.seewo.brick.ktx.static
import com.seewo.brick.ktx.textView
import com.seewo.brick.ktx.view
import com.seewo.brick.params.CompoundDrawables
import com.seewo.brick.params.EdgeInsets

class MainPage(context: Context, attributeSet: AttributeSet?) :
    BrickPreview(context, attributeSet) {
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
    val tabs = listOf(
        Pair(R.drawable.selector_icon_tabbar_component, "控件"),
        Pair(R.drawable.selector_icon_tabbar_util, "工具"),
        Pair(R.drawable.selector_icon_tabbar_expand, "扩展"),
    )
    expand {
        liveViewPager(
            MATCH_PARENT, MATCH_PARENT,
            data = tabs.static,
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
    liveTabLayout(
        MATCH_PARENT, 64.dp,
        data = tabs,
        margin = EdgeInsets.symmetric(horizontal = 44.dp),
        currentIndex = currentIndex,
    ) { _, item ->
        textView(
            text = item.second,
            style = R.style.TabStyle,
            compoundDrawables = CompoundDrawables(
                top = item.first.drawable
            ),
            textStyle = Typeface.BOLD,
            gravity = Gravity.CENTER,
        )
    }
}