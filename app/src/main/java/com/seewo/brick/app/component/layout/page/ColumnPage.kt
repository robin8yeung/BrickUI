package com.seewo.brick.app.component.layout.page

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.hjq.toast.ToastUtils
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.R
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.app.widget.buttonBackGround
import com.seewo.brick.ktx.*
import com.seewo.brick.params.CornerRadius
import com.seewo.brick.params.EdgeInsets
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ColumnPage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.ColumnPage()
        }
    }
}

fun Context.ColumnPage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    DownloadPage()
    ShowMarkDown()
}

private fun ViewGroup.DownloadPage() {
    column(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(horizontal = 16.dp, vertical = 8.dp),
        animateLayoutChanges = true,
    ) {
        // 用到的几个LiveData
        val isChecked = false.live
        val downloading = false.live
        val progress = 0.live
        // 进度条，下载时显示
        ProgressBar(progress, downloading) {
            // 下载完成回调，提示并复位状态
            context.showCompleteDialog()
            isChecked.data = false
            progress.data = 0
        }
        // 勾选选项
        CheckItem(isChecked)
        // 下载按钮，选项勾选后可用
        DownloadButton(isChecked) {
            // 点击事件回调
            if (downloading.value == true) {
                ToastUtils.show("正在下载")
                return@DownloadButton
            }
            // 使能下载状态，并控制进度从0到100
            downloading.data = true
            (context as? ComponentActivity)?.lifecycleScope?.launch {
                repeat(10) {
                    progress.data = progress.data + 10
                    delay(1000)
                }
            }
        }
    }
}

private fun LinearLayout.DownloadButton(
    isChecked: MutableLiveData<Boolean>,
    onClick: OnClickListener? = null,
) {
    liveText(
        MATCH_PARENT, 40.dp,
        text = "下载".static,
        textSize = 14.dp,
        textColor = android.R.color.white.COLOR,
        background = buttonBackGround,
        gravity = Gravity.CENTER,
        isEnabled = isChecked,
        onClick = onClick
    )
}

private fun LinearLayout.ProgressBar(
    progress: MutableLiveData<Int>,
    downloading: MutableLiveData<Boolean>,
    onFinish: (() -> Unit)? = null,
) {
    // 需要根据下载状态控制整行显隐，所以使用liveRow
    liveRow(
        MATCH_PARENT,
        visibility = downloading.map { if (it) View.VISIBLE else View.GONE },
    ) {
        textView(text = "下载进度")
        expand(
            margins = EdgeInsets.only(start = 8.dp, end = 6.dp)
        ) {
            liveHorizontalProgressBar(
                0.dp, 4.dp,
                progress = progress,
                progressType = PROGRESS_TYPE_SCALE,
                progressDrawable = rectDrawable(
                    corners = CornerRadius.all(8.dp),
                    fillColor = R.color.primary.color
                ),
                progressBackground = rectDrawable(
                    corners = CornerRadius.all(8.dp),
                    fillColor = R.color.grey_e4.color
                ),
                animateDuration = 200L,
                onProgressChanged = {
                    if (it >= 100) {
                        downloading.data = false
                        onFinish?.invoke()
                    }
                }
            )
        }
        liveText(text = progress.map { "$it %" })
    }
}

private fun ViewGroup.CheckItem(
    isChecked: MutableLiveData<Boolean>,
) = row {
    // CheckBox选中与LiveData双向绑定，所以使用liveCheckbox
    liveCheckbox(
        isChecked = isChecked,
        text = "待上传文件".static,
    )
}

private fun Context.showCompleteDialog() {
    AlertDialog.Builder(this)
        .setTitle("下载完成")
        .setPositiveButton("确定") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

```kotlin
private fun ViewGroup.DownloadPage() {
    column(
        MATCH_PARENT,
        padding = EdgeInsets.symmetric(horizontal = 16.dp, vertical = 8.dp),
        animateLayoutChanges = true,
    ) {
        // 用到的几个LiveData
        val isChecked = false.live
        val downloading = false.live
        val progress = 0.live
        // 进度条，下载时显示
        ProgressBar(progress, downloading) {
            // 下载完成回调，提示并复位状态
            context.showCompleteDialog()
            isChecked.data = false
            progress.data = 0
        }
        // 勾选选项
        CheckItem(isChecked)
        // 下载按钮，选项勾选后可用
        DownloadButton(isChecked) {
            // 点击事件回调
            if (downloading.value == true) {
                ToastUtils.show(("正在下载")
                return@DownloadButton
            }
            // 使能下载状态，并控制进度从0到100
            downloading.data = true
            (context as? ComponentActivity)?.lifecycleScope?.launch {
                repeat(10) {
                    progress.data = progress.data + 10
                    delay(1000)
                }
            }
        }
    }
}

private fun LinearLayout.DownloadButton(
    isChecked: MutableLiveData<Boolean>,
    onClick: OnClickListener? = null,
) {
    liveText(
        MATCH_PARENT, 40.dp,
        text = "下载".static,
        textSize = 14.dp,
        textColor = android.R.color.white.COLOR,
        background = buttonBackGround,
        gravity = Gravity.CENTER,
        isEnabled = isChecked,
        onClick = onClick
    )
}

private fun LinearLayout.ProgressBar(
    progress: MutableLiveData<Int>,
    downloading: MutableLiveData<Boolean>,
    onFinish: (() -> Unit)? = null,
) {
    // 需要根据下载状态控制整行显隐，所以使用liveRow
    liveRow(
        MATCH_PARENT,
        visibility = downloading.map { if (it) View.VISIBLE else View.GONE },
    ) {
        textView(text = "下载进度")
        expand(
            margins = EdgeInsets.only(start = 8.dp, end = 6.dp)
        ) {
            liveHorizontalProgressBar(
                0.dp, 4.dp,
                progress = progress,
                progressType = PROGRESS_TYPE_SCALE,
                progressDrawable = rectDrawable(
                    corners = CornerRadius.all(8.dp),
                    fillColor = R.color.primary.color
                ),
                progressBackground = rectDrawable(
                    corners = CornerRadius.all(8.dp),
                    fillColor = R.color.grey_e4.color
                ),
                animateDuration = 200L,
                onProgressChanged = {
                    if (it >= 100) {
                        downloading.data = false
                        onFinish?.invoke()
                    }
                }
            )
        }
        liveText(text = progress.map { "${'$'}it %" })
    }
}

private fun ViewGroup.CheckItem(
    isChecked: MutableLiveData<Boolean>,
) = row {
    // CheckBox选中与LiveData双向绑定，所以使用liveCheckbox
    liveCheckbox(
        isChecked = isChecked,
        text = "待上传文件".static,
    )
}
```
                """.trimIndent()
        )
    }
}
