package com.seewo.brick.app.helper.spannable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seewo.brick.app.helper.spannable.page.SpannablePage
import com.seewo.brick.app.widget.multiTabViewPager
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.frameLayout
import com.seewo.brick.ktx.setStatusBarTransparent

class SpannableHelperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent(true)
        setContentView(multiTabViewPager(
            data = listOf(
                "富文本",
            ),
            viewPagerBuilder = { _, index ->
                when (index) {
                    0 -> SpannablePage()

                    else -> frameLayout(
                        MATCH_PARENT, MATCH_PARENT,
                    )
                }
            }
        ))
    }
}