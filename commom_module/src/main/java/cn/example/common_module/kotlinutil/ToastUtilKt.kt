package cn.example.common_module.kotlinutil

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import kotlinx.coroutines.runBlocking

/**
 * Created by ldp.
 *
 * Date: 2021/11/29
 *
 * Summary:
 */
class ToastUtilKt {

    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context
        private lateinit var handler: Handler

        fun initLib(context: Context) {
            mContext = context
            val handlerThread = HandlerThread("handlerThread")
            handlerThread.start()
            handler = Handler(handlerThread.looper)
        }

        fun show(message: String) {
            handler.post {
                // ... 版本兼容操作

                // toast 显示
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
            }

            runBlocking {

            }
        }
    }
}