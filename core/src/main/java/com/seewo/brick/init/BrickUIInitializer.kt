package com.seewo.brick.init

import android.content.Context
import androidx.startup.Initializer
import com.seewo.brick.BrickUI

class BrickUIInitializer : Initializer<Unit>{
    override fun create(context: Context) {
        BrickUI.init(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}