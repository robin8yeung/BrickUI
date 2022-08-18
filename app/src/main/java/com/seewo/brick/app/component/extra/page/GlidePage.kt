package com.seewo.brick.app.component.extra.page

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.hjq.toast.ToastUtils
import com.seewo.brick.BrickPreview
import com.seewo.brick.app.widget.Markdown
import com.seewo.brick.ktx.*
import com.seewo.brick.params.EdgeInsets

private class GlidePage(context: Context, attrs: AttributeSet? = null) :
    BrickPreview(context, attrs) {

    override fun preview(context: Context) {
        view {
            context.GlidePage()
        }
    }
}

fun Context.GlidePage() = column(
    MATCH_PARENT, MATCH_PARENT,
) {
    networkImages()

    ShowMarkDown()
}

private fun LinearLayout.networkImages() {
    row(
        padding = EdgeInsets.symmetric(4.dp, 16.dp)
    ) {
        // 简单加载图片
        glideImage(
            urlOrPath = "https://easinote.seewo.com/statics/modules/themes/images/index/1x/qrode_ebf6c6e.png",
        ) {
            // 可选择imageView，也可选择liveImage
            imageView(84.dp, 84.dp)
        }
        // 自定义各种参数和回调
        glideImage(
            urlOrPath = "https://img0.baidu.com/it/u=3995530121,45712565&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=313",
            transition = DrawableTransitionOptions.withCrossFade(),
            requestOptions = RequestOptions()
                .transform(
                    CenterCrop(), RoundedCorners(4.dp),
                ),
            onLoadFailed = {
                ToastUtils.show("图片加载异常")
            },
            onResourceReady = {
                ToastUtils.show("图片加载完成")
            }
        ) {
            imageView(150.dp, 84.dp)
        }
    }
}

private fun LinearLayout.ShowMarkDown() {
    expand {
        Markdown(
            """
## 代码展示

主要提供了 glideImage、glideGif 2个函数来修饰ImageView，用于执行网络图片或本地图片加载，提供了Glide框架的相关能力

```kotlin
private fun LinearLayout.networkImages() {
    row(
        padding = EdgeInsets.symmetric(4.dp, 16.dp)
    ) {
        // 简单加载图片
        glideImage(
            urlOrPath = "https://easinote.seewo.com/statics/modules/themes/images/index/1x/qrode_ebf6c6e.png",
        ) {
            // 可选择imageView，也可选择liveImage
            imageView(84.dp, 84.dp)
        }
        // 自定义各种参数和回调
        glideImage(
            urlOrPath = "https://img0.baidu.com/it/u=3995530121,45712565&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=313",
            transition = DrawableTransitionOptions.withCrossFade(),
            requestOptions = RequestOptions()
                .transform(
                    CenterCrop(), RoundedCorners(4.dp),
                ),
            onLoadFailed = {
                ToastUtils.show("图片加载异常")
            },
            onResourceReady = {
                ToastUtils.show("图片加载完成")
            }
        ) {
            imageView(150.dp, 84.dp)
        }
    }
}
```
                """.trimIndent()
        )
    }
}
