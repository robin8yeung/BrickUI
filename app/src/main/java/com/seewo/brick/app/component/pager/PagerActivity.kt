package com.seewo.brick.app.component.pager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seewo.brick.app.component.pager.page.FragmentPagerPage
import com.seewo.brick.app.component.pager.page.ViewPagerPage
import com.seewo.brick.app.widget.multiTabViewPager
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.frameLayout
import com.seewo.brick.ktx.setStatusBarTransparent

class PagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent(true)
        setContentView(multiTabViewPager(
            data = listOf(
                "分页切换",
                "分页切换(基于Fragment)",
            ),
            viewPagerBuilder = { _, index ->
                when (index) {
                    0 -> ViewPagerPage()
                    1 -> FragmentPagerPage()
                    else -> frameLayout(
                        MATCH_PARENT, MATCH_PARENT,
                    )
                }
            }
        ))
    }
}