package com.seewo.brick.app.component.extra

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seewo.brick.app.component.extra.page.EmbededPage
import com.seewo.brick.app.component.extra.page.GlidePage
import com.seewo.brick.app.component.extra.page.SmartRefreshPage
import com.seewo.brick.app.widget.multiTabViewPager
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.frameLayout
import com.seewo.brick.ktx.setStatusBarTransparent

class ExtraComponentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent(true)
        setContentView(multiTabViewPager(
            data = listOf(
                "图片加载",
                "下拉加载",
                "原生控件嵌入",
            ),
            viewPagerBuilder = { _, index ->
                when (index) {
                    0 -> GlidePage()
                    1 -> SmartRefreshPage()
                    2 -> EmbededPage()
                    else -> frameLayout(
                        MATCH_PARENT, MATCH_PARENT,
                    )
                }
            }
        ))
    }
}