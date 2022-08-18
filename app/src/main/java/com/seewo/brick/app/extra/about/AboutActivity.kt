package com.seewo.brick.app.extra.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seewo.brick.ktx.setStatusBarTransparent

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent(true)
        setContentView(AboutPage())
    }
}
