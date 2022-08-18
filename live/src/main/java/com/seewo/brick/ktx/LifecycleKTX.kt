package com.seewo.brick.ktx

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

fun Context.inMyLifecycle(block: LifecycleOwner.() -> Unit) {
    if (this is LifecycleOwner) {
        block()
    }
}

fun View.inMyLifecycle(block: LifecycleOwner.() -> Unit) {
    context.run {
        if (this is LifecycleOwner) {
            block()
        }
    }
}

fun Fragment.inMyLifecycle(block: LifecycleOwner.() -> Unit) {
    block()
}