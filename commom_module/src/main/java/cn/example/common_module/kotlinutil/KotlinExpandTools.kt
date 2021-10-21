package com.example.common_module

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Looper
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import cn.example.common_module.AppContext
import com.google.android.material.snackbar.Snackbar

/**
 * Created by ldp.
 *
 * Date: 2021/9/8
 *
 * Summary: Kotlin 扩展函数 可以在不侵入类的情况下扩展类的功能
 */
// 测试 扩展方法
fun TextView.test() = this.apply {
    // paint.isFakeBoldText = true
    "扩展方法被调用".simpleToast()
}

// textview 加粗
var TextView.isBold: Boolean
    get() {
        return this.paint.isFakeBoldText
    }
    set(value) {
        this.paint.isFakeBoldText = value
    }

// list最后一个下标
val <T> List<T>.lastIndex: Int
    get() = size - 1

// 是否是主线程 任何Kotlin类均可用
val Any.isOnMainThread: Boolean
    get() = Looper.myLooper() == Looper.getMainLooper()

// 简易Toast 任何对象都可以  任何Kotlin类均可用
fun Any.simpleToast() = run {
    val globalToast = AppContext.getGlobalToast()
    val runnable = Runnable {
        globalToast.setText(toString())
        globalToast.show()
    }
    if (isOnMainThread) {
        runnable.run()
    } else {
        AppContext.HANDLER.post(runnable)
    }
}

// 简易Toast 任何对象都可以  任何Kotlin类均可用
fun Any.simpleContextToast(context: Context) = run {
    val runnable = Runnable {
        Toast.makeText(context, toString(), Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
            setText(this@run.toString())
        }.also { it.show() }
    }
    if (isOnMainThread) {
        runnable.run()
    } else {
        AppContext.HANDLER.post(runnable)
    }
}

// snackBar 代替Toast
fun Any.snackToast(context: Context?, callback: Snackbar.Callback? = null) = run {
    if (context is Activity) {
        Snackbar.make(
            context.window.decorView.findViewById(android.R.id.content),
            this.toString(),
            Snackbar.LENGTH_SHORT
        ).apply {
                setTextColor(Color.BLACK)
                setBackgroundTint(Color.parseColor("#ff8000"))
                addCallback(callback)
            }.show()
    } else {
        simpleToast()
    }
}


