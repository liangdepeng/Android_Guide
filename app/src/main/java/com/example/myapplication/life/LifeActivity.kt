package com.example.myapplication.life

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityLifeBinding

class LifeActivity : BaseActivity() {

    private val viewBinding by lazy {
        ActivityLifeBinding.inflate(LayoutInflater.from(this), null, false)
    }

    /**
     * android:configChanges="orientation|screenSize|keyboardHidden" 生命周期不变
     *
     * 不设置 走两遍生命周期
     *
     * --onCreate-- LifeActivity
     * --onStart-- LifeActivity
     * --onResume-- LifeActivity -- 竖屏
     * --onPause-- LifeActivity -- 开始切换横屏
     * --onStop-- LifeActivity
     * --onSaveInstanceState-- LifeActivity
     * --onDestroy-- LifeActivity
     * --onCreate-- LifeActivity
     * --onStart-- LifeActivity
     * --onRestoreInstanceState-- LifeActivity
     *  --onResume-- LifeActivity
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        intent?.data?.let {

            // ldphahaha://ldplpptest/android_guide?param1=参数111&param2=参数222

            val param1 = it.getQueryParameter("param1")
            val param2 = it.getQueryParameter("param2")

            viewBinding.button.postDelayed(Runnable {
                Toast.makeText(this,"scheme 跳转进来，参数\n param1 = $param1 \n param2 = $param2", Toast.LENGTH_LONG).show()
            },3000)
        }


        val fragment = LifeFragment()
        viewBinding.button.setOnClickListener {
            val beginTransaction = supportFragmentManager.beginTransaction()
            if (!fragment.isAdded) {
                beginTransaction.add(R.id.fl, fragment)
                beginTransaction.commitAllowingStateLoss()
                return@setOnClickListener
            }

            if (fragment.isVisible) {
                beginTransaction.hide(fragment)
            } else {
                beginTransaction.show(fragment)
            }
            beginTransaction.commitAllowingStateLoss()
        }
    }
}