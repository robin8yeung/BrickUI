package com.seewo.brick.app.component.list.list.page

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.LinearLayout
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.*
import com.seewo.brick.params.CornerRadius
import com.seewo.brick.params.EdgeInsets
import com.seewo.brick.params.RecyclerItemData
import java.util.*

private class StatefulListPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.StatefulListPage()
        }
    }
}

fun Context.StatefulListPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    statefulList()
    ShowMarkDown()
}

private fun LinearLayout.statefulList() {
    val dataLiveData = listOf(
        Person("老大"),
        Person("老二"),
        Person("老三"),
        Person("老四"),
        Person("老五"),
        Person("老六"),
        Person("老七"),
        Person("老八"),
        Person("老九"),
        Person("老十"),
    ).live
    liveSimpleRecyclerView(
        MATCH_PARENT, 100.dp,
        data = dataLiveData,
        padding = EdgeInsets.symmetric(horizontal = 12.dp)
    ) { data, index ->
        val person = data[index]
        row(
            MATCH_PARENT, 32.dp
        ) {
            textView(
                64.dp,
                text = person.id,
                textColor = R.color.primary.color,
                textSize = 12.dp,
                maxLines = 1,
                ellipsize = TextUtils.TruncateAt.END,
            )
            layoutParams(
                margins = EdgeInsets.symmetric(horizontal = 8.dp),
                weight = 1f,
            ) {
                textView(
                    MATCH_PARENT,
                    text = person.name,
                    textColor = Color.BLACK,
                    textSize = 14.dp,
                )
            }
            textView(
                text = "删除",
                textColor = Color.RED,
                textSize = 12.dp,
                padding = EdgeInsets.symmetric(horizontal = 4.dp, vertical = 2.dp),
                background = rectDrawable(
                    strokeColor = Color.RED,
                    strokeWidth = 1.dp,
                    corners = CornerRadius.all(4.dp)
                )
            ) {
                // 删除该项后更新LiveData即可自动更新列表
                dataLiveData.data = dataLiveData.value?.toMutableList()?.apply {
                    remove(person)
                }
            }
        }
    }
}

class Person(
    val name: String,
): RecyclerItemData {
    val id = UUID.randomUUID().toString()

    // 如何判断两项是同一项，可参考DiffUtil.Callback的使用
    override fun areSameItem(out: Any?): Boolean = out is Person && id == out.id

    // 如何判断两项内容相等，可参考DiffUtil.Callback的使用
    override fun equals(other: Any?): Boolean {
        return other is Person && id == other.id && name == other.name
    }

    // 重写了equals，同时重写hashCode（Android Studio自动填充即可）
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

liveSimpleRecyclerView，可通过LiveData<List<T: RecyclerItemData>>来驱动列表条目的增、删、改。
其内部基于DiffUtil.calculateDiff来实现，请按实际情况重写 areSameItem 和 equals 方法
值得注意的是，新增index为0的数据时，列表不会出现动画，这个是 DiffUtil.calculateDiff(DiffCallback(oldData, data)).dispatchUpdatesTo(this) 的已知问题
```kotlin
private fun LinearLayout.statefulList() {
    val dataLiveData = listOf(
        Person("老大"),
        Person("老二"),
        Person("老三"),
        Person("老四"),
        Person("老五"),
        Person("老六"),
        Person("老七"),
        Person("老八"),
        Person("老九"),
        Person("老十"),
    ).live
    liveSimpleRecyclerView(
        MATCH_PARENT, 100.dp,
        data = dataLiveData,
        padding = EdgeInsets.symmetric(horizontal = 12.dp)
    ) { data, index ->
        val person = data[index]
        row(
            MATCH_PARENT, 32.dp
        ) {
            textView(
                64.dp,
                text = person.id,
                textColor = R.color.primary.color,
                textSize = 12.dp,
                maxLines = 1,
                ellipsize = TextUtils.TruncateAt.END,
            )
            layoutParams(
                margins = EdgeInsets.symmetric(horizontal = 8.dp),
                weight = 1f,
            ) {
                textView(
                    MATCH_PARENT,
                    text = person.name,
                    textColor = Color.BLACK,
                    textSize = 14.dp,
                )
            }
            textView(
                text = "删除",
                textColor = Color.RED,
                textSize = 12.dp,
                padding = EdgeInsets.symmetric(horizontal = 4.dp, vertical = 2.dp),
                background = rectDrawable(
                    strokeColor = Color.RED,
                    strokeWidth = 1.dp,
                    corners = CornerRadius.all(4.dp)
                )
            ) {
                // 删除该项后更新LiveData即可自动更新列表
                dataLiveData.data = dataLiveData.value?.toMutableList()?.apply {
                    remove(person)
                }
            }
        }
    }
}

private class Person(
    val name: String,
): RecyclerItemData {
    val id = UUID.randomUUID().toString()

    // 如何判断两项是同一项，可参考DiffUtil.Callback的使用
    override fun areSameItem(out: Any?): Boolean = out is Person && id == out.id

    // 如何判断两项内容相等，可参考DiffUtil.Callback的使用
    override fun equals(other: Any?): Boolean {
        return other is Person && id == other.id && name == other.name
    }

    // 重写了equals，同时重写hashCode（Android Studio自动填充即可）
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}
```
                """.trimIndent()
        )
    }
}
