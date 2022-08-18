package com.seewo.brick.app.component.layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seewo.brick.app.component.layout.page.*
import com.seewo.brick.app.widget.multiTabViewPager
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.frameLayout
import com.seewo.brick.ktx.setStatusBarTransparent

class LayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent(true)
        setContentView(multiTabViewPager(
            data = listOf(
                "行布局",
                "列布局",
                "相对布局",
                "约束布局",
                "Flexbox布局",
            ),
            viewPagerBuilder = { _, index ->
                when (index) {
                    0 -> RowPage()
                    1 -> ColumnPage()
                    2 -> RelativePage()
                    3 -> ConstraintPage()
                    4 -> FlexboxPage()
                    else -> frameLayout(
                        MATCH_PARENT, MATCH_PARENT,
                    )
                }
            }
        ))
    }
}