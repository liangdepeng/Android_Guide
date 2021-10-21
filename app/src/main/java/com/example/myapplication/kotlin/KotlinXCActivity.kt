package com.example.myapplication.kotlin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.common_module.*
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityKotlinXcactivityBinding
import kotlinx.coroutines.*

class KotlinXCActivity : BaseActivity() {

    private val TAG = "kotlinXc"

    private val viewModel by lazy {
        ViewModelProvider(this).get(KotlinXcViewModel::class.java)
    }

    private val binding by lazy {
        ActivityKotlinXcactivityBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
       // runBlockXC()
       // notBlockXC()

        viewModel.testLiveData.observe(this,object :Observer<String>{
            override fun onChanged(data: String?) {
             //   data?.simpleToast()
             //   data?.simpleContextToast(this@KotlinXCActivity)
//                val makeText = Toast.makeText(this@KotlinXCActivity, data, Toast.LENGTH_SHORT)
//                makeText.setGravity(Gravity.CENTER,0,0)
//                makeText.show()
                data?.snackToast(this@KotlinXCActivity)
            }
        })

        binding.getData.setOnClickListener {
            viewModel.requestData()
        }
    }

    private fun notBlockXC() {
        Log.e(TAG, "主线程id：${mainLooper.thread.id}")
        // 全局携程
        val job = GlobalScope.launch {

            "全局携程执行 延时6秒".simpleToast()

            delay(6000)
            Log.e(TAG, "协程执行结束 -- 线程id：${Thread.currentThread().id}")

            withContext(Dispatchers.IO) {
                // io线程
            }

            withContext(Dispatchers.Main) {
                // 主线程
            }
        }
        Log.e(TAG, "主线程执行结束")

        //Job中的方法
        job.isActive
        job.isCancelled
        job.isCompleted
        job.cancel()
        //job.join()

        GlobalScope.launch {
            // 全局携程 无生命周期绑定
        }

        lifecycleScope.launch {
            // 绑定activity生命周期或fragment生命周期
        }
    }

    private fun runBlockXC() {
        Log.e("kotlinXc", "当前线程=${Thread.currentThread().toString()}")
        runBlocking {
            repeat(100) {
                Log.e("kotlinXc", "协程执行 $it 线程=${Thread.currentThread().toString()}")
            }
        }
        Log.e("kotlinXc", "协程执行结束 当前线程=${Thread.currentThread().toString()}")
    }
}