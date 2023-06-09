package com.seewo.brick.app.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import club.andnext.markdown.MarkdownWebView
import com.seewo.brick.ktx.MATCH_PARENT
import com.seewo.brick.ktx.init
import com.seewo.brick.ktx.placeholder
import com.seewo.brick.ktx.view

fun ViewGroup.Markdown(
    text: String
): View {
    if (isInEditMode) return placeholder() // 避免预览模式报错
    return view {
        // 第三方控件：MarkdownWebView
        MarkdownWebView(context).apply {
            // 初始化宽高
            init(MATCH_PARENT, MATCH_PARENT)
            setText(text)
        }
    }
}

fun Context.Markdown(
    text: String
): View {
    return MarkdownWebView(this).apply {
        // 初始化宽高
        init(MATCH_PARENT, MATCH_PARENT)
        setText(text)
    }
}