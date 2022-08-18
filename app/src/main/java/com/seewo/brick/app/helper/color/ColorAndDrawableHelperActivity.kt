package com.seewo.brick.app.helper.color

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seewo.brick.app.helper.color.page.ColorPage
import com.seewo.brick.app.helper.color.page.DrawablePage
import com.seewo.brick.app.widget.multiTabViewPager
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.frameLayout
import com.seewo.brick.ktx.setStatusBarTransparent

class ColorAndDrawableHelperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent(true)
        setContentView(multiTabViewPager(
            data = listOf(
                "颜色",
                "Drawable",
            ),
            viewPagerBuilder = { _, index ->
                when (index) {
                    0 -> ColorPage()
                    1 -> DrawablePage()

                    else -> frameLayout(
                        MATCH_PARENT, MATCH_PARENT,
                    )
                }
            }
        ))
    }
}