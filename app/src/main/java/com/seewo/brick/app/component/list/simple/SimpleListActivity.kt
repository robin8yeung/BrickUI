package com.seewo.brick.app.component.list.simple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seewo.brick.app.component.list.simple.page.GridListPage
import com.seewo.brick.app.component.list.simple.page.HorizontalListPage
import com.seewo.brick.app.component.list.simple.page.ScrollViewPage
import com.seewo.brick.app.component.list.simple.page.VerticalListPage
import com.seewo.brick.app.widget.multiTabViewPager
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.frameLayout
import com.seewo.brick.ktx.setStatusBarTransparent

class SimpleListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent(true)
        setContentView(multiTabViewPager(
            data = listOf(
                "纵向列表",
                "横向列表",
                "方阵列表",
                "页面滚动",
            ),
            viewPagerBuilder = { _, index ->
                when (index) {
                    0 -> VerticalListPage()
                    1 -> HorizontalListPage()
                    2 -> GridListPage()
                    3 -> ScrollViewPage()
                    else -> frameLayout(
                        MATCH_PARENT, MATCH_PARENT,
                    )
                }
            }
        ))
    }
}