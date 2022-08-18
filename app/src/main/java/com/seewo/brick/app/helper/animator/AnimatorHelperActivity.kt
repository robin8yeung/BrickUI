package com.seewo.brick.app.helper.animator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seewo.brick.app.helper.animator.page.AnimatorPage
import com.seewo.brick.app.widget.multiTabViewPager
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.frameLayout
import com.seewo.brick.ktx.setStatusBarTransparent

class AnimatorHelperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent(true)
        setContentView(multiTabViewPager(
            data = listOf(
                "动画",
            ),
            viewPagerBuilder = { _, index ->
                when (index) {
                    0 -> AnimatorPage()

                    else -> frameLayout(
                        MATCH_PARENT, MATCH_PARENT,
                    )
                }
            }
        ))
    }
}