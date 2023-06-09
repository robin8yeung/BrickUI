package com.seewo.brick.app.component

import android.content.Context
import android.util.AttributeSet
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.component.coordinator.CoordinatorLayoutActivity1
import com.seewo.brick.app.component.coordinator.CoordinatorLayoutActivity2
import com.seewo.brick.app.component.extra.ExtraComponentActivity
import com.seewo.brick.app.component.layout.LayoutActivity
import com.seewo.brick.app.component.list.list.ListActivity
import com.seewo.brick.app.component.list.simple.SimpleListActivity
import com.seewo.brick.app.component.pager.PagerActivity
import com.seewo.brick.app.main.MainItemBean
import com.seewo.brick.app.main.mainFragmentList
import com.seewo.brick.ktx.drawable
import com.seewo.brick.ktx.startActivity
import com.seewo.brick.ktx.view

class PreviewComponentFragment(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.ComponentPage()
        }
    }
}

fun Context.ComponentPage() = mainFragmentList(
    listOf(
        MainItemBean("控件与布局", R.drawable.ic_widget_layout.drawable) {
            startActivity<LayoutActivity>()
        },
        MainItemBean("吸顶布局", R.drawable.ic_widget_titlebar.drawable) {
            startActivity<CoordinatorLayoutActivity1>()
        },
        MainItemBean("折叠布局", R.drawable.ic_widget_titlebar.drawable) {
            startActivity<CoordinatorLayoutActivity2>()
        },
        MainItemBean("增强控件", R.drawable.icon_tabbar_expand_selected.drawable) {
            startActivity<ExtraComponentActivity>()
        },
        MainItemBean("静态列表", R.drawable.ic_widget_flowlayout.drawable) {
            startActivity<SimpleListActivity>()
        },
        MainItemBean("动态列表", R.drawable.ic_widget_picker_view.drawable) {
            startActivity<ListActivity>()
        },
        MainItemBean("分页", R.drawable.ic_widget_statelayout.drawable) {
            startActivity<PagerActivity>()
        },
    )
)