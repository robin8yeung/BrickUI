package com.seewo.brick.ktx // 包名别改

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

/**
 * Activity设置全屏
 */
fun Activity.setFullScreen(enable: Boolean) {
    val params = window.attributes
    if (enable) {
        params.flags = params.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
        // 适配刘海屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            params.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    } else {
        params.flags = params.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
        // 适配刘海屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            params.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
        }
    }
    window.attributes = params
}

/**
 * Activity设置状态栏透明和文字颜色
 */
fun Activity.setStatusBarTransparent(lightStatusBar: Boolean) {
    if (!miUISetStatusBarLightMode(this, lightStatusBar)) {
        if (!flymeSetStatusBarLightMode(this, lightStatusBar)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setStatusBarTransparentWithSys(this, lightStatusBar)
            }
        }
    }
}

/**
 * 启动指定Activity
 */
inline fun<reified T: Activity> Context.startActivity() {
    startActivity(Intent(this, T::class.java))
}

/**
 * 启动指定Activity，并配置Intent
 */
inline fun<reified T: Activity> Context.startActivity(block: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply (block))
}

private fun setStatusBarTransparentWithSys(activity: Activity, lightStatusBar: Boolean) {
    val window = activity.window
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    if (lightStatusBar) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = Color.TRANSPARENT
}

/**
 * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
 * 可以用来判断是否为Flyme用户
 *
 * @param window 需要设置的窗口
 * @param dark   是否把状态栏字体及图标颜色设置为深色
 * @return boolean 成功执行返回true
 */
private fun flymeSetStatusBarLightMode(activity: Activity, dark: Boolean): Boolean {
    var result = false
    val window = activity.window
    if (window != null) {
        try {
            val lp = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java
                .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java
                .getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            value = if (dark) {
                value or bit
            } else {
                value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            window.attributes = lp
            result = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 新版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                setStatusBarTransparentWithSys(activity, dark)
            }
        } catch (e: Exception) {
            // Do nothing
        }
    }
    return result
}

/**
 * 需要MIUIV6以上
 *
 * @param dark 是否把状态栏文字及图标颜色设置为深色
 * @return boolean 成功执行返回true
 */
private fun miUISetStatusBarLightMode(activity: Activity, dark: Boolean): Boolean {
    var result = false
    val window = activity.window
    if (window != null) {
        val clazz: Class<*> = window.javaClass
        try {
            var darkModeFlag = 0
            @SuppressLint("PrivateApi") val layoutParams =
                Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod(
                "setExtraFlags",
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
            if (dark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag) //状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag) //清除黑色字体
            }
            result = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                setStatusBarTransparentWithSys(activity, dark)
            }
        } catch (e: Exception) {
            // Do nothing
        }
    }
    return result
}
