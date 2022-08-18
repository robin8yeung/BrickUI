package com.seewo.brick.app.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seewo.brick.ktx.setStatusBarTransparent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent(true)
        setContentView(mainPage())
    }
}