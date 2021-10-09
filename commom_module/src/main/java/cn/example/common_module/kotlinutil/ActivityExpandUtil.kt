package cn.example.common_module.kotlinutil

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by ldp.
 *
 * Date: 2021/9/8
 *
 * Summary: https://github.com/shiweibsw/Android-kotlin-extend-utils
 */

// 横竖屏 是否是竖屏 和 设置 横竖屏
var Activity.isPortrait: Boolean
    get() {
        return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }
    @SuppressLint("SourceLockedOrientationActivity") set(value) {
        requestedOrientation = if (value) ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        else ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

/**
 * 显示软键盘
 */
fun Activity.showKeyboard() {
    val imm: InputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        ?: return
    var view = this.currentFocus
    if (view == null) {
        view = View(this)
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
    }
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

/**
 * 隐藏软键盘
 */
fun Activity.hideKeyboard() {
    val imm: InputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        ?: return
    var view = this.currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * 屏幕截图
 */
fun Activity.screenShot(activity: Activity, isDeleteStatusBar: Boolean = true): Bitmap {
    val decorView = activity.window.decorView
    decorView.isDrawingCacheEnabled = true
    decorView.buildDrawingCache()
    val bmp = decorView.drawingCache
    val dm = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(dm)
    var ret: Bitmap? = null
    if (isDeleteStatusBar) {
        val resources = activity.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(resourceId)
        ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight)
    } else {
        ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels)
    }
    decorView.destroyDrawingCache()
    return ret!!
}