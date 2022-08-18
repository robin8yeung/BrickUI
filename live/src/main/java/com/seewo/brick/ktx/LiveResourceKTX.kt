package com.seewo.brick.ktx

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData


val Int.DRAWABLE: LiveData<Drawable>
    get() = drawable.static

val Int.COLOR: LiveData<Int>
    get() = color.static

val Int.COLOR_DRAWABLE: LiveData<Drawable>
    get() = colorDrawable.static

val Int.STRING: LiveData<CharSequence>
    get() = string.static

val Int.DIMENSION: LiveData<Int>
    get() = dimension.static
