package com.ldp.jetpack_app.jetpack.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by ldp.
 *
 * Date: 2021/12/1
 *
 * Summary:
 */
open class BaseActivityKt : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
    }

    private val loadingDialog by lazy {
        ProgressDialog(this).apply {
            setTitle("提示")
            setMessage("正在加载中...")
            setCanceledOnTouchOutside(false)
        }
    }

    fun showLoading() {
        if (loadingDialog.isShowing)
            return

        runOnUiThread {
            loadingDialog.show()
        }
    }

    fun dismissLoading() {
        if (loadingDialog.isShowing)
            loadingDialog.dismiss()
    }
}