package com.seewo.brick.app.extra.counter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seewo.brick.ktx.setStatusBarTransparent

class CounterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent(true)
        setContentView(CounterPage())
    }
}
