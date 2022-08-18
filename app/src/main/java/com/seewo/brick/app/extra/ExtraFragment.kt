package com.seewo.brick.app.extra

import android.content.Context
import android.util.AttributeSet
import com.hjq.toast.ToastUtils
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.extra.about.AboutActivity
import com.seewo.brick.app.extra.counter.CounterActivity
import com.seewo.brick.app.extra.list.LongListActivity
import com.seewo.brick.app.main.MainItemBean
import com.seewo.brick.app.main.mainFragmentList
import com.seewo.brick.ktx.*

class PreviewExtraFragment(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.ExtraPage()
        }
    }
}

fun Context.ExtraPage() = mainFragmentList(
    listOf(
        MainItemBean("计数器", R.drawable.btn_add_food.drawable) {
            startActivity<CounterActivity>()
        },
        MainItemBean("长列表体验", R.drawable.ic_widget_picker_view.drawable) {
            startActivity<LongListActivity>()
        },
        MainItemBean("To-Do", R.drawable.ic_checked_right.drawable) {
            ToastUtils.show("有空再搞")
        },
        MainItemBean("关于", R.mipmap.ic_launcher.drawable?.clipRect(8.dp)) {
            startActivity<AboutActivity>()
        },
    )
)