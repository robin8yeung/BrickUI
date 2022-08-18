package com.seewo.brick.app.component.list.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seewo.brick.app.component.list.list.page.ComplexStatefulListPage
import com.seewo.brick.app.component.list.list.page.StatefulListPage
import com.seewo.brick.app.widget.multiTabViewPager
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.frameLayout
import com.seewo.brick.ktx.setStatusBarTransparent

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent(true)
        setContentView(multiTabViewPager(
            data = listOf(
                "动态列表",
                "动态列表(复杂实现)",
            ),
            viewPagerBuilder = { _, index ->
                when (index) {
                    0 -> StatefulListPage()
                    1 -> ComplexStatefulListPage()
                    else -> frameLayout(
                        MATCH_PARENT, MATCH_PARENT,
                    )
                }
            }
        ))
    }
}