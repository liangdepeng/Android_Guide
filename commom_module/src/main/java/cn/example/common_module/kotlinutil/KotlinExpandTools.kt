package com.example.common_module

import android.widget.TextView

/**
 * Created by ldp.
 *
 * Date: 2021/9/8
 *
 * Summary: Kotlin 扩展函数 可以在不侵入类的情况下扩展类的功能
 */

fun TextView.test() = this.apply {
   // paint.isFakeBoldText = true
}

var TextView.isBold: Boolean
    get() {
        return this.paint.isFakeBoldText
    }
    set(value) {
        this.paint.isFakeBoldText = value
    }

val <T> List<T>.lastIndex: Int
    get() = size - 1


