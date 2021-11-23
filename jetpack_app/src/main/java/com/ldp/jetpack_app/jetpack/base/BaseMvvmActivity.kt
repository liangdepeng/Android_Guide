package com.ldp.jetpack_app.jetpack.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * Created by ldp.
 *
 * Date: 2021/11/22
 *
 * Summary: 封装了 获取 viewbinding 和 viewmodel 的基类
 */
abstract class BaseMvvmActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    lateinit var mViewBinding: VB
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            //获取当前类的泛型参数类型 0 第一个 即 VB
            val clazz = type.actualTypeArguments[0] as Class<VB>
            // 反射获取 对应的方法调用
            val method = clazz.getDeclaredMethod("inflate", LayoutInflater::class.java)
            mViewBinding = method.invoke(null, layoutInflater) as VB
            // 设置 rootView
            setContentView(mViewBinding.root)
            //获取当前类的泛型参数类型 1 第2个 即 VM
            val clazzVM = type.actualTypeArguments[1] as Class<VM>
            // 绑定viewModel
            mViewModel = ViewModelProvider(this).get(clazzVM)
        }
    }
}