package com.ldp.jetpack_app.jetpack.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ldp.jetpack_app.R
import com.ldp.jetpack_app.databinding.ActivityLiveDataBinding

/**
 * jetpack 组件 lifecycle + livedata + viewModel + viewBinding
 */
class LiveDataActivity : AppCompatActivity() {

    /**
     * ViewBinding 绑定布局 无需 finviewbyid 空安全 类型安全
     */
    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityLiveDataBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    /**
     * ViewModel 有生命周期感知的组件
     */
    private val liveViewModel by lazy {
        ViewModelProvider(this).get(LiveViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewBinding
        setContentView(viewBinding.root)

        /**
         * livedata 生命周期感知的 数据绑定和更新  在后台不会更新数据 在认为可见时才会通知
         */
        liveViewModel.liveData.observe(this,object :Observer<TestBean>{
            override fun onChanged(bean: TestBean?) {
                viewBinding.result1tv.text = bean?.data1
            }
        })

        viewBinding.button1.setOnClickListener {
            liveViewModel.requestData()
        }
    }
}