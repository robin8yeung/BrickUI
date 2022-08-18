package com.seewo.brick.app.helper

import android.content.Context
import android.util.AttributeSet
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.helper.animator.AnimatorHelperActivity
import com.seewo.brick.app.helper.clip.ClipAndShadowHelperActivity
import com.seewo.brick.app.helper.color.ColorAndDrawableHelperActivity
import com.seewo.brick.app.helper.spannable.SpannableHelperActivity
import com.seewo.brick.app.main.MainItemBean
import com.seewo.brick.app.main.mainFragmentList
import com.seewo.brick.ktx.drawable
import com.seewo.brick.ktx.startActivity
import com.seewo.brick.ktx.view

class PreviewHelperFragment(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.HelperPage()
        }
    }
}

fun Context.HelperPage() = mainFragmentList(
    listOf(
        MainItemBean("裁剪与阴影", R.drawable.ic_util_drawable.drawable) {
            startActivity<ClipAndShadowHelperActivity>()
        },
        MainItemBean("颜色与Drawable", R.drawable.ic_util_color.drawable) {
            startActivity<ColorAndDrawableHelperActivity>()
        },
        MainItemBean("富文本", R.drawable.ic_widget_dialog.drawable) {
            startActivity<SpannableHelperActivity>()
        },
        MainItemBean("动画", R.drawable.ic_widget_loading.drawable) {
            startActivity<AnimatorHelperActivity>()
        },
    )
)