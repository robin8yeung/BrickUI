package com.seewo.brick.app.helper.clip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seewo.brick.app.helper.clip.page.ClipPage
import com.seewo.brick.app.helper.clip.page.ShadowPage
import com.seewo.brick.app.widget.multiTabViewPager
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.frameLayout
import com.seewo.brick.ktx.setStatusBarTransparent

class ClipAndShadowHelperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent(true)
        setContentView(multiTabViewPager(
            data = listOf(
                "阴影",
                "裁剪",
            ),
            viewPagerBuilder = { _, index ->
                when (index) {
                    0 -> ShadowPage()
                    1 -> ClipPage()

                    else -> frameLayout(
                        MATCH_PARENT, MATCH_PARENT,
                    )
                }
            }
        ))
    }
}