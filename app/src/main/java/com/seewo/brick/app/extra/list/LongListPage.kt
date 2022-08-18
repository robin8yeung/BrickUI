package com.seewo.brick.app.extra.list

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import com.hjq.toast.ToastUtils
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.TopBar
import com.seewo.brick.ktx.*
import com.seewo.brick.params.CompoundDrawables
import com.seewo.brick.params.CornerRadius
import com.seewo.brick.params.EdgeInsets

class LongListPage(context: Context, attributeSet: AttributeSet?): BrickPreview(context, attributeSet) {
    override fun preview(context: Context) {
        view {
            context.LongListPage()
        }
    }
}

fun Context.LongListPage() = column(
    MATCH_PARENT, MATCH_PARENT,
    fitsSystemWindows = true
) {
    TopBar()
    divider(background = ColorDrawable(Color.GRAY))
    column(
        MATCH_PARENT, MATCH_PARENT,
    ) {
        simpleStatelessRecyclerView(
            MATCH_PARENT, MATCH_PARENT,
            itemDecoration = dividerDecoration(
                color = Color.RED,
                padding = EdgeInsets.only(bottom = 8.dp)
            ),
            data = listOf(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
            ),
        ) { data, index ->
            if (index % 2 == 0) {
                textView(
                    height = 53.dp,
                    gravity = Gravity.CENTER_VERTICAL,
                    text = data[index].toString(),
                    compoundDrawables = CompoundDrawables(
                        start = ovalDrawable(
                            width = 16.dp, height = 16.dp,
                            fillColor = R.color.teal_200.color,
                            strokeColor = Color.RED,
                            strokeWidth = 1.dp,
                        ),
                        end = rectDrawable(
                            width = 16.dp, height = 16.dp,
                            fillColor = R.color.teal_200.color,
                            strokeColor = Color.RED,
                            strokeWidth = 1.dp,
                            corners = CornerRadius.only(rightTop = 2.dp),
                        )
                    ),
                    onClick = {
                        ToastUtils.show("click ${data[index]}")
                    }
                )
            } else {
                shadowBox(
                    onClick = {
                        ToastUtils.show("click ${data[index]}")
                    }
                ) {
                    row {
                        imageView(
                            drawable = R.mipmap.ic_launcher.drawable,
                        )
                        textView(
                            80.dp, 40.dp,
                            text = data[index].toString(),
                            gravity = Gravity.CENTER,
                            compoundDrawables = CompoundDrawables(
                                start = ovalDrawable(
                                    width = 16.dp, height = 16.dp,
                                    fillColor = R.color.teal_200.color,
                                    strokeColor = Color.RED,
                                    strokeWidth = 1.dp,
                                ),
                                end = rectDrawable(
                                    width = 16.dp, height = 16.dp,
                                    fillColor = R.color.teal_200.color,
                                    strokeColor = Color.RED,
                                    strokeWidth = 1.dp,
                                    corners = CornerRadius.only(rightTop = 2.dp),
                                )
                            ),
                        )
                    }
                }
            }
        }
    }
}