package com.seewo.brick.cache

import android.graphics.drawable.Drawable
import java.lang.ref.SoftReference

internal object DrawableCache {
    private val cacheMap: MutableMap<String, SoftReference<Drawable>> = mutableMapOf()

    fun put(key: String, value: Drawable) {
        cacheMap[key] = SoftReference(value)
    }

    inline fun <reified T: Drawable> get(key: String): T? {
        val reference = cacheMap[key]
        val drawable = reference?.get()
        if (reference != null && drawable == null) {
            cacheMap.remove(key)
        }
        return drawable as? T
    }
}
