package com.seewo.brick.app.extra.counter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.seewo.brick.ktx.data
import com.seewo.brick.ktx.live

class CounterPageViewModel(application: Application): AndroidViewModel(application) {
    val count: LiveData<Int> = 0.live

    fun plus() {
        count.data = count.data + 1
    }

    fun minus() {
        count.data = count.data - 1
    }
}
