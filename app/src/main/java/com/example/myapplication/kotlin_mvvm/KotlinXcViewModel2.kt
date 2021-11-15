package com.example.myapplication.kotlin_mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common_module.simpleToast
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by ldp.
 *
 * Date: 2021/10/21
 *
 * Summary:
 */
class KotlinXcViewModel2 : ViewModel() {

    val resultData = MutableLiveData<String>()

    fun requestDataTwo() {
        viewModelScope.launch {

            "请求开始".simpleToast()

            val startTimeMillis = System.currentTimeMillis()

            val async1 = async {
                delay(1000)
                "异步请求1--延时1000ms--结果1"
            }
            val async2 = async {
                delay(2000)
                "2--2000ms--结果2"
            }
            val async3 = async {
                delay(3000)
                "3--3000ms--结果3"
            }

//            async1.await()
//            async2.await()
//            async3.await()

            resultData.value = " ${async1.await()}  ${async2.await()} ${async3.await()} 总计用时: ${System.currentTimeMillis() - startTimeMillis}"
        }
    }

}