package com.seewo.brick.app.component.list.list.page

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.*
import com.seewo.brick.params.CornerRadius
import com.seewo.brick.params.EdgeInsets

private class ComplexStatefulListPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.ComplexStatefulListPage()
        }
    }
}

fun Context.ComplexStatefulListPage() = column(
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
    liveRecyclerView(
        MATCH_PARENT, 100.dp,
        data = dataLiveData,
        padding = EdgeInsets.symmetric(horizontal = 12.dp),
        // 通过 viewTypeBuilder 传入viewType的创建规则（列表index与ViewType的映射关系。如果列表只存在一种ItemView可以不传）
        // 通过viewBuilder 传入从viewType到view的mapper （ViewType与View的映射关系，这里的View将作为复用模板。ItemView模板必须用recyclerItem包裹，否则会抛异常）
        viewBuilder = {
            row(
                MATCH_PARENT, 32.dp
            ) {
                textView(
                    64.dp,
                    id = 0x111,
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
                        id = 0x112,
                        textColor = Color.BLACK,
                        textSize = 14.dp,
                    )
                }
                textView(
                    id = 0x113,
                    text = "删除",
                    textColor = Color.RED,
                    textSize = 12.dp,
                    padding = EdgeInsets.symmetric(horizontal = 4.dp, vertical = 2.dp),
                    background = rectDrawable(
                        strokeColor = Color.RED,
                        strokeWidth = 1.dp,
                        corners = CornerRadius.all(4.dp)
                    )
                )
            }
        }
    ) { data, index, view ->
        // 通过dataBinder来把数据信息装填到View中，这里提供的一种思路是通过findViewById去拿到view，这就需要相关View都要设置id
        val person = data[index]
        view.findViewById<TextView>(0x111).text = person.id
        view.findViewById<TextView>(0x112).text = person.name
        view.findViewById<View>(0x113).setOnClickListener {
            // 删除该项后更新LiveData即可自动更新列表
            dataLiveData.data = dataLiveData.value?.toMutableList()?.apply {
                remove(person)
            }
        }
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

liveRecyclerView与liveSimpleRecyclerView类似，也是可通过LiveData<List<T: RecyclerItemData>>来驱动列表条目的增、删、改。
其内部基于DiffUtil.calculateDiff来实现，请按实际情况重写 areSameItem 和 equals 方法

不同点在于liveSimpleRecyclerView的使用更简介和优雅；liveRecyclerView更繁琐，如可能需要设置viewTypeBuilder，viewBuilder和dataBinder 3个构造函数，还需要给View设置id，并通过findViewById这种与声明式ui相悖的方式去更新ui，但它在实现上完全还原了RecyclerView的经典实现，所以性能是最优的。
总的来说，如果对性能不偏执的话，还是更推荐使用liveSimpleRecyclerView

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
    liveRecyclerView(
        MATCH_PARENT, 100.dp,
        data = dataLiveData,
        padding = EdgeInsets.symmetric(horizontal = 12.dp),
        // 通过 viewTypeBuilder 传入viewType的创建规则（列表index与ViewType的映射关系。如果列表只存在一种ItemView可以不传）
        // 通过viewBuilder 传入从viewType到view的mapper （ViewType与View的映射关系，这里的View将作为复用模板。ItemView模板必须用recyclerItem包裹，否则会抛异常）
        viewBuilder = {
            row(
                MATCH_PARENT, 32.dp
            ) {
                textView(
                    64.dp,
                    id = 0x111,
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
                        id = 0x112,
                        textColor = Color.BLACK,
                        textSize = 14.dp,
                    )
                }
                textView(
                    id = 0x113,
                    text = "删除",
                    textColor = Color.RED,
                    textSize = 12.dp,
                    padding = EdgeInsets.symmetric(horizontal = 4.dp, vertical = 2.dp),
                    background = rectDrawable(
                        strokeColor = Color.RED,
                        strokeWidth = 1.dp,
                        corners = CornerRadius.all(4.dp)
                    )
                )
            }
        }
    ) { data, index, view ->
        // 通过dataBinder来把数据信息装填到View中，这里提供的一种思路是通过findViewById去拿到view，这就需要相关View都要设置id
        val person = data[index]
        view.findViewById<TextView>(0x111).text = person.id
        view.findViewById<TextView>(0x112).text = person.name
        view.findViewById<View>(0x113).setOnClickListener {
            // 删除该项后更新LiveData即可自动更新列表
            dataLiveData.data = dataLiveData.value?.toMutableList()?.apply {
                remove(person)
            }
        }
    }
}
```
                """.trimIndent()
        )
    }
}
