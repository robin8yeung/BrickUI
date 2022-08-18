package com.seewo.brick.app.main

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.drawable
import com.seewo.brick.ktx.simpleStatelessRecyclerView

class MainFragmentList(context: Context, attrs: AttributeSet): BrickPreview(context, attrs) {
    override fun preview(context: Context) {
        mainFragmentList(listOf(
            MainItemBean("计数器", R.drawable.btn_add_food.drawable),
            MainItemBean("To-Do", R.drawable.ic_checked_right.drawable),
            MainItemBean("关于", R.mipmap.ic_launcher.drawable),
        ))
    }
}

fun Context.mainFragmentList(
    items: List<MainItemBean>
) = simpleStatelessRecyclerView(
    MATCH_PARENT, MATCH_PARENT,
    data = items,
    layoutManager = GridLayoutManager(this, 3),
    overScrollMode = RecyclerView.OVER_SCROLL_NEVER,
) { data, i ->
    MainItemWidget(
        text = data[i].text,
        drawable = data[i].drawable,
        onClick = data[i].onClick,
    )
}

fun ViewGroup.mainFragmentList(
    items: List<MainItemBean>
) = context.mainFragmentList(items).also { addView(it) }